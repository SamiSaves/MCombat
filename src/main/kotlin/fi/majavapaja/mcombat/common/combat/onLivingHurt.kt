package fi.majavapaja.mcombat.common.combat

import fi.majavapaja.mcombat.common.effect.ModEffects
import fi.majavapaja.mcombat.common.enchantment.ModEnchantments
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.util.DamageSource
import net.minecraftforge.event.entity.living.LivingHurtEvent

fun onLivingHurtEvent(event: LivingHurtEvent) {
  if (event.source == DamageSource.FALL || event.source == DamageSource.DROWN) return

  event.isCanceled = true
  val entity = event.entity as EntityLivingBase
  val damageType = getDamageType(event.source.trueSource, event.source.immediateSource)
  val resistance = getResistance(entity, damageType)
  var damage = when (event.source.trueSource) {
    is EntityLivingBase -> 10f
    else -> 2f
  }

  damage += resistance * damage
  damage = if (damage < 0f) {
    0f
  } else {
    damage
  }

  entity.health -= damage
  println("Someone was hit with ${damageType.type} for $damage points of damage. Someone had $resistance resistance")
}

private fun getDamageType(trueSource: Entity?, immediateSource: Entity?): DamageType {
  var damageType: DamageType? = null

  if (immediateSource is EntityArrow) {
    damageType = DamageTypeProvider.getDamageType(immediateSource)
  } else if (trueSource is EntityLivingBase) {
    val weapon = trueSource.heldItemMainhand

    damageType = if (!weapon.isEmpty) {
      val enchantments = EnchantmentHelper.getEnchantments(weapon)

      if (enchantments.containsKey(ModEnchantments.HolyDamage)) {
        DamageType("holy")
      } else {
        DamageTypeProvider.getDamageType(weapon)
      }
    } else {
      DamageTypeProvider.getDamageType(trueSource)
    }
  }

  return damageType ?: DamageType()
}

private fun getResistance(entity: EntityLivingBase, damageType: DamageType): Float {
  val resistances = ArrayList<DamageResistance?>()

  val naturalResistance = DamageResistanceProvider.getDamageResistance(entity)
  if (naturalResistance?.type == damageType.type) {
    resistances.add(naturalResistance)
  }

  resistances.addAll(
      entity.armorInventoryList
          .filter { DamageResistanceProvider.getDamageResistance(it)?.type == damageType.type }
          .map { DamageResistanceProvider.getDamageResistance(it)}
  )

  if (damageType.type == "rotten") resistances.addAll(getEnchantmentResistance(entity))

  if (
      entity.activePotionEffects.find { it.effectName == ModEffects.resistance.name } != null &&
      damageType.type == "rotten"
  ) {
    resistances.add(DamageResistance("rotten", -1f))
  }

  var resistance = 0f
  resistances.filterNotNull().map { resistance += it.amount }

  return resistance
}

private fun getEnchantmentResistance(entity: Entity): ArrayList<DamageResistance> {
  val resistances = ArrayList<DamageResistance>()

  for (armorPiece in entity.armorInventoryList) {
    val enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.RottenResistance, armorPiece)
    resistances.add(DamageResistance("rotten", enchantmentLevel * -0.05f))
  }

  return resistances
}

