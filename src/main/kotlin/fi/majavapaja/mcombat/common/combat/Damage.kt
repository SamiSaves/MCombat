package fi.majavapaja.mcombat.common.combat

import net.minecraft.entity.EntityLivingBase
import net.minecraftforge.event.entity.living.LivingHurtEvent
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Suppress("unused")
object Damage {
  @SubscribeEvent(priority = EventPriority.LOWEST)
  fun onLivingHurt(event: LivingHurtEvent) {
    val entity = event.entity as EntityLivingBase
    entity.health = entity.health - 2f

    event.isCanceled = true
  }
}