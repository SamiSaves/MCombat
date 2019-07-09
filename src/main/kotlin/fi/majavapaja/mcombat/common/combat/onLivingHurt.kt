package fi.majavapaja.mcombat.common.combat

import net.minecraft.entity.EntityLivingBase
import net.minecraftforge.event.entity.living.LivingHurtEvent

fun onLivingHurtEvent(event: LivingHurtEvent) {
  event.isCanceled = true
  val entity = event.entity as EntityLivingBase

  if (event.source.trueSource is EntityLivingBase) {
    val trueSource = event.source.trueSource as EntityLivingBase
    val weapon = trueSource.heldItemMainhand
    var damage = 0f
    var damageType: DamageType?

    if (!weapon.isEmpty) {
      damageType = DamageTypeProvider.getDamageType(weapon)
      damage = 10f
    } else {
      damageType = DamageTypeProvider.getDamageType(trueSource)
      damage = 6f
    }

    val resistance = DamageResistanceProvider.getDamageResistance(entity)
    if (resistance?.type == damageType?.type) {
      damage += (resistance?.amount ?: 0f) * damage
      damage = if (damage < 0f) {
        0f
      } else {
        damage
      }
    }

    entity.health -= damage
    println("Someone was hit with ${damageType?.type} for $damage points of damage. Someone had $resistance resistance")
    return
  }

  entity.health -= 2f
}
