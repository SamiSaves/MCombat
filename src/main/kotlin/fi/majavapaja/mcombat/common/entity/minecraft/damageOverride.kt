package fi.majavapaja.mcombat.common.entity.minecraft

import fi.majavapaja.mcombat.common.combat.DamageType
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.monster.*

fun getMonsterDamage (entity: EntityLivingBase): HashMap<DamageType, Float> =
  when (entity::class) {
    EntityCreeper::class -> hashMapOf(DamageType.Fire to 6f)
    EntityEnderman::class ->  hashMapOf(DamageType.Normal to 10f)
    EntityEndermite::class -> hashMapOf(DamageType.Normal to 4f)
    EntityPigZombie::class -> hashMapOf(DamageType.Normal to 6f)
    EntitySkeleton::class -> hashMapOf(DamageType.Earth to 6f)
    EntitySlime::class -> hashMapOf(DamageType.Magic to 4f)
    EntitySnowman::class -> hashMapOf(DamageType.Ice to 10f)
    EntitySpider::class -> hashMapOf(DamageType.Normal to 4f)
    EntityZombie::class -> hashMapOf(DamageType.Earth to 6f)
    EntityZombieVillager::class -> hashMapOf(DamageType.Normal to 4f)
    else -> hashMapOf(DamageType.Normal to 2f)
  }
