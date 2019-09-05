package fi.majavapaja.mcombat

import fi.majavapaja.mcombat.common.combat.DamageType
import io.netty.buffer.Unpooled
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

data class TestSubject(
  val multiplier: Float,
  val str: String,
  val number: Int,
  val damageMap: Map<DamageType, Float>,
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
    damageMap = mapOf(
      DamageType.Normal to 10f,
      DamageType.Fire to 5f
    ),
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
