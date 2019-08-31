package fi.majavapaja.mcombat.common.message

import io.netty.buffer.ByteBuf
import io.netty.util.CharsetUtil

fun ByteBuf.writeString(str: String) {
  this.writeInt(str.length)
  this.writeCharSequence(str, CharsetUtil.UTF_8)
}

fun ByteBuf.readString(): String {
  val length = this.readInt()
  val sequence = this.readCharSequence(length, CharsetUtil.UTF_8)
  return sequence.toString()
}
