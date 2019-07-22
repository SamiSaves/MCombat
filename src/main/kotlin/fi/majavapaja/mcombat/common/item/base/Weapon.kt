package fi.majavapaja.mcombat.common.item.base

import fi.majavapaja.mcombat.common.combat.DamageType

interface Weapon {
  val damage: HashMap<DamageType, Float>
}