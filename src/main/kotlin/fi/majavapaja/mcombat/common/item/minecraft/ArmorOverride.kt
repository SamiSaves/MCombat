package fi.majavapaja.mcombat.common.item.minecraft

import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.common.item.base.IArmor
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.*

private val armorValues = hashMapOf(
    ArmorMaterial.LEATHER   to 20f,
    ArmorMaterial.CHAIN     to 40f,
    ArmorMaterial.GOLD      to 80f,
    ArmorMaterial.IRON      to 60f,
    ArmorMaterial.DIAMOND   to 100f
)

private val slotModifiers = hashMapOf(
    EquipmentSlotType.CHEST to 0.4f,
    EquipmentSlotType.LEGS  to 0.3f,
    EquipmentSlotType.FEET  to 0.1f,
    EquipmentSlotType.HEAD  to 0.2f
)

class MinecraftArmor(armorAmount: Float): IArmor {
  override var armor = hashMapOf(DamageType.Normal to armorAmount)
}

fun getAsArmor (item: Item): IArmor? {
  if (item is IArmor) return item
  if (item !is ArmorItem) return null

  val armorValue = armorValues[item.armorMaterial] ?: 0f
  val slotModifier = slotModifiers[item.equipmentSlot] ?: 0f

  return MinecraftArmor(armorValue * slotModifier)
}
