package fi.majavapaja.mcombat.common.item

import fi.majavapaja.mcombat.Main
import net.minecraft.item.Item

class ItemBase(val name: String): Item() {
  init {
    setRegistryName(name)
    unlocalizedName = name
    creativeTab = Main.creativeTab
  }

  fun registerItemModel() {
    Main.proxy.registerItemRenderer(this, 0 , name)
  }
}
