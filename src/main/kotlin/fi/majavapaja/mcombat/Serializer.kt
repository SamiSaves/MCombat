package fi.majavapaja.mcombat

import com.google.gson.Gson
import fi.majavapaja.mcombat.common.message.readString
import fi.majavapaja.mcombat.common.message.writeString
import io.netty.buffer.ByteBuf
import net.minecraft.nbt.NBTTagString
import kotlin.reflect.KClass

object Serializer {
  private val gson = Gson()

  fun <T> toNBT(obj: T): NBTTagString =
    NBTTagString(gson.toJson(obj))

  fun <T : Any> fromNBT(nbt: NBTTagString, type: KClass<T>): T =
    gson.fromJson(nbt.string, type.java)

  fun <T> toBytes(obj: T, buf: ByteBuf) =
    buf.writeString(gson.toJson(obj))

  fun <T : Any> fromBytes(buf: ByteBuf, type: KClass<T>): T =
    gson.fromJson(buf.readString(), type.java)

  fun <T> toJson(obj: T) =
    gson.toJson(obj)

  internal inline fun <reified T : Any> fromJson(json: String) =
    fromJson(json, T::class)

  internal fun <T : Any> fromJson(json: String, type: KClass<T>) =
    gson.fromJson(json, type.java)
}
