package fi.majavapaja.mcombat.client.particle

import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.common.util.SpriteCoordinates
import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly
import kotlin.random.Random

@SideOnly(Side.CLIENT)
class AttackParticle(
    worldIn: World,
    xCoordIn: Double,
    yCoordIn: Double,
    zCoordIn: Double,
    textureManager: TextureManager,
    damageType: DamageType
): CustomParticle(worldIn, xCoordIn, yCoordIn, zCoordIn, textureManager) {
  override var life = 0
  override var lifeTime = 16
  override var size = .25f
  override var animPhases = 16
  override var textureId = DamageType.getParticleId((damageType))
  override var spriteCoordinates = SpriteCoordinates(textureId, spriteSheetWidth, spriteSheetHeight, spriteWidth, spriteHeight)

  init {
    setRandomMotion()
    size -= Random.nextDouble(0.15).toFloat()
  }

  override fun onUpdate() {
    super.onUpdate()

    move(motionX, motionY, motionZ)

    motionY -= when {
      motionY > -.08 -> .04
      else -> .0
    }
    motionX = slowMotionDown(motionX)
    motionZ = slowMotionDown(motionZ)
  }

  private fun setRandomMotion() {
    motionX = Random.nextDouble(-.15, .15)
    motionZ = Random.nextDouble(-.15,.15)
    motionY = Random.nextDouble(0.12, 0.2)
  }

  private fun slowMotionDown(motion: Double): Double =
      when {
        motion > 0 -> {
          var newMotion = motion - .02
          if (newMotion < 0) newMotion = .0
          newMotion
        }
        motion < 0 -> {
          var newMotion = motion + .02
          if (newMotion > 0) newMotion = .0
          newMotion
        }
        else -> motion
      }
}
