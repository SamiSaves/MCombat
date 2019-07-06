package fi.majavapaja.mcombat

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

const val modId = "majavapajamcombat"
const val modName = "MCombat"
const val version = "0.0.1"

@Mod(
		modid = modId,
		name = modName,
		version = version,
		modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter"
)
object Main {

	@Mod.EventHandler
	fun preInit(event: FMLPreInitializationEvent) {
		println("Hello from Kotlin!")
	}
}