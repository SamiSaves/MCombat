package fi.majavapaja.mcombat.common.entity

import fi.majavapaja.mcombat.common.item.ModItems
import fi.majavapaja.mcombat.modId
import net.minecraft.client.renderer.entity.Render
import net.minecraft.client.renderer.entity.RenderArrow
import net.minecraft.client.renderer.entity.RenderManager
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.item.ItemStack
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraftforge.fml.client.registry.IRenderFactory

class DebugArrowEntity: EntityArrow {
  @Suppress("unused")
  constructor (world: World): super(world)
  constructor (world: World, shooter: EntityLivingBase): super(world, shooter)
  @Suppress("unused")
  constructor (world: World, x: Double, y: Double, z: Double): super(world, x, y, z)

  override fun getArrowStack(): ItemStack {
    return ItemStack(ModItems.debugArrow)
  }
}

class DebugArrowRenderer(renderManager: RenderManager): RenderArrow<DebugArrowEntity>(renderManager) {
  private val texture = ResourceLocation("$modId:textures/entity/debug_arrow.png")

  companion object {
    val factory = Render()
  }

  override fun getEntityTexture(entity: DebugArrowEntity): ResourceLocation? {
    return texture
  }
}

class Render: IRenderFactory<DebugArrowEntity> {
  override fun createRenderFor(renderManager: RenderManager): Render<in DebugArrowEntity> {
    return DebugArrowRenderer(renderManager)
  }
}
