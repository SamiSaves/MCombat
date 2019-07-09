package fi.majavapaja.mcombat.common.combat

import net.minecraft.entity.EntityLivingBase
import net.minecraftforge.event.entity.living.LivingHurtEvent

fun onLivingHurtEvent(event: LivingHurtEvent) {
  event.isCanceled = true
  val entity = event.entity as EntityLivingBase

  if (event.source.trueSource is EntityLivingBase) {
    val trueSource = event.source.trueSource as EntityLivingBase
    val weapon = trueSource.heldItemMainhand

    if (!weapon.isEmpty) {
      val damageType = DamageTypeProvider.getDamageType(weapon)
      if (damageType?.type == "holy") {
        println("Damage was made with a holy weapon!")
        entity.health -= 10f
        return
      }
    }

    val damageType = DamageTypeProvider.getDamageType(trueSource)

    println("Someone was hit with ${damageType?.type}")

    if (damageType?.type == "rotten") {
      println("Damage was rotten!")
      entity.health -= 6f
      return
    }
  }

  entity.health -= 2f
}
