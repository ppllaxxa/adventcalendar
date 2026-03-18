package advent2024.day16

import advent2024.day16.Part1.Point.{EAST, NORTH, SOUTH, WEST}

import scala.collection.mutable

//Day 16: Reindeer Maze
object Part1 {

  val input =
    """
#####
#..E#
#...#
#S..#
#####""".stripMargin.split("\r\n").filter(_.nonEmpty)

  def main(args: Array[String]): Unit = {
//    println(part1)
    val p2 = part2
    println(p2)
//    println(part2.flatMap(_._1.path).toSet.size)
  }

  def part2: List[(Road, Int)] = {
    val start = Point(input.length - 2, 1)
    val visited: mutable.Map[(Point, Point), Int] = mutable.Map()
    var onRoad = List((Road(List(start), EAST), 0))
    var results: List[(Road, Int)] = List()
    var bestCost = 0
    while (onRoad.nonEmpty) {
      val (road, cost) = onRoad.head
      onRoad = onRoad.tail
      if (input(road.position.y)(road.position.x) == 'E') {
        results = (road, cost) +: results
        return results
      } else if (cost <= visited.getOrElse(road.key, Int.MaxValue)) {
        visited.addOne(road.key, cost)
        val forwardRoad = road.step
        if (input(forwardRoad.position.y)(forwardRoad.position.x) != '#')
          onRoad = (forwardRoad, cost + 1) +: onRoad
        val leftRoad = road.left.step
        if (input(leftRoad.position.y)(leftRoad.position.x) != '#')
          onRoad = (leftRoad, cost + 1001) +: onRoad
        val rightRoad = road.right.step
        if (input(rightRoad.position.y)(rightRoad.position.x) != '#')
          onRoad = (rightRoad, cost + 1001) +: onRoad
        onRoad = onRoad.sortWith(_._2 < _._2)
      }
    }
    return results
  }

  case class Road(path: List[Point], direction: Point) {
    def key = (path.head, direction)
    def position = path.head
    def step = this.copy(path = (position + direction) +: path)
    def right = copy(direction = direction match {
      case NORTH => EAST
      case EAST => SOUTH
      case SOUTH => WEST
      case WEST => NORTH
    })
    def left = copy(direction = direction match {
      case NORTH => WEST
      case WEST => SOUTH
      case SOUTH => EAST
      case EAST => NORTH
    })

    override def toString: String = "Path: " + path.reverse.toString()
  }

  case class Point(y: Int, x: Int, label: String = "") {
    def +(p: Point) = Point(this.y + p.y, this.x + p.x)
    override def toString: String = if (label.isEmpty) s"x:$x, y:$y" else label
  }
  object Point {
    val NORTH = Point(-1, 0, "NORTH")
    val EAST = Point(0, 1, "EAST")
    val SOUTH = Point(1, 0, "EAST")
    val WEST = Point(0, -1, "WEST")
  }
}
/**
 *   N
 * W   E
 *   S
 * */