package fi.majavapaja.mcombat.common.item.base

import fi.majavapaja.mcombat.common.combat.DamageType
import net.minecraft.client.resources.I18n
import net.minecraft.util.text.TextFormatting

interface IWeapon {
  val damage: HashMap<DamageType, Float>

  fun getDamageTooltip(): List<String> {
    val tooltip = mutableListOf<String>()

    var totalDamage = 0f
    damage.forEach {

      tooltip.add("  ${it.value.toInt()} ${it.key}")
      totalDamage += it.value
    }

    val prefix = when {
      totalDamage > 0 -> "+"
      else -> ""
    }
    tooltip.add(0, "${TextFormatting.RED}$prefix${totalDamage.toInt()} ${I18n.format("damage")}")

    return tooltip
  }
}