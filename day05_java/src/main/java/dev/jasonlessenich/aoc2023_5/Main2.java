package dev.jasonlessenich.aoc2023_5;

import dev.jasonlessenich.aoc2023_5.almanac.Almanac;
import dev.jasonlessenich.aoc2023_5.almanac.AlmanacType;
import dev.jasonlessenich.aoc2023_5.typed_numbers.RangedTypedNum;
import dev.jasonlessenich.aoc2023_5.typed_numbers.TypedNum;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;

public class Main2 {
	private static final String INPUT = "input.txt";

	public static void main(String[] args) throws IOException {
		final List<String> lines = Files.readAllLines(Path.of(INPUT));
		final Almanac almanac = Almanac.parse(lines, true);
		long lowestLocation = Long.MAX_VALUE;
		for (TypedNum seed : almanac.seeds()) {
			if (seed instanceof RangedTypedNum ranged) {
				for (Iterator<TypedNum> it = ranged.iterator(); it.hasNext(); ) {
					TypedNum num = it.next();
					final TypedNum converted = almanac.convert(num, AlmanacType.LOCATION);
					if (converted.value() < lowestLocation) {
						lowestLocation = converted.value();
					}
				}
			}
		}
		System.out.println("Lowest Location: " + lowestLocation);
	}
}