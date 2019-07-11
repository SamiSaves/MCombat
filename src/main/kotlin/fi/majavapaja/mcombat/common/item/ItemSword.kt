package fi.majavapaja.mcombat.common.item

import fi.majavapaja.mcombat.Main
import net.minecraft.item.ItemSword

class ItemSword(val name: String, toolMaterial: ToolMaterial): ItemSword(toolMaterial) {
  init {
    setRegistryName(name)
    unlocalizedName = name
    creativeTab = Main.creativeTab
  }

  fun registerItemModel() {
    Main.proxy.registerItemRenderer(this, 0 , name)
  }
}
