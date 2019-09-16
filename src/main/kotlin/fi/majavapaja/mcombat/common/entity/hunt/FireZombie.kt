package fi.majavapaja.mcombat.common.entity.hunt

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.common.entity.ICustomMob
import net.minecraft.entity.monster.EntityZombie
import net.minecraft.util.DamageSource
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
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
}
