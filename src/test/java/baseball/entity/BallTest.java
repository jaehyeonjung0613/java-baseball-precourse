package baseball.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BallTest {
	@Test
	void 숫자_저장_후_반환() {
		int number = 1;
		Ball ball = new Ball(1);
		assertThat(number).isEqualTo(ball.getNumber());
	}
}
