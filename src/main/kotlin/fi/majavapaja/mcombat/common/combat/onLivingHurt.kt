package fi.majavapaja.mcombat.common.combat

import fi.majavapaja.mcombat.CommonProxy
import fi.majavapaja.mcombat.common.combat.CombatHelper.getArmorPoints
import fi.majavapaja.mcombat.common.entity.minecraft.getMonsterDamage
import fi.majavapaja.mcombat.common.entity.minecraft.isMinecraftMonster
import fi.majavapaja.mcombat.common.item.ModItems.isMinecraftItem
import fi.majavapaja.mcombat.common.item.base.IWeapon
import fi.majavapaja.mcombat.common.item.minecraft.getToolDamage
import fi.majavapaja.mcombat.common.message.ParticleMessage
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.util.DamageSource
import net.minecraft.util.EnumParticleTypes
import net.minecraftforge.event.entity.living.LivingHurtEvent
import net.minecraftforge.fml.common.network.NetworkRegistry

val ignoredDamageSources = listOf(
  DamageSource.FALL,
  DamageSource.DROWN,
  DamageSource.OUT_OF_WORLD,
  DamageSource.ON_FIRE
)

fun onLivingHurtEvent(event: LivingHurtEvent) {
  if (ignoredDamageSources.contains(event.source)) return

  event.isCanceled = true

  val entity = event.entity as EntityLivingBase
  val damage = getDamage(event.source.trueSource, event.source.immediateSource)
  val armorPoints = getArmorPoints(entity)
  var damageAmount = 0f


  for ((damageType, amount) in damage) {
    damageAmount += amount - ((armorPoints[damageType] ?: 0f) / 10)
  }

  damageAmount = if (damageAmount < 0f) {
    0f
  } else {
    damageAmount
  }

  entity.health -= damageAmount

  createParticles(entity, damage, damageAmount)
  println("Someone was hit with $damage for $damageAmount points of damage. Someone had $armorPoints resistance")
}

private fun getDamage(trueSource: Entity?, immediateSource: Entity?): HashMap<DamageType, Float> =
  if (immediateSource is EntityArrow) {
    when (immediateSource) {
      is IWeapon -> immediateSource.damage
      else -> hashMapOf(DamageType.Normal to 4f)
    }
    // TODO: Get bow Damage
  } else if (trueSource is EntityLivingBase) {
    val mainHandItem = trueSource.heldItemMainhand

    if (!mainHandItem.isEmpty) {
      val weapon = mainHandItem.item

      when {
        isMinecraftItem(weapon) -> getToolDamage(weapon)
        weapon is IWeapon -> weapon.damage
        else -> hashMapOf(DamageType.Normal to 2f)
      }
    } else {
      if (isMinecraftMonster(trueSource)) {
        println("This is a minecraft monster ${trueSource.name}")
        getMonsterDamage(trueSource)
      } else {
        println("This is not a minecraft monster ${trueSource.name}")
        hashMapOf(DamageType.Normal to 2f)
      }
    }
  } else {
    hashMapOf(DamageType.Normal to 2f)
  }

private fun createParticles (entity: EntityLivingBase, damage: HashMap<DamageType, Float>, amount: Float) {
  // Send particles to all nearby players
  val particleTargetPoint = NetworkRegistry.TargetPoint(entity.dimension, entity.posX, entity.posY, entity.posZ, 64.0)
    CommonProxy.particleNetwork.sendToAllAround(ParticleMessage(
        entity.posX,
        entity.posY,
        entity.posZ,
        amount,
        damage.map { it.key }
    ), particleTargetPoint)
}
