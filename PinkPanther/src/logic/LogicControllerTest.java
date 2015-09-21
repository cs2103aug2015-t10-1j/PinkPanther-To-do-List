package logic;

import static org.junit.Assert.*;

import org.junit.Test;

public class LogicControllerTest {

	@Test
	public void test() {
		String[] expectedOutput = {"add", "feed cat at 5pm"};
		String[] actualOutput = LogicController.getInput();
		assertEquals(expectedOutput[0], actualOutput[0]);
		assertEquals(expectedOutput[1], actualOutput[1]);
	}

}
