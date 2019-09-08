package fi.majavapaja.mcombat.common.command

import fi.majavapaja.mcombat.common.combat.StatOverridesCapability
import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.common.item.ModItems
import fi.majavapaja.mcombat.network.Network
import net.minecraft.command.CommandBase
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.item.ItemStack
import net.minecraft.server.MinecraftServer
import net.minecraft.util.EnumHand
import net.minecraft.util.text.TextComponentString
import java.lang.Float.parseFloat

class DebugStatsCommand : CommandBase() {
  val usage = "/${getName()} OR /${getName()} [damage | resistance] <type> <damage>"

  override fun getName() = "d"

  override fun checkPermission(server: MinecraftServer, sender: ICommandSender): Boolean = true

  override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<String>) {
    val entity = sender.commandSenderEntity!!
    if (!(entity is EntityPlayerMP)) return
    try {
      runCommand(entity, Args.parse(args))
    } catch (e: UserError) {
      sendMessage(sender, "${e.message}\nUsage: $usage")
    }
  }

  private fun runCommand(entity: EntityPlayerMP, args: Args) {
    val stack = itemInHand(entity)
    if (!ModItems.isDebugItem(stack)) {
      throw UserError("Command is only available when holding debug item")
    }

    val override = StatOverridesCapability.getStatOverrides(stack)!!
    when (args) {
      is Args.Show -> {
        sendMessage(entity, "${override}")
      }

      is Args.SetStat -> {
        when (args.stat) {
          Stat.Damage ->
            override.damage = when (args.value) {
              0f -> override.damage - args.type
              else -> override.damage + (args.type to args.value)
            }
          Stat.Resistance ->
            override.resistance = when (args.value) {
              0f -> override.resistance - args.type
              else -> override.resistance + (args.type to args.value)
            }
        }

        Network.jsonTo(override, entity)
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

sealed class Args {
  object Show : Args()
  data class SetStat(val stat: Stat, val type: DamageType, val value: Float) : Args()

  companion object {
    fun parse(args: Array<String>): Args = when {
      args.size == 0 -> Show
      args.size == 3 -> {
        when (val stat = Stat.parse(args[0])) {
          null -> throw UserError("First argument should be either ${Stat.Damage.value} or ${Stat.Resistance.value} (was ${args[0]})")
          else -> {
            val type = DamageType.getDamageType(args[1])
            val value = try {
              parseFloat(args[2])
            } catch (e: NumberFormatException) {
              throw UserError("${args[2]} is not a number")
            }
            if (value < 0) {
              throw UserError("Value should be positive (was ${value})")
            }
            SetStat(stat, type, value)
          }
        }
      }
      else -> throw UserError("Invalid number of arguments")
    }
  }
}

enum class Stat(val value: String) {
  Damage("damage"),
  Resistance("resistance");

  companion object {
    fun parse(s: String): Stat? = when {
      Damage.value.startsWith(s) -> Damage
      Resistance.value.startsWith(s) -> Resistance
      else -> null
    }
  }
}
