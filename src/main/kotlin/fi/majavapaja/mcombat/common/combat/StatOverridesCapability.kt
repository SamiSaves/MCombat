package fi.majavapaja.mcombat.common.combat

import fi.majavapaja.mcombat.common.capability.CapabilityProvider
import fi.majavapaja.mcombat.common.capability.CapabilityUtil
import fi.majavapaja.mcombat.common.item.ModItems
import fi.majavapaja.mcombat.common.message.readDamageType
import fi.majavapaja.mcombat.common.message.readMap
import fi.majavapaja.mcombat.common.message.writeDamageType
import fi.majavapaja.mcombat.common.message.writeMap
import fi.majavapaja.mcombat.modId
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumFacing
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.common.capabilities.*
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

data class StatOverrides(
  var damage: Map<DamageType, Float> = emptyMap(),
  var resistance: Map<DamageType, Float> = emptyMap()
)

object StatOverridesCapability {
  @CapabilityInject(StatOverrides::class)
  lateinit var CAPABILITY: Capability<StatOverrides>
  val RESOURCE = ResourceLocation(modId, "damage_override")

  val FACING = null

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

class UpdateStatOverridesMessageHandler : IMessageHandler<UpdateStatOverridesMessage, IMessage> {
  override fun onMessage(message: UpdateStatOverridesMessage, ctx: MessageContext): IMessage? {
    val minecraft = Minecraft.getMinecraft()
    val worldClient = minecraft.world
    minecraft.addScheduledTask { processMessage(message, worldClient) }
    return null
  }

  private fun processMessage(message: UpdateStatOverridesMessage, world: World) {
    val minecraft = Minecraft.getMinecraft()
    val player = minecraft.player
    when (val overrides = StatOverridesCapability.getStatOverrides(player.heldItemMainhand)) {
      null -> println("Item in main hand does not have StatOverrides")
      else -> {
        overrides.damage = message.overrides.damage
        overrides.resistance = message.overrides.resistance
      }
    }
  }
}

data class UpdateStatOverridesMessage(val overrides: StatOverrides = StatOverrides()) : IMessage {
  override fun toBytes(buf: ByteBuf) {
    buf.writeMap(overrides.damage) { b, entry ->
      b.writeDamageType(entry.key)
      b.writeFloat(entry.value)
    }
    buf.writeMap(overrides.resistance) { b, entry ->
      b.writeDamageType(entry.key)
      b.writeFloat(entry.value)
    }
  }

  override fun fromBytes(buf: ByteBuf) {
    overrides.damage = buf.readMap { b ->
      val type = b.readDamageType()
      val value = b.readFloat()
      type to value
    }
    overrides.resistance = buf.readMap { b ->
      val type = b.readDamageType()
      val value = b.readFloat()
      type to value
    }
  }
}

private class StatOverrideStorage : Capability.IStorage<StatOverrides> {
  override fun readNBT(capability: Capability<StatOverrides>, instance: StatOverrides, side: EnumFacing?, nbt: NBTBase) {
    nbt as NBTTagCompound
    instance.damage = DamageType.values()
      .filter { nbt.hasKey(damageKey(it)) }
      .map { it to nbt.getFloat(damageKey(it)) }.toMap()
    instance.resistance = DamageType.values()
      .filter { nbt.hasKey(resistanceKey(it)) }
      .map { it to nbt.getFloat(resistanceKey(it)) }.toMap()
  }

  override fun writeNBT(capability: Capability<StatOverrides>, instance: StatOverrides, side: EnumFacing?): NBTBase {
    val nbt = NBTTagCompound()
    instance.damage.forEach {
      nbt.setFloat(damageKey(it.key), it.value)
    }
    instance.resistance.forEach {
      nbt.setFloat(resistanceKey(it.key), it.value)
    }
    return nbt
  }

  private fun damageKey(type: DamageType) = statKey("damage", type)
  private fun resistanceKey(type: DamageType) = statKey("resistance", type)
  private fun statKey(stat: String, type: DamageType) = "$stat.${type.type}"
}
