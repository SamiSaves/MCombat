package fi.majavapaja.mcombat.common.block

import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent

object ModBlocks {
  val majavaBlock = MajavaBlock()

  fun registerBlocks(event: RegistryEvent.Register<Block>) {
    majavaBlock.registerBlock(event)
  }

  fun registerItems(event: RegistryEvent.Register<Item>) {
    majavaBlock.registerItem(event)
  }
}