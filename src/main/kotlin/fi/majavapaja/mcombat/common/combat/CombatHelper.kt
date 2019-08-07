package fi.majavapaja.mcombat.common.combat

import fi.majavapaja.mcombat.common.entity.ICustomMob
import fi.majavapaja.mcombat.common.entity.minecraft.getMonsterArmor
import fi.majavapaja.mcombat.common.entity.minecraft.isMinecraftMonster
import fi.majavapaja.mcombat.common.item.minecraft.getAsArmor
import net.minecraft.entity.EntityLivingBase

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
        entity is ICustomMob -> mergeHashMap(currentArmor, entity.armor)
        isMinecraftMonster(entity) -> mergeHashMap(currentArmor, getMonsterArmor(entity))
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
      val armor = getAsArmor(it.item) ?: return@forEach

      newTotalArmor = mergeHashMap(armor.armor, newTotalArmor)
    }

    return newTotalArmor
  }

  private fun mergeHashMap (map1: HashMap<DamageType, Float>, map2: HashMap<DamageType, Float>): HashMap<DamageType, Float> {
    val newMap = HashMap<DamageType, Float>()
    newMap.putAll(map1)

    for ((damageType, amount) in map2) {
      newMap[damageType] = amount + (newMap[damageType] ?: 0f)
    }

    return newMap
  }
}