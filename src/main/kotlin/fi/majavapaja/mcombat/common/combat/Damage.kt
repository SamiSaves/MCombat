package fi.majavapaja.mcombat.common.combat

import fi.majavapaja.mcombat.modId
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.monster.EntityMob
import net.minecraft.entity.monster.EntityZombie
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemSword
import net.minecraft.util.ResourceLocation
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.capabilities.CapabilityManager
import net.minecraftforge.event.AttachCapabilitiesEvent
import net.minecraftforge.event.entity.EntityJoinWorldEvent
import net.minecraftforge.event.entity.living.LivingHurtEvent
import net.minecraftforge.event.entity.living.LivingSpawnEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.eventhandler.EventPriority
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

@Suppress("unused")
@Mod.EventBusSubscriber
object Damage {
  private val damageTypeResource = ResourceLocation(modId, "damage_type")

  fun initialize() {
    CapabilityManager.INSTANCE.register(DamageType::class.java, DamageTypeStorage, DamageTypeFactory)
    MinecraftForge.EVENT_BUS.register(this)
    println("Initialized damage stuffs")
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  fun onLivingHurt(event: LivingHurtEvent) = onLivingHurtEvent(event)

  @SubscribeEvent
  fun attachCapabilityEntity(event: AttachCapabilitiesEvent<Entity>) {
    if (event.`object` is EntityLiving) {
      event.addCapability(damageTypeResource, DamageTypeProvider())
    }
  }

  @SubscribeEvent
  fun attachCapabilityItem(event: AttachCapabilitiesEvent<ItemStack>) {
    val item = event.`object`.item
    if (item is ItemSword) {
      val damageType = if (item.toolMaterialName == "DIAMOND") {
        "holy"
      } else {
        "normal"
      }

      event.addCapability(damageTypeResource, DamageTypeProvider(damageType))
    }
  }

  @SubscribeEvent
  fun onEntitySpawn(event: EntityJoinWorldEvent) {
    val entity = event.entity
    if (entity is EntityMob && entity is EntityZombie) {
      val damageType = DamageTypeProvider.getDamageType(entity)
      damageType?.type = "rotten"
      println("Damage type set ${damageType?.type}")
    }
  }
}