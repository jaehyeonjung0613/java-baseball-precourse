package baseball.entity;

import static baseball.entity.PocketConstants.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import baseball.Config;

public class PocketTest {
	@Test
	void 숫자_순차정보_저장_후_반환1() {
		int numbers = 123;
		int[] result = {1, 2, 3};
		Pocket pocket = new Pocket(numbers);
		assertThat(pocket.getNumbers()).containsExactly(result);
	}

	@Test
	void 숫자_순차정보_저장_후_반환2() {
		List<Integer> numbers = Arrays.asList(1, 2, 3);
		int[] result = {1, 2, 3};
		Pocket pocket = new Pocket(numbers);
		assertThat(pocket.getNumbers()).containsExactly(result);
	}

	@Test
	void 숫자_순차정보_저장_후_반환3() {
		int[] numbers = {1, 2, 3};
		int[] result = {1, 2, 3};
		Pocket pocket = new Pocket(numbers);
		assertThat(pocket.getNumbers()).containsExactly(result);
	}

	@Test
	void 숫자_순차정보_유효성_체크1() {
		List<Integer> numbers = IntStream.range(1, Config.NUMBER_LENGTH).boxed().collect(Collectors.toList());
		numbers.add(null);
		assertThatThrownBy(() -> new Pocket(numbers)).isInstanceOf(IllegalArgumentException.class)
			.hasMessage(EMPTY_NUMBER_MESSAGE);
	}

	@Test
	void 숫자_순차정보_유효성_체크2() {
		List<Integer> numbers = IntStream.rangeClosed(1, Config.NUMBER_LENGTH + 1).boxed().collect(Collectors.toList());
		String message = String.format(NUMBER_LENGTH_OVER_MESSAGE_FORMAT, Config.NUMBER_LENGTH);
		assertThatThrownBy(() -> new Pocket(numbers)).isInstanceOf(IllegalArgumentException.class).hasMessage(message);
	}

	@Test
	void 숫자_순차정보_비교_및_결과_반환1() {
		Pocket pocketA = new Pocket(123), pocketB = new Pocket(123);
		int strike = 3, ball = 0;
		Score score = pocketA.compare(pocketB);
		assertThat(score.getStrike()).isEqualTo(strike);
		assertThat(score.getBall()).isEqualTo(ball);
	}

	@Test
	void 숫자_순차정보_비교_및_결과_반환2() {
		Pocket pocketA = new Pocket(123), pocketB = new Pocket(132);
		int strike = 1, ball = 2;
		Score score = pocketA.compare(pocketB);
		assertThat(score.getStrike()).isEqualTo(strike);
		assertThat(score.getBall()).isEqualTo(ball);
	}

	@Test
	void 숫자_순차정보_비교_및_결과_반환3() {
		Pocket pocketA = new Pocket(123), pocketB = new Pocket(312);
		int strike = 0, ball = 3;
		Score score = pocketA.compare(pocketB);
		assertThat(score.getStrike()).isEqualTo(strike);
		assertThat(score.getBall()).isEqualTo(ball);
	}

	@Test
	void 숫자_순차정보_비교_및_결과_반환4() {
		Pocket pocketA = new Pocket(123), pocketB = new Pocket(456);
		int strike = 0, ball = 0;
		Score score = pocketA.compare(pocketB);
		assertThat(score.getStrike()).isEqualTo(strike);
		assertThat(score.getBall()).isEqualTo(ball);
	}
}
