package fi.majavapaja.mcombat.common.item

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.common.entity.DebugArrowEntity
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.item.ItemArrow
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class ArrowBase(private val name: String): ItemArrow() {
  init {
    setRegistryName(name)
    unlocalizedName = name
    creativeTab = Main.creativeTab
  }

  fun registerItemModel() {
    Main.proxy.registerItemRenderer(this, 0 , name)
  }

  override fun createArrow(worldIn: World, stack: ItemStack, shooter: EntityLivingBase): EntityArrow {
    return DebugArrowEntity(worldIn, shooter)
  }
}
