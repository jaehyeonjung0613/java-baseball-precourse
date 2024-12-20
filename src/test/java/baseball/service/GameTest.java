package baseball.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import baseball.entity.Pocket;
import baseball.ui.input.ConsoleInput;
import baseball.ui.output.ConsoleOutput;

public class GameTest {
	@Test
	void 숫자_순차정보_입력_처리() {
		ConsoleInput consoleInput = Mockito.mock(ConsoleInput.class);
		Mockito.when(consoleInput.readline()).thenReturn("123");

		InputHelper inputHelper = new InputHelper(consoleInput);
		ConsoleOutput output = new ConsoleOutput();
		Pocket answer = new Pocket(123);
		Game game = new Game(inputHelper, output, answer);

		assertThat(game.requestNumberList().getNumbers()).containsExactly(1, 2, 3);
	}
}
