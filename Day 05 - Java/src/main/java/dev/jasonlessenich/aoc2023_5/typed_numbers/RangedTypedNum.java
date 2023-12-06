package dev.jasonlessenich.aoc2023_5.typed_numbers;

import dev.jasonlessenich.aoc2023_5.almanac.AlmanacType;
import dev.jasonlessenich.aoc2023_5.util.Range;

import java.util.Iterator;

public class RangedTypedNum extends TypedNum {
	private final Range range;

	public RangedTypedNum(AlmanacType type, long value, Range range) {
		super(type, value);
		this.range = range;
	}

	public Range range() {
		return range;
	}

	public Iterator<TypedNum> iterator() {
		return new Iterator<>() {
			private long i = value();

			@Override
			public boolean hasNext() {
				return i < value() + range.to();
			}

			@Override
			public TypedNum next() {
				return new TypedNum(type(), i++);
			}
		};
	}
}
