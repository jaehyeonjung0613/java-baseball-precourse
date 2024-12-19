package baseball;

import baseball.service.manager.GameManager;
import baseball.service.manager.GameManagerBuilder;

public class Application {
	public static void main(String[] args) {
		GameManager gameManager = new GameManagerBuilder()
			.build();
		try {
			gameManager.run();
		} finally {
			gameManager.finish();
		}
	}
}
