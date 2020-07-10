package fi.majavapaja.mcombat.common.item.base

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.common.item.Groups
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.projectile.ArrowEntity
import net.minecraft.item.ArrowItem
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class Arrow(
    name: String,
    override val damage: HashMap<DamageType, Float>
): ArrowItem(Properties().group(Groups.DEFAULT)), IWeapon {
  init {
    setRegistryName(Main.MOD_ID, name)
  }

  override fun createArrow(worldIn: World, stack: ItemStack, shooter: LivingEntity): ArrowEntity {
    // TODO: Switch this to DebugArrowEntity
    return ArrowEntity(worldIn, shooter)
  }
}
