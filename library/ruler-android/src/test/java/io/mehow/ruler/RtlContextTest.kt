package io.mehow.ruler

import io.kotest.matchers.shouldBe
import io.mehow.ruler.ImperialLengthUnit.Yard
import io.mehow.ruler.SiLengthUnit.Centimeter
import io.mehow.ruler.SiLengthUnit.Meter
import io.mehow.ruler.format.NoOpFormatter
import io.mehow.ruler.format.FormattingContext
import io.mehow.ruler.format.ImperialFormatter
import io.mehow.ruler.test.ResetRulerRule
import io.mehow.ruler.test.contextualize
import io.mehow.ruler.test.getApplicationContext
import io.mehow.ruler.test.localize
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.Locale

@RunWith(RobolectricTestRunner::class)
internal class RtlContextTest {
  @get:Rule val resetRuleRule = ResetRulerRule
  private val context = getApplicationContext()

  @Before fun setUp() {
    Ruler.contextualize(context.localize(Locale("ar")))
  }

  @Test fun `positive SI length is displayed correctly`() {
    val length = Length.ofMeters(12.36)

    length.format(converter = null, formatter = NoOpFormatter) shouldBe "١٢٫٣٦م"
  }

  @Test fun `negative SI length is displayed correctly`() {
    val length = Length.ofMeters(-12.36)

    length.format(converter = null, formatter = NoOpFormatter) shouldBe "؜-١٢٫٣٦م"
  }

  @Test fun `custom separator is displayed in a correct position`() {
    val length = Length.ofMeters(12.36)

    val context = FormattingContext.withSeparator("|")
    Ruler.driver = Ruler.driver.newBuilder().withFormattingContext(context).build()

    length.format(converter = null, formatter = NoOpFormatter) shouldBe "١٢٫٣٦|م"
  }

  @Test fun `imperial unit parts are reversed with RTL locale`() {
    val distance = Distance.ofMiles(4) +
        Distance.ofYards(3) +
        Distance.ofFeet(2) +
        Distance.ofInches(1)
    val length = distance.toLength(Centimeter)
    val formatter = ImperialFormatter.Full

    length.format(converter = null, formatter = formatter) shouldBe "٤ميل ٣ياردة ٢قدم ١بوصة"
  }

  @Test fun `custom unit separator is displayed in a correct position with RTL locale`() {
    val distance = Distance.ofMiles(4) +
        Distance.ofYards(3) +
        Distance.ofFeet(2) +
        Distance.ofInches(1)
    val length = distance.toLength(Yard)
    val formatter = ImperialFormatter.Full

    val context = FormattingContext.withSeparator("|")
    Ruler.driver = Ruler.driver.newBuilder().withFormattingContext(context).build()

    length.format(converter = null, formatter = formatter) shouldBe "٤|ميل ٣|ياردة ٢|قدم ١|بوصة"
  }

  @Test fun `custom part separator is displayed in a correct position with RTL locale`() {
    val distance = Distance.ofMiles(4) +
        Distance.ofYards(3) +
        Distance.ofFeet(2) +
        Distance.ofInches(1)
    val length = distance.toLength(Meter)
    val formatter = ImperialFormatter.Builder()
        .withMiles()
        .withYards()
        .withFeet()
        .withInches()
        .withPartSeparator("|")
        .build()

    length.format(converter = null, formatter = formatter) shouldBe "٤ميل|٣ياردة|٢قدم|١بوصة"
  }
}
