package fi.majavapaja.mcombat.common.combat

import fi.majavapaja.mcombat.common.capability.CapabilityProvider
import fi.majavapaja.mcombat.common.capability.CapabilityUtil
import fi.majavapaja.mcombat.modId
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.capabilities.*
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent


data class DamageOverrides(
  var magic: Float = 0f,
  var fire: Float = 0f,
  var ice: Float = 0f,
  var water: Float = 0f,
  var lightning: Float = 0f,
  var earth: Float = 0f,
  var air: Float = 0f,
  var normal: Float = 0f
) {
  fun toMap(): Map<DamageType, Float> =
    hashMapOf(
      DamageType.Magic to magic,
      DamageType.Fire to fire,
      DamageType.Ice to ice,
      DamageType.Water to water,
      DamageType.Lightning to lightning,
      DamageType.Earth to earth,
      DamageType.Air to air,
      DamageType.Normal to normal
    ).filterValues { it != 0f }

}

object DamageOverridesCapability {
  @CapabilityInject(DamageOverrides::class)
  private lateinit var CAPABILITY: Capability<DamageOverrides>
  val RESOURCE = ResourceLocation(modId, "damage_override")

  private val FACING = null

  fun register() {
    CapabilityManager.INSTANCE.register(DamageOverrides::class.java, DamageOverrideStorage(), this::mkDefault)
  }

  fun getDamageOverrides(stack: ItemStack): DamageOverrides? =
    CapabilityUtil.getCapability(stack, CAPABILITY, FACING)

  fun createProvider(overrides: DamageOverrides) =
    CapabilityProvider(CAPABILITY, FACING, overrides)

  fun mkDefault(): DamageOverrides = DamageOverrides()
}

@Mod.EventBusSubscriber(modid = modId)
object DamageOverridesEventHandler {
  @Suppress("unused")
  @SubscribeEvent
  fun attachCapabilities(event: AttachCapabilitiesEvent<ItemStack>) {
    val stack = event.`object`
    if (stack is ItemStack) {
      val overrides = DamageOverridesCapability.mkDefault()
      event.addCapability(DamageOverridesCapability.RESOURCE, DamageOverridesCapability.createProvider(overrides))
    } else {
      println("!!! OBJECT IS NOT ITEMSTACK !!!")
    }
  }
}

private class DamageOverrideStorage : Capability.IStorage<DamageOverrides> {
  override fun readNBT(capability: Capability<DamageOverrides>, instance: DamageOverrides, side: EnumFacing?, nbt: NBTBase) {
    nbt as NBTTagCompound
    instance.magic = nbt.getFloat("magic")
    instance.fire = nbt.getFloat("fire")
    instance.ice = nbt.getFloat("ice")
    instance.water = nbt.getFloat("water")
    instance.lightning = nbt.getFloat("lightning")
    instance.earth = nbt.getFloat("earth")
    instance.air = nbt.getFloat("air")
    instance.normal = nbt.getFloat("normal")
  }

  override fun writeNBT(capability: Capability<DamageOverrides>, instance: DamageOverrides, side: EnumFacing?): NBTBase {
    val nbt = NBTTagCompound()
    nbt.setFloat("magic", instance.magic)
    nbt.setFloat("fire", instance.fire)
    nbt.setFloat("ice", instance.ice)
    nbt.setFloat("water", instance.water)
    nbt.setFloat("lightning", instance.lightning)
    nbt.setFloat("earth", instance.earth)
    nbt.setFloat("air", instance.air)
    nbt.setFloat("normal", instance.normal)
    return nbt
  }
}
