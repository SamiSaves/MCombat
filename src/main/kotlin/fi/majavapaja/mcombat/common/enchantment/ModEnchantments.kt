package fi.majavapaja.mcombat.common.enchantment

import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnumEnchantmentType
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraftforge.fml.common.registry.ForgeRegistries

object ModEnchantments {
  val HolyDamage = EnchantmentBase(
      "holy_damage",
      Enchantment.Rarity.COMMON,
      EnumEnchantmentType.WEAPON,
      arrayOf(EntityEquipmentSlot.MAINHAND)
  )
  val RottenResistance = EnchantmentBase(
      "rotten_resistance",
      Enchantment.Rarity.COMMON,
      EnumEnchantmentType.ARMOR,
      arrayOf(
          EntityEquipmentSlot.FEET,
          EntityEquipmentSlot.HEAD,
          EntityEquipmentSlot.LEGS,
          EntityEquipmentSlot.CHEST
      ),
      1,
      5
  )

  fun register() {
    ForgeRegistries.ENCHANTMENTS.registerAll(HolyDamage, RottenResistance)
  }
}