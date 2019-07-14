package fi.majavapaja.mcombat.common.combat

import fi.majavapaja.mcombat.common.item.ModItems
import fi.majavapaja.mcombat.modId
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.monster.EntityZombie
import net.minecraft.item.ItemArmor
import net.minecraft.item.ItemBow
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemSword
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.entity.living.LivingHurtEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Suppress("unused")
@Mod.EventBusSubscriber
object Damage {
  private val damageTypeResource = ResourceLocation(modId, "damage_type")
  private val damageResistanceResource = ResourceLocation(modId, "damage_resistance")

  fun initialize() {
    CapabilityManager.INSTANCE.register(DamageType::class.java, DamageTypeStorage, DamageTypeFactory)
    CapabilityManager.INSTANCE.register(DamageResistance::class.java, DamageResistanceStorage, DamageResistanceFactory)
    MinecraftForge.EVENT_BUS.register(this)
    println("Initialized damage stuffs")
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  fun onLivingHurt(event: LivingHurtEvent) = onLivingHurtEvent(event)

  @SubscribeEvent
  fun attachCapabilityEntity(event: AttachCapabilitiesEvent<Entity>) {
    val entity = event.`object`
    if (entity is EntityLiving) {
      var damageType: String
      var damageResistance: DamageResistance

      if (entity is EntityZombie) {
        damageResistance = DamageResistance("holy", 1f)
        damageType = "rotten"
      } else {
        damageResistance = DamageResistance()
        damageType = "normal"
      }

      event.addCapability(damageTypeResource, DamageTypeProvider(damageType))
      event.addCapability(damageResistanceResource, DamageResistanceProvider(damageResistance))
    }
  }

  @SubscribeEvent
  fun attachCapabilityItem(event: AttachCapabilitiesEvent<ItemStack>) {
    val item = event.`object`.item
    if (item is ItemSword || item is ItemBow) {
      val damageType = when (item.registryName) {
        ModItems.debugSword.registryName -> "holy"
        ModItems.debugBow.registryName -> "holy"
        else -> "normal"
      }

      event.addCapability(damageTypeResource, DamageTypeProvider(damageType))
    } else if (item is ItemArmor) {
      println("This armor is ${item.armorMaterial}")

      val damageResistance = when (item.registryName) {
        ModItems.debugChestplate.registryName -> DamageResistance("rotten", -0.4f)
        ModItems.debugLeggings.registryName -> DamageResistance("rotten", -0.35f)
        ModItems.debugHelmet.registryName -> DamageResistance("rotten", -0.15f)
        ModItems.debugBoots.registryName -> DamageResistance("rotten", -0.1f)
        else -> DamageResistance()
      }

      event.addCapability(damageResistanceResource, DamageResistanceProvider(damageResistance))
    }
  }
}