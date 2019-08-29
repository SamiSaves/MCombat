package fi.majavapaja.mcombat.client.render.gui

import net.minecraft.client.gui.GuiButton

class Button(
    buttonId: Int,
    x: Int,
    y: Int,
    widthIn: Int = 200,
    heightIn: Int = 20,
    buttonText: String,
    val action: () -> Unit
): GuiButton(buttonId, x, y, widthIn, heightIn, buttonText)