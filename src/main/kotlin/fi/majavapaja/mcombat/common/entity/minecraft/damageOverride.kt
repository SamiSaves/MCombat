package fi.majavapaja.mcombat.common.entity.minecraft

import fi.majavapaja.mcombat.common.combat.DamageType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.monster.*
import net.minecraft.entity.passive.SnowGolemEntity

fun getMonsterDamage (entity: LivingEntity): HashMap<DamageType, Float> =
  when (entity::class) {
    CreeperEntity::class -> hashMapOf(DamageType.Fire to 6f)
    EndermanEntity::class ->  hashMapOf(DamageType.Normal to 10f)
    EndermiteEntity::class -> hashMapOf(DamageType.Normal to 4f)
    ZombiePigmanEntity::class -> hashMapOf(DamageType.Normal to 6f)
    // Skeleton damage is in added to its bow + arrow damange
    SkeletonEntity::class -> hashMapOf(DamageType.Earth to 2f)
    SlimeEntity::class -> hashMapOf(DamageType.Magic to 4f)
    SnowGolemEntity::class -> hashMapOf(DamageType.Ice to 10f)
    SpiderEntity::class -> hashMapOf(DamageType.Normal to 4f)
    ZombieEntity::class -> hashMapOf(DamageType.Earth to 6f)
    ZombieVillagerEntity::class -> hashMapOf(DamageType.Normal to 4f)
    else -> hashMapOf(DamageType.Normal to 2f)
  }
