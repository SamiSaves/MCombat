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
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
abstract class AttackParticle(
    worldIn: World,
    xCoordIn: Double,
    yCoordIn: Double,
    zCoordIn: Double,
    xSpeedIn: Double,
    ySpeedIn: Double,
    zSpeedIn: Double,
    protected var textureManager: TextureManager
): Particle(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn) {
  protected var life = 0
  protected var lifeTime = 8
  protected var size = .25f
  protected var animPhases: Int = 8
  protected abstract val texture: ResourceLocation
  protected val vertexFormat: VertexFormat
    get() = VERTEX_FORMAT

  init {
    height = 1f
  }

  override fun renderParticle(worldRendererIn: BufferBuilder, entityIn: Entity?, partialTicks: Float, rotationX: Float, rotationZ: Float, rotationYZ: Float, rotationXY: Float, rotationXZ: Float) {
    val progress = (life + partialTicks) / lifeTime
    val currentPhase = (progress * animPhases).toInt()

    if(currentPhase < animPhases) {
      this.textureManager.bindTexture(texture)

      val halfSize = 0.5f * this.size
      val f5 = (this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX).toFloat()
      val f6 = (this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY).toFloat()
      val f7 = (this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ).toFloat()

      val rot1 = rotationX * halfSize
      val rot2 = rotationXY * halfSize

      val f17 = (f5 - rot1 - rot2).toDouble()
      val f8 =  (f5 - rot1 + rot2).toDouble()
      val f11 = (f5 + rot1 + rot2).toDouble()
      val f14 = (f5 + rot1 - rot2).toDouble()

      val rot3 = rotationZ * halfSize * height

      val f9 =  (f6 + rot3).toDouble()
      val f12 = (f6 + rot3).toDouble()
      val f15 = (f6 - rot3).toDouble()
      val f18 = (f6 - rot3).toDouble()

      val rot4 = rotationYZ * halfSize
      val rot5 = rotationXZ * halfSize

      val f10 = (f7 - rot4 + rot5).toDouble()
      val f13 = (f7 + rot4 + rot5).toDouble()
      val f16 = (f7 + rot4 - rot5).toDouble()
      val f19 = (f7 - rot4 - rot5).toDouble()

      GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f)
      GlStateManager.disableLighting()
      RenderHelper.disableStandardItemLighting()
      worldRendererIn.begin(7, vertexFormat)
      // the worldRendererIn.pos needs to be called 4 times
      // The .tex seems to be the texture x and y in the texture image.
      // So this would mean that if the texture was a sprite sheet we could easily render one sprite from it
      worldRendererIn.pos(f17, f18, f19)
          .tex(1.0, 1.0)
          .color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F)
          .lightmap(0, 240)
          .normal(0.0F, 1.0F, 0.0F)
          .endVertex()
      worldRendererIn.pos(f8, f9, f10)
          .tex(1.0, .0)
          .color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f)
          .lightmap(0, 240)
          .normal(0.0f, 1.0f, 0.0f)
          .endVertex()
      worldRendererIn.pos(f11, f12, f13)
          .tex(.0, .0)
          .color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f)
          .lightmap(0, 240)
          .normal(0.0f, 1.0f, 0.0f)
          .endVertex()
      worldRendererIn.pos(f14, f15, f16)
          .tex(.0, 1.0)
          .color(this.particleRed, this.particleGreen, this.particleBlue, 1.0f)
          .lightmap(0, 240)
          .normal(0.0f, 1.0f, 0.0f)
          .endVertex()
      Tessellator.getInstance().draw()
      GlStateManager.enableLighting()
    }
  }

  override fun getBrightnessForRender(p_189214_1_: Float): Int {
    return 61680
  }

  override fun onUpdate() {
    this.prevPosX = this.posX
    this.prevPosY = this.posY
    this.prevPosZ = this.posZ
    ++this.life

    if (this.life == this.lifeTime) {
      this.setExpired()
    }
  }

  override fun getFXLayer(): Int {
    return 3
  }

  companion object {
    val VERTEX_FORMAT = VertexFormat().addElement(DefaultVertexFormats.POSITION_3F).addElement(DefaultVertexFormats.TEX_2F).addElement(DefaultVertexFormats.COLOR_4UB).addElement(DefaultVertexFormats.TEX_2S).addElement(DefaultVertexFormats.NORMAL_3B).addElement(DefaultVertexFormats.PADDING_1B)
  }
}
