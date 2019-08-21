package fi.majavapaja.mcombat.common.block

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.common.block.base.DirectionalBlock
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.item.Item
import net.minecraft.util.BlockRenderLayer
import java.util.*

class NoticeBoard : DirectionalBlock(
  name = "notice_board",
  creativeTab = Main.creativeTab,
  material = Material.WOOD,
  layer = BlockRenderLayer.SOLID
) {
  override fun getItemDropped(state: IBlockState, rand: Random, fortune: Int): Item? = null
}
