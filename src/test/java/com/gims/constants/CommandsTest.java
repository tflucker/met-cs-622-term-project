package com.gims.constants;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CommandsTest {

	@Test
	public void isValidCommandTest_valid() {
		String testCommand = "SEARCH";
		boolean result = Commands.isValidCommand(testCommand);
		assertTrue(result);
	}

	@Test
	void isValidCommandTest_invalid() {
		String testCommand = "GARBAGE_COMMAND";
		boolean result = Commands.isValidCommand(testCommand);
		assertFalse(result);
	}
}
