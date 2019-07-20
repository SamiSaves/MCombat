package fi.majavapaja.mcombat.common.block

import fi.majavapaja.mcombat.Main
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.util.BlockRenderLayer
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

open class BaseBlock(
  val name: String,
  creativeTab: CreativeTabs,
  material: Material,
  val layer: BlockRenderLayer
) : Block(material) {
  init {
    setUnlocalizedName(name)
    setCreativeTab(creativeTab)
    setRegistryName(name)
  }

  @SideOnly(Side.CLIENT)
  override fun getBlockLayer(): BlockRenderLayer = layer
}

class MajavaBlock : BaseBlock(
  name = "majava_block",
  creativeTab = Main.creativeTab,
  material = Material.ROCK,
  layer = BlockRenderLayer.SOLID
) {
  init {
    println("Initializing MajavaBlock")
  }
}
