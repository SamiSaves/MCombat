package fi.majavapaja.mcombat

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

@Mod(modid = "mcombat", modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter")
object Main {

	@Mod.EventHandler
	fun preInit(event: FMLPreInitializationEvent) {
		println("Hello from Kotlin!")
	}

}