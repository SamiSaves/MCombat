package fi.majavapaja.mcombat.client.particle

import net.minecraft.client.Minecraft
import net.minecraft.client.particle.Particle
import net.minecraft.client.renderer.BufferBuilder
import net.minecraft.client.renderer.GlStateManager
import net.minecraft.entity.Entity
import net.minecraft.util.math.MathHelper
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@SideOnly(Side.CLIENT)
class DamageParticle(worldIn: World, x: Double, y: Double, z: Double, private val damage: Float): Particle(worldIn, x, y, z, .0, .0, .0) {
  var life = 0
  var lifeTime = 16

  override fun renderParticle(buffer: BufferBuilder, entityIn: Entity?, partialTicks: Float, rotationX: Float, rotationZ: Float, rotationYZ: Float, rotationXY: Float, rotationXZ: Float) {
    val weirdX = (prevPosX + (posX - prevPosX) * partialTicks - interpPosX).toFloat()
    val weirdY = (prevPosY + (posY - prevPosY) * partialTicks - interpPosY).toFloat()
    val weirdZ = (prevPosZ + (posZ - prevPosZ) * partialTicks - interpPosZ).toFloat()

    GlStateManager.pushMatrix()
    GlStateManager.translate(weirdX, weirdY, weirdZ)
    GlStateManager.rotate(-Minecraft.getMinecraft().player.rotationYaw, 0f, 1f, 0f)
    GlStateManager.rotate(Minecraft.getMinecraft().player.rotationPitch, 1f, 0f, 0f)
    GlStateManager.scale(-.05f, -.05f, .05f)

    val fontRenderer = Minecraft.getMinecraft().fontRenderer
    val text = damage.toInt().toString()
    fontRenderer.drawStringWithShadow(
      text,
      -MathHelper.floor(fontRenderer.getStringWidth(text) / 2f) + 1f,
      -MathHelper.floor(fontRenderer.FONT_HEIGHT / 2f) + 1f,
      0xFFFFFF
    )

    GlStateManager.popMatrix()
  }

  override fun getFXLayer(): Int = 1

  override fun onUpdate() {
    if (++life == lifeTime) {
      setExpired()
    }
  }
}
