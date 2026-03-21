package advent2024.day11

import scala.collection.mutable

object Part1 {

  private val input =
    """"""

  private val known = mutable.Map[String, List[String]]()

  //maybe we could replace 0, 1 with something
  def main(args: Array[String]): Unit = {
    println(part2)
  }

  def part1 = {
    var numbers = input.split(" ").toList
    var prev = 0
    1 to 30 foreach { i =>
      numbers = numbers.flatMap {
        case "0" => List("1")
        case n if n.length % 2 == 0 =>
          List(BigDecimal(n.substring(0, n.length / 2)).toString, BigDecimal(n.substring(n.length / 2)).toString)
        case n =>
          List((BigDecimal(n) * 2024).toString)
      }
      println(s"i: $i, lenght: ${numbers.length}, diff: ${numbers.length - prev}")
      prev = numbers.length
    }
    numbers.length
  }

  def part2 = {
    var result = input.split(" ").toList.map((_, 1L)).toMap
    1 to 75 foreach { i =>
      val currentIter = mutable.Map[String, Long]()
      result.map { (number, nbr) =>
        number match {
          case "0" =>
            currentIter.put("1", currentIter.getOrElse("1", 0L) + nbr)
          case n  if n.length % 2 == 0 =>
            val left = n.substring(0, n.length / 2)
            val right = n.substring(n.length / 2).toLong.toString
            currentIter.put(left, currentIter.getOrElse(left, 0L) + nbr)
            currentIter.put(right, currentIter.getOrElse(right, 0L) + nbr)
          case n =>
            val value = (n.toLong * 2024).toString
            currentIter.put(value, currentIter.getOrElse(value, 0L) + nbr)
        }
      }
      result = currentIter.toMap
    }
    result.values.sum
  }
}
