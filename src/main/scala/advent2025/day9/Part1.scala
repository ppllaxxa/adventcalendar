package advent2025.day9

import advent2025.day9.Part1.Direction.{DOWN, LEFT, RIGHT, UP}

import java.awt.Rectangle
import java.awt.geom.{Area, GeneralPath}
import java.text.DecimalFormat
import scala.collection.mutable.Map as ImmMap

object Part1 {

  val input =
    """
7,1
11,1
11,7
9,7
9,5
2,5
2,3
7,3""".stripMargin.split("\r\n").filter(_.nonEmpty)

  def main(args: Array[String]): Unit = {
    part2()
  }

  private def part1(): Unit = {
    val points = input.map(l => {
      val p = l.split(",").map(_.toInt)
      Point(p(0), p(1))
    })
    val allDistances: ImmMap[(Point, Point), Long] = ImmMap()
    for (i <- points.indices) {
      for (j <- i + 1 until points.length) {
        allDistances.put((points(i), points(j)), calcDistance(points(i), points(j)))
      }
    }
    val allDistancesSorted = allDistances.toList.sortWith(_._2 > _._2)
    println(allDistancesSorted.head)
  }
//not working
  private def part2(): Unit = {
    val points = input.map(l => {
      val p = l.split(",").map(_.toInt)
      Point(p(0), p(1))
    })
    val allDistances: ImmMap[(Point, Point), Long] = ImmMap()
    val xSections: ImmMap[Int, List[List[Int]]] = ImmMap()
    val ySections: ImmMap[Int, List[List[Int]]] = ImmMap()

    (points zip (points.tail :+ points.head)).foreach((p1, p2) => {
      if (p1.x == p2.x) {
        val sections = ySections.getOrElse(p1.x, List())
        ySections.put(p1.x, sections :+ List(math.min(p1.y, p2.y), math.max(p1.y, p2.y)))
      } else {
        val sections = xSections.getOrElse(p1.y, List())
        xSections.put(p1.y, sections :+ List(math.min(p1.x, p2.x), math.max(p1.x, p2.x)))
      }
    })

    for (i <- points.indices) {
      for (j <- i + 1 until points.length) {
        if (!skip(points(i), points(j), xSections, ySections))
          allDistances.put((points(i), points(j)), calcDistance(points(i), points(j)))
      }
    }
    val allDistancesSorted = allDistances.toList.sortWith(_._2 > _._2)
    println(allDistancesSorted.head)
  }

  private def skip(p1: Point, p2: Point, xSections: ImmMap[Int, List[List[Int]]], ySections: ImmMap[Int, List[List[Int]]]) = {
    val intersectionOnX =
      (math.min(p1.x, p2.x) to math.max(p1.x, p2.x)).exists(x => {
        val sections = ySections.getOrElse(x, List())
        sections.exists(s => (p1.y > s(0) && p1.y < s(1)) || (p2.y > s(0) && p2.y < s(1)))
      })
    val intersectionOnY =
      (math.min(p1.y, p2.y) to math.max(p1.y, p2.y)).exists(y => {
        val sections = xSections.getOrElse(y, List())
        sections.exists(s => (p1.x > s(0) && p1.x < s(1)) || (p2.x > s(0) && p2.x < s(1)))
      })
    intersectionOnX || intersectionOnY
  }

  private def calcDistance(b1: Point, b2: Point): Long = {
    if (b1.x == b2.x)
      (math.abs(b1.y - b2.y) + 1).toLong
    else if (b1.y == b2.y)
      (math.abs(b1.x - b2.x) + 1).toLong
    else
      (math.abs(b1.x - b2.x) + 1).toLong * (math.abs(b1.y - b2.y) + 1).toLong
  }

  private def getDirection(p1: Point, p2: Point) = {
    if (p1.x == p2.x && p1.y > p2.y) UP
    else if (p1.x == p2.x && p1.y < p2.y) DOWN
    else if (p1.y == p2.y && p1.x > p2.x) LEFT
    else RIGHT
  }

  case class Point(x: Int, y: Int)
  enum Direction:
    case UP, RIGHT, DOWN, LEFT

}
