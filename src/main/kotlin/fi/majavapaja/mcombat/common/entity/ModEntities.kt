package fi.majavapaja.mcombat.common.entity

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.modId
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.registry.EntityRegistry

object ModEntities {
  private const val debugArrowEntityId = 0

  fun registerEntities () {
    EntityRegistry.registerModEntity(
        ResourceLocation("$modId:debug_arrow"),
        DebugArrowEntity::class.java,
        "$modId:debug_arrow",
        debugArrowEntityId,
        Main,
        64,
        1,
        true
    )
  }
}