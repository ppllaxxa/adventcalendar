package advent2024.day2

object Part1 {

  val input =
    """
""".stripMargin.split("\r\n").filter(_.nonEmpty)

  def main(args: Array[String]): Unit = {
    val numbers = input.map(_.split(" ").map(_.toInt).toList).toList
    val result = numbers.filter(l => {
      isRealSafe(l)
    })
    println(result.size)
  }

  private def isSafe(l: List[Int]) = {
    var safe = true
    val asc = l(0) > l(1)
    if (asc)
      !l.sliding(2).exists {
        case List(a, b) =>
          val diff = a - b
          !(diff >= 1 && diff <= 3)
      }
    else
      !l.sliding(2).exists {
        case List(a, b) =>
          val diff = a - b
          !(diff >= -3 && diff <= -1)
      }
  }

  private def isRealSafe(l: List[Int]) = {
    if (isSafe(l))
      true
    else {
      l.zipWithIndex.exists { (el, i) =>
        val (p1, p2) = l.span(_ != el)
        val newL = l.take(i) ++ l.drop(i + 1)
        isSafe(newL)
      }
    }
  }
}
