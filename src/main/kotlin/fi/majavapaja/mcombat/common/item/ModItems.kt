package fi.majavapaja.mcombat.common.item

import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.common.item.base.*
import fi.majavapaja.mcombat.common.item.material.DebugMaterial
import fi.majavapaja.mcombat.common.item.base.Item as ItemBase
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemTier
import net.minecraftforge.event.RegistryEvent

object ModItems {
  val majavapaja = ItemBase("majavapaja")
  val debugSword = Sword("debug_sword", ItemTier.WOOD, hashMapOf(DamageType.Fire to 10f, DamageType.Normal to 5f))
  val debugChestplate = Armor("debug_chestplate", DebugMaterial, EquipmentSlotType.CHEST, hashMapOf(DamageType.Earth to 2f))
  val debugLeggings = Armor("debug_leggings", DebugMaterial, EquipmentSlotType.LEGS, hashMapOf(DamageType.Earth to 2f))
  val debugBoots = Armor("debug_boots", DebugMaterial, EquipmentSlotType.FEET, hashMapOf(DamageType.Earth to 2f))
  val debugHelmet = Armor("debug_helmet", DebugMaterial, EquipmentSlotType.HEAD, hashMapOf(DamageType.Earth to 2f))

  val debugBow = Bow("debug_bow", hashMapOf(DamageType.Magic to 4f))
  val debugArrow = Arrow("debug_arrow", hashMapOf(DamageType.Air to 2f))

  private val allDebugItems = arrayOf(
      debugSword,
      debugChestplate,
      debugLeggings,
      debugBoots,
      debugHelmet,
      debugBow,
      debugArrow
  )

  fun registerItems(event: RegistryEvent.Register<Item>) {
    event.registry.registerAll(
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

  // TODO: There was a custom tooltip method here

  fun isDebugItem(stack: ItemStack): Boolean =
    allDebugItems.contains(stack.item)
}