package fi.majavapaja.mcombat.common.entity

import fi.majavapaja.mcombat.modId

object ModEntities {
  fun registerEntities () {
    DebugArrowEntity.register("$modId:debug_arrow", 0)
  }
}