package fi.majavapaja.mcombat.common.block.base

import net.minecraft.block.BlockHorizontal
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
    this.defaultState = this.blockState.baseState.withProperty(BlockHorizontal.FACING, EnumFacing.SOUTH)
  }

  override fun getStateForPlacement(worldIn: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase): IBlockState {
    return this.defaultState.withProperty(BlockHorizontal.FACING, placer.horizontalFacing.opposite)
  }

  override fun createBlockState(): BlockStateContainer {
    return BlockStateContainer(this, *arrayOf<IProperty<*>>(BlockHorizontal.FACING))
  }

  override fun withRotation(state: IBlockState, rot: Rotation): IBlockState {
    return state.withProperty(BlockHorizontal.FACING, rot.rotate(state.getValue(BlockHorizontal.FACING) as EnumFacing))
  }

  override fun withMirror(state: IBlockState, mirrorIn: Mirror): IBlockState {
    return state.withRotation(mirrorIn.toRotation(state.getValue(BlockHorizontal.FACING) as EnumFacing))
  }

  override fun getStateFromMeta(meta: Int): IBlockState {
    return this.defaultState.withProperty(BlockHorizontal.FACING, EnumFacing.getHorizontal(meta))
  }

  override fun getMetaFromState(state: IBlockState): Int {
    return (state.getValue(BlockHorizontal.FACING) as EnumFacing).horizontalIndex
  }
}
