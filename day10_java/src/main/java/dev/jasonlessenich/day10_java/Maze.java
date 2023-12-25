package dev.jasonlessenich.day10_java;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public record Maze(char[][] layout, Point start) {
	private static final Map<Character, List<Direction>> CONNECTIONS_MAP = Map.of(
		'S', Arrays.stream(Direction.values()).toList(),
		'.', List.of(),
		'|', List.of(Direction.NORTH, Direction.SOUTH),
		'-', List.of(Direction.EAST, Direction.WEST),
		'F', List.of(Direction.EAST, Direction.SOUTH),
		'L', List.of(Direction.NORTH, Direction.EAST),
		'7', List.of(Direction.SOUTH, Direction.WEST),
		'J', List.of(Direction.NORTH, Direction.WEST)
	);

	public static Maze from(String content) {
		final char[][] arr = content.lines()
				.map(String::toCharArray)
				.toArray(char[][]::new);
		Point startPoint = null;
		int x, y;
		for (y = 0; y < arr.length; y++) {
			final char[] line = arr[y];
			for (x = 0; x < line.length; x++) {
				final Point p = new Point(x, y);
				char c = line[x];
				if (c == 'S') startPoint = p;
			}
		}
		if (startPoint == null) {
			throw new IllegalStateException("Could not find start point!");
		}
		return new Maze(arr, startPoint);
	}

	public int getMaxDistanceFromStart() {
		int maxDistance = 0;
		final Set<Point> explored = new HashSet<>();
		Set<Pipe> surrounding = new HashSet<>(getSurroundingPipes(getPipe(start), explored));
		while (!surrounding.isEmpty()) {
			final Set<Pipe> pipes = new HashSet<>(surrounding);
			surrounding.clear();
			for (Pipe pipe : pipes) {
				surrounding.addAll(getSurroundingPipes(pipe, explored));
			}
			maxDistance++;
		}
		return maxDistance;
	}

	private Set<Pipe> getSurroundingPipes(Pipe pipe, Set<Point> explored) {
		explored.add(pipe.pos);
		final Set<Pipe> surrounding = new HashSet<>();
		for (Direction direction : Direction.values()) {
			final Point p = pipe.pos.move(direction);
			if (explored.contains(p)) continue;
			final Pipe surroundingPipe = getPipe(p);
			if (canConnect(pipe, direction, surroundingPipe)) {
				surrounding.add(surroundingPipe);
			}
		}
		return surrounding;
	}

	private Pipe getPipe(Point p) {
		if (layout.length - 1 < p.y() || layout[0].length - 1 < p.x() || p.x() < 0 || p.y() < 0)
			return null;
		return new Pipe(layout[p.y()][p.x()], p);
	}

	private boolean canConnect(Pipe from, Direction direction, Pipe to) {
		if (from == null || direction == null || to == null)
			return false;
		List<Direction> fromOut = CONNECTIONS_MAP.get(from.pipe);
		List<Direction> toOut = CONNECTIONS_MAP.get(to.pipe);
		if (fromOut == null || toOut == null)
			throw new IllegalArgumentException("Illegal character: " + from + ", " + to);
		return fromOut.contains(direction) && toOut.contains(direction.opposite());
	}

	public record Pipe(Character pipe, Point pos) {}
}
