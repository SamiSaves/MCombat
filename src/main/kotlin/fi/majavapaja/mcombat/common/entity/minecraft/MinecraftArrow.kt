package fi.majavapaja.mcombat.common.entity.minecraft

import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.common.entity.IWeaponArrow
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.entity.projectile.EntitySpectralArrow
import net.minecraft.entity.projectile.EntityTippedArrow
import net.minecraft.init.Items
import net.minecraft.item.ItemArrow
import net.minecraft.item.ItemStack
import net.minecraft.world.World

fun createArrowEntity(world: World, arrow: ItemArrow, shooter: EntityLivingBase): EntityArrow =
    when (arrow) {
      Items.ARROW, Items.TIPPED_ARROW -> MinecraftTippedArrowEntity.create(world, ItemStack(arrow), shooter)
      Items.SPECTRAL_ARROW -> MinecrSpectralArrowEntity(world, shooter)
      else -> arrow.createArrow(world, ItemStack(arrow), shooter)
    }

class MinecraftTippedArrowEntity : EntityTippedArrow, IWeaponArrow {
  companion object {
    fun create (world: World, arrowStack: ItemStack, shooter: EntityLivingBase): EntityArrow {
      val entitytippedarrow = MinecraftTippedArrowEntity(world, shooter)
      entitytippedarrow.setPotionEffect(arrowStack)
      return entitytippedarrow
    }
  }

  constructor (world: World, shooter: EntityLivingBase): super(world, shooter)
  @Suppress("unused")
  constructor (world: World): super(world)
  @Suppress("unused")
  constructor (world: World, x: Double, y: Double, z: Double): super(world, x, y, z)

  override var customDamage = hashMapOf<DamageType, Float>()
}

class MinecrSpectralArrowEntity : EntitySpectralArrow, IWeaponArrow {
  constructor (world: World, shooter: EntityLivingBase): super(world, shooter)
  @Suppress("unused")
  constructor (world: World): super(world)
  @Suppress("unused")
  constructor (world: World, x: Double, y: Double, z: Double): super(world, x, y, z)

  override var customDamage = hashMapOf<DamageType, Float>()
}
