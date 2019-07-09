package fi.majavapaja.mcombat.common.combat

import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraftforge.common.capabilities.Capability
import net.minecraftforge.common.capabilities.CapabilityInject
import net.minecraftforge.common.capabilities.ICapabilitySerializable
import java.util.concurrent.Callable

class DamageResistance(var type: String = "normal", var amount: Float = 0f) {
  fun createNBT(): NBTTagCompound {
    val nbt = NBTTagCompound()
    nbt.setString("damage-resistance", type)
    return nbt
  }

  fun loadFromNBT(nbt: NBTTagCompound) {
    type = nbt.getString("damage-resistance")
  }
}

class DamageResistanceProvider(val resistance: DamageResistance): ICapabilitySerializable<NBTBase> {
  companion object {
    @CapabilityInject(DamageResistance::class)
    lateinit var damageResistanceCapability: Capability<DamageResistance>

    fun getDamageResistance(entity: EntityLivingBase): DamageResistance? =
      if (entity.hasCapability(damageResistanceCapability, null)) {
        entity.getCapability(damageResistanceCapability, null)
      } else {
        null
      }

    fun getDamageResistance(item: ItemStack): DamageResistance? =
        if (item.hasCapability(damageResistanceCapability, null)) {
          item.getCapability(damageResistanceCapability, null)
        } else {
          null
        }
  }

  override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? =
      when (capability) {
        damageResistanceCapability -> resistance as T
        else -> null
      }

  override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean =
      capability == damageResistanceCapability

  override fun serializeNBT(): NBTBase =
    damageResistanceCapability?.storage?.writeNBT(damageResistanceCapability, damageResistanceCapability.defaultInstance, null) as NBTBase

  override fun deserializeNBT(nbt: NBTBase?) {
    damageResistanceCapability?.storage?.readNBT(damageResistanceCapability, damageResistanceCapability.defaultInstance, null, nbt)
  }
}

object DamageResistanceStorage: Capability.IStorage<DamageResistance> {
  override fun readNBT(capability: Capability<DamageResistance>?, instance: DamageResistance?, side: EnumFacing?, nbt: NBTBase?) {
    instance?.loadFromNBT(nbt as NBTTagCompound)
  }

  override fun writeNBT(capability: Capability<DamageResistance>?, instance: DamageResistance?, side: EnumFacing?): NBTBase? {
    return instance?.createNBT()
  }
}

object DamageResistanceFactory: Callable<DamageResistance> {
  override fun call(): DamageResistance {
    return DamageResistance()
  }
}
