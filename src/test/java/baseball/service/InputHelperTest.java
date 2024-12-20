package baseball.service;

import static baseball.service.InputHelperConstants.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import baseball.Config;
import baseball.ui.input.ConsoleInput;

public class InputHelperTest {
	@Test
	void 숫자_명령어_입력_처리1() {
		ConsoleInput consoleInput = Mockito.mock(ConsoleInput.class);
		Mockito.when(consoleInput.readline()).thenReturn("123");

		InputHelper helper = new InputHelper(consoleInput);
		assertThat(helper.getNumber()).isEqualTo(123);
	}

	@Test
	void 숫자_명령어_입력_처리2() {
		ConsoleInput consoleInput = Mockito.mock(ConsoleInput.class);
		Mockito.when(consoleInput.readline()).thenReturn("123");

		InputHelper helper = new InputHelper(consoleInput);
		assertThat(helper.getNumberList(Config.COMMAND_SEPARATOR)).containsExactly(1, 2, 3);
	}

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

	@Test
	void 숫자_범위_명령어_입력_처리() {
		ConsoleInput consoleInput = Mockito.mock(ConsoleInput.class);
		Mockito.when(consoleInput.readline()).thenReturn("1");

		InputHelper helper = new InputHelper(consoleInput);
		assertThat(helper.getNumbersInRange(1, 2)).isEqualTo(1);
	}
}
