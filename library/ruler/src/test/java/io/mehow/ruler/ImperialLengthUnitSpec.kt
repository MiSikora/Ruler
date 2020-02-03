package io.mehow.ruler

import io.kotlintest.shouldBe
import io.kotlintest.specs.BehaviorSpec
import io.mehow.ruler.ImperialLengthUnit.Foot
import io.mehow.ruler.ImperialLengthUnit.Inch
import io.mehow.ruler.ImperialLengthUnit.Mile
import io.mehow.ruler.ImperialLengthUnit.Yard
import kotlin.Long.Companion.MAX_VALUE

class ImperialLengthUnitSpec : BehaviorSpec({
  Given("inches") {
    val unit = Inch

    Then("distance can be created from it") {
      listOf(
          0L to Distance.Zero,
          1L to Distance.create(meters = 0, nanometers = 25_400_000),
          7L to Distance.create(meters = 0, nanometers = 177_800_000),
          25L to Distance.create(meters = 0, nanometers = 635_000_000),
          133L to Distance.create(meters = 3, nanometers = 378_200_000),
          1_680L to Distance.create(meters = 42, nanometers = 672_000_000),
          131_296L to Distance.create(meters = 3_334, nanometers = 918_400_000),
          MAX_VALUE to Distance.create(meters = 234_273_649_736_111_305, nanometers = 497_800_000)
      ).map { (inches, expected) ->
        unit.toDistance(inches) shouldBe expected
        Distance.ofInches(inches) shouldBe expected
      }
    }
  }

  Given("feet") {
    val unit = Foot

    Then("distance can be created from it") {
      listOf(
          0L to Distance.Zero,
          1L to Distance.ofNanometers(304_800_000),
          7L to Distance.create(meters = 2, nanometers = 133_600_000),
          25L to Distance.create(meters = 7, nanometers = 620_000_000),
          133L to Distance.create(meters = 40, nanometers = 538_400_000),
          1_680L to Distance.create(meters = 512, nanometers = 64_000_000),
          131_296L to Distance.create(meters = 40_019, nanometers = 20_800_000),
          MAX_VALUE to Distance.create(meters = 2_811_283_796_833_335_665, nanometers = 973_600_000)
      ).map { (feet, expected) ->
        unit.toDistance(feet) shouldBe expected
        Distance.ofFeet(feet) shouldBe expected
      }
    }
  }

  Given("yards") {
    val unit = Yard

    Then("distance can be created from it") {
      listOf(
          0L to Distance.Zero,
          1L to Distance.ofNanometers(914_400_000),
          7L to Distance.create(meters = 6, nanometers = 400_800_000),
          25L to Distance.create(meters = 22, nanometers = 860_000_000),
          133L to Distance.create(meters = 121, nanometers = 615_200_000),
          1_680L to Distance.create(meters = 1_536, nanometers = 192_000_000),
          131_296L to Distance.create(meters = 120_057, nanometers = 62_400_000),
          MAX_VALUE to Distance.create(meters = 8_433_851_390_500_006_997, nanometers = 920_800_000)
      ).map { (yards, expected) ->
        unit.toDistance(yards) shouldBe expected
        Distance.ofYards(yards) shouldBe expected
      }
    }
  }

  Given("miles") {
    val unit = Mile

    When("value does not overflow") {
      Then("distance can be created from it") {
        listOf(
            0L to Distance.Zero,
            1L to Distance.create(meters = 1_609, nanometers = 344_000_000),
            7L to Distance.create(meters = 11_265, nanometers = 408_000_000),
            25L to Distance.create(meters = 40_233, nanometers = 600_000_000),
            133L to Distance.create(meters = 214_042, nanometers = 752_000_000),
            1_680L to Distance.create(meters = 2_703_697, nanometers = 920_000_000),
            131_296L to Distance.create(meters = 211_300_429, nanometers = 824_000_000)
        ).map { (miles, expected) ->
          unit.toDistance(miles) shouldBe expected
          Distance.ofMiles(miles) shouldBe expected
        }
      }
    }
  }
})
