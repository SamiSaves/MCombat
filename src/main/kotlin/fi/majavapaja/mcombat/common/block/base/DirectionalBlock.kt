package fi.majavapaja.mcombat.common.block.base

import net.minecraft.block.BlockDirectional
import net.minecraft.block.material.Material
import net.minecraft.block.properties.IProperty
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLivingBase
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.Mirror
import net.minecraft.util.Rotation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

open class DirectionalBlock(
    name: String,
    creativeTab: CreativeTabs,
    material: Material,
    layer: BlockRenderLayer
) : BaseBlock(name, creativeTab, material, layer) {
  init {
    this.defaultState = this.blockState.baseState.withProperty(BlockDirectional.FACING, EnumFacing.SOUTH)
  }

  override fun getStateForPlacement(worldIn: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase): IBlockState {
    return this.defaultState.withProperty(BlockDirectional.FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer).opposite)
  }

  override fun createBlockState(): BlockStateContainer {
    return BlockStateContainer(this, *arrayOf<IProperty<*>>(BlockDirectional.FACING))
  }

  override fun onBlockAdded(worldIn: World, pos: BlockPos, state: IBlockState) {
    if (!worldIn.isRemote)
      worldIn.setBlockState(pos, state.withProperty(BlockDirectional.FACING, EnumFacing.SOUTH), 2)
  }

  override fun withRotation(state: IBlockState, rot: Rotation): IBlockState {
    return state.withProperty(BlockDirectional.FACING, rot.rotate(state.getValue(BlockDirectional.FACING) as EnumFacing))
  }

  override fun withMirror(state: IBlockState, mirrorIn: Mirror): IBlockState {
    return state.withRotation(mirrorIn.toRotation(state.getValue(BlockDirectional.FACING) as EnumFacing))
  }

  override fun getStateFromMeta(meta: Int): IBlockState {
    var enumfacing = EnumFacing.getFront(meta)

    if (enumfacing.axis == EnumFacing.Axis.Y) {
      enumfacing = EnumFacing.NORTH
    }

    return this.defaultState.withProperty(BlockDirectional.FACING, enumfacing)
  }

  override fun getMetaFromState(state: IBlockState): Int {
    return (state.getValue(BlockDirectional.FACING) as EnumFacing).index
  }
}
