package fi.majavapaja.mcombat

import net.minecraft.entity.player.EntityPlayerMP
import net.minecraftforge.fml.common.network.NetworkRegistry
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.relauncher.Side
import kotlin.reflect.KClass

object Network {
  private val channel = NetworkRegistry.INSTANCE.newSimpleChannel(modId)!!
  private var DISCRIMINATOR = 100

  fun <H : IMessageHandler<M, R>, M : IMessage, R : IMessage> registerMessage(handler: KClass<H>, message: KClass<M>, side: Side) =
    channel.registerMessage(handler.java, message.java, DISCRIMINATOR++, side)

  fun sendTo(message: IMessage, entity: EntityPlayerMP) =
    channel.sendTo(message, entity)

  fun sendToServer(message: IMessage) =
    channel.sendToServer(message)

  fun sendToAllAround(message: IMessage, target: NetworkRegistry.TargetPoint) =
    channel.sendToAllAround(message, target)
}
