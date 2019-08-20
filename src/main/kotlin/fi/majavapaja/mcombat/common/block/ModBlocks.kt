package fi.majavapaja.mcombat.common.block

import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object ModBlocks {
  private val majavaBlock = MajavaBlock()
  private val noticeBoard = NoticeBoard()

  fun registerBlocks() {
    majavaBlock.registerBlock()
    noticeBoard.registerBlock()
  }

  @SideOnly(Side.CLIENT)
  fun registerModels() {
    majavaBlock.registerModel()
    noticeBoard.registerModel()
  }
}