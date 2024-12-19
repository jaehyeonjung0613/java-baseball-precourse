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
