package fi.majavapaja.mcombat.common.enchantment

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnumEnchantmentType
import net.minecraft.inventory.EntityEquipmentSlot

class EnchantmentBase(
    name: String,
    rarity: Rarity,
    type: EnumEnchantmentType,
    equipmentSlots: Array<EntityEquipmentSlot>,
    private val minimumLevel: Int = 1,
    private val maximumLevel: Int = 1
): Enchantment(rarity, type, equipmentSlots) {
  init {
    setRegistryName(name)
    setName(name)
  }

  override fun getMinLevel(): Int {
    return minimumLevel
  }

  override fun getMaxLevel(): Int {
    return maximumLevel
  }
}