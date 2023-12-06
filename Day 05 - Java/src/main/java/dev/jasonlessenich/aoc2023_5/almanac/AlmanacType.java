package dev.jasonlessenich.aoc2023_5.almanac;

import dev.jasonlessenich.aoc2023_5.typed_numbers.TypedNum;

import java.util.Arrays;
import java.util.Optional;

public enum AlmanacType {
	SEED,
	SOIL,
	FERTILIZER,
	WATER,
	LIGHT,
	TEMPERATURE,
	HUMIDITY,
	LOCATION;

	public static Optional<AlmanacType> findMapping(String s) {
		return Arrays.stream(values()).filter(t -> t.name().equalsIgnoreCase(s)).findFirst();
	}

	public TypedNum num(long value) {
		return new TypedNum(this, value);
	}
}
