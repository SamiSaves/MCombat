package fi.majavapaja.mcombat.network

import fi.majavapaja.mcombat.Serializer
import fi.majavapaja.mcombat.modId
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import net.minecraftforge.fml.relauncher.Side
import kotlin.reflect.KClass

typealias MessageHandler<Request, Response> = (Request, MessageContext) -> Response?

object Network {

  private var DISCRIMINATOR = 100
  private fun nextDiscriminator(): Int = DISCRIMINATOR++
  private val channel = NetworkRegistry.INSTANCE.newSimpleChannel(modId)!!

  fun init() {
    registerForgeHandler(JsonMessageHandler::class, Side.CLIENT)
    registerForgeHandler(JsonMessageHandler::class, Side.SERVER)
  }

  private inline fun <H : IMessageHandler<M, R>, reified M : IMessage, R : IMessage> registerForgeHandler(handler: KClass<H>, side: Side) =
    channel.registerMessage(handler.java, M::class.java, nextDiscriminator(), side)

  internal inline fun <reified Request : Any> jsonToServer(message: Request) {
    val id = NetworkHandlers.getTypeId(Request::class)
    channel.sendToServer(JsonMessage(id, Serializer.toJson(message)))
  }

  internal inline fun <reified Request : Any> jsonToAllAround(message: Request, target: NetworkRegistry.TargetPoint) {
    val id = NetworkHandlers.getTypeId(Request::class)
    channel.sendToAllAround(JsonMessage(id, Serializer.toJson(message)), target)
  }

  internal inline fun <reified Request : Any> jsonTo(message: Request, entity: EntityPlayerMP) {
    val id = NetworkHandlers.getTypeId(Request::class)
    channel.sendTo(JsonMessage(id, Serializer.toJson(message)), entity)
  }
}

