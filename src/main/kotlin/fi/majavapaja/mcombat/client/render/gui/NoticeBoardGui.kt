package fi.majavapaja.mcombat.client.render.gui

import fi.majavapaja.mcombat.CommonProxy
import fi.majavapaja.mcombat.common.message.HuntMessage
import fi.majavapaja.mcombat.modId
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiButton
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

  override fun initGui() {
    val buttonWidth = 100
    val buttonHeight = 20
    val buttonX = center(width, buttonWidth)
    val buttonY = center(height, center(textureHeight, buttonHeight))

    addButton(Button(0, buttonX, buttonY - 20, buttonWidth, buttonHeight, "Start Quest") {
      CommonProxy.network.sendToServer(HuntMessage(
          "mcombat:hunt/hunt",
          "start_quest"
      ))
    })

    addButton(Button(1, buttonX, buttonY + 20, buttonWidth, buttonHeight, "Finish Quest") {
      CommonProxy.network.sendToServer(HuntMessage(
          "mcombat:hunt/hunt",
          "finish_quest"
      ))
    })
  }

  override fun actionPerformed(button: GuiButton) {
    if (button is Button) button.action()
  }

  private fun center (size: Int, textureSize: Int): Int = (size / 2 - textureSize / 2)

  override fun doesGuiPauseGame(): Boolean = false
}
