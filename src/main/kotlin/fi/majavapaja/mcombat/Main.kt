package fi.majavapaja.mcombat

import fi.majavapaja.mcombat.client.renderers.ModRenders
import fi.majavapaja.mcombat.common.block.ModBlocks
import fi.majavapaja.mcombat.common.entity.ModEntities
import fi.majavapaja.mcombat.common.item.ModItems
import net.minecraft.block.Block
import net.minecraft.client.renderer.model.Model
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.tags.Tag
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.eventbus.api.IEventBus
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(Main.MOD_ID)
object Main {
  const val MOD_ID: String = "mcombat"
  val logger: Logger = LogManager.getLogger()

	init {
    MOD_BUS.addGenericListener(::registerBlocks)
    MOD_BUS.addGenericListener(::registerItems)
    MOD_BUS.addGenericListener(::registerEntities)

    MOD_BUS.addListener(::onSetupClient)
	}

	private fun registerBlocks(event: RegistryEvent.Register<Block>) {
		ModBlocks.registerBlocks(event)
	}

	private fun registerItems(event: RegistryEvent.Register<Item>) {
		ModItems.registerItems(event)
		ModBlocks.registerItems(event)
		ModEntities.registerEggs(event)
	}

	private fun registerEntities(event: RegistryEvent.Register<EntityType<out Entity>>) {
    ModEntities.registerEntities(event)
	}

	private fun onSetupClient(event: FMLClientSetupEvent) {
		logger.log(Level.INFO, "Initing client KEKW")
		ModRenders.registerRenders()
	}
}
