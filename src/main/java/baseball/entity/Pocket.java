package baseball.entity;

import static baseball.entity.PocketConstants.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import baseball.Config;
import baseball.util.Parsers;

public class Pocket {
	private int[] positions;
	private Ball[] balls;

	public Pocket(int number) throws IllegalArgumentException {
		initialize(Parsers.parseNumberList(number));
	}

	public Pocket(List<Integer> numberList) throws IllegalArgumentException {
		initialize(numberList);
	}

	public Pocket(int... numbers) throws IllegalArgumentException {
		initialize(numbers);
	}

	private void initialize(List<Integer> numberList) throws IllegalArgumentException {
		if (numberList == null) {
			numberList = new ArrayList<>();
		}
		initialize(convertNumberArray(numberList));
	}

	private int[] convertNumberArray(List<Integer> numberList) throws IllegalArgumentException {
		return numberList.stream().mapToInt((number) -> {
			validateNumber(number);
			return number;
		}).toArray();
	}

	private void validateNumber(Integer number) {
		if (number == null) {
			throw new IllegalArgumentException(EMPTY_NUMBER_MESSAGE);
		}
	}

	private void initialize(int... numbers) throws IllegalArgumentException {
		validateNumbers(numbers);
		positions = new int[BallConstants.MAX_NUMBER + 1];
		balls = new Ball[Config.NUMBER_LENGTH];

		setup(numbers);
	}

	private void validateNumbers(int... numbers) {
		if (numbers.length != Config.NUMBER_LENGTH) {
			throw new IllegalArgumentException(String.format(NUMBER_LENGTH_OVER_MESSAGE_FORMAT, Config.NUMBER_LENGTH));
		}
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
