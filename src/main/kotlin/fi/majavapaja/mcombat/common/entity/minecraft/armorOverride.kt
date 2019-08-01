package fi.majavapaja.mcombat.common.entity.minecraft

import fi.majavapaja.mcombat.common.combat.DamageType
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.*

fun getMonsterArmor (entity: EntityLivingBase): HashMap<DamageType, Float> =
  when (entity::class) {
    EntityCreeper::class -> hashMapOf(DamageType.Fire to -40f, DamageType.Normal to 20f)
    EntityEnderman::class ->  hashMapOf(DamageType.Normal to 60f, DamageType.Magic to 40f)
    EntitySkeleton::class -> hashMapOf(DamageType.Air to -40f, DamageType.Normal to -20f)
    EntitySnowman::class -> hashMapOf(DamageType.Normal to 50f)
    EntitySpider::class -> hashMapOf(DamageType.Normal to 20f)
    EntityZombie::class -> hashMapOf(DamageType.Air to -40f, DamageType.Normal to 40f)
    EntityZombieVillager::class -> hashMapOf(DamageType.Normal to 40f)
    else -> hashMapOf(DamageType.Normal to 0f)
  }
