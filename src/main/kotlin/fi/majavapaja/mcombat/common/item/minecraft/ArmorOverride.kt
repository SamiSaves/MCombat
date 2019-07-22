package fi.majavapaja.mcombat.common.item.minecraft

import fi.majavapaja.mcombat.common.combat.DamageType
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.*
import net.minecraft.item.ItemArmor.ArmorMaterial

private val armorValues = hashMapOf(
    ArmorMaterial.LEATHER   to 20f,
    ArmorMaterial.CHAIN     to 40f,
    ArmorMaterial.GOLD      to 80f,
    ArmorMaterial.IRON      to 60f,
    ArmorMaterial.DIAMOND   to 100f
)

private val slotModifiers = hashMapOf(
    EntityEquipmentSlot.CHEST to 0.4f,
    EntityEquipmentSlot.LEGS  to 0.3f,
    EntityEquipmentSlot.FEET  to 0.1f,
    EntityEquipmentSlot.HEAD  to 0.2f
)

fun getMinecraftArmorPoints (armor: ItemArmor): HashMap<DamageType, Float> {
  val armorValue = armorValues[armor.armorMaterial] ?: 0f
  val slotModifier = slotModifiers[armor.equipmentSlot] ?: 0f

  return hashMapOf(
      DamageType.Normal to (armorValue * slotModifier)
  )
}
