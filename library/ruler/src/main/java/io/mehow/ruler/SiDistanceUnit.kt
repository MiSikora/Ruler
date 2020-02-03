package io.mehow.ruler

import java.math.BigDecimal
import java.math.RoundingMode.CEILING
import kotlin.Double.Companion.MAX_VALUE

enum class SiDistanceUnit(
  private val applicableRange: ClosedRange<BigDecimal>,
  private val meterRatio: BigDecimal
) : DistanceUnit, Iterable<SiDistanceUnit> {
  Nanometer(
      0.0.toBigDecimal()..0.000_001.toBigDecimal(),
      0.000_000_001.toBigDecimal()
  ),
  Micrometer(
      0.000_001.toBigDecimal()..0.001.toBigDecimal(),
      0.000_001.toBigDecimal()
  ),
  Millimeter(
      0.001.toBigDecimal()..1.0.toBigDecimal(),
      0.001.toBigDecimal()
  ),
  Meter(
      1.0.toBigDecimal()..1_000.0.toBigDecimal(),
      1.0.toBigDecimal()
  ),
  Kilometer(
      1_000.0.toBigDecimal()..1_000_000.0.toBigDecimal(),
      1_000.0.toBigDecimal()
  ),
  Megameter(
      1_000_000.0.toBigDecimal()..1_000_000_000.0.toBigDecimal(),
      1_000_000.0.toBigDecimal()
  ),
  Gigameter(
      1_000_000_000.0.toBigDecimal()..MAX_VALUE.toBigDecimal(),
      1_000_000_000.0.toBigDecimal()
  ) {
    override fun appliesRangeTo(length: BigDecimal): Boolean {
      return length >= super.applicableRange.start
    }
  };

  override fun toLength(value: Long): Length {
    val meters = value.toBigDecimal() * meterRatio
    val exactMeters = meters.toBigInteger().longValueExact()
    val nanometers = (meters - exactMeters.toBigDecimal()) * 1_000_000_000.toBigDecimal()
    return Length.create(exactMeters, nanometers.toLong())
  }

  override fun toMeasuredLength(length: BigDecimal): Double {
    return length.divide(meterRatio, 9, CEILING).toDouble()
  }

  override fun iterator() = values.iterator()

  override fun appliesRangeTo(length: BigDecimal): Boolean {
    return length >= applicableRange.start && length < applicableRange.endInclusive
  }

  companion object {
    private val values = values().toList()
  }
}
