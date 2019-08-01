package fi.majavapaja.mcombat.common.item

import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.common.item.base.Armor
import fi.majavapaja.mcombat.common.item.base.Arrow
import fi.majavapaja.mcombat.common.item.base.Bow
import fi.majavapaja.mcombat.common.item.base.Sword
import fi.majavapaja.mcombat.common.item.base.Item as ItemBase
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.Item
import net.minecraftforge.registries.IForgeRegistry

object ModItems {
  val majavapaja = ItemBase("majavapaja")
  val debugSword = Sword("debug_sword", Item.ToolMaterial.WOOD, hashMapOf(DamageType.Earth to 10f, DamageType.Air to 1f, DamageType.Magic to 1f))
  val debugChestplate = Armor("debug_chestplate", Armor.debugMaterial, EntityEquipmentSlot.CHEST, hashMapOf(DamageType.Air to 2f))
  val debugLeggings = Armor("debug_leggings", Armor.debugMaterial, EntityEquipmentSlot.LEGS, hashMapOf(DamageType.Air to 2f))
  val debugBoots = Armor("debug_boots", Armor.debugMaterial, EntityEquipmentSlot.FEET, hashMapOf(DamageType.Air to 2f))
  val debugHelmet = Armor("debug_helmet", Armor.debugMaterial, EntityEquipmentSlot.HEAD, hashMapOf(DamageType.Air to 2f))

  val debugBow = Bow("debug_bow", hashMapOf(DamageType.Earth to 4f))
  val debugArrow = Arrow("debug_arrow", hashMapOf(DamageType.Earth to 1f))

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

  fun isMinecraftItem(item: Item): Boolean = item.registryName.toString().startsWith("minecraft:")
}