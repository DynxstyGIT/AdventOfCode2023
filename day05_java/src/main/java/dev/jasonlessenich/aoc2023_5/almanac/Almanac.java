package dev.jasonlessenich.aoc2023_5.almanac;

import dev.jasonlessenich.aoc2023_5.almanac.mappings.Mapping;
import dev.jasonlessenich.aoc2023_5.almanac.mappings.MappingGroup;
import dev.jasonlessenich.aoc2023_5.util.Range;
import dev.jasonlessenich.aoc2023_5.typed_numbers.RangedTypedNum;
import dev.jasonlessenich.aoc2023_5.typed_numbers.TypedNum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

public record Almanac(List<TypedNum> seeds, List<Mapping> mappings) {
	public static Almanac parse(List<String> lines, boolean seedRanges) {
		List<TypedNum> seeds = null;
		final Stack<Mapping> mappings = new Stack<>();
		for (String line : lines) {
			if (line.isEmpty() || line.isBlank()) continue;
			if (line.startsWith("seeds: ")) {
				final List<Long> nums = Arrays.stream(line.replace("seeds: ", "")
								.split("\\s+"))
						.map(Long::parseLong)
						.toList();
				if (seedRanges) {
					if (nums.size() % 2 != 0) {
						throw new IllegalStateException("Seed ranges must be even!");
					}
					final List<TypedNum> ranges = new ArrayList<>();
					for (int i = 0; i < nums.size(); i += 2) {
						final long from = nums.get(i);
						final long to = nums.get(i + 1);
						ranges.add(new RangedTypedNum(AlmanacType.SEED, from, new Range(from, to)));
					}
					seeds = ranges;
				} else {
					seeds = nums.stream().map(AlmanacType.SEED::num).toList();
				}
			} else if (line.endsWith("map:")) {
				final String name = line.replace(" map:", "");
				final String[] split = name.split("-to-");
				final Optional<AlmanacType> from = AlmanacType.findMapping(split[0]);
				final Optional<AlmanacType> to = AlmanacType.findMapping(split[1]);
				if (from.isEmpty() || to.isEmpty()) {
					throw new IllegalStateException("Unknown Almanac Type in line: " + line);
				}
				mappings.add(new Mapping(from.get(), to.get(), new ArrayList<>()));
			} else {
				final Long[] l = Arrays.stream(line.split("\\s+")).map(Long::parseLong).toArray(Long[]::new);
				mappings.peek().groups().add(new MappingGroup(l[0], l[1], l[2]));
			}
		}
		if (seeds == null)
			throw new IllegalStateException("Could not find seeds!");
		return new Almanac(seeds, mappings);
	}

	public TypedNum convert(TypedNum typedNum, AlmanacType to) {
		if (typedNum.type() == to) return typedNum;
		final Optional<Mapping> mappingOptional = mappings.stream()
				.filter(m -> m.from() == typedNum.type())
				.findFirst();
		if (mappingOptional.isEmpty()) {
			throw new IllegalStateException("Could not convert: %s (%s) -> %s".formatted(typedNum.type(), typedNum.value(), to));
		}
		final TypedNum converted = mappingOptional.get().convert(typedNum);
		if (converted.type() != to) {
			return convert(converted, to);
		}
		return converted;
	}

	@Override
	public String toString() {
		return "seeds: %s, mappings: %s".formatted(seeds, mappings);
	}
}
