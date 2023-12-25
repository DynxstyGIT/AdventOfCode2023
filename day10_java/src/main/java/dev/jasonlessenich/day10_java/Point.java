package dev.jasonlessenich.day10_java;

public record Point(int x, int y) {
	public Point move(Direction direction) {
		return switch (direction) {
			case NORTH -> new Point(x, y - 1);
			case EAST -> new Point(x + 1, y);
			case SOUTH -> new Point(x, y + 1);
			case WEST -> new Point(x - 1, y);
		};
	}
}
