import com.typesane.bclock.BerlinClock
import com.typesane.bclock.BerlinClock._
import org.scalatest.FunSuite
import org.scalatest.Matchers._

class BerlinClockTest extends FunSuite {

  test("Wrong time input") {
    an [IllegalArgumentException] should be thrownBy new BerlinClock(Hours(-1), Minutes(0), Seconds(0))
    an [IllegalArgumentException] should be thrownBy new BerlinClock(Hours(0), Minutes(-1), Seconds(0))
    an [IllegalArgumentException] should be thrownBy new BerlinClock(Hours(0), Minutes(0), Seconds(-1))
    an [IllegalArgumentException] should be thrownBy new BerlinClock(Hours(24), Minutes(0), Seconds(0))
    an [IllegalArgumentException] should be thrownBy new BerlinClock(Hours(0), Minutes(60), Seconds(0))
    an [IllegalArgumentException] should be thrownBy new BerlinClock(Hours(0), Minutes(0), Seconds(60))
  }

  test("Midnight test") {
    val bc = new BerlinClock(Hours(0), Minutes(0), Seconds(0))
    assert(bc.toString == "O\nO | O | O | O\nO | O | O | O\nO | O | O | O | O | O | O | O | O | O | O\nO | O | O | O")
  }

  test("Seconds test") {
    val bc = new BerlinClock(Hours(0), Minutes(0), Seconds(1))
    assert(bc.toString == "R\nO | O | O | O\nO | O | O | O\nO | O | O | O | O | O | O | O | O | O | O\nO | O | O | O")
  }

  test("5 hours test 1") {
    val bc = new BerlinClock(Hours(5), Minutes(0), Seconds(0))
    assert(bc.toString == "O\nR | O | O | O\nO | O | O | O\nO | O | O | O | O | O | O | O | O | O | O\nO | O | O | O")
  }

  test("5 hours test 2") {
    val bc = new BerlinClock(Hours(20), Minutes(0), Seconds(0))
    assert(bc.toString == "O\nR | R | R | R\nO | O | O | O\nO | O | O | O | O | O | O | O | O | O | O\nO | O | O | O")
  }

  test("1 hours test 1") {
    val bc = new BerlinClock(Hours(1), Minutes(0), Seconds(0))
    assert(bc.toString == "O\nO | O | O | O\nR | O | O | O\nO | O | O | O | O | O | O | O | O | O | O\nO | O | O | O")
  }

  test("1 hours test 2") {
    val bc = new BerlinClock(Hours(4), Minutes(0), Seconds(0))
    assert(bc.toString == "O\nO | O | O | O\nR | R | R | R\nO | O | O | O | O | O | O | O | O | O | O\nO | O | O | O")
  }

  test("5 minutes test 1") {
    val bc = new BerlinClock(Hours(0), Minutes(5), Seconds(0))
    assert(bc.toString == "O\nO | O | O | O\nO | O | O | O\nY | O | O | O | O | O | O | O | O | O | O\nO | O | O | O")
  }

  test("5 minutes test 2") {
    val bc = new BerlinClock(Hours(0), Minutes(55), Seconds(0))
    assert(bc.toString == "O\nO | O | O | O\nO | O | O | O\nY | Y | R | Y | Y | R | Y | Y | R | Y | Y\nO | O | O | O")
  }

  test("1 minutes test 1") {
    val bc = new BerlinClock(Hours(0), Minutes(1), Seconds(0))
    assert(bc.toString == "O\nO | O | O | O\nO | O | O | O\nO | O | O | O | O | O | O | O | O | O | O\nY | O | O | O")
  }

  test("1 minutes test 2") {
    val bc = new BerlinClock(Hours(0), Minutes(4), Seconds(0))
    assert(bc.toString == "O\nO | O | O | O\nO | O | O | O\nO | O | O | O | O | O | O | O | O | O | O\nY | Y | Y | Y")
  }

}
