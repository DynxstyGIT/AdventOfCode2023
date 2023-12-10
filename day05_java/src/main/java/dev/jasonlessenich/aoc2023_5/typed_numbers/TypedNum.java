package dev.jasonlessenich.aoc2023_5.typed_numbers;

import dev.jasonlessenich.aoc2023_5.almanac.AlmanacType;

public class TypedNum {
	private final AlmanacType type;
	private final long value;

	public TypedNum(AlmanacType type, long value) {
		this.type = type;
		this.value = value;
	}

	public AlmanacType type() {
		return type;
	}

	public long value() {
		return value;
	}

	@Override
	public String toString() {
		return "TypedNum{" +
				"type=" + type +
				", value=" + value +
				'}';
	}
}
