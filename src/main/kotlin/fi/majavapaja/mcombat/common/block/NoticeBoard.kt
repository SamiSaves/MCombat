package fi.majavapaja.mcombat.common.block

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.client.render.gui.NoticeBoardGui
import fi.majavapaja.mcombat.common.advancement.ModTriggers
import fi.majavapaja.mcombat.common.block.base.DirectionalBlock
import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.Item
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import java.util.*

class NoticeBoard : DirectionalBlock(
  name = "notice_board",
  creativeTab = Main.creativeTab,
  material = Material.WOOD,
  layer = BlockRenderLayer.SOLID
) {
  override fun getItemDropped(state: IBlockState, rand: Random, fortune: Int): Item? = null

  override fun onBlockActivated(worldIn: World, pos: BlockPos, state: IBlockState, playerIn: EntityPlayer, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): Boolean {
    if (worldIn.isRemote) {
      Minecraft.getMinecraft().displayGuiScreen(NoticeBoardGui())
    } else {
      if (playerIn is EntityPlayerMP) {
        ModTriggers.openNoticeBoardTrigger.trigger(playerIn, this)
      }
    }

    return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ)
  }
}
