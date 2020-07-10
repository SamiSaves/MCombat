package fi.majavapaja.mcombat.common.combat

import net.minecraft.client.resources.I18n

enum class DamageType(val type: String) {
  Normal("normal"),
  Magic("magic"),
  Fire("fire"),
  Ice("ice"),
  Water("water"),
  Lightning("lightning"),
  Earth("earth"),
  Air("air");

  fun toDisplayString(): String =
    when (this) {
      Magic -> I18n.format("damageType.magic")
      Fire -> I18n.format("damageType.fire")
      Ice -> I18n.format("damageType.ice")
      Water -> I18n.format("damageType.water")
      Lightning -> I18n.format("damageType.lightning")
      Earth -> I18n.format("damageType.earth")
      Air -> I18n.format("damageType.air")
      else -> I18n.format("damageType.normal")
    }

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
