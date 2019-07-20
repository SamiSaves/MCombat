package fi.majavapaja.mcombat.common.command

import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.server.MinecraftServer
import net.minecraft.util.DamageSource

class FooCommand() : CommandBase() {
  override fun getName() = "foo"

  override fun checkPermission(server: MinecraftServer, sender: ICommandSender): Boolean = true

  override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
    val entity = sender.commandSenderEntity
    when (entity) {
      is EntityPlayer -> kill(entity)
      else -> println("fukk")
    }
  }

  override fun getUsage(sender: ICommandSender): String {
    return "Just do it"
  }

  fun kill(e: EntityPlayer) = e.attackEntityFrom(DamageSource.OUT_OF_WORLD, Float.MAX_VALUE);
}
