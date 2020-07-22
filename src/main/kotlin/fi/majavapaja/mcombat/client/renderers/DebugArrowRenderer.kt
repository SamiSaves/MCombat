package fi.majavapaja.mcombat.client.renderers

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.common.entity.DebugArrowEntity
import net.minecraft.client.renderer.entity.*
import net.minecraft.util.ResourceLocation

class DebugArrowRenderer(manager: EntityRendererManager): ArrowRenderer<DebugArrowEntity>(manager) {
  private val location = ResourceLocation(Main.MOD_ID, "textures/entity/debug_arrow.png")

  override fun getEntityTexture(entity: DebugArrowEntity): ResourceLocation {
    return location
  }
}
