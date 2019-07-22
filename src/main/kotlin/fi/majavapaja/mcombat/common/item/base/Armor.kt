package fi.majavapaja.mcombat.common.item.base

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.modId
import net.minecraft.init.SoundEvents
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.ItemArmor
import net.minecraftforge.common.util.EnumHelper

class Armor(
    private val name: String,
    armorMaterial: ArmorMaterial,
    equipmentSlot: EntityEquipmentSlot,
    val armorPoints: HashMap<DamageType, Float>
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
