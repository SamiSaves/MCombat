package fi.majavapaja.mcombat.common.entity

import fi.majavapaja.mcombat.common.combat.DamageType

interface ICustomMob {
  val damage: HashMap<DamageType, Float>
  val armor: HashMap<DamageType, Float>
}