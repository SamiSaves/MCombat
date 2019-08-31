package fi.majavapaja.mcombat.common.advancement

import java.lang.reflect.InvocationTargetException
import net.minecraft.advancements.ICriterionTrigger
import net.minecraft.advancements.CriteriaTriggers
import net.minecraftforge.fml.relauncher.ReflectionHelper

object ModTriggers {
  private const val registerTriggerMethodName = "func_192118_a"

  val playerInteractBlock = PlayerInteractBlockTrigger()

  fun registerTriggers() {
    val registerTriggerMethod = ReflectionHelper.findMethod(
        CriteriaTriggers::class.java,
        "register",
        registerTriggerMethodName,
        ICriterionTrigger::class.java
    )

    registerTriggerMethod.isAccessible = true

    try {
      registerTriggerMethod.invoke(null, playerInteractBlock)
    } catch (e: IllegalAccessException) {
      e.printStackTrace()
    } catch (e: IllegalArgumentException) {
      e.printStackTrace()
    } catch (e: InvocationTargetException) {
      e.printStackTrace()
    }
  }
}