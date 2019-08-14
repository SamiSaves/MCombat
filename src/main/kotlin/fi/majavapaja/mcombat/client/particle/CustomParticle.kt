package fi.majavapaja.mcombat.client.particle

import fi.majavapaja.mcombat.common.util.SpriteCoordinates
import fi.majavapaja.mcombat.modId
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
import org.lwjgl.opengl.GL11

@SideOnly(Side.CLIENT)
abstract class CustomParticle(
    worldIn: World,
    xCoordIn: Double,
    yCoordIn: Double,
    zCoordIn: Double,
    protected var textureManager: TextureManager
): Particle(worldIn, xCoordIn, yCoordIn, zCoordIn, .0, .0, .0) {
  companion object {
    val spriteSheet = ResourceLocation("$modId:textures/particle/particles.png")
    const val spriteSheetWidth = 256.0
    const val spriteSheetHeight = 256.0
  }

  private val vertexFormat = VertexFormat().addElement(DefaultVertexFormats.POSITION_3F).addElement(DefaultVertexFormats.TEX_2F).addElement(DefaultVertexFormats.COLOR_4UB).addElement(DefaultVertexFormats.TEX_2S).addElement(DefaultVertexFormats.NORMAL_3B).addElement(DefaultVertexFormats.PADDING_1B)

  protected abstract var life: Int
  protected abstract var lifeTime: Int
  protected abstract var size: Float
  protected abstract var animPhases: Int
  protected abstract var textureId: Int
  protected abstract var spriteCoordinates: SpriteCoordinates

  protected var spriteWidth = 16.0
  protected var spriteHeight = 16.0

  init {
    height = 1f
  }

  // The rotation parameters are numbers that ensure that the 2D particle is always rendered towards the player.
  override fun renderParticle(buffer: BufferBuilder, entityIn: Entity?, partialTicks: Float, rotationX: Float, rotationZ: Float, rotationYZ: Float, rotationXY: Float, rotationXZ: Float) {
    val progress = (life + partialTicks) / lifeTime
    val currentPhase = (progress * animPhases).toInt()

    if(currentPhase < animPhases) {
      bindTexture()

      val halfSize = 0.5f * size
      val weirdX = (prevPosX + (posX - prevPosX) * partialTicks - interpPosX).toFloat()
      val weirdY = (prevPosY + (posY - prevPosY) * partialTicks - interpPosY).toFloat()
      val weirdZ = (prevPosZ + (posZ - prevPosZ) * partialTicks - interpPosZ).toFloat()

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
      if (shouldDisableDepth()) GlStateManager.disableDepth()
      RenderHelper.disableStandardItemLighting()
      buffer.begin(GL11.GL_QUADS, vertexFormat)
      // the buffer.pos needs to be called 4 times
      // The .tex seems to be the texture x and y in the texture image.
      buffer.pos(weirdX1, weirdY1, weirdZ1)
          .tex(spriteCoordinates.right, spriteCoordinates.bottom)
          .color(particleRed, particleGreen, particleBlue, particleAlpha)
          .lightmap(0, 240)
          .normal(0.0F, 1.0F, 0.0F)
          .endVertex()
      buffer.pos(weirdX2, weirdY2, weirdZ2)
          .tex(spriteCoordinates.right, spriteCoordinates.top)
          .color(particleRed, particleGreen, particleBlue, particleAlpha)
          .lightmap(0, 240)
          .normal(0.0f, 1.0f, 0.0f)
          .endVertex()
      buffer.pos(weirdX3, weirdY3, weirdZ3)
          .tex(spriteCoordinates.left, spriteCoordinates.top)
          .color(particleRed, particleGreen, particleBlue, particleAlpha)
          .lightmap(0, 240)
          .normal(0.0f, 1.0f, 0.0f)
          .endVertex()
      buffer.pos(weirdX4, weirdY4, weirdZ4)
          .tex(spriteCoordinates.left, spriteCoordinates.bottom)
          .color(particleRed, particleGreen, particleBlue, particleAlpha)
          .lightmap(0, 240)
          .normal(0.0f, 1.0f, 0.0f)
          .endVertex()
      Tessellator.getInstance().draw()
      if (shouldDisableDepth()) GlStateManager.enableLighting()
      GlStateManager.enableDepth()
    }
  }

  open fun bindTexture() {
    textureManager.bindTexture(spriteSheet)
  }

  override fun onUpdate() {
    prevPosX = posX
    prevPosY = posY
    prevPosZ = posZ
    ++life

    if (life == lifeTime) {
      setExpired()
    }
  }

  override fun getFXLayer(): Int = 3
}
