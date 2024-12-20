package baseball.service;

import static baseball.service.GameConstants.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import baseball.Config;
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

	@Test
	void 게임_종료_메시지_출력() {
		ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
		ConsoleOutput output = Mockito.mock(ConsoleOutput.class);

		InputHelper inputHelper = new InputHelper(new ConsoleInput());
		Pocket answer = new Pocket(123);
		Game game = new Game(inputHelper, output, answer);
		String message = String.format(GAME_FINISH_MESSAGE_FORMAT, Config.NUMBER_LENGTH);

		game.finish();
		Mockito.verify(output).println(captor.capture());
		assertThat(captor.getValue()).isEqualTo(message);
	}
}
