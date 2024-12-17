package baseball.entity;

import java.util.Arrays;
import java.util.List;

import baseball.Config;
import baseball.util.Parsers;

public class Pocket {
	private int[] positions;
	private Ball[] balls;

	public Pocket(int number) {
		initialize(Parsers.parseNumberList(number));
	}

	public Pocket(List<Integer> numberList) {
		initialize(numberList);
	}

	public Pocket(int... numbers) {
		initialize(numbers);
	}

	private void initialize(List<Integer> numberList) {
		initialize(convertNumberArray(numberList));
	}

	private int[] convertNumberArray(List<Integer> numberList) {
		return numberList.stream().mapToInt(Integer::intValue).toArray();
	}

	private void initialize(int... numbers) {
		positions = new int[BallConstants.MAX_NUMBER + 1];
		balls = new Ball[Config.NUMBER_LENGTH];

		setup(numbers);
	}

	private void setup(int... numbers) {
		int number, length = numbers.length;
		for (int i = 0; i < length; i++) {
			number = numbers[i];
			positions[number] = length - i;
			balls[i] = new Ball(number);
		}
	}

	public int[] getNumbers() {
		return Arrays.stream(balls).mapToInt(Ball::getNumber).toArray();
	}
}
