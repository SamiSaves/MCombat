package fi.majavapaja.mcombat.common.item

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.modId
import net.minecraft.init.SoundEvents
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemArmor
import net.minecraftforge.common.util.EnumHelper

class ArmorBase(
    private val name: String,
    armorMaterial: ArmorMaterial,
    equipmentSlot: EntityEquipmentSlot
): ItemArmor(armorMaterial, 0, equipmentSlot) {
  companion object {
    val debugMaterial = EnumHelper.addArmorMaterial(
        "DEBUG",
        "$modId:debug_armor",
        100,
        intArrayOf(1,1,1,1),
        0,
        SoundEvents.BLOCK_GLASS_PLACE,
        0f
    ) ?: ArmorMaterial.LEATHER
  }

  init {
    setRegistryName(name)
    unlocalizedName = name
    creativeTab = Main.creativeTab
  }

  fun registerItemModel() {
    Main.proxy.registerItemRenderer(this, 0 , name)
  }
}
