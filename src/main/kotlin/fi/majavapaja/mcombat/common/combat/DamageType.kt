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

    fun getParticleId(damageType: DamageType): Int =
        when(damageType) {
          Magic     -> 0
          Fire      -> 1
          Ice       -> 2
          Water     -> 3
          Lightning -> 4
          Earth     -> 5
          Air       -> 6
          else      -> -1
        }
  }
}
