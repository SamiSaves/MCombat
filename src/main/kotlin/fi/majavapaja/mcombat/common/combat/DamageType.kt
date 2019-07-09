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

class DamageType(var type: String = "normal") {
  fun createNBT(): NBTTagCompound {
    val nbt = NBTTagCompound()
    nbt.setString("damage-type", type)
    return nbt
  }

  fun loadFromNBT(nbt: NBTTagCompound) {
    type = nbt.getString("damage-type")
  }
}

class DamageTypeProvider(var type: String = "normal"): ICapabilitySerializable<NBTBase> {
  companion object {
    @CapabilityInject(DamageType::class)
    lateinit var damageTypeCapability: Capability<DamageType>

    fun getDamageType(entity: EntityLivingBase): DamageType? =
      if (entity.hasCapability(damageTypeCapability, null)) {
        entity.getCapability(damageTypeCapability, null)
      } else {
        null
      }

    fun getDamageType(itemStack: ItemStack): DamageType? =
        if (itemStack.hasCapability(damageTypeCapability, null)) {
          itemStack.getCapability(damageTypeCapability, null)
        } else {
          null
        }
  }
  private var instance = DamageType(this.type)

  override fun <T : Any?> getCapability(capability: Capability<T>, facing: EnumFacing?): T? =
      when (capability) {
        damageTypeCapability -> instance as T
        else -> null
      }

  override fun hasCapability(capability: Capability<*>, facing: EnumFacing?): Boolean =
      capability == damageTypeCapability

  override fun serializeNBT(): NBTBase =
    damageTypeCapability?.storage?.writeNBT(damageTypeCapability, damageTypeCapability.defaultInstance, null) as NBTBase

  override fun deserializeNBT(nbt: NBTBase?) {
    damageTypeCapability?.storage?.readNBT(damageTypeCapability, damageTypeCapability.defaultInstance, null, nbt)
  }
}

object DamageTypeStorage: Capability.IStorage<DamageType> {
  override fun readNBT(capability: Capability<DamageType>?, instance: DamageType?, side: EnumFacing?, nbt: NBTBase?) {
    instance?.loadFromNBT(nbt as NBTTagCompound)
  }

  override fun writeNBT(capability: Capability<DamageType>?, instance: DamageType?, side: EnumFacing?): NBTBase? {
    return instance?.createNBT()
  }
}

object DamageTypeFactory: Callable<DamageType> {
  override fun call(): DamageType {
    return DamageType()
  }
}
