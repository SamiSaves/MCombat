package fi.majavapaja.mcombat.client.particle

import net.minecraft.client.particle.Particle
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.client.renderer.Tessellator
import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.client.renderer.vertex.DefaultVertexFormats
import net.minecraft.client.renderer.vertex.VertexFormat
import net.minecraft.entity.Entity
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import kotlin.random.Random

@SideOnly(Side.CLIENT)
abstract class AttackParticle(
    worldIn: World,
    xCoordIn: Double,
    yCoordIn: Double,
    zCoordIn: Double,
    xSpeedIn: Double,
    ySpeedIn: Double,
    zSpeedIn: Double,
    private var textureManager: TextureManager
): Particle(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn) {
  private var life = 0
  private var lifeTime = 16
  private var size = .25f
  private var animPhases: Int = 16
  protected abstract val texture: ResourceLocation
  private val vertexFormat = VertexFormat().addElement(DefaultVertexFormats.POSITION_3F).addElement(DefaultVertexFormats.TEX_2F).addElement(DefaultVertexFormats.COLOR_4UB).addElement(DefaultVertexFormats.TEX_2S).addElement(DefaultVertexFormats.NORMAL_3B).addElement(DefaultVertexFormats.PADDING_1B)

  init {
    height = 1f
    setRandomDirection()
    this.motionY = .16

    size -= Random.nextDouble(0.1).toFloat()
  }

  // The rotation parameters are numbers that ensure that the 2D particle is always rendered towards the player.
  override fun renderParticle(buffer: BufferBuilder, entityIn: Entity?, partialTicks: Float, rotationX: Float, rotationZ: Float, rotationYZ: Float, rotationXY: Float, rotationXZ: Float) {
    val progress = (life + partialTicks) / lifeTime
    val currentPhase = (progress * animPhases).toInt()

    if(currentPhase < animPhases) {
      this.textureManager.bindTexture(texture)

      val halfSize = 0.5f * this.size
      val weirdX = (this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX).toFloat()
      val weirdY = (this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY).toFloat()
      val weirdZ = (this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ).toFloat()

      val rotX = rotationX * halfSize
      val rotXY = rotationXY * halfSize

      val weirdX1 = (weirdX - rotX - rotXY).toDouble()
      val weirdX2 = (weirdX - rotX + rotXY).toDouble()
      val weirdX3 = (weirdX + rotX + rotXY).toDouble()
      val weirdX4 = (weirdX + rotX - rotXY).toDouble()

      val rotZ = rotationZ * halfSize * height

      val weirdY1 = (weirdY - rotZ).toDouble()
      val weirdY2 = (weirdY + rotZ).toDouble()
      val weirdY3 = (weirdY + rotZ).toDouble()
      val weirdY4 = (weirdY - rotZ).toDouble()

      val rotYZ = rotationYZ * halfSize
      val rotXZ = rotationXZ * halfSize

      val weirdZ1 = (weirdZ - rotYZ - rotXZ).toDouble()
      val weirdZ2 = (weirdZ - rotYZ + rotXZ).toDouble()
      val weirdZ3 = (weirdZ + rotYZ + rotXZ).toDouble()
      val weirdZ4 = (weirdZ + rotYZ - rotXZ).toDouble()

      GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
      GlStateManager.disableLighting()
      RenderHelper.disableStandardItemLighting()
      buffer.begin(7, vertexFormat)
      // the buffer.pos needs to be called 4 times
      // The .tex seems to be the texture x and y in the texture image.
      // So this would mean that if the texture was a sprite sheet we could easily render one sprite from it
      buffer.pos(weirdX1, weirdY1, weirdZ1)
          .tex(1.0, 1.0)
          .color(particleRed, this.particleGreen, this.particleBlue, particleAlpha)
          .lightmap(0, 240)
          .normal(0.0F, 1.0F, 0.0F)
          .endVertex()
      buffer.pos(weirdX2, weirdY2, weirdZ2)
          .tex(1.0, .0)
          .color(particleRed, particleGreen, particleBlue, particleAlpha)
          .lightmap(0, 240)
          .normal(0.0f, 1.0f, 0.0f)
          .endVertex()
      buffer.pos(weirdX3, weirdY3, weirdZ3)
          .tex(.0, .0)
          .color(particleRed, particleGreen, particleBlue, particleAlpha)
          .lightmap(0, 240)
          .normal(0.0f, 1.0f, 0.0f)
          .endVertex()
      buffer.pos(weirdX4, weirdY4, weirdZ4)
          .tex(.0, 1.0)
          .color(particleRed, particleGreen, particleBlue, particleAlpha)
          .lightmap(0, 240)
          .normal(0.0f, 1.0f, 0.0f)
          .endVertex()
      Tessellator.getInstance().draw()
      GlStateManager.enableLighting()
    }
  }

  override fun onUpdate() {
    prevPosX = posX
    prevPosY = posY
    prevPosZ = posZ
    ++life

    move(motionX, motionY, motionZ)

    motionY -= when {
      motionY < -.08 -> .04
      else -> .0
    }
    motionX = slowMotionDown(motionX)
    motionZ = slowMotionDown(motionZ)

    if (life == lifeTime) {
      setExpired()
    }
  }

  override fun getFXLayer(): Int = 3

  private fun setRandomDirection() {
    motionX = arrayOf(.1, -.1, .0).random()
    motionZ = arrayOf(.1, -.1, .0).random()
  }

  private fun slowMotionDown(motion: Double): Double =
      when {
        motion > 0 -> {
          var newMotion = motion - .02
          if (newMotion < 0) newMotion = .0
          newMotion
        }
        motion < 0 -> {
          var newMotion = motion + .02
          if (newMotion > 0) newMotion = .0
          newMotion
        }
        else -> motion
      }
}
