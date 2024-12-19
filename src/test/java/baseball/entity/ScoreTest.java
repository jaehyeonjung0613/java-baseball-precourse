package baseball.entity;

import static baseball.entity.ScoreConstants.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import baseball.Config;

public class ScoreTest {
	@Test
	void 비교_결과_저장_후_반환() {
		int strike = 0, ball = 0;
		Score score = new Score(strike, ball);
		assertThat(score.getStrike()).isEqualTo(strike);
		assertThat(score.getBall()).isEqualTo(ball);
	}

	@Test
	void 비교_결과_유효성_체크1() {
		int strike = Config.NUMBER_LENGTH, ball = 1;
		assertThatThrownBy(() -> new Score(strike, ball)).isInstanceOf(IllegalArgumentException.class)
			.hasMessage(SCORE_OVER_MESSAGE);
	}

	@Test
	void 비교_결과_유효성_체크2() {
		int strike = -1, ball = 0;
		assertThatThrownBy(() -> new Score(strike, ball)).isInstanceOf(IllegalArgumentException.class)
			.hasMessage(SCORE_NOT_NEGATIVE_MESSAGE);
	}

	@Test
	void 비교_결과_유효성_체크3() {
		int strike = 0, ball = -1;
		assertThatThrownBy(() -> new Score(strike, ball)).isInstanceOf(IllegalArgumentException.class)
			.hasMessage(SCORE_NOT_NEGATIVE_MESSAGE);
	}

	@Test
	void 비교_결과_문구_반환1() {
		int strike = 3, ball = 0;
		String message = String.format("%d%s", strike, STRIKE_MESSAGE);

		Score score = new Score(strike, ball);
		assertThat(score.getResultMessage()).isEqualTo(message);
	}

	@Test
	void 비교_결과_문구_반환2() {
		int strike = 1, ball = 1;
		String message = String.format("%d%s%s%d%s", ball, BALL_MESSAGE, RESULT_MESSAGE_SEPARATOR, strike,
			STRIKE_MESSAGE);

		Score score = new Score(strike, ball);
		assertThat(score.getResultMessage()).isEqualTo(message);
	}

	@Test
	void 비교_결과_문구_반환3() {
		int strike = 0, ball = 3;
		String message = String.format("%d%s", ball, BALL_MESSAGE);

		Score score = new Score(strike, ball);
		assertThat(score.getResultMessage()).isEqualTo(message);
	}

	@Test
	void 비교_결과_문구_반환4() {
		int strike = 0, ball = 0;

		Score score = new Score(strike, ball);
		assertThat(score.getResultMessage()).isEqualTo(ZERO_SCORE_MESSAGE);
	}
}
