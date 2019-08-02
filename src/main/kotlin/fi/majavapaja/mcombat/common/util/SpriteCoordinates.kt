package fi.majavapaja.mcombat.common.util

import kotlin.math.floor

class SpriteCoordinates(
    spriteId: Int,
    sheetWidth: Double,
    sheetHeight: Double,
    spriteWidth: Double,
    spriteHeight: Double
) {
  val left: Double
  val right: Double
  val top: Double
  val bottom: Double

  init {
    val spritesPerRow = sheetWidth / spriteWidth

    val row = floor(spriteId / spritesPerRow)
    val col = spriteId - (row * spritesPerRow)

    left = col * spriteWidth / sheetWidth
    right = left + (spriteWidth / sheetWidth)
    top = row * spriteHeight / sheetHeight
    bottom = top + (spriteHeight / sheetHeight)
  }
}