package baseball.ui.output;

public interface Output {
	void print(String message);

	void printByFormat(String format, Object... args);
}
