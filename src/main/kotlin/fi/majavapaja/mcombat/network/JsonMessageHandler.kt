package fi.majavapaja.mcombat.network

import fi.majavapaja.mcombat.Serializer
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

class JsonMessageHandler : IMessageHandler<JsonMessage, JsonMessage> {
  override fun onMessage(message: JsonMessage, ctx: MessageContext): JsonMessage? {
    println("JsonMessageHandler.onMessage($message, $ctx)")

    val handler = NetworkHandlers.getHandler(message.typeId!!)
    return handler(deserialize(message), ctx)?.let {reply -> serialize(reply) }
  }

  private inline fun <reified T : Any> serialize(reply: T): JsonMessage =
    JsonMessage(NetworkHandlers.getTypeId(reply::class), Serializer.toJson(reply))

  private fun deserialize(message: JsonMessage): Any {
    val type = NetworkHandlers.getType(message.typeId!!)
    return Serializer.fromJson(message.json!!, type)
  }
}

