package fi.majavapaja.mcombat.client.renderers

import fi.majavapaja.mcombat.Main
import net.minecraft.client.renderer.entity.*
import net.minecraft.entity.monster.ZombieEntity
import net.minecraft.util.ResourceLocation

class FireZombieRenderer(manager: EntityRendererManager): ZombieRenderer(manager) {
  private val location = ResourceLocation(Main.MOD_ID, "textures/entity/fire_zombie.png")

  override fun getEntityTexture(entity: ZombieEntity): ResourceLocation? {
    return location
  }
}
