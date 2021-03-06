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
  /**
   * Minimal and maximal distances that contain a natural [count of units][Length.measure] before a different unit
   * is a better fit for the same distances.
   */
  public val bounds: ClosedRange<Distance>,
  private val ordinal: Int,
  private val name: String,
) : Comparable<T> where T : LengthUnit<T> {
  final override fun compareTo(other: T): Int = ordinal.compareTo(other.ordinal)

  final override fun toString(): String = name

  /**
   * All available units in this [length unit][LengthUnit] system.
   */
  public abstract val units: List<T>

  /**
   * Checks if a distance is in range of this unit.
   */
  public operator fun contains(distance: Distance): Boolean = distance.abs() in bounds

  /**
   * Checks if a length is in range of this unit.
   */
  public operator fun contains(length: Length<*>): Boolean = length.distance in this

  public companion object {
    /**
     * All available length units.
     */
    public val units: List<LengthUnit<*>> by lazy(NONE) {
      SiLengthUnit.units + ImperialLengthUnit.units
    }
  }
}

/**
 * Meter based units.
 */
public sealed class SiLengthUnit(
  meterRatio: BigDecimal,
  bounds: ClosedRange<Distance>,
  ordinal: Int,
  name: String,
) : LengthUnit<SiLengthUnit>(meterRatio, bounds, ordinal, name) {
  public object Nanometer : SiLengthUnit(
      meterRatio = 0.000_000_001.toBigDecimal(),
      bounds = Distance.Zero..Distance.create(nanometers = 1_000) - Distance.Epsilon,
      ordinal = 0,
      name = "Nanometer",
  )

  public object Micrometer : SiLengthUnit(
      meterRatio = 0.000_001.toBigDecimal(),
      bounds = Distance.create(nanometers = 1_000)..Distance.create(nanometers = 1_000_000) - Distance.Epsilon,
      ordinal = 1,
      name = "Micrometer",
  )

  public object Millimeter : SiLengthUnit(
      meterRatio = 0.001.toBigDecimal(),
      bounds = Distance.create(nanometers = 1_000_000)..Distance.create(nanometers = 10_000_000) - Distance.Epsilon,
      ordinal = 2,
      name = "Millimeter",
  )

  public object Centimeter : SiLengthUnit(
      meterRatio = 0.01.toBigDecimal(),
      bounds = Distance.create(nanometers = 10_000_000)..Distance.create(nanometers = 100_000_000) - Distance.Epsilon,
      ordinal = 3,
      name = "Centimeter",
  )

  public object Decimeter : SiLengthUnit(
      meterRatio = 0.1.toBigDecimal(),
      bounds = Distance.create(nanometers = 100_000_000)..Distance.create(meters = 1) - Distance.Epsilon,
      ordinal = 4,
      name = "Decimeter",
  )

  public object Meter : SiLengthUnit(
      meterRatio = BigDecimal.ONE,
      bounds = Distance.create(meters = 1)..Distance.create(meters = 10) - Distance.Epsilon,
      ordinal = 5,
      name = "Meter",
  )

  public object Decameter : SiLengthUnit(
      meterRatio = BigDecimal.TEN,
      bounds = Distance.create(meters = 10)..Distance.create(meters = 100) - Distance.Epsilon,
      ordinal = 6,
      name = "Decameter",
  )

  public object Hectometer : SiLengthUnit(
      meterRatio = 100.toBigDecimal(),
      bounds = Distance.create(meters = 100)..Distance.create(meters = 1_000) - Distance.Epsilon,
      ordinal = 7,
      name = "Hectometer",
  )

  public object Kilometer : SiLengthUnit(
      meterRatio = 1_000.0.toBigDecimal(),
      bounds = Distance.create(meters = 1_000)..Distance.create(meters = 1_000_000) - Distance.Epsilon,
      ordinal = 8,
      name = "Kilometer",
  )

  public object Megameter : SiLengthUnit(
      meterRatio = 1_000_000.0.toBigDecimal(),
      bounds = Distance.create(meters = 1_000_000)..Distance.create(meters = 1_000_000_000) - Distance.Epsilon,
      ordinal = 9,
      name = "Megameter",
  )

  public object Gigameter : SiLengthUnit(
      meterRatio = 1_000_000_000.0.toBigDecimal(),
      bounds = Distance.create(meters = 1_000_000_000)..Distance.Max,
      ordinal = 10,
      name = "Gigameter",
  )

  override val units: List<SiLengthUnit> get() = SiLengthUnit.units

  public companion object {
    /**
     * All available units in the [SI][SiLengthUnit] system.
     */
    public val units: List<SiLengthUnit> by lazy(NONE) {
      listOf(
          Nanometer,
          Micrometer,
          Millimeter,
          Centimeter,
          Decimeter,
          Meter,
          Decameter,
          Hectometer,
          Kilometer,
          Megameter,
          Gigameter,
      )
    }
  }
}

/**
 * Basic units from the imperial system.
 */
public sealed class ImperialLengthUnit(
  meterRatio: BigDecimal,
  bounds: ClosedRange<Distance>,
  ordinal: Int,
  name: String,
) : LengthUnit<ImperialLengthUnit>(meterRatio, bounds, ordinal, name) {
  public object Inch : ImperialLengthUnit(
      meterRatio = 0.025_4.toBigDecimal(),
      bounds = Distance.Zero..Distance.create(nanometers = 304_800_000) - Distance.Epsilon,
      ordinal = 0,
      name = "Inch",
  )

  public object Foot : ImperialLengthUnit(
      meterRatio = 0.304_8.toBigDecimal(),
      bounds = Distance.create(nanometers = 304_800_000)..Distance.create(nanometers = 914_400_000) - Distance.Epsilon,
      ordinal = 1,
      name = "Foot",
  )

  public object Yard : ImperialLengthUnit(
      meterRatio = 0.914_4.toBigDecimal(),
      bounds = Distance.create(nanometers = 914_400_000)..Distance.create(1_609, 344_000_000) - Distance.Epsilon,
      ordinal = 2,
      name = "Yard",
  )

  public object Mile : ImperialLengthUnit(
      meterRatio = 1_609.344.toBigDecimal(),
      bounds = Distance.create(1_609, 344_000_000)..Distance.Max,
      ordinal = 3,
      name = "Mile",
  )

  override val units: List<ImperialLengthUnit> get() = ImperialLengthUnit.units

  public companion object {
    /**
     * All available units in the [imperial][ImperialLengthUnit] system.
     */
    public val units: List<ImperialLengthUnit> by lazy(NONE) {
      listOf(Inch, Foot, Yard, Mile)
    }
  }
}
