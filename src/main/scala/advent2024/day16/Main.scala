package advent2024.day16

import scala.collection.mutable

object Main {

  def main(args: Array[String]): Unit = {
    val source = scala.io.Source.fromFile(this.getClass.getResource("input.txt").toURI)
    val input = """
###############
#.......#....E#
#.#.###.#.###.#
#.....#.#...#.#
#.###.#####.#.#
#.#.#.......#.#
#.#.#####.###.#
#...........#.#
###.#.#####.#.#
#...#.....#.#.#
#.#.#.###.#.#.#
#.....#...#.#.#
#.###.#.#.#.#.#
#S..#.....#...#
###############""".stripMargin.split("\r\n")
    val allNodes = mutable.Map[(Int, Int), Road]()
    for (i <- 1 until input.length - 1) {
      val line = input(i)
      for (j <- 1 until line.length - 1) {
        if (line(j) != '#') {
          val road = allNodes.getOrElse((i, j), Road((i, j), List()))
          if (road.connections.isEmpty)
            road.connections = buildConnections(i, j, input, allNodes)
          allNodes.put((i, j), road)
        }
      }
    }

    val start = allNodes((input.length - 2, 1))
    var onRoad: List[(Road, Int, Direction)] = List((start, 0, E))
    var allCosts: List[Int] = List()
    while (onRoad.nonEmpty) {
      val (currentRoad, cost, fromDirection) = onRoad.head
      if (input(currentRoad.key._1)(currentRoad.key._2) == 'E')
        allCosts = allCosts :+ cost
        onRoad = onRoad.tail
      else {
        val newRoads = currentRoad.connections.filter((neighbor, direction) => !(neighbor.isVisited(direction) || direction.isBack(fromDirection))).map((neighbor, direction) => {
          val newCost =
            if (fromDirection != direction) {
              1001 + cost
            } else 1 + cost
          (neighbor, newCost, direction)
        })
        currentRoad.visitedFromDirections = currentRoad.visitedFromDirections :+ fromDirection
        onRoad = (onRoad.tail ::: newRoads).sortWith(_._2 < _._2)
      }
    }
    println(allCosts)
  }

  def buildConnections(i: Int, j: Int, input: Array[String], allNodes: mutable.Map[(Int, Int), Road]): List[(Road, Direction)] = {
    val north =
      if (input(i - 1)(j) != '#')
        Option(allNodes.getOrElse((i - 1, j), Road((i - 1, j), List())))
      else None
    val east =
      if (input(i)(j + 1) != '#')
        Option(allNodes.getOrElse((i, j + 1), Road((i, j + 1), List())))
      else None
    val south =
      if (input(i + 1)(j) != '#')
        Option(allNodes.getOrElse((i + 1, j), Road((i + 1, j), List())))
      else None
    val west =
      if (input(i)(j - 1) != '#')
        Option(allNodes.getOrElse((i, j - 1), Road((i, j - 1), List())))
      else None
    val connections = List((north, N), (east, E), (south, S), (west, W)).filter(_._1.isDefined).map(p => (p._1.get, p._2))
    connections.foreach(p => allNodes.put(p._1.key, p._1))
    connections
  }
  //105508

  case class Road(key: (Int, Int), var connections: List[(Road, Direction)], var visitedFromDirections: List[Direction] = List()) {
    override def toString: String = s"Road(${key._1}, ${key._2}, ${connections.length}, $visitedFromDirections)"

    def isVisited(fromDirection: Direction) = visitedFromDirections.contains(fromDirection)
  }

  trait Direction {
    def isBack(d: Direction): Boolean
  }

  case object N extends Direction {
    override def isBack(d: Direction): Boolean = d == S
  }

  case object E extends Direction {
    override def isBack(d: Direction): Boolean = d == W
  }

  case object S extends Direction {
    override def isBack(d: Direction): Boolean = d == N
  }

  case object W extends Direction {
    override def isBack(d: Direction): Boolean = d == E
  }

  case object NESW extends Direction {
    override def isBack(d: Direction): Boolean = false
  }
}

/**
 *   N
 * W   E
 *   S
 * */