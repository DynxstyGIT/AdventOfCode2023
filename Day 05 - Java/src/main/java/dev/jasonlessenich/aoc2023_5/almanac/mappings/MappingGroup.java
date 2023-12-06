package dev.jasonlessenich.aoc2023_5.almanac.mappings;

import dev.jasonlessenich.aoc2023_5.util.Range;
import dev.jasonlessenich.aoc2023_5.typed_numbers.TypedNum;

import java.util.Optional;

public record MappingGroup(long destRangeStart, long srcRangeStart, long rangeLength) {
	public Optional<Long> convert(TypedNum i) {
		if (i == null) return Optional.empty();
		long diff = destRangeStart + (i.value() - srcRangeStart);
		return Optional.ofNullable(getDestinationRange().test(diff) ? diff : null);
	}

	public Range getDestinationRange() {
		return new Range(destRangeStart, destRangeStart + rangeLength - 1);
	}

	public Range getSourceRange() {
		return new Range(srcRangeStart, srcRangeStart + rangeLength - 1);
	}
}
