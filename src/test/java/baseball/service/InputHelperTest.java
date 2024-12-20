package baseball.service;

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
}
