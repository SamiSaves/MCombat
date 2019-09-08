package fi.majavapaja.mcombat.common.message

import fi.majavapaja.mcombat.client.particle.AttackParticle
import fi.majavapaja.mcombat.client.particle.DamageParticle
import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.network.NetworkHandlers
import net.minecraft.client.Minecraft
import net.minecraft.world.World
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import net.minecraftforge.fml.relauncher.Side
import kotlin.random.Random

data class ParticleMessage(
  val x: Double,
  val y: Double,
  val z: Double,
  val damageTypes: List<DamageType>,
  val amount: Float
)

object ParticleMessageHandler {
  fun handle(message: ParticleMessage, ctx: MessageContext): NetworkHandlers.NoReply? {
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
    val minecraft = Minecraft.getMinecraft()
    val damageParticles = message.damageTypes.filter { DamageType.getParticleId(it) >= 0 }

    damageParticles.forEach {
      val maxParticles = when {
        damageParticles.size == 1 -> 7
        damageParticles.size == 2 -> 5
        else -> 3
      }

      val particleAmount = Random.nextInt(2, maxParticles)
      for (i in 1..particleAmount) {
        val x = message.x
        val y = message.y + 1.5
        val z = message.z

        val effect = AttackParticle(world, x, y, z, minecraft.textureManager, it)
        minecraft.effectRenderer.addEffect(effect)
      }
    }


    val effect = DamageParticle(world, message.x, message.y + 1.5, message.z, message.amount)
    minecraft.effectRenderer.addEffect(effect)
  }
}

