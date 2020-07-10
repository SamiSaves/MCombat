package fi.majavapaja.mcombat.common.item.base

import fi.majavapaja.mcombat.common.combat.DamageType

interface IWeapon {
  val damage: HashMap<DamageType, Float>

  companion object {
    // TODO: There getTooltip (used to show custom damages)
  }
}