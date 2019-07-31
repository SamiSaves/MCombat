package fi.majavapaja.mcombat.client.particle


import fi.majavapaja.mcombat.modId
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
class FireAttackParticle(
    world: World,
    xCoordIn: Double,
    yCoordIn: Double,
    zCoordIn: Double,
    xSpeedIn: Double,
    ySpeedIn: Double,
    zSpeedIn: Double,
    textureManager: TextureManager
): AttackParticle(world, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, textureManager) {
  override val texture = ResourceLocation("$modId:textures/particle/fire.png")
}
