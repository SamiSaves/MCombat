package fi.majavapaja.mcombat.common.item.base

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.common.combat.DamageType
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemBow
import net.minecraft.item.ItemStack
import net.minecraft.world.World

class Bow(
    private val name: String,
    override val damage: HashMap<DamageType, Float>
): ItemBow(), IWeapon {
  init {
    setRegistryName(name)
    unlocalizedName = name
    creativeTab = Main.creativeTab
  }

  fun registerItemModel() {
    Main.proxy.registerItemRenderer(this, 0 , name)
  }
}
