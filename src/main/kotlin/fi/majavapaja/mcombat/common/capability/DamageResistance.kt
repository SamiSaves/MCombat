package fi.majavapaja.mcombat.common.capability

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import java.util.concurrent.Callable

data class DamageResistance(var damageType: DamageType, var amount: Float = 0f)

class DamageResistanceProvider(private val damageResistance: DamageResistance): ICapabilitySerializable<NBTBase> {
  companion object {
    @CapabilityInject(DamageResistance::class)
    lateinit var damageResistanceCapability: Capability<DamageResistance>

    fun getDamageResistance(capability: ICapabilitySerializable<NBTTagCompound>): DamageResistance? =
        if (capability.hasCapability(damageResistanceCapability, null)) {
          capability.getCapability(damageResistanceCapability, null)
        } else {
          null
        }

    fun register() {
      CapabilityManager.INSTANCE.register(DamageResistance::class.java, DamageResistanceStorage, DamageResistanceFactory)
    }
  }

  override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? =
      @Suppress("UNCHECKED_CAST")
      when (capability) {
        damageResistanceCapability -> damageResistance as T
        else -> null
      }

  override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean =
      capability == damageResistanceCapability

  override fun serializeNBT(): NBTBase =
    damageResistanceCapability.storage.writeNBT(damageResistanceCapability, damageResistanceCapability.defaultInstance, null) as NBTBase

  override fun deserializeNBT(nbt: NBTBase?) {
    damageResistanceCapability.storage.readNBT(damageResistanceCapability, damageResistanceCapability.defaultInstance, null, nbt)
  }
}

private object DamageResistanceStorage: Capability.IStorage<DamageResistance> {
  override fun readNBT(capability: Capability<DamageResistance>, instance: DamageResistance, side: EnumFacing?, nbt: NBTBase) {
    nbt as NBTTagCompound
    instance.damageType = DamageType(nbt.getString("damage-resistance"))
  }

  override fun writeNBT(capability: Capability<DamageResistance>, instance: DamageResistance, side: EnumFacing?): NBTBase {
    val nbt = NBTTagCompound()
    nbt.setString("damage-resistance", instance.damageType.type)
    return nbt
  }
}

private object DamageResistanceFactory: Callable<DamageResistance> {
  override fun call(): DamageResistance {
    return DamageResistance(DamageType())
  }
}
