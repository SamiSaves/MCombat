package fi.majavapaja.mcombat.common.combat

import fi.majavapaja.mcombat.common.capability.CapabilityProvider
import fi.majavapaja.mcombat.common.capability.CapabilityUtil
import fi.majavapaja.mcombat.common.item.ModItems
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

data class StatOverrides(
  var damage: Map<DamageType, Float> = emptyMap(),
  var resistance: Map<DamageType, Float> = emptyMap()
)

object StatOverridesCapability {
  @CapabilityInject(StatOverrides::class)
  private lateinit var CAPABILITY: Capability<StatOverrides>
  val RESOURCE = ResourceLocation(modId, "damage_override")

  private val FACING = null

  fun register() {
    CapabilityManager.INSTANCE.register(StatOverrides::class.java, StatOverrideStorage(), this::mkDefault)
  }

  fun getStatOverrides(stack: ItemStack): StatOverrides? =
    CapabilityUtil.getCapability(stack, CAPABILITY, FACING)

  fun createProvider(overrides: StatOverrides) =
    CapabilityProvider(CAPABILITY, FACING, overrides)

  fun mkDefault(): StatOverrides = StatOverrides()
}

@Mod.EventBusSubscriber(modid = modId)
object StatOverridesEventHandler {
  @Suppress("unused")
  @SubscribeEvent
  fun attachCapabilities(event: AttachCapabilitiesEvent<ItemStack>) {
    // Attach StatOverridesCapability to only debug items
    if (ModItems.isDebugItem(event.`object`)) {
      val overrides = StatOverridesCapability.mkDefault()
      event.addCapability(StatOverridesCapability.RESOURCE, StatOverridesCapability.createProvider(overrides))
    }
  }
}

private class StatOverrideStorage : Capability.IStorage<StatOverrides> {
  override fun readNBT(capability: Capability<StatOverrides>, instance: StatOverrides, side: EnumFacing?, nbt: NBTBase) {
    nbt as NBTTagCompound
    instance.damage = DamageType.values()
      .filter { nbt.hasKey("damage.${it.type}") }
      .map { it to nbt.getFloat(it.type) }.toMap()
    instance.resistance = DamageType.values()
      .filter { nbt.hasKey("resistance.${it.type}") }
      .map { it to nbt.getFloat(it.type) }.toMap()
  }

  override fun writeNBT(capability: Capability<StatOverrides>, instance: StatOverrides, side: EnumFacing?): NBTBase {
    val nbt = NBTTagCompound()
    instance.damage.forEach {
      nbt.setFloat("damage.${it.key.type}", it.value)
    }
    instance.resistance.forEach {
      nbt.setFloat("resistance.${it.key.type}", it.value)
    }
    return nbt
  }
}
