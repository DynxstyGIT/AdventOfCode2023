package dev.jasonlessenich.aoc2023_2

import scala.io.Source

object Main {
  private final val INPUT = "input.txt"

  private final val MAX_RED = 12
  private final val MAX_GREEN = 13
  private final val MAX_BLUE = 14

  def main(args: Array[String]): Unit = {
    val source = Source.fromFile(INPUT)
    var totalInvalidGames = 0
    var minCubesPerGame = 0
    for (line <- source.getLines().zipWithIndex) {
      val id = line._2 + 1
      val games = line._1.split(":")(1)
        .trim
        .split(";")
      println(s"\n--- Game $id ---")
      val (red, green, blue) = getMinCubesNeeded(games)
      println(s"Needs at least: $red red, $green green, $blue blue")
      if (red <= MAX_RED && green <= MAX_GREEN && blue <= MAX_BLUE) {
         totalInvalidGames += id
      } else {
        println(s"[!] Exceeds maximum amount of cubes!")
      }
      minCubesPerGame += (red * green * blue)
    }
    println(s"\nTotal IDs of games with invalid cube configurations: $totalInvalidGames")
    println(s"Total power of cubes: $minCubesPerGame")
    source.close()
  }

  private def getMinCubesNeeded(games: Array[String]) : (Int, Int, Int) = {
    var (red, green, blue) = (0, 0, 0)
    for (game <- games) {
      val cubes = game.trim.split(",")
      for (cube <- cubes) {
        val tc = cube.trim
        if (cube.contains("red")) {
          val redOption = trimColor(tc, "red")
          if (redOption.isDefined && redOption.get > red)
            red = redOption.get
        } else if (cube.contains("green")) {
          val greenOption = trimColor(tc, "green")
          if (greenOption.isDefined && greenOption.get > green)
            green = greenOption.get
        } else if (cube.contains("blue")) {
          val blueOption = trimColor(tc, "blue")
          if (blueOption.isDefined && blueOption.get > blue)
            blue = blueOption.get
        }
      }
    }
    (red, green, blue)
  }

  private def trimColor(s: String, color: String) : Option[Int] = {
    s.replace(color, "").trim.toIntOption
  }
}