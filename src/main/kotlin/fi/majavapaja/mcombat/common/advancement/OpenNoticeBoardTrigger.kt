package fi.majavapaja.mcombat.common.advancement

import com.google.common.collect.Lists
import com.google.common.collect.Maps
import com.google.common.collect.Sets
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonObject
import com.google.gson.JsonSyntaxException
import fi.majavapaja.mcombat.modId
import net.minecraft.advancements.ICriterionTrigger
import net.minecraft.advancements.PlayerAdvancements
import net.minecraft.advancements.critereon.AbstractCriterionInstance
import net.minecraft.block.Block
import net.minecraft.util.JsonUtils
import net.minecraft.util.ResourceLocation
import net.minecraft.entity.player.EntityPlayerMP



class OpenNoticeBoardTrigger: ICriterionTrigger<OpenNoticeBoardTrigger.Instance> {
  companion object {
    private val ID = ResourceLocation(modId, "open_notice_board")
  }
  private val listeners = Maps.newHashMap<PlayerAdvancements, Listeners>()

  override fun getId(): ResourceLocation {
    return ID
  }

  override fun addListener(playerAdvancementsIn: PlayerAdvancements, listener: ICriterionTrigger.Listener<Instance>) {
    if (listeners[playerAdvancementsIn] == null) {
      listeners[playerAdvancementsIn] = Listeners(playerAdvancementsIn)
    }

    listeners[playerAdvancementsIn]!!.add(listener)
  }

  override fun removeListener(playerAdvancementsIn: PlayerAdvancements, listener: ICriterionTrigger.Listener<Instance>) {
    val listeners = this.listeners[playerAdvancementsIn] ?: return
    listeners.remove(listener)

    if (listeners.isEmpty()) {
      this.listeners.remove(playerAdvancementsIn)
    }
  }

  override fun removeAllListeners(playerAdvancementsIn: PlayerAdvancements) {
    this.listeners.remove(playerAdvancementsIn)
  }

  fun trigger(player: EntityPlayerMP, block: Block) {
    this.listeners[player.advancements]?.trigger(block)
  }

  /**
   * Deserialize a ICriterionInstance of this trigger from the data in the JSON.
   */
  override fun deserializeInstance(json: JsonObject, context: JsonDeserializationContext): Instance {
    var block: Block? = null

    if (json.has("block")) {
      val resourcelocation = ResourceLocation(JsonUtils.getString(json, "block"))

      if (!Block.REGISTRY.containsKey(resourcelocation)) {
        throw JsonSyntaxException("Unknown block type '$resourcelocation'")
      }

      block = Block.REGISTRY.getObject(resourcelocation)
    }

    return Instance(block)
  }

  class Instance(private val block: Block?) : AbstractCriterionInstance(ID) {
    fun test(interactedBlock: Block): Boolean = interactedBlock == block
  }

  internal class Listeners(private val playerAdvancements: PlayerAdvancements) {
    private val listeners = Sets.newHashSet<ICriterionTrigger.Listener<Instance>>()

    fun isEmpty(): Boolean = this.listeners.isEmpty()

    fun add(listener: ICriterionTrigger.Listener<Instance>) {
      this.listeners.add(listener)
    }

    fun remove(listener: ICriterionTrigger.Listener<Instance>) {
      this.listeners.remove(listener)
    }

    fun trigger(block: Block) {
      var list: MutableList<ICriterionTrigger.Listener<Instance>>? = null

      for (listener in this.listeners) {
        if ((listener.criterionInstance as Instance).test(block)) {
          if (list == null) {
            list = Lists.newArrayList()
          }

          list!!.add(listener)
        }
      }

      if (list != null) {
        for (listener1 in list) {
          listener1.grantCriterion(this.playerAdvancements)
        }
      }
    }
  }
}