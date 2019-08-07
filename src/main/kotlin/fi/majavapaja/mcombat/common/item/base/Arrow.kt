package fi.majavapaja.mcombat.common.item.base

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.common.entity.DebugArrowEntity
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.item.ItemArrow
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class Arrow(
    private val name: String,
    override val damage: HashMap<DamageType, Float>
): ItemArrow(), IWeapon {
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
