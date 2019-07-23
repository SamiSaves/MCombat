package fi.majavapaja.mcombat.common.entity.minecraft

import net.minecraft.entity.Entity
import net.minecraft.entity.monster.*


fun isMinecraftMonster (entity: Entity) =
    when (entity::class) {
      EntityBlaze::class -> true
      EntityCaveSpider::class -> true
      EntityCreeper::class -> true
      EntityElderGuardian::class -> true
      EntityEnderman::class -> true
      EntityEndermite::class -> true
      EntityEvoker::class -> true
      EntityGhast::class -> true
      EntityGiantZombie::class -> true
      EntityGolem::class -> true
      EntityGuardian::class -> true
      EntityHusk::class -> true
      EntityIllusionIllager::class-> true
      EntityIronGolem::class -> true
      EntityMagmaCube::class -> true
      EntityPigZombie::class -> true
      EntityPolarBear::class -> true
      EntityShulker::class -> true
      EntitySilverfish::class -> true
      EntitySkeleton::class -> true
      EntitySlime::class -> true
      EntitySnowman::class -> true
      EntitySpellcasterIllager::class -> true
      EntitySpider::class -> true
      EntityStray::class -> true
      EntityVex::class -> true
      EntityVindicator::class -> true
      EntityWitch::class -> true
      EntityWitherSkeleton::class -> true
      EntityZombie::class -> true
      EntityZombieVillager::class -> true
      else -> false
    }
