package fi.majavapaja.mcombat.common.combat

import net.minecraft.entity.EntityLivingBase
import net.minecraftforge.event.entity.living.LivingHurtEvent

fun onLivingHurtEvent(event: LivingHurtEvent) {
  val entity = event.entity as EntityLivingBase
  entity.health = entity.health - 2f

  event.isCanceled = true
}