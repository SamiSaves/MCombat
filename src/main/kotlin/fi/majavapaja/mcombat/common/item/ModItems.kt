package fi.majavapaja.mcombat.common.item

import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.Item
import net.minecraftforge.registries.IForgeRegistry

object ModItems {
  val majavapaja = ItemBase("majavapaja")
  val debugSword = SwordBase("debug_sword", Item.ToolMaterial.WOOD)
  val debugChestplate = ArmorBase("debug_chestplate", ArmorBase.debugMaterial, EntityEquipmentSlot.CHEST)
  val debugLeggings = ArmorBase("debug_leggings", ArmorBase.debugMaterial, EntityEquipmentSlot.LEGS)
  val debugBoots = ArmorBase("debug_boots", ArmorBase.debugMaterial, EntityEquipmentSlot.FEET)
  val debugHelmet = ArmorBase("debug_helmet", ArmorBase.debugMaterial, EntityEquipmentSlot.HEAD)

  val debugBow = BowBase("debug_bow")
  val debugArrow = ArrowBase("debug_arrow")

  fun register(registry: IForgeRegistry<Item>) {
    registry.registerAll(
        majavapaja,
        debugSword,
        debugChestplate,
        debugLeggings,
        debugBoots,
        debugHelmet,
        debugBow,
        debugArrow
    )
  }

  fun registerModels() {
    majavapaja.registerItemModel()
    debugSword.registerItemModel()
    debugChestplate.registerItemModel()
    debugLeggings.registerItemModel()
    debugBoots.registerItemModel()
    debugHelmet.registerItemModel()
    debugBow.registerItemModel()
    debugArrow.registerItemModel()
  }

  fun isMinecraftItem(item: Item) = item.registryName.toString().startsWith("minecraft:")
}