package fi.majavapaja.mcombat.common.combat

import fi.majavapaja.mcombat.common.entity.ICustomMob
import fi.majavapaja.mcombat.common.entity.minecraft.getMonsterArmor
import fi.majavapaja.mcombat.common.entity.minecraft.isMinecraftMonster
import fi.majavapaja.mcombat.common.item.base.Bow
import fi.majavapaja.mcombat.common.item.minecraft.getAsArmor
import fi.majavapaja.mcombat.common.item.minecraft.getAsWeapon
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack

object CombatHelper {
  fun getArmorPoints (entity: EntityLivingBase): HashMap<DamageType, Float> {
    val entityArmorPoints = addNaturalEntityArmor(entity)
    return addEntityEquipmentArmor(entity, entityArmorPoints)
  }

  private fun addNaturalEntityArmor(
      entity: EntityLivingBase,
      currentArmor: HashMap<DamageType, Float> = HashMap()
  ): HashMap<DamageType, Float> =
      when {
        entity is ICustomMob -> mergeDamageMap(currentArmor, entity.armor)
        isMinecraftMonster(entity) -> mergeDamageMap(currentArmor, getMonsterArmor(entity))
        else -> currentArmor
      }

  private fun addEntityEquipmentArmor(
      entity: EntityLivingBase,
      currentArmor: HashMap<DamageType, Float> = HashMap()
  ): HashMap<DamageType, Float> {
    var newTotalArmor = HashMap<DamageType, Float>()
    newTotalArmor.putAll(currentArmor)

    entity.armorInventoryList.forEach {
      if (it.isEmpty) return@forEach
      val armorStats = getItemArmorStat(it)
      if (armorStats.isEmpty()) return@forEach

      newTotalArmor = mergeDamageMap(armorStats, newTotalArmor)
    }

    return newTotalArmor
  }

  fun mergeDamageMap(map1: Map<DamageType, Float>, map2: Map<DamageType, Float>): HashMap<DamageType, Float> {
    val newMap = HashMap<DamageType, Float>()
    newMap.putAll(map1)

    for ((damageType, amount) in map2) {
      newMap[damageType] = amount + (newMap[damageType] ?: 0f)
    }

    return newMap
  }

  fun getItemArmorStat(stack: ItemStack): Map<DamageType, Float> {
    // If the item has stat overrides those override everything else
    val overrides = StatOverridesCapability.getStatOverrides(stack)
    if (overrides != null && !overrides.resistance.isEmpty()) {
      return overrides.resistance
    }

    return when (val armor = getAsArmor(stack.item)) {
      null -> emptyMap()
      else -> armor.armor
    }
  }

  fun getItemDamageStat(stack: ItemStack): Map<DamageType, Float> {
    // If the item has stat overrides those override everything else
    val overrides = StatOverridesCapability.getStatOverrides(stack)
    if (overrides != null && !overrides.damage.isEmpty()) {
      return overrides.damage
    }

    return when (val weapon = getAsWeapon(stack.item)) {
      is Bow -> hashMapOf(DamageType.Normal to 2f)
      null -> hashMapOf(DamageType.Normal to 2f)
      else -> weapon.damage
    }
  }
}