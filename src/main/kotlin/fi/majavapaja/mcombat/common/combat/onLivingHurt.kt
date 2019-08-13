package fi.majavapaja.mcombat.common.combat

import fi.majavapaja.mcombat.CommonProxy
import fi.majavapaja.mcombat.common.combat.CombatHelper.getArmorPoints
import fi.majavapaja.mcombat.common.entity.ICustomMob
import fi.majavapaja.mcombat.common.entity.IWeaponArrow
import fi.majavapaja.mcombat.common.entity.minecraft.getMonsterDamage
import fi.majavapaja.mcombat.common.entity.minecraft.isMinecraftMonster
import fi.majavapaja.mcombat.common.item.base.Bow
import fi.majavapaja.mcombat.common.item.minecraft.getAsWeapon
import fi.majavapaja.mcombat.common.message.ParticleMessage
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.EntityMob
import net.minecraft.entity.monster.EntitySkeleton
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.entity.projectile.EntitySpectralArrow
import net.minecraft.entity.projectile.EntityTippedArrow
import net.minecraft.init.Items
import net.minecraft.util.DamageSource
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
    getArrowDamage(trueSource, immediateSource)
  } else if (trueSource is EntityLivingBase) {
    val mainHandItem = trueSource.heldItemMainhand

    if (!mainHandItem.isEmpty) {
      when (val weapon = getAsWeapon(mainHandItem.item)) {
        is Bow -> hashMapOf(DamageType.Normal to 2f)
        else -> weapon?.damage ?: hashMapOf(DamageType.Normal to 2f)
      }
    } else {

      when {
        trueSource is ICustomMob -> trueSource.damage
        isMinecraftMonster(trueSource) -> getMonsterDamage(trueSource)
        else -> hashMapOf(DamageType.Normal to 2f)
      }
    }
  } else {
    hashMapOf(DamageType.Normal to 2f)
  }

private fun getArrowDamage(trueSource: Entity?, immediateSource: Entity?): HashMap<DamageType, Float> {
  var damage = when (immediateSource) {
    is IWeaponArrow -> immediateSource.customDamage
    is EntityTippedArrow -> getAsWeapon(Items.ARROW)?.damage ?: hashMapOf(DamageType.Normal to 2f)
    is EntitySpectralArrow -> getAsWeapon(Items.SPECTRAL_ARROW)?.damage ?: hashMapOf(DamageType.Normal to 2f)
    else -> hashMapOf(DamageType.Normal to 2f)
  }

  if (trueSource is EntityMob && trueSource !is EntityPlayer) {
    val bowDamage = getAsWeapon(trueSource.heldItemMainhand.item)?.damage ?: hashMapOf(DamageType.Normal to 0f)
    damage = CombatHelper.mergeHashMap(damage, bowDamage)

    if (trueSource is EntitySkeleton) {
      val skeletonDamage = getMonsterDamage(trueSource)
      damage = CombatHelper.mergeHashMap(damage, skeletonDamage)
    }
  }

  return damage
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
