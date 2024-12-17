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
	private Config(){}

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
	private PocketConstants(){}

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
		assertThatThrownBy(() -> new Pocket(numbers)).isInstanceOf(IllegalArgumentException.class).hasMessage(EMPTY_NUMBER_MESSAGE);
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




