package fi.majavapaja.mcombat.common.enchantment

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnumEnchantmentType
import net.minecraft.inventory.EntityEquipmentSlot

class EnchantmentBase(
    name: String,
    rarity: Rarity,
    type: EnumEnchantmentType,
    equipmentSlots: Array<EntityEquipmentSlot>
): Enchantment(rarity, type, equipmentSlots) {
  init {
    setRegistryName(name)
    setName(name)
  }
}