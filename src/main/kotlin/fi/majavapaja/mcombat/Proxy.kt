package fi.majavapaja.mcombat

import fi.majavapaja.mcombat.client.render.mob.FireZombieRender
import fi.majavapaja.mcombat.client.render.projectile.DebugArrowRenderer
import fi.majavapaja.mcombat.common.advancement.ModTriggerEvents
import fi.majavapaja.mcombat.common.advancement.ModTriggers
import fi.majavapaja.mcombat.common.block.ModBlocks
import fi.majavapaja.mcombat.common.combat.Damage
import fi.majavapaja.mcombat.common.combat.StatOverridesCapability
import fi.majavapaja.mcombat.common.combat.StatOverridesEventHandler
import fi.majavapaja.mcombat.common.effect.ModEffects
import fi.majavapaja.mcombat.common.enchantment.ModEnchantments
import fi.majavapaja.mcombat.common.entity.DebugArrowEntity
import fi.majavapaja.mcombat.common.entity.FireZombie
import fi.majavapaja.mcombat.common.entity.ModEntities
import fi.majavapaja.mcombat.common.item.ModItems
import fi.majavapaja.mcombat.common.message.HuntMessage
import fi.majavapaja.mcombat.common.message.HuntMessageHandler
import fi.majavapaja.mcombat.common.message.ParticleMessage
import fi.majavapaja.mcombat.common.message.ParticleMessageHandler
import net.minecraft.item.Item
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.client.registry.RenderingRegistry
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@Suppress("unused")
open class CommonProxy(val side: Side) {
  companion object {
    val network = NetworkRegistry.INSTANCE.newSimpleChannel(modId)!!
  }

  open fun preInit(ev: FMLPreInitializationEvent) {
    MinecraftForge.EVENT_BUS.register(this)
    StatOverridesCapability.register()
    MinecraftForge.EVENT_BUS.register(StatOverridesEventHandler)
    MinecraftForge.EVENT_BUS.register(Damage)
    MinecraftForge.EVENT_BUS.register(ModTriggerEvents)

    ModBlocks.registerBlocks()

    network.registerMessage(
        ParticleMessageHandler::class.java,
        ParticleMessage::class.java,
        100,
        Side.CLIENT
    )
    network.registerMessage(
        HuntMessageHandler::class.java,
        HuntMessage::class.java,
        101,
        Side.SERVER
    )
  }

  open fun init(ev: FMLInitializationEvent) {
    ModEffects.registerEffects()
    ModEntities.registerEntities()
    ModEnchantments.register()
    ModTriggers.registerTriggers()
  }
  open fun postInit(ev:FMLPostInitializationEvent) { }
  open fun registerItemRenderer(item: Item, meta: Int, id: String) { }

  @SubscribeEvent
  fun registerItems(event: RegistryEvent.Register<Item>) {
    ModItems.register(event.registry)
  }

}

@SideOnly(Side.CLIENT)
@Suppress("unused")
class ClientProxy: CommonProxy(Side.CLIENT) {
  override fun registerItemRenderer(item: Item, meta: Int, id: String) {
    ModelLoader.setCustomModelResourceLocation(
        item,
        meta,
        ModelResourceLocation("$modId:$id", "inventory")
    )
  }

  override fun preInit(ev: FMLPreInitializationEvent) {
    super.preInit(ev)
    RenderingRegistry.registerEntityRenderingHandler(DebugArrowEntity::class.java, DebugArrowRenderer.factory)
    RenderingRegistry.registerEntityRenderingHandler(FireZombie::class.java) { FireZombieRender(it) }

    MinecraftForge.EVENT_BUS.register(ModItems)

    ModBlocks.registerModels()
  }

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  fun registerItems(event: ModelRegistryEvent) {
    ModItems.registerModels()
  }
}

@SideOnly(Side.SERVER)
@Suppress("unused")
class ServerProxy: CommonProxy(Side.SERVER)
