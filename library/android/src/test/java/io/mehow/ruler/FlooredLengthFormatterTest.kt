package io.mehow.ruler

import android.content.Context
import android.content.res.Configuration
import androidx.test.core.app.ApplicationProvider
import io.kotest.matchers.shouldBe
import io.mehow.ruler.ImperialLengthUnit.Foot
import io.mehow.ruler.ImperialLengthUnit.Inch
import io.mehow.ruler.ImperialLengthUnit.Mile
import io.mehow.ruler.ImperialLengthUnit.Yard
import io.mehow.ruler.SiLengthUnit.Gigameter
import io.mehow.ruler.SiLengthUnit.Kilometer
import io.mehow.ruler.SiLengthUnit.Megameter
import io.mehow.ruler.SiLengthUnit.Meter
import io.mehow.ruler.SiLengthUnit.Micrometer
import io.mehow.ruler.SiLengthUnit.Millimeter
import io.mehow.ruler.SiLengthUnit.Nanometer
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.util.Locale

@RunWith(RobolectricTestRunner::class)
internal class FlooredLengthFormatterTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()

  @Test fun `meters are used as a default formatting SI unit`() {
    // Only language of locale can be set with @Config and we need to set country.
    val config = context.resources.configuration
    val localizedConfig = Configuration(config).apply { setLocale(Locale.UK) }
    val localizedContext = context.createConfigurationContext(localizedConfig)

    val distance = Distance.ofGigameters(1) +
        Distance.ofMegameters(2) +
        Distance.ofKilometers(3) +
        Distance.ofMeters(4) +
        Distance.ofMillimeters(5) +
        Distance.ofMicrometers(6) +
        Distance.ofNanometers(7)

    val formattedDistance = distance.formatFloored(localizedContext)

    formattedDistance shouldBe "1002003004m"
  }

  @Test fun `yards are used as a default formatting imperial unit`() {
    // Only language of locale can be set with @Config and we need to set country.
    val config = context.resources.configuration
    val localizedConfig = Configuration(config).apply { setLocale(Locale.US) }
    val localizedContext = context.createConfigurationContext(localizedConfig)

    val distance = Distance.ofMiles(1) +
        Distance.ofYards(500) +
        Distance.ofFeet(4) +
        Distance.ofInches(5)

    val formattedDistance = distance.formatFloored(localizedContext)

    formattedDistance shouldBe "2261yd"
  }

  @Test fun `gigameters are properly formatted`() {
    val distance = Distance.ofGigameters(1) +
        Distance.ofMegameters(1) +
        Distance.ofKilometers(1) +
        Distance.ofMeters(1) +
        Distance.ofMillimeters(1) +
        Distance.ofMicrometers(1) +
        Distance.ofNanometers(1)

    val formattedDistance = distance.toLength(Gigameter).formatFloored(context)

    formattedDistance shouldBe "1Gm"
  }

  @Test fun `megameters are properly formatted`() {
    val distance = Distance.ofGigameters(1) +
        Distance.ofMegameters(1) +
        Distance.ofKilometers(1) +
        Distance.ofMeters(1) +
        Distance.ofMillimeters(1) +
        Distance.ofMicrometers(1) +
        Distance.ofNanometers(1)

    val formattedDistance = distance.toLength(Megameter).formatFloored(context)

    formattedDistance shouldBe "1001Mm"
  }

  @Test fun `kilometers are properly formatted`() {
    val distance = Distance.ofGigameters(1) +
        Distance.ofMegameters(1) +
        Distance.ofKilometers(1) +
        Distance.ofMeters(1) +
        Distance.ofMillimeters(1) +
        Distance.ofMicrometers(1) +
        Distance.ofNanometers(1)

    val formattedDistance = distance.toLength(Kilometer).formatFloored(context)

    formattedDistance shouldBe "1001001km"
  }

  @Test fun `meters are properly formatted`() {
    val distance = Distance.ofGigameters(1) +
        Distance.ofMegameters(1) +
        Distance.ofKilometers(1) +
        Distance.ofMeters(1) +
        Distance.ofMillimeters(1) +
        Distance.ofMicrometers(1) +
        Distance.ofNanometers(1)

    val formattedDistance = distance.toLength(Meter).formatFloored(context)

    formattedDistance shouldBe "1001001001m"
  }

  @Test fun `millimeters are properly formatted`() {
    val distance = Distance.ofGigameters(1) +
        Distance.ofMegameters(1) +
        Distance.ofKilometers(1) +
        Distance.ofMeters(1) +
        Distance.ofMillimeters(1) +
        Distance.ofMicrometers(1) +
        Distance.ofNanometers(1)

    val formattedDistance = distance.toLength(Millimeter).formatFloored(context)

    formattedDistance shouldBe "1001001001001mm"
  }

  @Test fun `micrometers are properly formatted`() {
    val distance = Distance.ofGigameters(1) +
        Distance.ofMegameters(1) +
        Distance.ofKilometers(1) +
        Distance.ofMeters(1) +
        Distance.ofMillimeters(1) +
        Distance.ofMicrometers(1) +
        Distance.ofNanometers(1)

    val formattedDistance = distance.toLength(Micrometer).formatFloored(context)

    formattedDistance shouldBe "1001001001001001µm"
  }

  @Test fun `nanometers are properly formatted`() {
    val distance = Distance.ofGigameters(1) +
        Distance.ofMegameters(1) +
        Distance.ofKilometers(1) +
        Distance.ofMeters(1) +
        Distance.ofMillimeters(1) +
        Distance.ofMicrometers(1) +
        Distance.ofNanometers(1)

    val formattedDistance = distance.toLength(Nanometer).formatFloored(context)

    formattedDistance shouldBe "1001001001001001001nm"
  }

  @Test fun `gigameters are formatting zeros properly`() {
    val distance = Distance.ofMegameters(999) +
        Distance.ofKilometers(999) +
        Distance.ofMeters(999) +
        Distance.ofMillimeters(999) +
        Distance.ofMicrometers(999) +
        Distance.ofNanometers(999)

    val formattedDistance = distance.toLength(Gigameter).formatFloored(context)

    formattedDistance shouldBe "0Gm"
  }

  @Test fun `megameters are formatting zeros properly`() {
    val distance = Distance.ofKilometers(999) +
        Distance.ofMeters(999) +
        Distance.ofMillimeters(999) +
        Distance.ofMicrometers(999) +
        Distance.ofNanometers(999)

    val formattedDistance = distance.toLength(Megameter).formatFloored(context)

    formattedDistance shouldBe "0Mm"
  }

  @Test fun `kilometers are formatting zeros properly`() {
    val distance = Distance.ofMeters(999) +
        Distance.ofMillimeters(999) +
        Distance.ofMicrometers(999) +
        Distance.ofNanometers(999)

    val formattedDistance = distance.toLength(Kilometer).formatFloored(context)

    formattedDistance shouldBe "0km"
  }

  @Test fun `meters are formatting zeros properly`() {
    val distance = Distance.ofMillimeters(999) +
        Distance.ofMicrometers(999) +
        Distance.ofNanometers(999)

    val formattedDistance = distance.toLength(Meter).formatFloored(context)

    formattedDistance shouldBe "0m"
  }

  @Test fun `millimeters are formatting zeros properly`() {
    val distance = Distance.ofMicrometers(999) +
        Distance.ofNanometers(999)

    val formattedDistance = distance.toLength(Millimeter).formatFloored(context)

    formattedDistance shouldBe "0mm"
  }

  @Test fun `micrometers are formatting zeros properly`() {
    val distance = Distance.ofNanometers(999)

    val formattedDistance = distance.toLength(Micrometer).formatFloored(context)

    formattedDistance shouldBe "0µm"
  }

  @Test fun `nanometers are formatting zeros properly`() {
    val distance = Distance.Zero

    val formattedDistance = distance.toLength(Nanometer).formatFloored(context)

    formattedDistance shouldBe "0nm"
  }

  @Test fun `miles are properly formatted`() {
    val distance = Distance.ofMiles(1) +
        Distance.ofYards(1) +
        Distance.ofFeet(1) +
        Distance.ofInches(1)

    val formattedDistance = distance.toLength(Mile).formatFloored(context)

    formattedDistance shouldBe "1mi"
  }

  @Test fun `yards are properly formatted`() {
    val distance = Distance.ofYards(1) +
        Distance.ofFeet(1) +
        Distance.ofInches(1)

    val formattedDistance = distance.toLength(Yard).formatFloored(context)

    formattedDistance shouldBe "1yd"
  }

  @Test fun `feet are properly formatted`() {
    val distance = Distance.ofFeet(1) +
        Distance.ofInches(1)

    val formattedDistance = distance.toLength(Foot).formatFloored(context)

    formattedDistance shouldBe "1ft"
  }

  @Test fun `inches are properly formatted`() {
    val distance = Distance.ofInches(1)

    val formattedDistance = distance.toLength(Inch).formatFloored(context)

    formattedDistance shouldBe "1in"
  }

  @Test fun `miles are formatting zeros properly`() {
    val distance = Distance.ofYards(1759) +
        Distance.ofFeet(2) +
        Distance.ofInches(11.99)

    val formattedDistance = distance.toLength(Mile).formatFloored(context)

    formattedDistance shouldBe "0mi"
  }

  @Test fun `yards are formatting zeros properly`() {
    val distance = Distance.ofFeet(2) +
        Distance.ofInches(11.99)

    val formattedDistance = distance.toLength(Yard).formatFloored(context)

    formattedDistance shouldBe "0yd"
  }

  @Test fun `feet are formatting zeros properly`() {
    val distance = Distance.ofInches(11.99)

    val formattedDistance = distance.toLength(Foot).formatFloored(context)

    formattedDistance shouldBe "0ft"
  }

  @Test fun `inches are formatting zeros properly`() {
    val distance = Distance.ofInches(0.99)

    val formattedDistance = distance.toLength(Inch).formatFloored(context)

    formattedDistance shouldBe "0in"
  }
}
