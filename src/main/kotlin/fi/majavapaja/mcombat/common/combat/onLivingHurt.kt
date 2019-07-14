package fi.majavapaja.mcombat.common.combat

import fi.majavapaja.mcombat.common.effect.ModEffects
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.projectile.EntityArrow
import net.minecraftforge.event.entity.living.LivingHurtEvent

fun onLivingHurtEvent(event: LivingHurtEvent) {
  event.isCanceled = true
  val entity = event.entity as EntityLivingBase
  val damageType = getDamageType(event.source.trueSource, event.source.immediateSource)

  if (event.source.trueSource is EntityLivingBase) {
    var damage = 10f
    var resistanceAmount = 0f

    entity.armorInventoryList
        .filter { DamageResistanceProvider.getDamageResistance(it)?.type == damageType?.type }
        .map { resistanceAmount += DamageResistanceProvider.getDamageResistance(it)?.amount ?: 0f }

    val naturalResistance = DamageResistanceProvider.getDamageResistance(entity)
    if (naturalResistance?.type == damageType?.type) {
      resistanceAmount += naturalResistance?.amount ?: 0f
    }

    entity.activePotionEffects.map { println(it.effectName) }
    println(ModEffects.resistance.name)
    resistanceAmount = if (entity.activePotionEffects.find { it.effectName === ModEffects.resistance.name } != null) {
      -1f
    } else {
      resistanceAmount
    }

    damage += resistanceAmount * damage
    damage = if (damage < 0f) {
      0f
    } else {
      damage
    }

    entity.health -= damage
    println("Someone was hit with ${damageType?.type} for $damage points of damage. Someone had $resistanceAmount resistance")
    return
  }

  entity.health -= 2f
}

private fun getDamageType(trueSource: Entity?, immediateSource: Entity?): DamageType {
  var damageType: DamageType? = null

  if (immediateSource is EntityArrow) {
    damageType = DamageTypeProvider.getDamageType(immediateSource)
  } else if (trueSource is EntityLivingBase) {
    val weapon = trueSource.heldItemMainhand

    damageType = if (!weapon.isEmpty) {
      DamageTypeProvider.getDamageType(weapon)
    } else {
      DamageTypeProvider.getDamageType(trueSource)
    }
  }

  return damageType ?: DamageType()
}
