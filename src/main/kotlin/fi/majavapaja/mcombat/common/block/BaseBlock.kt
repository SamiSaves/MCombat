package fi.majavapaja.mcombat.common.block

import fi.majavapaja.mcombat.modId
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.ItemBlock
import net.minecraft.util.BlockRenderLayer
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.common.registry.ForgeRegistries
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

open class BaseBlock(
    val name: String,
    creativeTab: CreativeTabs,
    material: Material,
    val layer: BlockRenderLayer
) : Block(material) {
  init {
    unlocalizedName = name
    setRegistryName(name)
    setCreativeTab(creativeTab)
  }

  @SideOnly(Side.CLIENT)
  override fun getBlockLayer(): BlockRenderLayer = layer

  fun registerBlock() {
    ForgeRegistries.BLOCKS.register(this)
    val item = ItemBlock(this)
    item.registryName = this.registryName

    ForgeRegistries.ITEMS.register(item)

    val model = ModelResourceLocation("$modId:${this.name}", "inventory")
    ModelLoader.setCustomModelResourceLocation(item, 0, model)
  }
}
