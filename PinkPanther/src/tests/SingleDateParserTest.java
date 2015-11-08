/* @@author A0126473E*/
package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import parser.SingleDateParser;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SingleDateParserTest {

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE");
	String dayTodayFull = LocalDate.now().getDayOfWeek().toString();
	String dayToday = LocalDate.now().format(dtf);
	
	SingleDateParser sdp = new SingleDateParser();
	
	// Testing fixDate
	// Should work for both valid and invalid dates
	@Test
	public void testFixDatesWithoutYears() {
		String[] testInput = {"21 nov", "4/5", "39 sePTTmber ", 
				"7/fEB", "twenty two", "6-7", "dogs-cats"};
		String[] expectedOutput = {"21 Nov 2015", "4/5/2015",
				"39 Septtmber 2015", "7/Feb/2015", "twenty Two 2015",
				"6-7-2015", "dogs-Cats-2015"};
		String[] testOutput = new String[testInput.length];
		Boolean[] testOutputAppend = new Boolean[testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = sdp.fixDate(testInput[i]).getFirst();
			testOutputAppend[i] = sdp.fixDate(testInput[i]).getSecond();
			assertEquals(expectedOutput[i], testOutput[i]);
			assertEquals(true, testOutputAppend[i]);
		}	
	}

	
	@Test
	public void testFixDatesWithYears() {
		String[] testInput = {"10 mAY 2015", "2/8/17", "39 cats 15",
				"7-fEB-1998", "I/like/cats"};
		String[] expectedOutput = {"10 May 2015", "2/8/17",
				"39 Cats 15", "7-Feb-1998", "I/Like/cats"};
		String[] testOutput = new String[testInput.length];
		Boolean[] testOutputAppend = new Boolean[testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = sdp.fixDate(testInput[i]).getFirst();
			testOutputAppend[i] = sdp.fixDate(testInput[i]).getSecond();
			assertEquals(expectedOutput[i], testOutput[i]);
			assertEquals(false, testOutputAppend[i]);
		}	
	}
	
	@Test
	public void testFixGibberishFormats() {
		String[] testInput = {"twenty", "good.night", "30July", 
				"", "i love cats forever"};
		String[] expectedOutput = {"twenty", "good.night", "30July", 
				"", "i Love cats forever"};
		String[] testOutput = new String[testInput.length];
		Boolean[] testOutputAppend = new Boolean[testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = sdp.fixDate(testInput[i]).getFirst();
			testOutputAppend[i] = sdp.fixDate(testInput[i]).getSecond();
			assertEquals(expectedOutput[i], testOutput[i]);
			assertEquals(false, testOutputAppend[i]);
		}	
	}
	
	// Testing parse
	@Test
	public void testParseOneWordNormal() {
		String[] testInput = {"toDAy", "nOw", "Tonight", "tomorrOW"};
		LocalDate[] expectedOutput = {LocalDate.now(), LocalDate.now(),
				LocalDate.now(), LocalDate.now().plusDays(1)};
		LocalDate[] testOutput = new LocalDate[testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = sdp.parse(testInput[i]);
			assertEquals(expectedOutput[i], testOutput[i]);
		}	
	}
	
	@Test
	public void testParseOneWordGibberish() {
		String[] testInput = {"Wednesday", "goodnight", "30July", ""};
		LocalDate[] expectedOutput = {null, null, null, null};
		LocalDate[] testOutput = new LocalDate[testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = sdp.parse(testInput[i]);
			assertEquals(expectedOutput[i], testOutput[i]);
		}	
	}
	
	@Test
	public void testParseTwoWordsNormal() {
		String[] testInput = {"next week", "next month", "next year",
				"this " + dayTodayFull, "next "+ dayToday};
		LocalDate[] expectedOutput = {LocalDate.now().plusWeeks(1), 
				LocalDate.now().plusMonths(1), LocalDate.now().plusYears(1), 
				LocalDate.now(), LocalDate.now().plusWeeks(1)};
		LocalDate[] testOutput = new LocalDate[testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = sdp.parse(testInput[i]);
			assertEquals(expectedOutput[i], testOutput[i]);
		}	
	}
	
	@Test
	public void testParseTwoWordsGibberish() {
		String[] testInput = {"this week", "this month", "this year",
				"never saturday", "cat sat", "hopping rabbits"};
		LocalDate[] testOutput = new LocalDate[testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = sdp.parse(testInput[i]);
			assertEquals(null, testOutput[i]);
		}
	}
	
	@Test
	public void testParseDatesNormal() {
		String[] testInput = {"28 Feb 2016", "4 March", "3 07 2015",
				"01 01", "14/Jul/2016", "5/September", "15/02/2015",
				"09/04", "20-May-14", "19-November", "13-08-2018",
				"9-3"};
		LocalDate[] expectedOutput = {LocalDate.of(2016, 2, 28), 
				LocalDate.of(2015, 3, 4), LocalDate.of(2015, 7, 3), 
				LocalDate.of(2015, 1, 1), LocalDate.of(2016, 7, 14), 
				LocalDate.of(2015, 9, 5), LocalDate.of(2015, 2, 15), 
				LocalDate.of(2015, 4, 9), LocalDate.of(2014, 5, 20), 
				LocalDate.of(2015, 11, 19), LocalDate.of(2018, 8, 13), 
				LocalDate.of(2015, 3, 9)};
		LocalDate[] testOutput = new LocalDate[testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = sdp.parse(testInput[i]);
			assertEquals(expectedOutput[i], testOutput[i]);
		}	
	}
	
	@Test
	public void testParseDatesGibberish() {
		String[] testInput = {"28.Feb.2016", "hello", "meow",
				"14/Jules/Verne", "5/Pikachu", "15/Febuary/2015"};
		LocalDate[] testOutput = new LocalDate[testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = sdp.parse(testInput[i]);
			assertEquals(null, testOutput[i]);
		}	
	}
	
	@Test
	// Leap years and date range correction
	public void testParseDatesBoundaryPositive() {
		String[] testInput = {"31 Feb 2016", "31 April", 
				"31/June/2016", "31/09/2015", "31-noV-14", 
				"29/2/2015", "29/2/2016", "29/2/2017", "29/2/2018",
				"20/7/3999", "20/7/00"};
		LocalDate[] expectedOutput = {LocalDate.of(2016, 2, 29), 
				LocalDate.of(2015, 4, 30), LocalDate.of(2016, 6, 30), 
				LocalDate.of(2015, 9, 30), LocalDate.of(2014, 11, 30), 
				LocalDate.of(2015, 2, 28), LocalDate.of(2016, 2, 29), 
				LocalDate.of(2017, 2, 28), LocalDate.of(2018, 2, 28), 
				LocalDate.of(3999, 7, 20), LocalDate.of(2000, 7, 20)};
		LocalDate[] testOutput = new LocalDate[testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = sdp.parse(testInput[i]);
			assertEquals(expectedOutput[i], testOutput[i]);
		}	
	}

	@Test
	// Dates that are really out of range
	public void testParseDatesBoundaryNegative() {
		String[] testInput = {"32 07 2015", "00 01", "32/jun", 
				"34/09", "32-November", "32-08-2018",
				"29/13/2015", "1/0/1997", "20/7/0"};
		LocalDate[] testOutput = new LocalDate[testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = sdp.parse(testInput[i]);
			assertEquals(null, testOutput[i]);
		}	
	}
}
