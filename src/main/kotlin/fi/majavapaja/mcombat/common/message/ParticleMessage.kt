package fi.majavapaja.mcombat.common.message

import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.util.EnumParticleTypes
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import net.minecraftforge.fml.relauncher.Side
import kotlin.random.Random

class ParticleMessage() : IMessage {
  var x = 0.0
  var y = 0.0
  var z = 0.0
  var particleType: Int = 0

  constructor(x: Double, y: Double, z: Double, particleType: EnumParticleTypes): this() {
    this.x = x
    this.y = y
    this.z = z
    this.particleType = particleType.particleID
  }

  override fun toBytes(buf: ByteBuf) {
    buf.writeDouble(x)
    buf.writeDouble(y)
    buf.writeDouble(z)
    buf.writeInt(particleType)
  }

  override fun fromBytes(buf: ByteBuf) {
    x = buf.readDouble()
    y = buf.readDouble()
    z = buf.readDouble()
    particleType = buf.readInt()
  }
}

class ParticleMessageHandler : IMessageHandler<ParticleMessage, IMessage> {
  override fun onMessage(message: ParticleMessage, ctx: MessageContext): IMessage? {
    if (ctx.side != Side.CLIENT) {
      System.err.println("TargetEffectMessageToClient received on wrong side:" + ctx.side)
      return null
    }
    val minecraft = Minecraft.getMinecraft()
    val worldClient = minecraft.world
    minecraft.addScheduledTask { processMessage(message, worldClient) }

    return null
  }

  private fun processMessage(message: ParticleMessage, world: World) {
    val particleType = EnumParticleTypes.getParticleFromId(message.particleType)

    for (i in 1..10) {
      world.spawnParticle(
          particleType,
          message.x - Random.nextDouble(-1.0, 1.0),
          message.y + 1.0,
          message.z - Random.nextDouble(-1.0, 1.0),
          .0,
          .0,
         .0
      )
    }
  }
}

