package fi.majavapaja.mcombat.common.effect

import fi.majavapaja.mcombat.modId
import net.minecraft.client.Minecraft
import net.minecraft.potion.Potion
import net.minecraft.potion.PotionEffect
import net.minecraft.potion.PotionType
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.common.registry.ForgeRegistries

open class EffectBase(name: String, isBadEffect: Boolean, color: Int): Potion(isBadEffect, color) {
  val defaultEffect: PotionType

  init {
    setPotionName(name)
    setRegistryName(name)
    setIconIndex(0, 0)

    defaultEffect = PotionType(name, PotionEffect(this, 2401)).setRegistryName(name)
  }

  fun registerEffect() {
    ForgeRegistries.POTIONS.register(this)
    ForgeRegistries.POTION_TYPES.register(defaultEffect)
  }

  override fun hasStatusIcon(): Boolean {
    Minecraft.getMinecraft().textureManager.bindTexture(ResourceLocation("$modId:textures/gui/effects.png"))
    return true
  }
}
