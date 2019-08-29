package fi.majavapaja.mcombat.client.render

import fi.majavapaja.mcombat.modId
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiScreen
import net.minecraft.util.ResourceLocation

class NoticeBoardGui: GuiScreen() {
  companion object {
    private const val textureWidth = 256
    private const val textureHeight = 156

    private val texture = ResourceLocation(modId, "textures/gui/notice_board_gui.png")
  }

  override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
    drawDefaultBackground()
    Minecraft.getMinecraft().renderEngine.bindTexture(texture)
    drawTexturedModalRect(
        center(width, textureWidth),
        center(height, textureHeight),
        0,
        0,
        textureWidth,
        textureHeight
    )
    super.drawScreen(mouseX, mouseY, partialTicks)
  }

  private fun center (size: Int, textureSize: Int): Int = (size / 2 - textureSize / 2)

  override fun doesGuiPauseGame(): Boolean = true
}
