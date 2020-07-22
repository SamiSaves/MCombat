package fi.majavapaja.mcombat.common.item.base

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.common.item.ModItemGroups
import net.minecraft.item.Item

open class Item(name: String): Item(Properties().group(ModItemGroups.DEFAULT)) {
  init {
    setRegistryName(Main.MOD_ID, name)
  }
}
