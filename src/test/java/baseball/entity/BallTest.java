package baseball.entity;

import static baseball.entity.BallConstants.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BallTest {
	@Test
	void 숫자_저장_후_반환() {
		int number = 1;
		Ball ball = new Ball(1);
		assertThat(number).isEqualTo(ball.getNumber());
	}

	@Test
	void 숫자_유효성_체크() {
		int number = 0;
		String message = String.format(NUMBER_RANGE_OVER_MESSAGE_FORMAT, MIN_NUMBER, MAX_NUMBER);
		assertThatThrownBy(() -> new Ball(number)).isInstanceOf(IllegalArgumentException.class).hasMessage(message);
	}
}
