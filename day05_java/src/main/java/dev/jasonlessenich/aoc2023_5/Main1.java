package dev.jasonlessenich.aoc2023_5;

import dev.jasonlessenich.aoc2023_5.almanac.Almanac;
import dev.jasonlessenich.aoc2023_5.almanac.AlmanacType;
import dev.jasonlessenich.aoc2023_5.typed_numbers.TypedNum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Main1 {
	private static final String INPUT = "input.txt";

	public static void main(String[] args) throws IOException {
		final List<String> lines = Files.readAllLines(Path.of(INPUT));
		final Almanac almanac = Almanac.parse(lines, false);
		long lowestLocation = Long.MAX_VALUE;
		for (TypedNum seed : almanac.seeds()) {
			final TypedNum converted = almanac.convert(seed, AlmanacType.LOCATION);
			if (converted.value() < lowestLocation) {
				lowestLocation = converted.value();
			}
		}
		System.out.println("Lowest Location: " + lowestLocation);
	}
}