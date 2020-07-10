package fi.majavapaja.mcombat.common.item.base

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.common.item.Groups
import net.minecraft.item.ItemTier
import net.minecraft.item.SwordItem

class Sword(
    name: String,
    toolMaterial: ItemTier,
    override val damage: HashMap<DamageType, Float>
): SwordItem(toolMaterial, 0, 1.0F, Properties().group(Groups.DEFAULT)), IWeapon {
  init {
    setRegistryName(Main.MOD_ID, name)
  }
}
