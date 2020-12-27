package io.mehow.ruler

import java.math.BigDecimal
import kotlin.LazyThreadSafetyMode.NONE

/**
 * A unit of measurement that can be applied to length.
 *
 * Set of units within the same measurement system cannot have overlapping bounds.
 */
public sealed class LengthUnit<T>(
  /**
   * Amount of meters in quantity of 1 of this unit.
   */
  public val meterRatio: BigDecimal,
  private val lowerBound: Distance,
  private val upperBound: Distance?,
  private val ordinal: Int,
) : Comparable<T>, Iterable<T> where T : LengthUnit<T> {
  final override fun compareTo(other: T): Int = ordinal.compareTo(other.ordinal)

  internal operator fun contains(distance: Distance): Boolean {
    val meters = distance.meters.abs()
    val inLowerBound = meters >= lowerBound.meters
    val inUpperBound = upperBound?.meters?.let { meters < it } != false
    return inLowerBound && inUpperBound
  }
}

/**
 * Meter based units.
 */
public sealed class SiLengthUnit(
  meterRatio: BigDecimal,
  lowerBound: Distance,
  upperBound: Distance?,
  ordinal: Int,
) : LengthUnit<SiLengthUnit>(meterRatio, lowerBound, upperBound, ordinal) {
  public object Nanometer : SiLengthUnit(
      meterRatio = 0.000_000_001.toBigDecimal(),
      lowerBound = Distance.Zero,
      upperBound = Distance.create(nanometers = 1_000),
      ordinal = 0
  )

  public object Micrometer : SiLengthUnit(
      meterRatio = 0.000_001.toBigDecimal(),
      lowerBound = Distance.create(nanometers = 1_000),
      upperBound = Distance.create(nanometers = 1_000_000),
      ordinal = 1
  )

  public object Millimeter : SiLengthUnit(
      meterRatio = 0.001.toBigDecimal(),
      lowerBound = Distance.create(nanometers = 1_000_000),
      upperBound = Distance.create(meters = 1),
      ordinal = 2
  )

  public object Meter : SiLengthUnit(
      meterRatio = BigDecimal.ONE,
      lowerBound = Distance.create(meters = 1),
      upperBound = Distance.create(meters = 1_000),
      ordinal = 3
  )

  public object Kilometer : SiLengthUnit(
      meterRatio = 1_000.0.toBigDecimal(),
      lowerBound = Distance.create(meters = 1_000),
      upperBound = Distance.create(meters = 1_000_000),
      ordinal = 4
  )

  public object Megameter : SiLengthUnit(
      meterRatio = 1_000_000.0.toBigDecimal(),
      lowerBound = Distance.create(meters = 1_000_000),
      upperBound = Distance.create(meters = 1_000_000_000),
      ordinal = 5
  )

  public object Gigameter : SiLengthUnit(
      meterRatio = 1_000_000_000.0.toBigDecimal(),
      lowerBound = Distance.create(meters = 1_000_000_000),
      upperBound = null,
      ordinal = 6
  )

  final override fun iterator(): Iterator<SiLengthUnit> = values.iterator()

  public companion object {
    public val values: List<SiLengthUnit> by lazy(NONE) {
      listOf(Nanometer, Micrometer, Millimeter, Meter, Kilometer, Megameter, Gigameter)
    }
  }
}

/**
 * Basic units from the imperial system.
 */
public sealed class ImperialLengthUnit(
  meterRatio: BigDecimal,
  lowerBound: Distance,
  upperBound: Distance?,
  ordinal: Int,
) : LengthUnit<ImperialLengthUnit>(meterRatio, lowerBound, upperBound, ordinal) {
  public object Inch : ImperialLengthUnit(
      meterRatio = 0.025_4.toBigDecimal(),
      lowerBound = Distance.Zero,
      upperBound = Distance.create(nanometers = 304_800_000),
      ordinal = 0
  )

  public object Foot : ImperialLengthUnit(
      meterRatio = 0.304_8.toBigDecimal(),
      lowerBound = Distance.create(nanometers = 304_800_000),
      upperBound = Distance.create(nanometers = 914_400_000),
      ordinal = 1
  )

  public object Yard : ImperialLengthUnit(
      meterRatio = 0.914_4.toBigDecimal(),
      lowerBound = Distance.create(nanometers = 914_400_000),
      upperBound = Distance.create(meters = 1_609, nanometers = 344_000_000),
      ordinal = 2
  )

  public object Mile : ImperialLengthUnit(
      meterRatio = 1_609.344.toBigDecimal(),
      lowerBound = Distance.create(meters = 1_609, nanometers = 344_000_000),
      upperBound = null,
      ordinal = 3
  )

  final override fun iterator(): Iterator<ImperialLengthUnit> = values.iterator()

  public companion object {
    public val values: List<ImperialLengthUnit> by lazy(NONE) {
      listOf(Inch, Foot, Yard, Mile)
    }
  }
}
