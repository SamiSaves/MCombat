package fi.majavapaja.mcombat

import fi.majavapaja.mcombat.common.block.ModBlocks
import fi.majavapaja.mcombat.common.item.ModItems
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent
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
	}

	private fun registerBlocks(event: RegistryEvent.Register<Block>) {
		ModBlocks.registerBlocks(event)
	}

	private fun registerItems(event: RegistryEvent.Register<Item>) {
		ModBlocks.registerItems(event)
		ModItems.registerItems(event)
	}
}