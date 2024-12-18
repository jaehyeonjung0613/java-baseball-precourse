package baseball.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ScoreTest {
	@Test
	void 비교_결과_저장_후_반환() {
		int strike = 0, ball = 0;
		Score score = new Score(strike, ball);
		assertThat(score.getStrike()).isEqualTo(strike);
		assertThat(score.getBall()).isEqualTo(ball);
	}
}
