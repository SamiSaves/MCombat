package fi.majavapaja.mcombat.common.item.base

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.common.item.ModItemGroups
import net.minecraft.item.*

class Bow(
    name: String,
    override val damage: HashMap<DamageType, Float>
): BowItem(Properties().group(ModItemGroups.DEFAULT)), IWeapon {
  init {
    setRegistryName(Main.MOD_ID, name)
  }

  // TODO: onPlayerStoppedUsing setMinecraftPropertie and getAmmo methods were overrided in 1.12. Might still  be needed for custom bow damage
}
