package fi.majavapaja.mcombat.common.item.base

import fi.majavapaja.mcombat.common.combat.CombatHelper
import fi.majavapaja.mcombat.common.combat.DamageType
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.util.text.TextFormatting

interface IArmor {
  var armor: HashMap<DamageType, Float>

  companion object {
    fun getTooltip(stack: ItemStack): List<String> {
      val tooltip = mutableListOf<String>()
      val armor = CombatHelper.getItemArmorStat(stack)

      armor.forEach {
        val prefix = when {
          it.value > 0 -> "+"
          else -> ""
        }
        tooltip.add("${TextFormatting.DARK_GREEN}$prefix${it.value.toInt()} ${it.key} ${I18n.format("armor")}")
      }

      return tooltip
    }
  }
}