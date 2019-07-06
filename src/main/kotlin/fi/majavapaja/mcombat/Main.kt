package fi.majavapaja.mcombat

import fi.majavapaja.mcombat.common.item.ModItems
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.SidedProxy
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

const val modId = "majavapajamcombat"
const val modName = "MCombat"
const val version = "0.0.1"

@Suppress("unused")
@Mod(
		modid = modId,
		name = modName,
		version = version,
		modLanguageAdapter = "net.shadowfacts.forgelin.KotlinAdapter"
)
object Main {
	@SidedProxy (
			clientSide = "fi.majavapaja.mcombat.ClientProxy",
			serverSide = "fi.majavapaja.mcombat.ServerSide"
	)
	lateinit var proxy: CommonProxy

	val creativeTab: CreativeTabs = object : CreativeTabs(modId) {
		override fun getIconItemStack() = ItemStack(ModItems.majavapaja)
		override fun getTabIconItem() = this.iconItemStack
		override fun getTabLabel(): String = modName
	}

	@Mod.EventHandler
	fun preInit(event: FMLPreInitializationEvent) {
    proxy.preInit(event)
	}

	@Mod.EventHandler
	fun init(event: FMLInitializationEvent) {
		proxy.init(event)
	}

	@Mod.EventHandler
	fun init(event: FMLPostInitializationEvent) {
		proxy.postInit(event)
	}
}