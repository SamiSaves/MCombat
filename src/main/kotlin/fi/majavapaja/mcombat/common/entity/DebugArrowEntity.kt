package fi.majavapaja.mcombat.common.entity

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.common.item.ModItems
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.fml.common.registry.EntityRegistry

class DebugArrowEntity: EntityArrow, IWeaponArrow {
  companion object {
    fun register(name: String, id: Int) {
      EntityRegistry.registerModEntity(
          ResourceLocation(name),
          DebugArrowEntity::class.java,
          name,
          id,
          Main,
          64,
          1,
          true
      )
    }
  }

  override var customDamage = hashMapOf<DamageType, Float>()

  constructor (world: World, shooter: EntityLivingBase): super(world, shooter)
  @Suppress("unused")
  constructor (world: World): super(world)
  @Suppress("unused")
  constructor (world: World, x: Double, y: Double, z: Double): super(world, x, y, z)

  override fun getArrowStack(): ItemStack {
    return ItemStack(ModItems.debugArrow)
  }
}
