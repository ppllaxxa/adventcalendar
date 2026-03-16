package advent2025.day8

import scala.collection.mutable.{Map => ImmMap}
//Day 8: Playground
object Part1 {

  def main(args: Array[String]): Unit = {
    val numberConnections = 1000
    val source = scala.io.Source.fromFile(this.getClass.getResource("input.txt").toURI)
    val boxes = try source.getLines().map(l => {
      val n = l.split(",").map(_.toInt)
      Box(n(0), n(1), n(2))
    }).toArray finally source.close()
    val allDistances: ImmMap[(Box, Box), Double] = ImmMap()
    for (i <- boxes.indices) {
      for (j <- i + 1 until boxes.length) {
        allDistances.put((boxes(i), boxes(j)), calcDistance(boxes(i), boxes(j)))
      }
    }
    val allDistancesSorted = allDistances.toList.sortWith(_._2 < _._2).toArray
    val circuits = ImmMap(boxes.toList.map(b => (b, List(b))):_*)

    (0 until numberConnections).foreach(i => {
      val ((box1, box2), dis) = allDistancesSorted(i)
      val circuit1 = circuits(box1)
      val circuit2 = circuits(box2)
      if (circuit1 != circuit2) {
        val circuit = circuit1 ::: circuit2
        circuits.put(box1, circuit)
        circuit.foreach(b => circuits.put(b, circuit))
      }
    })
    val sorted = circuits.values.toSet.toList.sortWith(_.length > _.length)
    println(sorted(0).length*sorted(1).length*sorted(2).length)
  }

  private def calcDistance(b1: Box, b2: Box) = math.sqrt(math.pow(b1.x - b2.x, 2) + math.pow(b1.y - b2.y, 2) + math.pow(b1.z - b2.z, 2))

  case class Box(x: Int, y: Int, z: Int)

}
