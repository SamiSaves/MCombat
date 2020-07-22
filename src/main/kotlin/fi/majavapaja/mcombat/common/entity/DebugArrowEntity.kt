package fi.majavapaja.mcombat.common.entity

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.common.item.ModItems
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityClassification
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.projectile.ArrowEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.network.IPacket
import net.minecraft.world.World
import net.minecraftforge.event.RegistryEvent
import net.minecraftforge.fml.network.NetworkHooks

class DebugArrowEntity: ArrowEntity, IWeaponArrow {
  companion object {
    val entityType = EntityType.Builder.create(::DebugArrowEntity, EntityClassification.MISC)
        .build("debug_arrow")
        .setRegistryName(Main.MOD_ID, "debug_arrow") as EntityType<DebugArrowEntity>

    fun register(event: RegistryEvent.Register<EntityType<out Entity>>) {
      event.registry.register(entityType)
    }
  }

  override var customDamage = ModItems.debugArrow.damage

  constructor (entityType: EntityType<out DebugArrowEntity>, world: World): super(entityType, world)
  @Suppress("unused")
  constructor (world: World, shooter: LivingEntity): super(world, shooter)
  @Suppress("unused")
  constructor (world: World, x: Double, y: Double, z: Double): super(world, x, y, z)

  override fun getType(): EntityType<*> = entityType
  override fun getArrowStack(): ItemStack = ItemStack(ModItems.debugArrow)

  override fun createSpawnPacket(): IPacket<*> {
    return NetworkHooks.getEntitySpawningPacket(this)
  }
}
