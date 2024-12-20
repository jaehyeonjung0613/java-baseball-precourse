package baseball.service;

import static baseball.service.InputHelperConstants.*;

import java.util.ArrayList;
import java.util.List;

import baseball.ui.input.Input;
import baseball.util.Parsers;

public class InputHelper {
	private final Input input;

	public InputHelper(Input input) {
		this.input = input;
	}

	public Integer getNumber() throws IllegalArgumentException {
		String strNumber = input.readline();
		validateNumericString(strNumber);
		return Parsers.parseNumber(strNumber);
	}

	public List<Integer> getNumberList(String separator) throws IllegalArgumentException {
		List<Integer> numberList = new ArrayList<>();
		String strNumberList = input.readline();

		for (String strNumber : strNumberList.split(separator)) {
			validateNumericString(strNumber);
			numberList.add(Parsers.parseNumber(strNumber));
		}
		return numberList;
	}

	private void validateNumericString(String strNumber) throws IllegalArgumentException {
		if (!strNumber.chars().allMatch(Character::isDigit)) {
			throw new IllegalArgumentException(NOT_NUMERIC_STRING_MESSAGE);
		}
	}
}
