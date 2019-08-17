package fi.majavapaja.mcombat.common.block

import fi.majavapaja.mcombat.Main
import net.minecraft.block.material.Material
import net.minecraft.util.BlockRenderLayer

class MajavaBlock : BaseBlock(
  name = "majava_block",
  creativeTab = Main.creativeTab,
  material = Material.ROCK,
  layer = BlockRenderLayer.TRANSLUCENT
)
