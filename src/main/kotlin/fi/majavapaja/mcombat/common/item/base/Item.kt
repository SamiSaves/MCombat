package fi.majavapaja.mcombat.common.item.base

import fi.majavapaja.mcombat.Main
import net.minecraft.item.Item

open class Item(val name: String): Item() {
  init {
    setRegistryName(name)
    unlocalizedName = name
    creativeTab = Main.creativeTab
  }

  fun registerItemModel() {
    Main.proxy.registerItemRenderer(this, 0 , name)
  }
}
