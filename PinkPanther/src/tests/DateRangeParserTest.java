/* @@author A0126473E */
package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import parser.AddStringParser;
import parser.DateRangeParser;
import common.Pair;
import java.time.LocalDate;

public class DateRangeParserTest {

	AddStringParser asp = new AddStringParser();
	DateRangeParser drp = new DateRangeParser(asp);
	
	@Test
	public void testDateRangeParsingPositive() {
		String[] testInput = {"2-4/8", "10 may-12 may", "17 dec to 5 jan", 
				"20 nov to 19 nov"};
		LocalDate[] expectedOutputFirst = {LocalDate.of(2015,8,2), LocalDate.of(2015,8,2),
				LocalDate.of(2015,8,2), LocalDate.of(2015,8,2),};
		LocalDate[] expectedOutputSecond = {LocalDate.of(2015,8,4), LocalDate.of(2015,8,2),
				LocalDate.of(2015,8,2), LocalDate.of(2015,8,2),};
		Pair <LocalDate, LocalDate>[] testOutput = new Pair [testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = drp.parse(testInput[i]);
			assertEquals(expectedOutputFirst[i], testOutput[i].getFirst());
			assertEquals(expectedOutputSecond[i], testOutput[i].getSecond());
		}
	}
}
