package fi.majavapaja.mcombat.common.message

import fi.majavapaja.mcombat.common.combat.DamageType
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

fun ByteBuf.writeDamageType(type: DamageType) =
  writeString(type.type)

fun ByteBuf.readDamageType(): DamageType =
  DamageType.getDamageType(readString())

fun <K, V> ByteBuf.writeMap(map: Map<K, V>, entryWriter: (ByteBuf, Map.Entry<K, V>) -> Unit) {
  writeInt(map.size)
  map.forEach { entryWriter(this, it) }
}

fun <K, V> ByteBuf.readMap(entryReader: (ByteBuf) -> Pair<K, V>): Map<K, V> {
  val size = readInt()
  return (0 until size).map { entryReader(this) }.toMap()
}

fun ByteBuf.writeIntLater(): (Int) -> Unit {
  // Write a zero value as placeholder and return a function to write over it
  val index = writerIndex()
  writeInt(0)
  return { n -> setInt(index, n) }
}
