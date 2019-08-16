package fi.majavapaja.mcombat.common.block

import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

object ModBlocks {
  private val majavaBlock = MajavaBlock()

  fun registerBlocks() {
    majavaBlock.registerBlock()
  }

  @SideOnly(Side.CLIENT)
  fun registerModels() {
    majavaBlock.registerModel()
  }
}