package fi.majavapaja.mcombat.common.block.base

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.common.item.Groups
import net.minecraft.block.Block
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.material.Material
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.BlockRenderLayer
import net.minecraftforge.event.RegistryEvent

open class BaseBlock(
    val name: String,
    material: Material,
    val layer: BlockRenderLayer
) : Block(Properties.create(material)) {
  private val itemProperties = Item.Properties().group(Groups.DEFAULT)
  val item = BlockItem(this, itemProperties)

  init {
    setRegistryName(Main.MOD_ID, name)
  }

  fun registerBlock(event: RegistryEvent.Register<Block>) {
    event.registry.register(this)
  }

  fun registerItem(event: RegistryEvent.Register<Item>) {
    item.registryName = this.registryName
    event.registry.register(item)
  }

  override fun getRenderLayer(): BlockRenderLayer {
    return layer;
  }
}
