package fi.majavapaja.mcombat.common.combat

import net.minecraft.entity.EntityLivingBase
import net.minecraftforge.event.entity.living.LivingHurtEvent

fun onLivingHurtEvent(event: LivingHurtEvent) {
  event.isCanceled = true
  val entity = event.entity as EntityLivingBase

  if (event.source.trueSource is EntityLivingBase) {
    val trueSource = event.source.trueSource as EntityLivingBase
    val weapon = trueSource.heldItemMainhand
    var damage: Float
    var damageType: DamageType?

    if (!weapon.isEmpty) {
      damageType = DamageTypeProvider.getDamageType(weapon)
      damage = 10f
    } else {
      damageType = DamageTypeProvider.getDamageType(trueSource)
      damage = 6f
    }

    var resistanceAmount = 0f

    entity.armorInventoryList
        .filter { DamageResistanceProvider.getDamageResistance(it)?.type == damageType?.type }
        .map { resistanceAmount += DamageResistanceProvider.getDamageResistance(it)?.amount ?: 0f }

    val naturalResistance = DamageResistanceProvider.getDamageResistance(entity)
    if (naturalResistance?.type == damageType?.type) {
      resistanceAmount += naturalResistance?.amount ?: 0f
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
