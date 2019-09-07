package fi.majavapaja.mcombat.common.message

import ibxm.Player
import io.netty.buffer.ByteBuf
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.AdvancementList
import net.minecraft.advancements.AdvancementManager
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.util.ResourceLocation
import net.minecraft.world.World
import net.minecraft.world.WorldServer
import net.minecraftforge.common.DimensionManager
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import net.minecraftforge.fml.relauncher.Side
import javax.annotation.Resource

class HuntMessage(
    var advancementName: String = "",
    var advancementCriterion: String = ""
) : IMessage {
  companion object {
    private val validCriterions = arrayOf("start_quest", "finish_quest")
    fun isValidCriterion(criterion: String): Boolean = validCriterions.contains(criterion)
  }
  // This empty constructor call is needed for Minecraft
  // @Suppress("unused")
  // constructor(): this("", "") { }

  override fun toBytes(buf: ByteBuf) {
    buf.writeString(advancementName)
    buf.writeString(advancementCriterion)
  }

  override fun fromBytes(buf: ByteBuf) {
    advancementName = buf.readString()
    advancementCriterion = buf.readString()
  }
}

class HuntMessageHandler : IMessageHandler<HuntMessage, Nothing> {
  override fun onMessage(message: HuntMessage, ctx: MessageContext): Nothing? {
    if (ctx.side != Side.SERVER) {
      System.err.println("HuntMessage received on wrong side: ${ctx.side}")
      return null
    }

    val sender = ctx.serverHandler.player
    val world = DimensionManager.getWorld(0) as WorldServer
    val advancement = world.advancementManager.getAdvancement(ResourceLocation(message.advancementName))

    if (advancement == null) {
      System.err.println("Advancement ${message.advancementName} was not found")
      return null
    }

    if (!HuntMessage.isValidCriterion(message.advancementCriterion)) {
      System.err.println("Client sent illegal criterion ${message.advancementCriterion} for hunt ${message.advancementName}")
      return null
    }

    val huntProggress = sender.advancements.getProgress(advancement)
    when (message.advancementCriterion) {
      "start_quest" -> huntProggress.grantCriterion(message.advancementCriterion)
      "finish_quest" -> {
        if (huntProggress.completedCriteria.contains("start_quest")) {
          huntProggress.grantCriterion(message.advancementCriterion)
        } else {
          println("Can't complete the hunt because player has not started it >:(")
        }
      }
    }

    return null
  }
}

