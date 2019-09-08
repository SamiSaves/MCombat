package fi.majavapaja.mcombat.network

import kotlin.reflect.KClass

object NetworkHandlers {
  // Type to indicate the handler does not send a reply
  class NoReply

  private var typeIdentifier: Int = 1000
  private fun nextTypeId() = typeIdentifier++

  // When receiving a message we need to find the actual type and handler using the type ID
  private val types: MutableMap<Int, KClass<*>> = mutableMapOf()
  private val handlers: MutableMap<Int, MessageHandler<*, *>> = mutableMapOf()
  // When sending a message we need to know the type ID for the actual type
  private val ids: MutableMap<KClass<*>, Int> = mutableMapOf()

  internal inline fun <reified Request : Any, reified Response : Any>  registerHandler(noinline handler: MessageHandler<Request, Response>) {
    assertTypeIsRegistered(Response::class)

    val id = nextTypeId()
    println("Registering message type ${Request::class.simpleName} with ID $id")

    types.put(id, Request::class)
    ids.put(Request::class, id)
    handlers.put(id, handler)
  }

  fun getHandler(id: Int): MessageHandler<Any, Any> =
    when (val handler = handlers.get(id)) {
      null -> throw RuntimeException("Handler for type id $id was not found")
      else -> {
        @Suppress("unchecked_cast")
        handler as MessageHandler<Any, Any>
      }
    }

  fun getTypeId(type: KClass<*>): Int = ids.get(type)!!

  fun getType(id: Int): KClass<*> = types.get(id)!!

  private fun assertTypeIsRegistered(type: KClass<*>) {
    if (type != NoReply::class && !ids.containsKey(type)) {
      throw RuntimeException("Handler for message response has not been registered")
    }
  }
}
