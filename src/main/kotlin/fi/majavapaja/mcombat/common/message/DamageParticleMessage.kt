package fi.majavapaja.mcombat.common.message

import fi.majavapaja.mcombat.common.combat.DamageType
import io.netty.buffer.ByteBuf
import io.netty.util.CharsetUtil
import net.minecraft.client.Minecraft
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
  var damageTypes = listOf(DamageType.Normal)
  var amount = 0f

  constructor(
      x: Double,
      y: Double,
      z: Double,
      amount: Float,
      damageTypes: List<DamageType>
  ): this() {
    this.x = x
    this.y = y
    this.z = z
    this.damageTypes = damageTypes
    this.amount = amount
  }

  override fun toBytes(buf: ByteBuf) {
    buf.writeDouble(x)
    buf.writeDouble(y)
    buf.writeDouble(z)
    buf.writeFloat(amount)

    val damageTypes = this.damageTypes.joinToString(",") { it.type }
    buf.writeInt(damageTypes.length)
    buf.writeCharSequence(damageTypes, CharsetUtil.UTF_8)
  }

  override fun fromBytes(buf: ByteBuf) {
    x = buf.readDouble()
    y = buf.readDouble()
    z = buf.readDouble()
    amount = buf.readFloat()
    val damageTypeLength = buf.readInt()
    val damageTypeData = buf.readCharSequence(damageTypeLength, CharsetUtil.UTF_8)
    damageTypes = damageTypeData.toString().split(",").map { DamageType.getDamageType(it) }
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
    message.damageTypes.forEach {
      val particleType = DamageType.getParticle(it) ?: return@forEach

      for (i in 1..10) {
        val x = message.x - Random.nextDouble(-.5, .5)
        val y = message.y + 1.5
        val z = message.z - Random.nextDouble(-.5, .5)
        val speed = .0

        world.spawnParticle(particleType, false, x, y, z, speed, speed, speed)
      }
    }
  }
}

