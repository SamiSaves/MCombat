package fi.majavapaja.mcombat.common.item.base

import fi.majavapaja.mcombat.common.combat.CombatHelper
import fi.majavapaja.mcombat.common.combat.DamageType
import net.minecraft.client.resources.I18n
import net.minecraft.item.ItemStack
import net.minecraft.util.text.TextFormatting

interface IWeapon {
  val damage: HashMap<DamageType, Float>

  companion object {
    fun getTooltip(stack: ItemStack): List<String> {
      val tooltip = mutableListOf<String>()

      val damage = CombatHelper.getItemDamageStat(stack)
      var totalDamage = 0f
      if (damage.size > 1) {
        damage.forEach {
          tooltip.add("  ${it.value.toInt()} ${it.key}")
          totalDamage += it.value
        }
      } else {
        totalDamage = damage.values.first()
      }

      val prefix = when {
        totalDamage > 0 -> "+"
        else -> ""
      }

      val damageString = "${TextFormatting.RED}$prefix${totalDamage.toInt()}"

      val damageTooltip = if (damage.size == 1) {
        "$damageString ${damage.keys.first()} ${I18n.format("damage")}"
      } else {
        "$damageString ${I18n.format("damage")}"
      }

      tooltip.add(0, damageTooltip)

      return tooltip
    }
  }
}