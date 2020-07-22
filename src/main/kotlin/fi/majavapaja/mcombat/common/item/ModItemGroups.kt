package fi.majavapaja.mcombat.common.item

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.common.block.ModBlocks
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack

object ModItemGroups {
  object DEFAULT: ItemGroup("${Main.MOD_ID}.default") {
    override fun createIcon(): ItemStack = ItemStack(ModBlocks.majavaBlock.item)
  }
}
