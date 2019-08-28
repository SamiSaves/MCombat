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

sealed class Args {
  object Show : Args()
  data class SetDamage(val type: DamageType, val damage: Float) : Args()

  companion object {
    fun parse(args: Array<String>): Args = when {
      args.size == 0 -> Args.Show
      args.size == 2 -> {
        val type = DamageType.getDamageType(args[0])
        val damage = try {
          parseFloat(args[1])
        } catch (e: NumberFormatException) {
          throw UserError("${args[1]} is not a number")
        }
        if (damage < 0) {
          throw UserError("Damage should be positive (was ${damage})")
        }
        Args.SetDamage(type, damage)
      }
      else -> throw UserError("Invalid number of arguments")
    }

  }
}

class DebugStatsCommand() : CommandBase() {
  val usage = "/${getName()} OR /${getName()} <type> <damage>"

  override fun getName() = "d"

  override fun checkPermission(server: MinecraftServer, sender: ICommandSender): Boolean = true

  override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
    val entity = sender.commandSenderEntity!!
    if (!(entity is EntityPlayer)) return
    try {
      runCommand(entity, Args.parse(args))
    } catch (e: UserError) {
      sendMessage(sender, "${e.message}\nUsage: $usage")
    }
  }

  private fun runCommand(entity: EntityPlayer, args: Args) {
    val stack = itemInHand(entity)
    if (!ModItems.isDebugItem(stack)) {
      throw UserError("Command is only available when holding debug item")
    }

    val override = StatOverridesCapability.getStatOverrides(stack)!!
    when (args) {
      is Args.Show -> {
        sendMessage(entity, "${override}")
      }

      is Args.SetDamage -> {
        override.damage = when (args.damage) {
          0f -> override.damage - args.type
          else -> override.damage + (args.type to args.damage)
        }
        sendMessage(entity, "${override}")
      }
    }
  }

  override fun getUsage(sender: ICommandSender): String = usage

  fun sendMessage(p: ICommandSender, message: String) =
    p.sendMessage(TextComponentString(message))

  fun itemInHand(p: EntityPlayer): ItemStack =
    p.getHeldItem(EnumHand.MAIN_HAND)
}

class UserError(override val message: String): RuntimeException(message)
