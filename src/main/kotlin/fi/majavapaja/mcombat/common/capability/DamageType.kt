package fi.majavapaja.mcombat.common.capability

import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import java.util.concurrent.Callable

data class DamageType(var type: String = "normal")

class DamageTypeProvider(private val damageType: DamageType = DamageType()): ICapabilitySerializable<NBTBase> {
  companion object {
    @CapabilityInject(DamageType::class)
    lateinit var damageTypeCapability: Capability<DamageType>

    fun getDamageType (capability: ICapabilitySerializable<NBTTagCompound>) =
        if (capability.hasCapability(damageTypeCapability, null)) {
          capability.getCapability(damageTypeCapability, null)
        } else {
          null
        }

    fun register () {
      CapabilityManager.INSTANCE.register(DamageType::class.java, Storage, Factory)
    }
  }

  override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? =
      @Suppress("UNCHECKED_CAST")
      when (capability) {
        damageTypeCapability -> damageType as T
        else -> null
      }

  override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean =
      capability == damageTypeCapability

  override fun serializeNBT(): NBTBase =
    damageTypeCapability.storage?.writeNBT(damageTypeCapability, damageTypeCapability.defaultInstance, null) as NBTBase

  override fun deserializeNBT(nbt: NBTBase?) {
    damageTypeCapability.storage?.readNBT(damageTypeCapability, damageTypeCapability.defaultInstance, null, nbt)
  }
}

private object Storage: Capability.IStorage<DamageType> {
  override fun readNBT(capability: Capability<DamageType>, instance: DamageType, side: EnumFacing, nbt: NBTBase) {
    nbt as NBTTagCompound
    instance.type = nbt.getString("damage-type")
  }

  override fun writeNBT(capability: Capability<DamageType>, instance: DamageType, side: EnumFacing): NBTBase {
    val nbt = NBTTagCompound()
    nbt.setString("damage-type", instance.type)
    return nbt
  }
}

private object Factory: Callable<DamageType> {
  override fun call(): DamageType {
    return DamageType()
  }
}

