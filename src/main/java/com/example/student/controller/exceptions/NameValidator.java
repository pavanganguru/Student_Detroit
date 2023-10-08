package com.example.student.controller.exceptions;

import java.util.stream.Stream;

public class NameValidator {
	
	public static boolean isValidName(String s) {
		Stream<Character> charStream = s.chars().mapToObj(ch->(char)ch);	
		return charStream.anyMatch(ch->Character.isDigit(ch));
	}

}
