package fi.majavapaja.mcombat.common.message

import io.netty.buffer.ByteBuf
import io.netty.util.CharsetUtil

fun ByteBuf.writeString(str: String) {
  val writeLength = writeIntLater()
  val bytesWritten = this.writeCharSequence(str, CharsetUtil.UTF_8)
  writeLength(bytesWritten)
}

fun ByteBuf.readString(): String {
  val length = this.readInt()
  val sequence = this.readCharSequence(length, CharsetUtil.UTF_8)
  return sequence.toString()
}

fun ByteBuf.writeIntLater(): (Int) -> Unit {
  // Write a zero value as placeholder and return a function to write over it
  val index = writerIndex()
  writeInt(0)
  return { n -> setInt(index, n) }
}
