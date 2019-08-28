package fi.majavapaja.mcombat.common.capability

import net.minecraft.nbt.NBTBase
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.ICapabilityProvider
import net.minecraftforge.common.capabilities.ICapabilitySerializable

class CapabilityProvider<T>(val capability: Capability<T>, val facing: EnumFacing?, val instance: T) : ICapabilitySerializable<NBTBase> {
  override fun <T : Any?> getCapability(c: Capability<T>, facing: EnumFacing?): T? =
    @Suppress("unchecked_cast")
    when {
      hasCapability(c, facing) -> this.capability.cast(instance)
      else -> null
    }

  override fun deserializeNBT(nbt: NBTBase?) {
    capability.readNBT(instance, facing, nbt)
  }

  override fun serializeNBT(): NBTBase =
    capability.writeNBT(instance, facing) as NBTBase

  override fun hasCapability(c: Capability<*>, facing: EnumFacing?): Boolean =
    c == capability
}

object CapabilityUtil {
  fun <T> getCapability(provider: ICapabilityProvider?, capability: Capability<T>, facing: EnumFacing?): T? = when {
    provider == null -> null
    provider.hasCapability(capability, facing) ->
      provider.getCapability(capability, facing)
    else -> null
  }
}
