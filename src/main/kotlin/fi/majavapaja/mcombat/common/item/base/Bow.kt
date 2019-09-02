package fi.majavapaja.mcombat.common.item.base

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.common.combat.CombatHelper
import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.common.entity.IWeaponArrow
import fi.majavapaja.mcombat.common.entity.minecraft.createArrowEntity
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntityArrow
import net.minecraft.init.Enchantments
import net.minecraft.init.Items
import net.minecraft.init.SoundEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemArrow
import net.minecraft.item.ItemBow
import net.minecraft.item.ItemStack
import net.minecraft.stats.StatList
import net.minecraft.util.EnumHand
import net.minecraft.util.SoundCategory
import net.minecraft.world.World
import kotlin.math.round

class Bow(
    private val name: String,
    override val damage: HashMap<DamageType, Float>
): ItemBow(), IWeapon {
  init {
    setRegistryName(name)
    unlocalizedName = name
    creativeTab = Main.creativeTab
  }

  fun registerItemModel() {
    Main.proxy.registerItemRenderer(this, 0 , name)
  }

  override fun onPlayerStoppedUsing(bow: ItemStack, worldIn: World, player: EntityLivingBase, timeLeft: Int) {
    if (player !is EntityPlayer) return

    val ammo = getAmmo(player)
    if (ammo.isEmpty) return

    var chargeTime = this.getMaxItemUseDuration(bow) - timeLeft
    chargeTime = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(bow, worldIn, player, chargeTime, true)
    if (chargeTime < 0) return

    val arrowVelocity = getArrowVelocity(chargeTime)
    if (arrowVelocity < 0.1f) return

    val itemArrow = ammo.item as ItemArrow

    val hasInfiniteAmmo = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, bow) > 0
    val canArrowBeInfinite = itemArrow.isInfinite(ammo, bow, player)
    val isInfinite = hasInfiniteAmmo && canArrowBeInfinite

    if (!worldIn.isRemote) {
      val entityArrow = createArrowEntity(worldIn, itemArrow, player)

      if (entityArrow is IWeaponArrow) {
        val bowDamage = CombatHelper.getItemDamageStat(bow)
        val arrowDamage = CombatHelper.getItemDamageStat(ammo)

        val damage = CombatHelper.mergeDamageMap(bowDamage, arrowDamage)
        damage.forEach { damage[it.key] = round(it.value * arrowVelocity) }

        entityArrow.customDamage = damage
      }

      setMinecraftProperties(entityArrow, bow, arrowVelocity)

      entityArrow.shoot(player, player.rotationPitch, player.rotationYaw, 0.0f, arrowVelocity * 3.0f, 1.0f)

      bow.damageItem(1, player)

      if (isInfinite || player.isCreative) {
        entityArrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY
      }

      worldIn.spawnEntity(entityArrow)
    }

    worldIn.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0f, 1.0f / (Item.itemRand.nextFloat() * 0.4f + 1.2f) + arrowVelocity * 0.5f)

    if (!isInfinite && !player.isCreative) {
      ammo.shrink(1)

      if (ammo.isEmpty) {
        player.inventory.deleteStack(ammo)
      }
    }

    player.addStat(StatList.getObjectUseStats(this)!!)
  }

  private fun setMinecraftProperties(entityarrow: EntityArrow, bow: ItemStack, arrowVelocity: Float) {
    if (arrowVelocity == 1.0f) {
      entityarrow.isCritical = true
    }

    val powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, bow)

    if (powerLevel > 0) {
      entityarrow.damage = entityarrow.damage + powerLevel.toDouble() * 0.5 + 0.5
    }

    val punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, bow)

    if (punchLevel > 0) {
      entityarrow.setKnockbackStrength(punchLevel)
    }

    if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, bow) > 0) {
      entityarrow.setFire(100)
    }
  }

  private fun getAmmo(player: EntityPlayer): ItemStack = when {
      isArrow(player.getHeldItem(EnumHand.OFF_HAND)) -> player.getHeldItem(EnumHand.OFF_HAND)
      isArrow(player.getHeldItem(EnumHand.MAIN_HAND)) -> player.getHeldItem(EnumHand.MAIN_HAND)
      else -> {
        var arrow = when {
          player.isCreative -> ItemStack(Items.ARROW)
          else -> ItemStack.EMPTY
        }

        for (i in 0 until player.inventory.sizeInventory) {
          val itemstack = player.inventory.getStackInSlot(i)

          if (isArrow(itemstack)) {
            arrow = itemstack
          }
        }

        arrow
      }
    }
}
