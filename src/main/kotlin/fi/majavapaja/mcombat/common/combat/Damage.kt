package fi.majavapaja.mcombat.common.combat

import fi.majavapaja.mcombat.common.capability.DamageResistance
import fi.majavapaja.mcombat.common.capability.DamageResistanceProvider
import fi.majavapaja.mcombat.common.capability.DamageType
import fi.majavapaja.mcombat.common.capability.DamageTypeProvider
import fi.majavapaja.mcombat.common.entity.DebugArrowEntity
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
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.entity.living.LivingHurtEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Mod.EventBusSubscriber
object Damage {
  private val damageTypeResource = ResourceLocation(modId, "damage_type")
  private val damageResistanceResource = ResourceLocation(modId, "damage_resistance")

  fun initialize() {
    DamageTypeProvider.register()
    DamageResistanceProvider.register()
    MinecraftForge.EVENT_BUS.register(this)
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  fun onLivingHurt(event: LivingHurtEvent) = onLivingHurtEvent(event)

  @SubscribeEvent
  fun attachCapabilityEntity(event: AttachCapabilitiesEvent<Entity>) {
    val entity = event.`object`
    if (entity is EntityLiving) {
      val damageType = DamageType()
      val damageResistance: DamageResistance

      if (entity is EntityZombie) {
        damageResistance = DamageResistance(DamageType("holy"), 1f)
        damageType.type = "rotten"
      } else {
        damageResistance = DamageResistance(DamageType())
      }

      event.addCapability(damageTypeResource, DamageTypeProvider(damageType))
      event.addCapability(damageResistanceResource, DamageResistanceProvider(damageResistance))
    } else if (entity is DebugArrowEntity) {
      event.addCapability(damageTypeResource, DamageTypeProvider(DamageType("holy")))
    }
  }

  @SubscribeEvent
  fun attachCapabilityItem(event: AttachCapabilitiesEvent<ItemStack>) {
    val item = event.`object`.item
    if (item is ItemSword || item is ItemBow) {
      val damageType = when (item.registryName) {
        ModItems.debugSword.registryName -> DamageType("holy")
        ModItems.debugBow.registryName -> DamageType("holy")
        else -> DamageType()
      }

      event.addCapability(damageTypeResource, DamageTypeProvider(damageType))
    } else if (item is ItemArmor) {
      val damageResistance = when (item.registryName) {
        ModItems.debugChestplate.registryName -> DamageResistance(DamageType("rotten"), -0.4f)
        ModItems.debugLeggings.registryName -> DamageResistance(DamageType("rotten"), -0.35f)
        ModItems.debugHelmet.registryName -> DamageResistance(DamageType("rotten"), -0.15f)
        ModItems.debugBoots.registryName -> DamageResistance(DamageType("rotten"), -0.1f)
        else -> DamageResistance(DamageType())
      }

      event.addCapability(damageResistanceResource, DamageResistanceProvider(damageResistance))
    }
  }
}