package fi.majavapaja.mcombat.common.entity.minecraft

import fi.majavapaja.mcombat.common.entity.ICustomMob
import net.minecraft.entity.Entity

fun isMinecraftMonster (entity: Entity) = (entity !is ICustomMob)
