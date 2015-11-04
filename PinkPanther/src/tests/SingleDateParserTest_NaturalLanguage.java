package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import parser.SingleDateParser;

import java.time.LocalDate;

public class SingleDateParserTest_NaturalLanguage {

	//@Test
	public void test_Parse_TestCase1() {
		SingleDateParser dateParser = new SingleDateParser();
		String[] listOfWords = {"Today", "tonight", "nOw", "toMOrrOW"};
		
		assertEquals(dateParser.parse(listOfWords[0]), LocalDate.now());
		assertEquals(dateParser.parse(listOfWords[1]), LocalDate.now());
		assertEquals(dateParser.parse(listOfWords[2]), LocalDate.now());
		assertEquals(dateParser.parse(listOfWords[3]), LocalDate.now().plusDays(1));
	}
	
	@Test
	public void test_Parse_TestCase2() { //As tested on 30 September 2015, Wednesday
		SingleDateParser dateParser = new SingleDateParser();
		/*String[] listOfWords = {"This", "Next"};
		String[] listOfTimePeriods = {"Week", "month", "yeAR"};
		String[] listOfDays = {"Monday", "tuesday", "WednesDay", "ThuRSDAY", "FRiDaY", "SaturdAY", "SUNDAy"};
		LocalDate today = LocalDate.of(2015, 9, 29);*/
	
		assertEquals(dateParser.parse("this monday"), LocalDate.now().plusDays(5));	
		assertEquals(dateParser.parse("this tuesday"), LocalDate.now().plusDays(6));
		assertEquals(dateParser.parse("this wednesday"), LocalDate.now().plusDays(0));
		assertEquals(dateParser.parse("this thursday"), LocalDate.now().plusDays(1));
		assertEquals(dateParser.parse("this friday"), LocalDate.now().plusDays(2));
		assertEquals(dateParser.parse("this saturday"), LocalDate.now().plusDays(3));
		assertEquals(dateParser.parse("this sunday"), LocalDate.now().plusDays(4));
		
		assertEquals(dateParser.parse("next monday"), LocalDate.now().plusDays(5));
		assertEquals(dateParser.parse("next tuesday"), LocalDate.now().plusDays(6));
		assertEquals(dateParser.parse("next wednesday"), LocalDate.now().plusDays(7));
		assertEquals(dateParser.parse("next thursday"), LocalDate.now().plusDays(8));
		assertEquals(dateParser.parse("next friday"), LocalDate.now().plusDays(9));
		assertEquals(dateParser.parse("next saturday"), LocalDate.now().plusDays(10));
		assertEquals(dateParser.parse("next sunday"), LocalDate.now().plusDays(11));	
	}
	
	@Test
	public void test_Parse_TestCase3() { //As tested on 30 September 2015, Wednesday
		SingleDateParser dateParser = new SingleDateParser();
		/*String[] listOfWords = {"This", "Next"};
		String[] listOfTimePeriods = {"Week", "month", "yeAR"};
		String[] listOfDays = {"Monday", "tuesday", "WednesDay", "ThuRSDAY", "FRiDaY", "SaturdAY", "SUNDAy"};
		LocalDate today = LocalDate.of(2015, 9, 29);*/
	
		assertEquals(dateParser.parse("next week"), LocalDate.now().plusWeeks(1));	
		assertEquals(dateParser.parse("next month"), LocalDate.now().plusMonths(1));
		assertEquals(dateParser.parse("next year"), LocalDate.now().plusYears(1));
	}
}
