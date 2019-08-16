package fi.majavapaja.mcombat.common.block

import fi.majavapaja.mcombat.Main
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.util.BlockRenderLayer
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly


class MajavaBlock : BaseBlock(
  name = "majava_block",
  creativeTab = Main.creativeTab,
  material = Material.ROCK,
  layer = BlockRenderLayer.TRANSLUCENT
) {
  init {
    println("Initializing MajavaBlock")
  }
}
