package baseball.service;

import java.util.ArrayList;
import java.util.List;

import baseball.ui.input.Input;
import baseball.util.Parsers;

public class InputHelper {
	private final Input input;

	public InputHelper(Input input) {
		this.input = input;
	}

	public Integer getNumber() {
		String strNumber = input.readline();

		return Parsers.parseNumber(strNumber);
	}

	public List<Integer> getNumberList(String separator) {
		List<Integer> numberList = new ArrayList<>();
		String strNumberList = input.readline();

		for (String strNumber : strNumberList.split(separator)) {
			numberList.add(Parsers.parseNumber(strNumber));
		}
		return numberList;
	}
}
