package baseball.entity;

import static baseball.entity.ScoreConstants.*;

import baseball.Config;

public class Score {
	private final int strike;
	private final int ball;

	public Score(int strike, int ball) throws IllegalArgumentException {
		validateScore(strike, ball);
		this.strike = strike;
		this.ball = ball;
	}

	private void validateScore(int strike, int ball) throws IllegalArgumentException {
		if (strike + ball > Config.NUMBER_LENGTH) {
			throw new IllegalArgumentException(SCORE_OVER_MESSAGE);
		}
		validateStrike(strike);
		validateBall(ball);
	}

	private void validateStrike(int strike) throws IllegalArgumentException {
		if (strike < 0) {
			throw new IllegalArgumentException(SCORE_NOT_NEGATIVE_MESSAGE);
		}
	}

	private void validateBall(int ball) throws IllegalArgumentException {
		if (ball < 0) {
			throw new IllegalArgumentException(SCORE_NOT_NEGATIVE_MESSAGE);
		}
	}

	public int getStrike() {
		return strike;
	}

	public int getBall() {
		return ball;
	}
}
