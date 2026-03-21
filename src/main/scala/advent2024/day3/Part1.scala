package advent2024.day3

object Part1 {

  private val input =
"""
"""


  def main(args: Array[String]): Unit = {
    part2
  }

  def part1 = {
    val regex = "mul\\(([0-9]{1,3}),([0-9]{1,3})\\)".r
    val res = regex.findAllMatchIn(input).toList.map(m => {
      m.group(1).toInt * m.group(2).toInt
    }).sum
    println(res)
  }

  def part2 = {
    val inp = "xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"
    var enabled = true
    val regex = "don't\\(\\)|do\\(\\)|mul\\(([0-9]{1,3}),([0-9]{1,3})\\)".r
    val res = regex.findAllMatchIn(input).toList.map(m => {
      val str = m.matched
      if (str == "don't()") {
        enabled = false
        0
      } else if (str == "do()") {
        enabled = true
        0
      } else if (enabled)
        m.group(1).toInt * m.group(2).toInt
      else
        0
    }).sum
    println(res)
  }
}
