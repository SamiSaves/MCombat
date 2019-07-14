package fi.majavapaja.mcombat.common.effect

object ModEffects {
  val resistance = Resistance()

  fun registerEffects() {
    resistance.registerEffect()
  }
}