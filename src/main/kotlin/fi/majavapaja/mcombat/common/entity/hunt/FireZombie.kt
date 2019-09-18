package fi.majavapaja.mcombat.common.entity.hunt

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.common.entity.ICustomMob
import net.minecraft.entity.monster.EntityZombie
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.DamageSource
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraft.world.WorldServer
import net.minecraftforge.common.DimensionManager
import net.minecraftforge.fml.common.registry.EntityRegistry

class FireZombie(world: World): EntityZombie(world), ICustomMob {
  companion object {
    fun register(name: String, id: Int) {
      EntityRegistry.registerModEntity(
          ResourceLocation(name),
          FireZombie::class.java,
          name,
          id,
          Main,
          64,
          1,
          true,
          0x3d672f,
      0xd96900
      )
    }
  }

  override val damage = hashMapOf(DamageType.Normal to 2f, DamageType.Fire to 4f)
  override val armor = hashMapOf(DamageType.Normal to 20f, DamageType.Fire to 120f, DamageType.Water to -120f)

  init {
    isImmuneToFire = true
  }

  override fun isBurning(): Boolean = true

  override fun onDeath(cause: DamageSource) {
    super.onDeath(cause)

    val player = cause.trueSource
    if (player is EntityPlayerMP) {
      val world = DimensionManager.getWorld(0) as WorldServer
      val advancement = world.advancementManager.getAdvancement(ResourceLocation("mcombat:hunt/hunt"))
      val huntProggress = player.advancements.getProgress(advancement)
      if (huntProggress.completedCriteria.contains("start_quest")) {
        huntProggress.grantCriterion("defeat_hunt")
      } else {
        println("Killing monster doesn't do anything if player has not started the hunt >:(")
      }

    }
  }
}
