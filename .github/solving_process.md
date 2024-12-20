# 🧐 미션 - 숫자 야구 게임

[우아한테크코스](https://github.com/woowacourse) precourse 문제
중 [미션 - 숫자 야구 게임](https://github.com/woowacourse/java-baseball-precourse) 풀이 기록하기.

개발은 TDD(테스트 주도 개발) 방식으로 진행될 것이며, 입출력 및 프로그래밍 요구사항을 부합하도록 풀어 볼 예정.

## 0. 설계

### entity

|  클래스   | 기능                                              |
|:------:|:------------------------------------------------|
|  Ball  | - 숫자 저장<br/> - 유효성 체크                           |
| Pocket | - ball 순차정보 저장<br/> - pocket 비교<br/> - 유효성 체크   |
| Score  | - pocket 비교 결과 저장<br/> - 결과 문구 반환<br/> - 유효성 체크 |

### service

|     클래스     | 기능                        |
|:-----------:|:--------------------------|
| GameManager | - 게임 관리                   |
|    Game     | - 게임 진행 관리                |
| InputHelper | - 게임 명령어 처리<br/> - 유효성 체크 |

### ui

|  클래스   | 기능          |
|:------:|:------------|
| Input  | - 사용자 입력 처리 |
| Output | - 출력 처리     |

### util

|  클래스   | 기능      |
|:------:|:--------|
| Parser | - 형식 변환 |

### config

|  클래스   | 기능         |
|:------:|:-----------|
| Config | - 게임 설정 관리 |

## 1. 숫자 저장

```java
// BallTest.java

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
```

테스트 케이스 생성.

```java
// Ball.java

package baseball.entity;

public class Ball {
	private final int number;

	public Ball(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}
}
```

테스트 케이스에 맞춰 Ball 생성.

## 2. 숫자 유효성 체크

```java
// Ball.Constants.java

package baseball.entity;

public final class BallConstants {
	private BallConstants() {
	}

	public static final int MIN_NUMBER = 1;
	public static final int MAX_NUMBER = 9;

	public static final String NUMBER_RANGE_OVER_MESSAGE_FORMAT = "%d ~ %d 범위 내 숫자만 입력 가능합니다.";
}
```

유효성 판단에 필요한 상수 클래스 생성.

```java
// BallTest.java

package baseball.entity;

import static baseball.entity.BallConstants.*;

import org.junit.jupiter.api.Test;

public class BallTest {
	@Test
	void 숫자_유효성_체크() {
		int number = 0;
		String message = String.format(NUMBER_RANGE_OVER_MESSAGE_FORMAT, MIN_NUMBER, MAX_NUMBER);
		assertThatThrownBy(() -> new Ball(number)).isInstanceOf(IllegalArgumentException.class).hasMessage(message);
	}
}
```

테스트 케이스 생성.

```java
// Ball.java

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
}
```

숫자 저장 시 유효성 체크하도록 함.

## 3. 숫자 순차정보 저장

```java
// PocketTest.java

package baseball.entity;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

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
}
```

테스트 케이스 생성.

```java
// Config.java

package baseball;

public final class Config {
	private Config() {
	}

	public static final int NUMBER_LENGTH = 3;
}
```

숫자 자릿수 정의.

```java
// Parsers.java

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
```

숫자를 순차목록으로 파싱하는 함수 생성.

```java
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
```

테스트 케이스에 맞춰 Pocket 생성.

## 4. 숫자 순차정보 유효성 체크

```java
// PocketConstants.java

package baseball.entity;

public final class PocketConstants {
	private PocketConstants() {
	}

	public static final String EMPTY_NUMBER_MESSAGE = "숫자에 NULL 입력이 되었습니다.";
	public static final String NUMBER_LENGTH_OVER_MESSAGE_FORMAT = "%d 자리 숫자가 입력이 되어야합니다.";
}
```

유효성 판단에 필요한 상수 클래스 생성.

```java
// PocketTest.java

package baseball.entity;

import static baseball.entity.PocketConstants.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import baseball.Config;

public class PocketTest {
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
}
```

테스트 케이스 생성.

```java
// Pocket.java

package baseball.entity;

import static baseball.entity.PocketConstants.*;

import java.util.ArrayList;
import java.util.List;

import baseball.Config;

public class Pocket {
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
}
```

숫자 순차정보 저장 시 유효성 체크하도록 함.

## 5. 비교 결과 저장

```java
// ScoreTest.java

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
```

테스트 케이스 생성.

```java
// Score.java

package baseball.entity;

public class Score {
	private final int strike;
	private final int ball;

	public Score(int strike, int ball) {
		this.strike = strike;
		this.ball = ball;
	}

	public int getStrike() {
		return strike;
	}

	public int getBall() {
		return ball;
	}
}
```

테스트 케이스에 맞춰 Score 생성.

## 6. 비교 결과 유효성 체크

```java
// ScoreConstants.java

package baseball.entity;

public final class ScoreConstants {
	private ScoreConstants() {
	}

	public static final String SCORE_OVER_MESSAGE = "부여할 수 있는 점수 한계치가 벗어났습니다.";
	public static final String SCORE_NOT_NEGATIVE_MESSAGE = "점수는 음수가 될 수 없습니다.";
}
```

유효성 판단에 필요한 상수 클래스 생성.

```java
// ScoreTest.java

package baseball.entity;

import static baseball.entity.ScoreConstants.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import baseball.Config;

public class ScoreTest {
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
}
```

테스트 케이스 생성.

```java
// Score.java

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
}
```

비교 결과 저장 시 유효성 체크하도록 함.

## 7. Pocket 비교 및 결과 반환

```java
// PocketTest.java

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
```

테스트 케이스 생성.

```java
// Pocket.java

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

	public Score compare(Pocket other) throws IllegalArgumentException {
		int[] numbers = other.getNumbers();
		int strike = 0, ball = 0, length = numbers.length;

		int number, position;
		for (int i = 0; i < length; i++) {
			number = numbers[i];
			position = length - i;
			if (this.positions[number] == position) {
				strike++;
			} else if (this.positions[number] > 0) {
				ball++;
			}
		}
		return new Score(strike, ball);
	}
}
```

숫자 순차정보 비교 기능 생성.

## 8. 비교 결과 문구 반환

```java
// ScoreConstants.java

package baseball.entity;

public final class ScoreConstants {
	private ScoreConstants(){}

	public static final String RESULT_MESSAGE_SEPARATOR = " ";
	public static final String STRIKE_MESSAGE = "스트라이크";
	public static final String BALL_MESSAGE = "볼";
	public static final String ZERO_SCORE_MESSAGE = "낫싱";
}
```

비교 결과 문구 관련 상수 선언.

```java
// ScoreTest.java

package baseball.entity;

import static baseball.entity.ScoreConstants.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import baseball.Config;

public class ScoreTest {
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
		String message = String.format("%d%s%s%d%s", ball, BALL_MESSAGE, RESULT_MESSAGE_SEPARATOR, strike, STRIKE_MESSAGE);

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
```

테스트 케이스 생성.

```java
// Score.java

package baseball.entity;

import static baseball.entity.ScoreConstants.*;

import java.util.StringJoiner;

import baseball.Config;

public class Score {
	private final int strike;
	private final int ball;
	
	public String getResultMessage() {
		StringJoiner message = new StringJoiner(RESULT_MESSAGE_SEPARATOR);
		if (this.ball > 0) {
			message.add(String.format("%d%s", this.ball, BALL_MESSAGE));
		}
		if (this.strike > 0) {
			message.add(String.format("%d%s", this.strike, STRIKE_MESSAGE));
		}
		if (this.ball == 0 && this.strike == 0) {
			message.setEmptyValue(ZERO_SCORE_MESSAGE);
		}
		return message.toString();
	}
}
```

비교 결과 문구 출력 기능 생성.

## 9. 입출력

```java
// Input.java

package baseball.ui.input;

public interface Input {
	String readline();
}
```

```java
// Output.java

package baseball.ui.output;

public interface Output {
	void print(String message);

	void printByFormat(String format, Object... args);
}
```

입출력 인터페이스 생성.

```java
// ConsoleInput.java

package baseball.ui.input;

import camp.nextstep.edu.missionutils.Console;

public class ConsoleInput implements Input {
	@Override
	public String readline() {
		return Console.readLine();
	}
}
```

```java
// ConsoleOutput.java

package baseball.ui.output;

public class ConsoleOutput implements Output {
	@Override
	public void print(String message) {
		System.out.println(message);
	}

	@Override
	public void printByFormat(String format, Object... args) {
		System.out.printf((format) + "%n", args);
	}
}
```

주어진 기능을 활용하여 콘솔 입출력 기능 구현.

## 10. 숫자 명령어 입력 처리

```java
// Config.java
package baseball;

public final class Config {
	private Config(){}

	public static final String COMMAND_SEPARATOR = "";
}
```

명령어 구분자 정의.

```java
// InputHelperTest.java

package baseball.service;

import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;

import baseball.Config;
import baseball.ui.input.ConsoleInput;

import org.junit.jupiter.api.Test;

public class InputHelperTest {
	@Test
	void 숫자_입력_명령어_처리1() {
		ConsoleInput consoleInput = Mockito.mock(ConsoleInput.class);
		Mockito.when(consoleInput.readline()).thenReturn("123");

		InputHelper helper = new InputHelper(consoleInput);
		assertThat(helper.getNumber()).isEqualTo(123);
	}

	@Test
	void 숫자_입력_명령어_처리2() {
		ConsoleInput consoleInput = Mockito.mock(ConsoleInput.class);
		Mockito.when(consoleInput.readline()).thenReturn("123");

		InputHelper helper = new InputHelper(consoleInput);
		assertThat(helper.getNumberList(Config.COMMAND_SEPARATOR)).containsExactly(1, 2, 3);
	}
}
```

테스트 케이스 생성.

```java
// Parser.java

package baseball.util;

public final class Parsers {
	private Parsers() {
	}
	
	public static Integer parseNumber(String strNumber) {
		return Integer.valueOf(strNumber);
	}
}
```

문자열 숫자형태 파싱 기능 생성.

```java
// InputHelper.java

package baseball.service;

import java.util.ArrayList;
import java.util.List;

import baseball.ui.input.Input;
import baseball.util.Parsers;

public class InputHelper {
	private final Input input;

	public InputHelper(Input input) {
		this.input = input;
	}

	public Integer getNumber() {
		String strNumber = input.readline();

		return Parsers.parseNumber(strNumber);
	}

	public List<Integer> getNumberList(String separator) {
		List<Integer> numberList = new ArrayList<>();
		String strNumberList = input.readline();

		for (String strNumber : strNumberList.split(separator)) {
			numberList.add(Parsers.parseNumber(strNumber));
		}
		return numberList;
	}
}
```

숫자 명령어 입력 처리 기능 생성.

## 11. 숫자 명령어 입력 유효성 체크

```java
// InputHelperConstants.java

package baseball.service;

public final class InputHelperConstants {
	private InputHelperConstants(){}

	public static final String NOT_NUMERIC_STRING_MESSAGE = "숫자만 입력 가능합니다.";
}
```

유효성 판단 관련 상수 정의.

```java
// InputHelperTest.java

package baseball.service;

import static baseball.service.InputHelperConstants.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import baseball.Config;
import baseball.ui.input.ConsoleInput;

public class InputHelperTest {
	@Test
	void 숫자_명령어_입력_유효성_체크1() {
		ConsoleInput consoleInput = Mockito.mock(ConsoleInput.class);
		Mockito.when(consoleInput.readline()).thenReturn("i23");

		InputHelper helper = new InputHelper(consoleInput);
		assertThatThrownBy(helper::getNumber).isInstanceOf(IllegalArgumentException.class)
			.hasMessage(NOT_NUMERIC_STRING_MESSAGE);
	}

	@Test
	void 숫자_명령어_입력_유효성_체크2() {
		ConsoleInput consoleInput = Mockito.mock(ConsoleInput.class);
		Mockito.when(consoleInput.readline()).thenReturn("i23");

		InputHelper helper = new InputHelper(consoleInput);
		assertThatThrownBy(() -> helper.getNumberList(Config.COMMAND_SEPARATOR)).isInstanceOf(
			IllegalArgumentException.class).hasMessage(NOT_NUMERIC_STRING_MESSAGE);
	}
}
```

테스트 케이스 생성.

```java
// InputHelper.java

package baseball.service;

import static baseball.service.InputHelperConstants.*;

import java.util.ArrayList;
import java.util.List;

import baseball.ui.input.Input;
import baseball.util.Parsers;

public class InputHelper {
	private final Input input;

	public Integer getNumber() throws IllegalArgumentException {
		String strNumber = input.readline();
		validateNumericString(strNumber);
		return Parsers.parseNumber(strNumber);
	}

	public List<Integer> getNumberList(String separator) throws IllegalArgumentException {
		List<Integer> numberList = new ArrayList<>();
		String strNumberList = input.readline();

		for (String strNumber : strNumberList.split(separator)) {
			validateNumericString(strNumber);
			numberList.add(Parsers.parseNumber(strNumber));
		}
		return numberList;
	}

	private void validateNumericString(String strNumber) throws IllegalArgumentException {
		if (!strNumber.chars().allMatch(Character::isDigit)) {
			throw new IllegalArgumentException(NOT_NUMERIC_STRING_MESSAGE);
		}
	}
}
```

숫자 명령어 입력 유효성 체크 기능 생성.

## 12. 숫자 범위 명령어 입력 처리

```java
// InputHelperTest.java

package baseball.service;

import static baseball.service.InputHelperConstants.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import baseball.Config;
import baseball.ui.input.ConsoleInput;

public class InputHelperTest {
	@Test
	void 숫자_범위_명령어_입력_처리() {
		ConsoleInput consoleInput = Mockito.mock(ConsoleInput.class);
		Mockito.when(consoleInput.readline()).thenReturn("1");

		InputHelper helper = new InputHelper(consoleInput);
		assertThat(helper.getNumbersInRange(1, 2)).isEqualTo(1);
	}
}
```

테스트 케이스 생성.

```java
// InputHelper.java

package baseball.service;

import static baseball.service.InputHelperConstants.*;

import java.util.ArrayList;
import java.util.List;

import baseball.ui.input.Input;
import baseball.util.Parsers;

public class InputHelper {
	private final Input input;

	public Integer getNumbersInRange(int startInclusive, int endInclusive) {
		return this.getNumber();
	}
}
```

숫자 범위 명령어 입력 기능 생성.

## 13. 숫자 범위 명령어 입력 유효성 체크

```java
// InputHelperConstants.java

package baseball.service;

public final class InputHelperConstants {
	private InputHelperConstants(){}

	public static final String NUMBER_RANGE_OVER_MESSAGE = "주어진 범위에서 벗어난 숫자가 입력되었습니다.";
}

```

유효성 판단 관련 상수 정의.

```java
// InputHelperTest.java

package baseball.service;

import static baseball.service.InputHelperConstants.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import baseball.Config;
import baseball.ui.input.ConsoleInput;

public class InputHelperTest {
	@Test
	void 숫자_범위_명령어_입력_유효성_체크() {
		ConsoleInput consoleInput = Mockito.mock(ConsoleInput.class);
		Mockito.when(consoleInput.readline()).thenReturn("3");

		InputHelper helper = new InputHelper(consoleInput);
		assertThatThrownBy(() -> helper.getNumbersInRange(1, 2)).isInstanceOf(
			IllegalArgumentException.class).hasMessage(NUMBER_RANGE_OVER_MESSAGE);
	}
}
```

테스트 케이스 생성.

```java
// InputHelper.java

package baseball.service;

import static baseball.service.InputHelperConstants.*;

import java.util.ArrayList;
import java.util.List;

import baseball.ui.input.Input;
import baseball.util.Parsers;

public class InputHelper {
	public Integer getNumbersInRange(int startInclusive, int endInclusive) throws IllegalArgumentException {
		Integer number = this.getNumber();
		if (number < startInclusive || number > endInclusive) {
			throw new IllegalArgumentException(NUMBER_RANGE_OVER_MESSAGE);
		}
		return number;
	}
}
```

숫자 범위 입력 받을시 범위 유효성 체크.

## 14. 만점 여부 체크

```java
// ScoreTest.java

package baseball.entity;

import static baseball.entity.ScoreConstants.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import baseball.Config;

public class ScoreTest {
	@Test
	void 만점_여부_체크1() {
		int strike = Config.NUMBER_LENGTH, ball = 0;

		Score score = new Score(strike, ball);
		assertThat(score.isPerfect()).isEqualTo(true);
	}

	@Test
	void 만점_여부_체크2() {
		int strike = 0, ball = 0;

		Score score = new Score(strike, ball);
		assertThat(score.isPerfect()).isEqualTo(false);
	}
}
```

테스트 케이스 생성.

```java
// Score.java

package baseball.entity;

import static baseball.entity.ScoreConstants.*;

import java.util.StringJoiner;

import baseball.Config;

public class Score {
	public boolean isPerfect() {
		return this.strike == Config.NUMBER_LENGTH;
	}
}
```

만점 여부 체크 기능 생성.