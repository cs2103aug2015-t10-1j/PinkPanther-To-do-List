/* @@author A0126473E */
package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import parser.SingleTimeParser;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class SingleTimeParserTest {

	SingleTimeParser stp = new SingleTimeParser();
	
	@Test
	public void testParseOneWordTimes() {
		String[] testInput = {"tonight", "now", "morning",
				"afternoon", "night"};
		LocalTime[] expectedOutput = {LocalTime.of(19, 0), 
				LocalTime.now().truncatedTo(ChronoUnit.MINUTES),
				LocalTime.of(9, 0), LocalTime.of(12, 0), LocalTime.of(19, 0)};
		LocalTime[] testOutput = new LocalTime[testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = stp.parse(testInput[i]);
			assertEquals(expectedOutput[i], testOutput[i]);
		}	
	}
	
	@Test
	public void testParseOneWordGibberish() {
		String[] testInput = {"toonight", "never", "Wednesday",
				"99999", "meow"};
		LocalTime[] testOutput = new LocalTime[testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = stp.parse(testInput[i]);
			assertEquals(null, testOutput[i]);
		}	
	}
	
	@Test
	public void testParseNormalTimes() {
		String[] testInput = {"12.20pm", "02.04Am", "7:19pm",
				"4:25PM", "3am", "1105pM", "105am", "2.45", "18.30",
				"23:11", "00:00", "2400", "0156"};
		LocalTime[] expectedOutput = {LocalTime.of(12, 20), 
				LocalTime.of(2, 4), LocalTime.of(19, 19), 
				LocalTime.of(16, 25), LocalTime.of(3, 0),
				LocalTime.of(23, 5), LocalTime.of(1, 5),
				LocalTime.of(2, 45), LocalTime.of(18, 30),
				LocalTime.of(23, 11), LocalTime.of(0, 0),
				LocalTime.of(0, 0), LocalTime.of(1, 56)};
		LocalTime[] testOutput = new LocalTime[testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = stp.parse(testInput[i]);
			assertEquals(expectedOutput[i], testOutput[i]);
		}	
	}
	
	@Test
	public void testParseTimesBoundaryNegative() {
		String[] testInput = {"12.2pm", "12.2", "2401", 
				"12318263", "-123", "-0000", "1 2", "a.p", "2p.m."};
		LocalTime[] testOutput = new LocalTime[testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = stp.parse(testInput[i]);
			assertEquals(null, testOutput[i]);
		}	
	}
}
