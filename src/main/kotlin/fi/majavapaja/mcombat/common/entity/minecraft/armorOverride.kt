package fi.majavapaja.mcombat.common.entity.minecraft

import fi.majavapaja.mcombat.common.combat.DamageType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.monster.*
import net.minecraft.entity.passive.SnowGolemEntity

fun getMonsterArmor (entity: LivingEntity): HashMap<DamageType, Float> =
  when (entity::class) {
    CreeperEntity::class -> hashMapOf(DamageType.Fire to -40f, DamageType.Normal to 20f)
    EndermanEntity::class ->  hashMapOf(DamageType.Normal to 60f, DamageType.Magic to 40f)
    SkeletonEntity::class -> hashMapOf(DamageType.Air to -40f, DamageType.Normal to -20f)
    SnowGolemEntity::class -> hashMapOf(DamageType.Normal to 50f)
    SpiderEntity::class -> hashMapOf(DamageType.Normal to 20f)
    ZombieEntity::class -> hashMapOf(DamageType.Air to -40f, DamageType.Normal to 40f)
    ZombieVillagerEntity::class -> hashMapOf(DamageType.Normal to 40f)
    else -> hashMapOf(DamageType.Normal to 0f)
  }
