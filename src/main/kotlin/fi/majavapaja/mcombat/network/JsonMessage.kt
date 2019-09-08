package fi.majavapaja.mcombat.network

import fi.majavapaja.mcombat.common.message.readString
import fi.majavapaja.mcombat.common.message.writeString
import io.netty.buffer.ByteBuf
import net.minecraftforge.fml.common.network.simpleimpl.IMessage

data class JsonMessage(var typeId: Int? = null, var json: String? = null) : IMessage {
  override fun fromBytes(buf: ByteBuf) {
    typeId = buf.readInt()
    json = buf.readString()
  }

  override fun toBytes(buf: ByteBuf) {
    buf.writeInt(typeId!!)
    buf.writeString(json!!)
  }
}

