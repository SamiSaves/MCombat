package fi.majavapaja.mcombat.common.entity

import fi.majavapaja.mcombat.common.combat.DamageType

interface IWeaponArrow {
  var customDamage: HashMap<DamageType, Float>
}