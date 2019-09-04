package fi.majavapaja.mcombat.common.item

import fi.majavapaja.mcombat.common.combat.DamageType
import fi.majavapaja.mcombat.common.item.base.*
import fi.majavapaja.mcombat.common.item.minecraft.getAsArmor
import fi.majavapaja.mcombat.common.item.minecraft.getAsWeapon
import net.minecraft.advancements.Advancement
import net.minecraft.advancements.ICriterionInstance
import fi.majavapaja.mcombat.common.item.base.Item as ItemBase
import net.minecraft.inventory.EntityEquipmentSlot
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent
import net.minecraftforge.registries.IForgeRegistry

object ModItems {
  val majavapaja = ItemBase("majavapaja")
  val debugSword = Sword("debug_sword", Item.ToolMaterial.WOOD, hashMapOf(DamageType.Fire to 10f, DamageType.Normal to 5f))
  val debugChestplate = Armor("debug_chestplate", Armor.debugMaterial, EntityEquipmentSlot.CHEST, hashMapOf(DamageType.Earth to 2f))
  val debugLeggings = Armor("debug_leggings", Armor.debugMaterial, EntityEquipmentSlot.LEGS, hashMapOf(DamageType.Earth to 2f))
  val debugBoots = Armor("debug_boots", Armor.debugMaterial, EntityEquipmentSlot.FEET, hashMapOf(DamageType.Earth to 2f))
  val debugHelmet = Armor("debug_helmet", Armor.debugMaterial, EntityEquipmentSlot.HEAD, hashMapOf(DamageType.Earth to 2f))

  val debugBow = Bow("debug_bow", hashMapOf(DamageType.Magic to 4f))
  val debugArrow = Arrow("debug_arrow", hashMapOf(DamageType.Air to 2f))

  private val allDebugItems = arrayOf(
    debugSword,
    debugChestplate,
    debugLeggings,
    debugBoots,
    debugHelmet,
    debugBow,
    debugArrow
  )

  fun register(registry: IForgeRegistry<Item>) {
    registry.registerAll(
        majavapaja,
        debugSword,
        debugChestplate,
        debugLeggings,
        debugBoots,
        debugHelmet,
        debugBow,
        debugArrow
    )
  }

  fun registerModels() {
    majavapaja.registerItemModel()
    debugSword.registerItemModel()
    debugChestplate.registerItemModel()
    debugLeggings.registerItemModel()
    debugBoots.registerItemModel()
    debugHelmet.registerItemModel()
    debugBow.registerItemModel()
    debugArrow.registerItemModel()
  }

  @SubscribeEvent
  @Suppress("unused")
  fun setTooltip(event: ItemTooltipEvent) {
    val cleanTooltip = {
      // Remove everything after an empty line (should remove the "When in hand" section)
      val indexOfEmptyLine = event.toolTip.indexOf("")
      if (indexOfEmptyLine > 0) event.toolTip.removeAll { event.toolTip.indexOf(it) >= indexOfEmptyLine }

      if (event.toolTip.size > 1) event.toolTip.add("")
    }

    val weapon = getAsWeapon(event.itemStack.item)
    if (weapon is IWeapon) {
      cleanTooltip()
      event.toolTip.addAll(IWeapon.getTooltip(event.itemStack))
    }

    val armor = getAsArmor(event.itemStack.item)
    if (armor is IArmor) {
      cleanTooltip()
      event.toolTip.addAll(IArmor.getTooltip(event.itemStack))
    }
  }

  fun isDebugItem(stack: ItemStack): Boolean =
    allDebugItems.contains(stack.item)
}