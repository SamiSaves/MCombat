package fi.majavapaja.mcombat

import fi.majavapaja.mcombat.common.combat.Damage
import fi.majavapaja.mcombat.common.effect.ModEffects
import fi.majavapaja.mcombat.common.item.ModItems
import net.minecraft.item.Item
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

@Suppress("unused")
open class CommonProxy(val side: Side) {
  open fun preInit(ev: FMLPreInitializationEvent) {
    MinecraftForge.EVENT_BUS.register(this)
    Damage.initialize()
  }

  open fun init(ev: FMLInitializationEvent) {
    ModEffects.registerEffects()
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

  @SubscribeEvent
  @SideOnly(Side.CLIENT)
  fun registerItems(event: ModelRegistryEvent) {
    ModItems.registerModels()
  }
}

@SideOnly(Side.SERVER)
@Suppress("unused")
class ServerProxy: CommonProxy(Side.SERVER)
