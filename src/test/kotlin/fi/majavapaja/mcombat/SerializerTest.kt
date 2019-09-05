package fi.majavapaja.mcombat

import io.netty.buffer.Unpooled
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

data class TestSubject(
  val multiplier: Float,
  val str: String,
  val number: Int,
  val map: Map<String, Int>,
  val list: List<String>,
  val child: Child
)

data class Child(val name: String)

@Suppress("unused")
class SerializerTest {
  val input = TestSubject(
    multiplier = 0.1f,
    str = "foobar",
    number = 1337,
    map = mapOf("foo" to 0, "bar" to 3),
    list = listOf("List", "of", "items"),
    child = Child("kiddie")
  )

  @Test
  fun shouldSerializeToAndFromNBT() {
    val nbt = Serializer.toNBT(input)
    val output = Serializer.fromNBT(nbt, TestSubject::class)
    assertThat(input).isEqualTo(output)
  }

  @Test
  fun shouldSerializeToAndFromBytes() {
    val buf = Unpooled.buffer()
    Serializer.toBytes(input, buf)
    val output = Serializer.fromBytes(buf, TestSubject::class)
    assertThat(input).isEqualTo(output)
  }
}
