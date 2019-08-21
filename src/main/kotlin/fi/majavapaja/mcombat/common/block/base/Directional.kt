package fi.majavapaja.mcombat.common.block.base

import fi.majavapaja.mcombat.modId
import net.minecraft.block.Block
import net.minecraft.block.BlockDirectional
import net.minecraft.block.material.Material
import net.minecraft.block.properties.IProperty
import net.minecraft.block.state.BlockStateContainer
import net.minecraft.block.state.IBlockState
import net.minecraft.client.renderer.block.model.ModelResourceLocation
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemBlock
import net.minecraft.util.BlockRenderLayer
import net.minecraft.util.EnumFacing
import net.minecraft.util.Mirror
import net.minecraft.util.Rotation
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.common.registry.ForgeRegistries
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

open class Directional(
    val name: String,
    creativeTab: CreativeTabs,
    material: Material,
    val layer: BlockRenderLayer
) : BlockDirectional(material) {
  var item = ItemBlock(this)

  init {
    unlocalizedName = name
    setRegistryName(name)
    setCreativeTab(creativeTab)
    this.defaultState = this.blockState.baseState.withProperty(FACING, EnumFacing.SOUTH)
  }

  override fun getStateForPlacement(worldIn: World, pos: BlockPos, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float, meta: Int, placer: EntityLivingBase): IBlockState {
    return this.defaultState.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer).opposite)
  }

  override fun createBlockState(): BlockStateContainer {
    return BlockStateContainer(this, *arrayOf<IProperty<*>>(FACING))
  }

  override fun onBlockAdded(worldIn: World, pos: BlockPos, state: IBlockState) {
    if (!worldIn.isRemote)
      worldIn.setBlockState(pos, state.withProperty(FACING, EnumFacing.SOUTH), 2)
  }

  override fun withRotation(state: IBlockState, rot: Rotation): IBlockState {
    return state.withProperty(FACING, rot.rotate(state.getValue(FACING) as EnumFacing))
  }

  override fun withMirror(state: IBlockState, mirrorIn: Mirror): IBlockState {
    return state.withRotation(mirrorIn.toRotation(state.getValue(FACING) as EnumFacing))
  }

  override fun getStateFromMeta(meta: Int): IBlockState {
    var enumfacing = EnumFacing.getFront(meta)

    if (enumfacing.axis == EnumFacing.Axis.Y) {
      enumfacing = EnumFacing.NORTH
    }

    return this.defaultState.withProperty(FACING, enumfacing)
  }

  override fun getMetaFromState(state: IBlockState): Int {
    return (state.getValue(FACING) as EnumFacing).index
  }

  @SideOnly(Side.CLIENT)
  override fun getBlockLayer(): BlockRenderLayer = layer

  @SideOnly(Side.CLIENT)
  fun registerModel () {
    val model = ModelResourceLocation("$modId:${this.name}", "inventory")
    ModelLoader.setCustomModelResourceLocation(item, 0, model)
  }

  fun registerBlock() {
    ForgeRegistries.BLOCKS.register(this)

    item.registryName = this.registryName
    ForgeRegistries.ITEMS.register(item)
  }
}
