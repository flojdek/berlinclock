import com.typesane.bclock.BerlinClock._
import com.typesane.bclock._

object MainApp {

  def main(args: Array[String]) = {
    val sc = new java.util.Scanner(System.in)
    while (true) {
      print("enter time (hh:mm:ss) (Ctrl+C to exit): ")
      sc.next().split(":").map(_.toInt) match {
        case Array(hh, mm, ss) =>
          println(new BerlinClock(Hours(hh), Minutes(mm), Seconds(ss)) toString)
        case _ =>
          println("error: can't process input, try again")
      }
    }
  }

}
