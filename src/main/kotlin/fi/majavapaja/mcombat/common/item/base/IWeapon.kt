package fi.majavapaja.mcombat.common.item.base

import fi.majavapaja.mcombat.common.combat.DamageType
import net.minecraft.client.resources.I18n
import net.minecraft.util.text.TextFormatting

interface IWeapon {
  val damage: HashMap<DamageType, Float>

  fun getDamageTooltip(): List<String> {
    val tooltip = mutableListOf<String>()

    damage.forEach {
      val prefix = when {
        it.value < 0 -> "-"
        else -> "+"
      }
      tooltip.add("${TextFormatting.AQUA}$prefix${it.value.toInt()} ${it.key} ${I18n.format("damage")}")
    }

    return tooltip
  }
}