package fi.majavapaja.mcombat.common.item.base

import fi.majavapaja.mcombat.Main
import fi.majavapaja.mcombat.common.combat.CombatHelper
import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.common.entity.IWeaponArrow
import fi.majavapaja.mcombat.common.item.minecraft.getAsWeapon
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

  override fun onPlayerStoppedUsing(stack: ItemStack, worldIn: World, player: EntityLivingBase, timeLeft: Int) {
    if (player !is EntityPlayer) return

    val ammo = getAmmo(player)
    if (ammo.isEmpty) return

    var charge = this.getMaxItemUseDuration(stack) - timeLeft
    charge = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, player, charge, true)
    if (charge < 0) return

    val arrowVelocity = getArrowVelocity(charge)

    if (arrowVelocity >= 0.1f) {
      val itemarrow = ammo.item as ItemArrow
      val hasInfiniteAmmo = EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0
      val canArrowBeInfinite = itemarrow.isInfinite(ammo, stack, player)
      val isInfinite = hasInfiniteAmmo && canArrowBeInfinite

      if (!worldIn.isRemote) {
        val entityarrow = itemarrow.createArrow(worldIn, ammo, player)

        if (entityarrow is IWeaponArrow) {
          val bowDamage = getAsWeapon(stack.item)?.damage ?: hashMapOf()
          val arrowDamage = getAsWeapon(ammo.item)?.damage ?: hashMapOf()

          entityarrow.customDamage = CombatHelper.mergeHashMap(bowDamage, arrowDamage)
        }

        entityarrow.shoot(
            player,
            player.rotationPitch,
            player.rotationYaw,
            0.0f,
            arrowVelocity * 3.0f,
            1.0f
        )

        if (arrowVelocity == 1.0f) {
          entityarrow.isCritical = true
        }

        val powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack)

        if (powerLevel > 0) {
          entityarrow.damage = entityarrow.damage + powerLevel.toDouble() * 0.5 + 0.5
        }

        val punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack)

        if (punchLevel > 0) {
          entityarrow.setKnockbackStrength(punchLevel)
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantments.FLAME, stack) > 0) {
          entityarrow.setFire(100)
        }

        stack.damageItem(1, player)

        if (isInfinite || player.isCreative) {
          entityarrow.pickupStatus = EntityArrow.PickupStatus.CREATIVE_ONLY
        }

        worldIn.spawnEntity(entityarrow)
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
