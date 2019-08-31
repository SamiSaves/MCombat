package fi.majavapaja.mcombat.common.advancement

import java.lang.reflect.InvocationTargetException
import net.minecraft.advancements.ICriterionTrigger
import net.minecraft.advancements.CriteriaTriggers
import net.minecraftforge.fml.relauncher.ReflectionHelper

object ModTriggers {
  private const val registerTriggerMethodName = "func_192118_a"

  val openNoticeBoardTrigger = OpenNoticeBoardTrigger()

  fun registerTriggers() {
    val method = ReflectionHelper.findMethod(
        CriteriaTriggers::class.java,
        "register",
        registerTriggerMethodName,
        ICriterionTrigger::class.java
    )

    method.isAccessible = true

    try {
      method.invoke(null, openNoticeBoardTrigger)
    } catch (e: IllegalAccessException) {
      e.printStackTrace()
    } catch (e: IllegalArgumentException) {
      e.printStackTrace()
    } catch (e: InvocationTargetException) {
      e.printStackTrace()
    }
  }
}