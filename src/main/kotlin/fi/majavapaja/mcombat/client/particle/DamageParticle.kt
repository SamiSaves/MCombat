package fi.majavapaja.mcombat.client.particle

import fi.majavapaja.mcombat.common.util.SpriteCoordinates
import fi.majavapaja.mcombat.modId
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.renderer.texture.DynamicTexture
import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import sun.font.FontFamily
import java.awt.Color
import java.awt.Font
import java.awt.image.BufferedImage

@SideOnly(Side.CLIENT)
class DamageParticle(
    worldIn: World,
    xCoordIn: Double,
    yCoordIn: Double,
    zCoordIn: Double,
    textureManager: TextureManager,
    private val damageAmount: Float
): CustomParticle(worldIn, xCoordIn, yCoordIn, zCoordIn, textureManager) {
  override var life = 0
  override var lifeTime = 16
  override var size = 0.75f
  override var animPhases = 16
  override var textureId = 0
  override var spriteCoordinates = SpriteCoordinates(textureId,1.0,1.0,1.0,1.0)

  private lateinit var textureLocation: ResourceLocation

  init {
    createTexture()
  }

  override fun bindTexture() {
    textureManager.bindTexture(textureLocation)
  }

  private fun createTexture() {
    val text = this.damageAmount.toInt().toString()

    val image = createImage(text)
    val graphics = image.createGraphics()
    val x = graphics.fontMetrics.stringWidth(text) / 2
    val y = graphics.fontMetrics.height / 2 + graphics.fontMetrics.ascent

    graphics.color = Color.BLACK
    graphics.drawString(text, x + 1, y + 1)
    graphics.color = Color.WHITE
    graphics.drawString(text, x, y)

    graphics.dispose()

    val dynamicTexture = DynamicTexture(image)
    textureLocation = ResourceLocation("$modId:dynamic/damage/$text")
    textureManager.loadTexture(textureLocation, dynamicTexture)
  }

  private fun createImage(text: String): BufferedImage {
    val image = BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB)
    val graphics = image.createGraphics()
    val metrics = graphics.fontMetrics
    val width = metrics.stringWidth(text) + 1
    val height = metrics.height + metrics.ascent + 1
    val size = when {
      width > height -> width
      else -> height
    }

    graphics.dispose()

    return BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB)
  }
}
