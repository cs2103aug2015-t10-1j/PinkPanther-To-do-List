/* @@author A0126473E */
package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import parser.AddStringParser;
import parser.DateRangeParser;
import common.Pair;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@SuppressWarnings("unchecked")
public class DateRangeParserTest {

	AddStringParser asp = new AddStringParser();
	DateRangeParser drp = new DateRangeParser(asp);
	
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE");
	String dayTodayFull = LocalDate.now().getDayOfWeek().toString();
	String dayToday = LocalDate.now().format(dtf);
	
	@Test
	public void testDateRangeParsingPositive() {
		String[] testInput = {"2-4/8/2017", "7-12 may 2005", "11 to 13-2-2016", 
				"11/jun/2015 - 12/Jul/2015", "2 to 5-2", " 7 - 21 Jun 2015",
				"20 nov to 2 dec 2017", " 4 Apr 2013-6 May", " 30 nov - 16 Jan",
				"17 october - 8 May 2018", "6/3/2012-22/2"};
		LocalDate[] expectedOutputFirst = {LocalDate.of(2017, 8, 2), 
				LocalDate.of(2005, 5, 7), LocalDate.of(2016, 2, 11), 
				LocalDate.of(2015, 6, 11), LocalDate.of(2015, 2, 2),
				LocalDate.of(2015, 6, 7), LocalDate.of(2017, 11, 20),
				LocalDate.of(2013, 4, 4), LocalDate.of(2015, 11, 30),
				LocalDate.of(2017, 10, 17), LocalDate.of(2012, 3, 6)};
		LocalDate[] expectedOutputSecond = {LocalDate.of(2017, 8, 4), 
				LocalDate.of(2005, 5, 12), LocalDate.of(2016, 2, 13), 
				LocalDate.of(2015, 7, 12), LocalDate.of(2015, 2, 5),
				LocalDate.of(2015, 6, 21), LocalDate.of(2017, 12, 2),
				LocalDate.of(2013, 5, 6), LocalDate.of(2016, 1, 16),
				LocalDate.of(2018, 5, 8), LocalDate.of(2013, 2, 22)};
		Pair <LocalDate, LocalDate>[] testOutput = new Pair [testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = drp.parse(testInput[i]);
			assertEquals(expectedOutputFirst[i], testOutput[i].getFirst());
			assertEquals(expectedOutputSecond[i], testOutput[i].getSecond());
		}
	}
	
	@Test
	public void testDateRangeParsingBoundaryPositive() {
		String[] testInput = {"7-31/9", "1/1/00-2/2/00", "2/2-7 nov 2015", "4-6"};
		LocalDate[] expectedOutputFirst = {LocalDate.of(2015, 9, 7), 
				LocalDate.of(2000, 1, 1), LocalDate.of(2015, 2, 2),
				LocalDate.of(2015, 6, 4)};
		LocalDate[] expectedOutputSecond = {LocalDate.of(2015, 9, 30), 
				LocalDate.of(2000, 2, 2), LocalDate.of(2015, 11, 7),
				LocalDate.of(2015, 6, 4)};
		Pair <LocalDate, LocalDate>[] testOutput = new Pair [testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = drp.parse(testInput[i]);
			assertEquals(expectedOutputFirst[i], testOutput[i].getFirst());
			assertEquals(expectedOutputSecond[i], testOutput[i].getSecond());
		}
	}
	
	@Test
	public void testDateRangeParsingBoundaryNegative() {
		String[] testInput = {"31-30/9", "1/1/1-2/2/1", "5-5-2015-6-6-2016"};
		Pair <LocalDate, LocalDate>[] testOutput = new Pair [testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = drp.parse(testInput[i]);
			assertEquals(null, testOutput[i]);
		}
	}
	
	@Test
	// done on a Sunday
	// please change the sundays in this test to your current day
	public void testDateRangeSingleDateParsingPositive() {
		String[] testInput = {"toDAy", "nOw", "Tonight", "tomorrOW",
				"next week", "next month", "next year",
				"this " + dayTodayFull, "next " + dayToday, "28 Feb 2016", 
				"4 March", "3 07 2015", "01 01", "14/Jul/2016", "5/September", 
				"15/02/2015", "09/04", "20-May-14", "19-November", 
				"13-08-2018", "9-3" };
		LocalDate[] expectedOutputFirst = {LocalDate.now(), LocalDate.now(),
				LocalDate.now(), LocalDate.now().plusDays(1),
				LocalDate.now().plusWeeks(1), LocalDate.now().plusMonths(1), 
				LocalDate.now().plusYears(1), LocalDate.now(), 
				LocalDate.now().plusWeeks(1), LocalDate.of(2016, 2, 28), 
				LocalDate.of(2015, 3, 4), LocalDate.of(2015, 7, 3), 
				LocalDate.of(2015, 1, 1), LocalDate.of(2016, 7, 14), 
				LocalDate.of(2015, 9, 5), LocalDate.of(2015, 2, 15), 
				LocalDate.of(2015, 4, 9), LocalDate.of(2014, 5, 20), 
				LocalDate.of(2015, 11, 19), LocalDate.of(2018, 8, 13), 
				LocalDate.of(2015, 3, 9)};
		LocalDate[] expectedOutputSecond = {LocalDate.now(), LocalDate.now(),
				LocalDate.now(), LocalDate.now().plusDays(1),
				LocalDate.now().plusWeeks(1), LocalDate.now().plusMonths(1), 
				LocalDate.now().plusYears(1), LocalDate.now(), 
				LocalDate.now().plusWeeks(1), LocalDate.of(2016,2,28), 
				LocalDate.of(2015, 3, 4), LocalDate.of(2015, 7, 3), 
				LocalDate.of(2015, 1, 1), LocalDate.of(2016, 7, 14), 
				LocalDate.of(2015, 9, 5), LocalDate.of(2015, 2, 15), 
				LocalDate.of(2015, 4, 9), LocalDate.of(2014, 5, 20), 
				LocalDate.of(2015, 11, 19), LocalDate.of(2018, 8, 13), 
				LocalDate.of(2015, 3, 9)};
		Pair <LocalDate, LocalDate>[] testOutput = new Pair [testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = drp.parse(testInput[i]);
			assertEquals(expectedOutputFirst[i], testOutput[i].getFirst());
			assertEquals(expectedOutputSecond[i], testOutput[i].getSecond());
		}
	}
	
	@Test
	// leap years and range correction
	public void testDateRangeSingleDateParsingBoundaryPositive() {
		String[] testInput = {"31 Feb 2016", "31 April", 
				"31/June/2016", "31/09/2015", "31-noV-14", 
				"29/2/2015", "29/2/2016", "29/2/2017", "29/2/2018",
				"20/7/3999", "20/7/00"};
		LocalDate[] expectedOutputFirst = {LocalDate.of(2016, 2, 29), 
				LocalDate.of(2015, 4, 30), LocalDate.of(2016, 6, 30), 
				LocalDate.of(2015, 9, 30), LocalDate.of(2014, 11, 30), 
				LocalDate.of(2015, 2, 28), LocalDate.of(2016, 2, 29), 
				LocalDate.of(2017, 2, 28), LocalDate.of(2018, 2, 28), 
				LocalDate.of(3999, 7, 20), LocalDate.of(2000, 7, 20)};
		LocalDate[] expectedOutputSecond = {LocalDate.of(2016, 2, 29), 
				LocalDate.of(2015, 4, 30), LocalDate.of(2016, 6, 30), 
				LocalDate.of(2015, 9, 30), LocalDate.of(2014, 11, 30), 
				LocalDate.of(2015, 2, 28), LocalDate.of(2016, 2, 29), 
				LocalDate.of(2017, 2, 28), LocalDate.of(2018, 2, 28), 
				LocalDate.of(3999, 7, 20), LocalDate.of(2000, 7, 20)};
		Pair <LocalDate, LocalDate>[] testOutput = new Pair [testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = drp.parse(testInput[i]);
			assertEquals(expectedOutputFirst[i], testOutput[i].getFirst());
			assertEquals(expectedOutputSecond[i], testOutput[i].getSecond());
		}
	}
	
	@Test
	public void testDateRangeSingleDateParsingBoundaryNegative() {
		String[] testInput = {"32 07 2015", "00 01", "32/jun", 
				"34/09", "32-November", "32-08-2018",
				"29/13/2015", "1/0/1997", "20/7/0"};
		Pair <LocalDate, LocalDate>[] testOutput = new Pair [testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = drp.parse(testInput[i]);
			assertEquals(null, testOutput[i]);
		}
	}
	
	@Test
	public void testDateRangeSingleDateParsingNegative() {
		String[] testInput = {"Wednesday", "goodnight", "30July", "-", "",
				"this week", "this month", "this year",
				"never saturday", "cat sat", "hopping rabbits",
				"28.Feb.2016", "hello", "meow",
				"14/Jules/Verne", "5/Pikachu", "15/Febuary/2015"};
		Pair <LocalDate, LocalDate>[] testOutput = new Pair [testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = drp.parse(testInput[i]);
			assertEquals(null, testOutput[i]);
		}
	}
}
