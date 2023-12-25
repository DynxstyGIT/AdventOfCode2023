package dev.jasonlessenich.day10_java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
	private static final String PART_1_INPUT = "input1.txt";

	public static void main(String[] args) throws IOException {
		final Maze maze1 = Maze.from(Files.readString(Path.of(PART_1_INPUT)));
		int part1 = maze1.getMaxDistanceFromStart();
		System.out.println("Part 1: " + part1);
	}
}