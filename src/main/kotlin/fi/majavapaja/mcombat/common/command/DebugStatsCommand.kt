package fi.majavapaja.mcombat.common.command

import fi.majavapaja.mcombat.common.combat.StatOverridesCapability
import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.common.item.ModItems
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.server.MinecraftServer
import net.minecraft.util.EnumHand
import net.minecraft.util.text.TextComponentString
import java.lang.Float.parseFloat
import java.lang.RuntimeException

class DebugStatsCommand() : CommandBase() {
  val usage = "/${getName()} <type> <damage>"

  override fun getName() = "d"

  override fun checkPermission(server: MinecraftServer, sender: ICommandSender): Boolean = true

  override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
    val entity = sender.commandSenderEntity!!
    try {
      if (!(entity is EntityPlayer)) return
      val stack = itemInHand(entity)
      if (!ModItems.isDebugItem(stack)) {
        throw UserError("Command is only available when holding debug item")
      }

      val override = StatOverridesCapability.getStatOverrides(stack)!!
      if (args.size == 0) {
        sendMessage(entity, "${override}")
        return
      }

      val (type, damage) = parseArgs(args)
      override.damage = override.damage + (type to damage)
      sendMessage(entity, "${override}")

    } catch (e: UserError) {
      sendMessage(entity, "${e.message}\nUsage: $usage")
    }
  }

  override fun getUsage(sender: ICommandSender): String = usage

  fun sendMessage(p: ICommandSender, message: String) =
    p.sendMessage(TextComponentString(message))

  fun itemInHand(p: EntityPlayer): ItemStack =
    p.getHeldItem(EnumHand.MAIN_HAND)

  fun parseArgs(args: Array<String>): Pair<DamageType, Float> {
    if (args.size != 2) {
      throw UserError("Command requires two parameters")
    }

    val type = DamageType.getDamageType(args[0])
    val damage = try {
      parseFloat(args[1])
    } catch (e: NumberFormatException) {
      throw UserError("${args[1]} is not a number")
    }

    if (damage < 0) {
      throw UserError("Damage should be positive (was ${damage})")
    }

    return Pair(type, damage)
  }
}

class UserError(override val message: String): RuntimeException(message)
