package baseball.util;

import java.util.ArrayList;
import java.util.List;

public final class Parsers {
	private Parsers() {
	}

	public static List<Integer> parseNumberList(int number) {
		List<Integer> numbers = new ArrayList<>();
		do {
			numbers.add(0, number % 10);
			number /= 10;
		} while (number > 0);
		return numbers;
	}
}
