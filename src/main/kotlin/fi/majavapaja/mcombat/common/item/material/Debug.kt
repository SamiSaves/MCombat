package fi.majavapaja.mcombat.common.item.material

import net.minecraft.inventory.EquipmentSlotType
import net.minecraft.item.IArmorMaterial
import net.minecraft.item.Items
import net.minecraft.item.crafting.Ingredient
import net.minecraft.util.SoundEvent
import net.minecraft.util.SoundEvents

object DebugMaterial: IArmorMaterial {
  override fun getDurability(slotIn: EquipmentSlotType): Int = -1
  override fun getDamageReductionAmount(slotIn: EquipmentSlotType): Int = 0
  override fun getEnchantability(): Int = 0
  override fun getSoundEvent(): SoundEvent = SoundEvents.BLOCK_GLASS_PLACE
  override fun getRepairMaterial(): Ingredient = Ingredient.fromItems(Items.DIAMOND)
  override fun getName(): String = "DEBUG"
  override fun getToughness(): Float = 2.0F
}
