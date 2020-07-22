package fi.majavapaja.mcombat.common.entity

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.common.item.Groups
import net.minecraft.entity.*
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.monster.ZombieEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.item.SpawnEggItem
import net.minecraft.world.World
import net.minecraftforge.event.RegistryEvent

class FireZombie(entityType: EntityType<out FireZombie>, world: World): ZombieEntity(entityType, world), ICustomMob {
  override val damage = hashMapOf(DamageType.Normal to 2f, DamageType.Fire to 4f)
  override val armor = hashMapOf(DamageType.Normal to 20f, DamageType.Fire to 120f, DamageType.Water to -120f)
  override fun isBurning(): Boolean = true

  override fun registerGoals() {
    goalSelector.addGoal(1, MeleeAttackGoal(this, .5, false))
    goalSelector.addGoal(2, RandomWalkingGoal(this, .35))
    goalSelector.addGoal(3, LookRandomlyGoal(this))
    targetSelector.addGoal(4, NearestAttackableTargetGoal(this, PlayerEntity::class.java, 10, true, false, ::shouldAttack));
  }

  override fun registerAttributes() {
    super.registerAttributes()
    getAttribute(SharedMonsterAttributes.MAX_HEALTH).baseValue = 20.0;
    getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).baseValue = .45;
  }

  private fun shouldAttack(target: LivingEntity?): Boolean = (target != null)

  companion object {
    val entityType: EntityType<FireZombie> = EntityType.Builder.create(::FireZombie, EntityClassification.MONSTER)
        .immuneToFire()
        .build("fire_zombie")
        .setRegistryName(Main.MOD_ID, "fire_zombie") as EntityType<FireZombie>

    fun register(event: RegistryEvent.Register<EntityType<out Entity>>) {
      event.registry.register(entityType)
    }

    fun registerSpawnEgg(event: RegistryEvent.Register<Item>) {
      val spawnEgg = SpawnEggItem(entityType, 0x3d672f, 0xd96900, Item.Properties().group((Groups.DEFAULT)))
      spawnEgg.setRegistryName(Main.MOD_ID, "fire_zombie_spawn_egg")
      event.registry.register(spawnEgg)
    }
  }
}