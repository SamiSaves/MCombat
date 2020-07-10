package fi.majavapaja.mcombat.common.item.minecraft

import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.common.item.base.IWeapon
import net.minecraft.item.*

private fun getDamage(amount: Float) = hashMapOf(DamageType.Normal to amount)

class MinecraftWeapon(damageAmount: Float): IWeapon {
  override var damage = getDamage(damageAmount)
}

fun getAsWeapon(item: Item): IWeapon? {
  if (item is IWeapon) return item

  val material = when (item) {
    is TieredItem -> item.tier
    is ArrowItem -> return MinecraftWeapon(4f)
    else -> return null
  }

  return when (material) {
    ItemTier.WOOD -> when (item) {
      is SwordItem -> MinecraftWeapon(4f)
      is AxeItem -> MinecraftWeapon(3f)
      else -> MinecraftWeapon(2f)
    }
    ItemTier.STONE -> when (item) {
      is SwordItem -> MinecraftWeapon(6f)
      is AxeItem -> MinecraftWeapon(4f)
      else -> MinecraftWeapon(3f)
    }
    ItemTier.GOLD -> when (item) {
      is SwordItem -> MinecraftWeapon(10f)
      is AxeItem -> MinecraftWeapon(8f)
      else -> MinecraftWeapon(5f)
    }
    ItemTier.IRON -> when (item) {
      is SwordItem -> MinecraftWeapon(8f)
      is AxeItem -> MinecraftWeapon(6f)
      else -> MinecraftWeapon(4f)
    }
    ItemTier.DIAMOND -> when (item) {
      is SwordItem -> MinecraftWeapon(12f)
      is AxeItem -> MinecraftWeapon(10f)
      else -> MinecraftWeapon(7f)
    }
    else -> null
  }
}
