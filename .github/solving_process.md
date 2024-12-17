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









