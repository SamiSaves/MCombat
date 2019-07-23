package fi.majavapaja.mcombat.client.render.projectile

import fi.majavapaja.mcombat.common.entity.DebugArrowEntity
import net.minecraftforge.fml.client.registry.IRenderFactory
import net.minecraft.util.ResourceLocation
import fi.majavapaja.mcombat.modId
import net.minecraft.client.renderer.entity.Render
import net.minecraft.client.renderer.entity.RenderArrow
import net.minecraft.client.renderer.entity.RenderManager

class DebugArrowRenderer(renderManager: RenderManager): RenderArrow<DebugArrowEntity>(renderManager) {
  private val texture = ResourceLocation("$modId:textures/entity/debug_arrow.png")

  companion object {
    val factory = Render()
  }

  override fun getEntityTexture(entity: DebugArrowEntity): ResourceLocation? {
    return texture
  }
}

class Render: IRenderFactory<DebugArrowEntity> {
  override fun createRenderFor(renderManager: RenderManager): Render<in DebugArrowEntity> {
    return DebugArrowRenderer(renderManager)
  }
}
