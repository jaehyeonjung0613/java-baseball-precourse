package baseball.entity;

import static baseball.entity.BallConstants.*;

public class Ball {
	private final int number;

	public Ball(int number) throws IllegalArgumentException {
		validateNumber(number);
		this.number = number;
	}

	private void validateNumber(int number) throws IllegalArgumentException {
		if (MIN_NUMBER > number || MAX_NUMBER < number) {
			throw new IllegalArgumentException(String.format(NUMBER_RANGE_OVER_MESSAGE_FORMAT, MIN_NUMBER, MAX_NUMBER));
		}
	}

	public int getNumber() {
		return number;
	}
}
