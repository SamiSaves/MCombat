package fi.majavapaja.mcombat.common.item.minecraft

import fi.majavapaja.mcombat.common.combat.DamageType
import net.minecraft.item.Item
import net.minecraft.item.Item.ToolMaterial
import net.minecraft.item.ItemAxe
import net.minecraft.item.ItemSword
import net.minecraft.item.ItemTool

private fun getDamage(amount: Float) = hashMapOf(DamageType.Lightning to amount)

fun getToolDamage (item: Item): HashMap<DamageType, Float> {
  val material = when (item) {
    is ItemSword -> item.toolMaterialName
    is ItemTool -> item.toolMaterialName
    else -> ToolMaterial.WOOD.name
  }

  return when (material) {
    ToolMaterial.WOOD.name ->
      when (item) {
        is ItemSword -> getDamage(4f)
        is ItemAxe -> getDamage(3f)
        else -> getDamage(2f)
      }
    ToolMaterial.STONE.name ->
      when (item) {
        is ItemSword -> getDamage(6f)
        is ItemAxe -> getDamage(4f)
        else -> getDamage(3f)
      }
    ToolMaterial.GOLD.name ->
      when (item) {
        is ItemSword -> getDamage(10f)
        is ItemAxe -> getDamage(8f)
        else -> getDamage(5f)
      }
    ToolMaterial.IRON.name ->
      when (item) {
        is ItemSword -> getDamage(8f)
        is ItemAxe -> getDamage(6f)
        else -> getDamage(4f)
      }
    ToolMaterial.DIAMOND.name ->
      when (item) {
        is ItemSword -> getDamage(12f)
        is ItemAxe -> getDamage(10f)
        else -> getDamage(7f)
      }
    else -> getDamage(2f)
  }
}
