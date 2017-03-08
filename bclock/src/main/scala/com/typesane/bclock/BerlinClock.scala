package com.typesane.bclock

object BerlinClock {

  // Possible states of each lamp.
  sealed trait LampState
  case object LitRed extends LampState { override def toString = "R" }
  case object LitYellow extends LampState { override def toString = "Y" }
  case object NotLit extends LampState { override def toString = "O" }

  // Type safe parameters. Otherwise one could mix the order.
  case class Hours(h: Int)
  case class Minutes(m: Int)
  case class Seconds(s: Int)

}

// This design is quite simple. I haven't introduced boilerplate case classes for things
// that in my opinion didn't need that.
case class BerlinClock(hours: BerlinClock.Hours, minutes: BerlinClock.Minutes, seconds: BerlinClock.Seconds) {

  import BerlinClock._

  // Check if time is given to us properly.
  private val h = hours.h
  private val m = minutes.m
  private val s = seconds.s
  require(h >= 0 && h < 24 && m >= 0 && m <= 59 && s >= 0 && s <= 59)

  // Constants.
  private val LampPerSecondSize = 1
  private val LampPer5HoursSize = 4
  private val LampPerHoursSize = 4
  private val LampPer5MinutesSize = 11
  private val LampPerMinuteSize = 4

  // Using `Tuple` would be type safer, but I would have to write more boiler plate code for the business
  // logic as `Tuple` is not as flexible as `List`. It's a trade-off for coding flexibility. The cost is
  // a runtime check plus being extra careful while doing business logic operations. Most likely it's also
  // slower when using a `List` but we're not trying to do premature optimization here.
  private val lampPerSecond: LampState = sToLampPerSecond(s)
  private val lampPer5Hours: List[LampState] = hToLampPer5Hours(h)
  private val lampPerHour: List[LampState] = hToLampPerHour(h)
  private val lampPer5Minutes: List[LampState] = mToLampPer5Minute(m)
  private val lampPerMinute: List[LampState] = mToLampPerMinute(m)
  require(lampPer5Hours.size == LampPer5HoursSize &&
    lampPerHour.size == LampPerHoursSize &&
    lampPer5Minutes.size == LampPer5MinutesSize &&
    lampPerMinute.size == LampPerMinuteSize)

  // Conversions.
  private def sToLampPerSecond(s: Int) = if (s % 2 == 0) NotLit else LitRed
  private def hToLampPer5Hours(h: Int) = List.fill(h / 5)(LitRed).padTo(LampPer5HoursSize, NotLit)
  private def hToLampPerHour(h: Int) = List.fill(h % 5)(LitRed).padTo(LampPerHoursSize, NotLit)
  private def mToLampPer5Minute(m: Int) = List.fill(m / 5)(LitYellow).padTo(LampPer5MinutesSize, NotLit)
    .zipWithIndex.map { case (ls, idx) =>
    // Fix the 15, 30 and 45 minutes lamps to light up red.
    if (ls == LitYellow && (idx == 2 || idx == 5 || idx == 8)) LitRed else ls
  }
  private def mToLampPerMinute(m: Int) = List.fill(m % 5)(LitYellow).padTo(LampPerMinuteSize, NotLit)

  // --== PUBLIC ==--

  override def toString: String =
    lampPerSecond + "\n" +
    lampPer5Hours.mkString(" | ") + "\n" +
    lampPerHour.mkString(" | ") + "\n" +
    lampPer5Minutes.mkString(" | ") + "\n" +
    lampPerMinute.mkString(" | ")

}
