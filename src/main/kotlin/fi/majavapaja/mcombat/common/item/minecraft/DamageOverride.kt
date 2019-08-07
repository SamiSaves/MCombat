package fi.majavapaja.mcombat.common.item.minecraft

import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.common.item.base.IWeapon
import net.minecraft.item.*
import net.minecraft.item.Item.ToolMaterial

private fun getDamage(amount: Float) = hashMapOf(DamageType.Normal to amount)

class MinecraftWeapon(damageAmount: Float): IWeapon {
  override var damage = getDamage(damageAmount)
}

fun getAsWeapon(item: Item): IWeapon? {
  if (item is IWeapon) return item

  val material = when (item) {
    is ItemSword -> item.toolMaterialName
    is ItemTool -> item.toolMaterialName
    else -> return null
  }

  return when (material) {
    ToolMaterial.WOOD.name -> when (item) {
      is ItemSword -> MinecraftWeapon(4f)
      is ItemAxe -> MinecraftWeapon(3f)
      else -> MinecraftWeapon(2f)
    }
    ToolMaterial.STONE.name -> when (item) {
      is ItemSword -> MinecraftWeapon(6f)
      is ItemAxe -> MinecraftWeapon(4f)
      else -> MinecraftWeapon(3f)
    }
    ToolMaterial.GOLD.name -> when (item) {
      is ItemSword -> MinecraftWeapon(10f)
      is ItemAxe -> MinecraftWeapon(8f)
      else -> MinecraftWeapon(5f)
    }
    ToolMaterial.IRON.name -> when (item) {
      is ItemSword -> MinecraftWeapon(8f)
      is ItemAxe -> MinecraftWeapon(6f)
      else -> MinecraftWeapon(4f)
    }
    ToolMaterial.DIAMOND.name -> when (item) {
      is ItemSword -> MinecraftWeapon(12f)
      is ItemAxe -> MinecraftWeapon(10f)
      else -> MinecraftWeapon(7f)
    }
    else -> null
  }
}
