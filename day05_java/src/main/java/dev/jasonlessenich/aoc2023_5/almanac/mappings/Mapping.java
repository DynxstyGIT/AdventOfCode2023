package dev.jasonlessenich.aoc2023_5.almanac.mappings;

import dev.jasonlessenich.aoc2023_5.almanac.AlmanacType;
import dev.jasonlessenich.aoc2023_5.typed_numbers.TypedNum;

import java.util.List;
import java.util.Optional;

public record Mapping(AlmanacType from, AlmanacType to, List<MappingGroup> groups) {
	public TypedNum convert(TypedNum i) {
		if (i.type() != from)
			throw new IllegalArgumentException("Cannot convert " + i.type() + " to " + from);
		TypedNum result = new TypedNum(to, i.value());
		for (MappingGroup group : groups) {
			Optional<Long> convertOptional = group.convert(i);
			if (convertOptional.isPresent()) {
				result = new TypedNum(to, convertOptional.get());
				break;
			}
		}
		return result;
	}
}
