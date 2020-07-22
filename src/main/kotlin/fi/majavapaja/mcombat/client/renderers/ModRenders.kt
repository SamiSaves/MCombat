package fi.majavapaja.mcombat.client.renderers

import fi.majavapaja.mcombat.common.entity.DebugArrowEntity
import fi.majavapaja.mcombat.common.entity.FireZombie
import net.minecraftforge.fml.client.registry.RenderingRegistry

object ModRenders {
  fun registerRenders() {
    RenderingRegistry.registerEntityRenderingHandler(FireZombie.entityType, ::FireZombieRenderer)
    RenderingRegistry.registerEntityRenderingHandler(DebugArrowEntity.entityType, ::DebugArrowRenderer)
  }
}