package fi.majavapaja.mcombat.common.item

import fi.majavapaja.mcombat.Main
import net.minecraft.item.ItemArrow
import net.minecraft.item.ItemBow

class ArrowBase(private val name: String): ItemArrow() {
  init {
    setRegistryName(name)
    unlocalizedName = name
    creativeTab = Main.creativeTab
  }

  fun registerItemModel() {
    Main.proxy.registerItemRenderer(this, 0 , name)
  }
}
