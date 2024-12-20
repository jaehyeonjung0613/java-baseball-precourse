package baseball.service;

import static baseball.service.GameConstants.*;

import java.util.List;

import baseball.Config;
import baseball.entity.Pocket;
import baseball.ui.output.Output;

public class Game {
	private final InputHelper inputHelper;
	private final Output output;
	private final Pocket answer;

	public Game(InputHelper inputHelper, Output output, Pocket answer) {
		this.inputHelper = inputHelper;
		this.output = output;
		this.answer = answer;
	}

	public Pocket requestNumberList() throws IllegalArgumentException {
		output.print(REQUEST_NUMBER_LIST_QUERY);
		List<Integer> numberList = inputHelper.getNumberList(Config.COMMAND_SEPARATOR);
		return new Pocket(numberList);
	}

	public void finish() {
		this.output.println(String.format(GAME_FINISH_MESSAGE_FORMAT, Config.NUMBER_LENGTH));
	}
}
