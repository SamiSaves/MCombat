package fi.majavapaja.mcombat.common.advancement

import net.minecraft.entity.player.EntityPlayerMP
import net.minecraftforge.event.entity.player.PlayerInteractEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent

object ModTriggerEvents {
  @SubscribeEvent
  fun triggerPlayerInteractBlock(event: PlayerInteractEvent.RightClickBlock) {
    if (!event.world.isRemote && event.entityPlayer is EntityPlayerMP) {
      val player = event.entityPlayer as EntityPlayerMP

      val block = event.world.getBlockState(event.pos).block
      ModTriggers.playerInteractBlock.trigger(player, block)
    }
  }
}