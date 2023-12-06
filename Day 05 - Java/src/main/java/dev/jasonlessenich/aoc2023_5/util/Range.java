package dev.jasonlessenich.aoc2023_5.util;

import java.util.function.Predicate;

public record Range(long from, long to) implements Predicate<Long> {
	@Override
	public boolean test(Long l) {
		return l >= from && l <= to;
	}
}
