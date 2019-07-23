package fi.majavapaja.mcombat.common.entity

import fi.majavapaja.mcombat.common.item.ModItems
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class DebugArrowEntity: EntityArrow {
  constructor (world: World, shooter: EntityLivingBase): super(world, shooter)
  @Suppress("unused")
  constructor (world: World): super(world)
  @Suppress("unused")
  constructor (world: World, x: Double, y: Double, z: Double): super(world, x, y, z)

  override fun getArrowStack(): ItemStack {
    return ItemStack(ModItems.debugArrow)
  }
}
