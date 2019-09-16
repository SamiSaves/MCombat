package fi.majavapaja.mcombat.client.render.mob

import fi.majavapaja.mcombat.common.entity.hunt.FireZombie
import fi.majavapaja.mcombat.modId
import net.minecraft.client.model.ModelZombie
import net.minecraft.client.renderer.entity.RenderLiving
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.util.ResourceLocation

class FireZombieRender(manager: RenderManager): RenderLiving<FireZombie>(manager, ModelZombie(), 0.5f) {
  companion object {
    private val texture: ResourceLocation = ResourceLocation("$modId:textures/entity/fire_zombie.png")
  }

  override fun getEntityTexture(entity: FireZombie): ResourceLocation = texture
}