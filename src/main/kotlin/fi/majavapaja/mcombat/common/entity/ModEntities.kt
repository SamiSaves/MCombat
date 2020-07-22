package fi.majavapaja.mcombat.common.entity

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent

object ModEntities {
  fun registerEntities (event: RegistryEvent.Register<EntityType<out Entity>>) {
    DebugArrowEntity.register(event)
    FireZombie.register(event)
  }

  fun registerEggs (event: RegistryEvent.Register<Item>) {
    FireZombie.registerSpawnEgg(event)
  }
}