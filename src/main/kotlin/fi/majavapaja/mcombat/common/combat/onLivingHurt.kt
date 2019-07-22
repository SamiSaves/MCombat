package fi.majavapaja.mcombat.common.combat

import fi.majavapaja.mcombat.common.item.ModItems.isMinecraftItem
import fi.majavapaja.mcombat.common.item.minecraft.getMinecraftArmorPoints
import fi.majavapaja.mcombat.common.item.minecraft.getToolDamage
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.item.ItemArmor
import net.minecraft.item.ItemStack
import net.minecraft.util.DamageSource
import net.minecraftforge.event.entity.living.LivingHurtEvent

val ignoredDamageSources = listOf(
  DamageSource.FALL,
  DamageSource.DROWN,
  DamageSource.OUT_OF_WORLD
)

fun onLivingHurtEvent(event: LivingHurtEvent) {
  if (ignoredDamageSources.contains(event.source)) return

  event.isCanceled = true

  val entity = event.entity as EntityLivingBase
  val damage = getDamage(event.source.trueSource, event.source.immediateSource)
  val armorPoints = getArmorPoints(entity)
  var damageAmount = 0f

  for ((damageType, amount) in damage) {
    damageAmount += amount - ((armorPoints[damageType] ?: 0f) / 10)
  }

  damageAmount = if (damageAmount < 0f) {
    0f
  } else {
    damageAmount
  }

  entity.health -= damageAmount
  println("Someone was hit with $damage for $damageAmount points of damage. Someone had $armorPoints resistance")
}

private fun getDamage(trueSource: Entity?, immediateSource: Entity?): HashMap<DamageType, Float> =
  if (immediateSource is EntityArrow) {
    // TODO Get arrow damage type
    hashMapOf(DamageType.Normal to 4f)
  } else if (trueSource is EntityLivingBase) {
    val weapon = trueSource.heldItemMainhand

    if (!weapon.isEmpty) {
      if (isMinecraftItem(weapon.item)) {
        getToolDamage(weapon.item)
      } else {
        hashMapOf(DamageType.Normal to 2f)
      }
    } else {
      hashMapOf(DamageType.Normal to 2f)
    }
  } else {
    hashMapOf(DamageType.Normal to 2f)
  }

private fun getArmorPoints(entity: EntityLivingBase): HashMap<DamageType, Float> {
  val armorPoints = HashMap<DamageType, Float>()

  // TODO Get entity natural armor points

  entity.armorInventoryList.map { addArmorPoints(it, armorPoints) }

  return armorPoints
}

private fun addArmorPoints(itemStack: ItemStack, armorPoints: HashMap<DamageType, Float>) {
  if (itemStack.isEmpty || itemStack.item !is ItemArmor) return
  val armorPiecePoints = getArmorPiecePoints(itemStack.item as ItemArmor)

  for ((damageType, amount) in armorPiecePoints) {
    armorPoints.merge(damageType, amount) { f1, f2 -> f1 + f2 }
  }
}

private fun getArmorPiecePoints(armor: ItemArmor) =
  if (isMinecraftItem(armor)) {
    getMinecraftArmorPoints(armor)
  } else {
    hashMapOf(DamageType.Normal to 0f)
  }

