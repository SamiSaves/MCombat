package fi.majavapaja.mcombat.common.combat

import fi.majavapaja.mcombat.common.entity.ICustomMob
import fi.majavapaja.mcombat.common.entity.minecraft.getMonsterArmor
import fi.majavapaja.mcombat.common.entity.minecraft.isMinecraftMonster
import fi.majavapaja.mcombat.common.item.ModItems.isMinecraftItem
import fi.majavapaja.mcombat.common.item.minecraft.getMinecraftArmorPoints
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemArmor

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
      if (it.isEmpty || it.item !is ItemArmor) return@forEach

      val armorPiecePoints = getArmorPiecePoints(it.item as ItemArmor)
      newTotalArmor = mergeHashMap(armorPiecePoints, newTotalArmor)
    }

    return newTotalArmor
  }

  private fun getArmorPiecePoints(armor: ItemArmor) =
      when {
        isMinecraftItem(armor) -> getMinecraftArmorPoints(armor)
        else -> hashMapOf(DamageType.Normal to 0f)
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