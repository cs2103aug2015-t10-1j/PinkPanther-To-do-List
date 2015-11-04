/* @@author Brayton */

package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import parser.SingleTimeParser;

import java.time.LocalTime;

public class SingleTimeParserTest_Colon {

	//@Test
	public void test_Parse_ColonCase1() { // h:mma
		int hour, min;
		SingleTimeParser timeParser = new SingleTimeParser();

		for (hour = 1; hour <= 9; hour++) {
			for (min = 0; min <= 59; min++) {
				if (min < 10) {
					assertEquals(timeParser.parse(Integer.toString(hour) + ":" + "0" + Integer.toString(min) + "am"), LocalTime.of(hour, min));
				}
				else {
					assertEquals(timeParser.parse(Integer.toString(hour) + ":" + Integer.toString(min) + "am"), LocalTime.of(hour, min));
				}
			}
		}

		for (hour = 1; hour <= 9; hour++) {
			for (min = 0; min <= 59; min++) {
				if (min < 10) {
					assertEquals(timeParser.parse(Integer.toString(hour) + ":" + "0" + Integer.toString(min) + "pm"), LocalTime.of(hour + 12, min));
				}
				else {
					assertEquals(timeParser.parse(Integer.toString(hour) + ":" + Integer.toString(min) + "pm"), LocalTime.of(hour + 12, min));
				}
			}
		}
	}

	//@Test
	public void test_Parse_ColonCase2() { // hh:mma
		int hour, min;
		SingleTimeParser timeParser = new SingleTimeParser();

		for (hour = 1; hour <= 11; hour++) {
			for (min = 0; min <= 59; min++) {
				if (hour < 10) {
					if (min < 10) {
						assertEquals(timeParser.parse("0" + Integer.toString(hour) + ":" + "0" + Integer.toString(min) + "am"), LocalTime.of(hour, min));
					}
					else {
						assertEquals(timeParser.parse("0" + Integer.toString(hour) + ":" + Integer.toString(min) + "am"), LocalTime.of(hour, min));
					}
				}
				else {
					if (min < 10) {
						assertEquals(timeParser.parse(Integer.toString(hour) + ":" + "0" + Integer.toString(min) + "am"), LocalTime.of(hour, min));
					}
					else {
						assertEquals(timeParser.parse(Integer.toString(hour) + ":" + Integer.toString(min) + "am"), LocalTime.of(hour, min));
					}
				}
			}
		}

		for (hour = 1; hour <= 11; hour++) {
			for (min = 0; min <= 59; min++) {
				if (min < 10) {
					assertEquals(timeParser.parse(Integer.toString(hour) + ":" + "0" + Integer.toString(min) + "pm"), LocalTime.of(hour + 12, min));
				}
				else {
					assertEquals(timeParser.parse(Integer.toString(hour) + ":" + Integer.toString(min) + "pm"), LocalTime.of(hour + 12, min));
				}
			}
		}

		assertEquals(timeParser.parse("12:00am"), LocalTime.of(0, 0));
		assertEquals(timeParser.parse("12:00pm"), LocalTime.of(12, 0));
	}

	@Test
	public void test_Parse_ColonCase3() { // h:mm
		int hour, min;
		SingleTimeParser timeParser = new SingleTimeParser();

		for (hour = 0; hour <= 9; hour++) {
			for (min = 0; min <= 59; min++) {
				if (min < 10) {
					assertEquals(timeParser.parse(Integer.toString(hour) + ":" + "0" + Integer.toString(min)), LocalTime.of(hour, min));
				}
				else {
					assertEquals(timeParser.parse(Integer.toString(hour) + ":" + Integer.toString(min)), LocalTime.of(hour, min));
				}
			}
		}
	}

	@Test
	public void test_Parse_ColonCase4() { // hh:mm
		int hour, min;
		SingleTimeParser timeParser = new SingleTimeParser();

		for (hour = 0; hour <= 12; hour++) {
			for (min = 0; min <= 59; min++) {
				if (hour < 10) {
					if (min < 10) {
						assertEquals(timeParser.parse("0" + Integer.toString(hour) + ":" + "0" + Integer.toString(min)), LocalTime.of(hour, min));
					}
					else {
						assertEquals(timeParser.parse("0" + Integer.toString(hour) + ":" + Integer.toString(min)), LocalTime.of(hour, min));
					}
				}
				else {
					if (min < 10) {
						assertEquals(timeParser.parse(Integer.toString(hour) + ":" + "0" + Integer.toString(min)), LocalTime.of(hour, min));
					}
					else {
						assertEquals(timeParser.parse(Integer.toString(hour) + ":" + Integer.toString(min)), LocalTime.of(hour, min));
					}
				}
			}
		}
	}
}