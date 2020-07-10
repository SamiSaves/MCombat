package fi.majavapaja.mcombat.common.item.base

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.common.item.Groups
import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.ArmorItem
import net.minecraft.item.IArmorMaterial

class Armor(
    name: String,
    armorMaterial: IArmorMaterial,
    equipmentSlot: EquipmentSlotType,
    override var armor: HashMap<DamageType, Float>
): ArmorItem(armorMaterial, equipmentSlot, Properties().group(Groups.DEFAULT)), IArmor {
  init {
    setRegistryName(Main.MOD_ID, name)
  }
}
