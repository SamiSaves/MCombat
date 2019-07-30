package fi.majavapaja.mcombat.common.combat

import net.minecraft.util.EnumParticleTypes

enum class DamageType(val type: String) {
  Normal("normal"),
  Magic("magic"),
  Fire("fire"),
  Ice("ice"),
  Water("water"),
  Lightning("lightning"),
  Earth("earth"),
  Air("air");

  companion object {
    fun getDamageType(type: String): DamageType =
      when (type) {
        Magic.type -> Magic
        Fire.type -> Fire
        Ice.type -> Ice
        Water.type -> Water
        Lightning.type -> Lightning
        Earth.type -> Earth
        Air.type -> Air
        else -> Normal
    }

    fun getParticle(damageType: DamageType): EnumParticleTypes? =
        when(damageType) {
          Magic -> EnumParticleTypes.ENCHANTMENT_TABLE
          Fire -> EnumParticleTypes.FLAME
          Ice -> EnumParticleTypes.SNOWBALL
          Water -> EnumParticleTypes.WATER_SPLASH
          Lightning -> EnumParticleTypes.VILLAGER_ANGRY
          Earth -> EnumParticleTypes.TOTEM
          Air -> EnumParticleTypes.END_ROD
          else -> null
        }
  }
}
