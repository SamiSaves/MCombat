package fi.majavapaja.mcombat.common.item

import net.minecraft.item.Item
import net.minecraftforge.registries.IForgeRegistry

object ModItems {
  val majavapaja: ItemBase = ItemBase("majavapaja")
  private val debugSword: ItemSword = ItemSword("debug_sword", Item.ToolMaterial.WOOD)

  fun register(registry: IForgeRegistry<Item>) {
    registry.registerAll(majavapaja, debugSword)
  }

  fun registerModels() {
    majavapaja.registerItemModel()
    debugSword.registerItemModel()
  }
}