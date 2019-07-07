package fi.majavapaja.mcombat.common.combat

import net.minecraftforge.event.entity.living.LivingHurtEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Suppress("unused")
object Damage {
  @SubscribeEvent(priority = EventPriority.LOWEST)
  fun onLivingHurt(event: LivingHurtEvent) = onLivingHurtEvent(event)
}