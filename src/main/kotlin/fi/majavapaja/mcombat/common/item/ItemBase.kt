package fi.majavapaja.mcombat.common.item

import fi.majavapaja.mcombat.Main
import net.minecraft.item.Item

@Suppress("unused")
class ItemBase: Item {
  var name: String

  constructor(name: String) {
    this.name = name
    setRegistryName(name)
    unlocalizedName = name
    creativeTab = Main.creativeTab
  }

  fun registerItemModel() {
    Main.proxy.registerItemRenderer(this, 0 , name)
  }
}
