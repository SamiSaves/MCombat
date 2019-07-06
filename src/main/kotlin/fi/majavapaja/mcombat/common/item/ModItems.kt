package fi.majavapaja.mcombat.common.item

import net.minecraft.item.Item
import net.minecraftforge.registries.IForgeRegistry

object ModItems {
  val majavapaja: ItemBase = ItemBase("majavapaja")

  fun register(registry: IForgeRegistry<Item>) {
    registry.registerAll(majavapaja)
  }

  fun registerModels() {
    majavapaja.registerItemModel()
  }
}