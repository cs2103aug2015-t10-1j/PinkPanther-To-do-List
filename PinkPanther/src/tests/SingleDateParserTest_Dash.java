/* @@author A0125250W */

package tests;

import static org.junit.Assert.*;
import java.time.LocalDate;

import org.junit.Test;

import parser.SingleDateParser;

public class SingleDateParserTest_Dash {

	@Test
	public void test_Parse_DashCase1() { // dd-MM-yy
		SingleDateParser dateParser = new SingleDateParser();
		int day, month, year;
		
		for (year = 2016; year <= 2099; year++) {
			for (month = 1; month <= 12; month++) {
				for (day = 1; day <= 31; day++) {
					if ((day == 31 && month == 4) || (day == 31 && month == 6) || (day == 31 && month == 9) || (day == 31 && month == 11)
							|| (day > 28 && month == 2)) {
						continue;
					}
					
					else if (day < 10) {
						assertEquals(dateParser.parse("0" + Integer.toString(day) + "-" + Integer.toString(month) +
								"-" + Integer.toString(year).substring(2)), LocalDate.of(year, month, day));
					}
					else if (month < 10){
						assertEquals(dateParser.parse(Integer.toString(day) + "-" + "0" + Integer.toString(month) +
								"-" + Integer.toString(year).substring(2)), LocalDate.of(year, month, day));
					}
					else {
						assertEquals(dateParser.parse(Integer.toString(day) + "-" + Integer.toString(month) +
								"-" + Integer.toString(year).substring(2)), LocalDate.of(year, month, day));
					}	
				}
			}
		}
	}
	
	@Test
	public void test_Parse_DashCase2() { // d-MM-yy
		SingleDateParser dateParser = new SingleDateParser();
		int day, month, year;
		
		for (year = 2016; year <= 2099; year++) {
			for (month = 1; month <= 12; month++) {
				for (day = 1; day <= 9; day++) {
					if ((day == 31 && month == 4) || (day == 31 && month == 6) || (day == 31 && month == 9) || (day == 31 && month == 11)
							|| (day > 28 && month == 2)) {
						continue;
					}
					
					else if (day < 10) {
						assertEquals(dateParser.parse(Integer.toString(day) + "-" + Integer.toString(month) +
								"-" + Integer.toString(year).substring(2)), LocalDate.of(year, month, day));
					}
					else if (month < 10){
						assertEquals(dateParser.parse(Integer.toString(day) + "-" + "0" + Integer.toString(month) +
								"-" + Integer.toString(year).substring(2)), LocalDate.of(year, month, day));
					}
					else {
						assertEquals(dateParser.parse(Integer.toString(day) + "-" + Integer.toString(month) +
								"-" + Integer.toString(year).substring(2)), LocalDate.of(year, month, day));
					}	
				}
			}
		}
	}
	@Test
	public void test_Parse_DashCase3() { // dd-M-yy
		SingleDateParser dateParser = new SingleDateParser();
		int day, month, year;
		
		for (year = 2016; year <= 2099; year++) {
			for (month = 1; month <= 9; month++) {
				for (day = 1; day <= 31; day++) {
					if ((day == 31 && month == 4) || (day == 31 && month == 6) || (day == 31 && month == 9) || (day == 31 && month == 11)
							|| (day > 28 && month == 2)) {
						continue;
					}
					
					else if (day < 10) {
						assertEquals(dateParser.parse(Integer.toString(day) + "-" + Integer.toString(month) +
								"-" + Integer.toString(year).substring(2)), LocalDate.of(year, month, day));
					}
					else {
						assertEquals(dateParser.parse(Integer.toString(day) + "-" + Integer.toString(month) +
								"-" + Integer.toString(year).substring(2)), LocalDate.of(year, month, day));
					}	
				}
			}
		}
	}
	
	@Test
	public void test_Parse_DashCase4() { // d-M-yy
		SingleDateParser dateParser = new SingleDateParser();
		int day, month, year;
		
		for (year = 2016; year <= 2099; year++) {
			for (month = 1; month <= 9; month++) {
				for (day = 1; day <= 9; day++) {
					assertEquals(dateParser.parse(Integer.toString(day) + "-" + Integer.toString(month) +
								"-" + Integer.toString(year).substring(2)), LocalDate.of(year, month, day));
				}
			}
		}
	}
	
	@Test
	public void test_Parse_DashCase5() { // dd-MMM-yy
		SingleDateParser dateParser = new SingleDateParser();
		int day, year;
		String[] month = {"Jan", "feb", "MAR", "ApR", "mAy", "juN", "JUl", "auG", "Sep", "OcT", "nOv", "DEC"};
		
		for (year = 2016; year <= 2099; year++) {
			for (int j = 1; j <= month.length; j++) {
				for (day = 1; day <= 31; day++) {
					if ((day == 31 && j == 4) || (day == 31 && j == 6) || (day == 31 && j == 9) || (day == 31 && j == 11)
							|| (day > 28 && j == 2)) {
						continue;
					}
					
					else if (day < 10) {
						assertEquals(dateParser.parse("0" + Integer.toString(day) + "-" + month[j - 1] +
								"-" + Integer.toString(year).substring(2)), LocalDate.of(year, j, day));
					}
					else {
						assertEquals(dateParser.parse(Integer.toString(day) + "-" + month[j - 1] +
								"-" + Integer.toString(year).substring(2)), LocalDate.of(year, j, day));
					}	
				}
			}
		}
	}
	
	@Test
	public void test_Parse_DashCase6() { // d-MMM-yy
		SingleDateParser dateParser = new SingleDateParser();
		int day, year;
		String[] month = {"Jan", "feb", "MAR", "ApR", "mAy", "juN", "JUl", "auG", "Sep", "OcT", "nOv", "DEC"};
		
		for (year = 2016; year <= 2099; year++) {
			for (int j = 1; j <= month.length; j++) {
				for (day = 1; day <= 9; day++) {
					assertEquals(dateParser.parse(Integer.toString(day) + "-" + month[j - 1] +
								"-" + Integer.toString(year).substring(2)), LocalDate.of(year, j, day));	
				}
			}
		}
	}
	
	@Test
	public void test_Parse_DashCase7() { // dd-MM-yyyy
		SingleDateParser dateParser = new SingleDateParser();
		int day, month, year;
		
		for (year = 2016; year <= 2099; year++) {
			for (month = 1; month <= 12; month++) {
				for (day = 1; day <= 31; day++) {
					if ((day == 31 && month == 4) || (day == 31 && month == 6) || (day == 31 && month == 9) || (day == 31 && month == 11)
							|| (day > 28 && month == 2)) {
						continue;
					}
					
					else if (day < 10) {
						assertEquals(dateParser.parse("0" + Integer.toString(day) + "-" + Integer.toString(month) +
								"-" + Integer.toString(year)), LocalDate.of(year, month, day));
					}
					else if (month < 10){
						assertEquals(dateParser.parse(Integer.toString(day) + "-" + "0" + Integer.toString(month) +
								"-" + Integer.toString(year)), LocalDate.of(year, month, day));
					}
					else {
						assertEquals(dateParser.parse(Integer.toString(day) + "-" + Integer.toString(month) +
								"-" + Integer.toString(year)), LocalDate.of(year, month, day));
					}	
				}
			}
		}
	}
	
	@Test
	public void test_Parse_DashCase8() { // d-MM-yyyy
		SingleDateParser dateParser = new SingleDateParser();
		int day, month, year;
		
		for (year = 2016; year <= 2099; year++) {
			for (month = 1; month <= 12; month++) {
				for (day = 1; day <= 9; day++) {			
					if (month < 10){
						assertEquals(dateParser.parse(Integer.toString(day) + "-" + "0" + Integer.toString(month) +
								"-" + Integer.toString(year)), LocalDate.of(year, month, day));
					}
					else {
						assertEquals(dateParser.parse(Integer.toString(day) + "-" + Integer.toString(month) +
								"-" + Integer.toString(year)), LocalDate.of(year, month, day));
					}	
				}
			}
		}
	}
	
	@Test
	public void test_Parse_DashCase9() { // dd-M-yyyy
		SingleDateParser dateParser = new SingleDateParser();
		int day, month, year;

		for (year = 2016; year <= 2099; year++) {
			for (month = 1; month <= 9; month++) {
				for (day = 1; day <= 31; day++) {
					if ((day == 31 && month == 4) || (day == 31 && month == 6) || (day == 31 && month == 9) || (day == 31 && month == 11)
							|| (day > 28 && month == 2)) {
						continue;
					}
					else if (day < 10) {
						assertEquals(dateParser.parse("0" + Integer.toString(day) + "-" + Integer.toString(month) +
								"-" + Integer.toString(year)), LocalDate.of(year, month, day));
					}
					else {
						assertEquals(dateParser.parse(Integer.toString(day) + "-" + Integer.toString(month) +
								"-" + Integer.toString(year)), LocalDate.of(year, month, day));
					}	
				}
			}
		}
	}
	
	@Test
	public void test_Parse_DashCase10() { // d-M-yyyy
		SingleDateParser dateParser = new SingleDateParser();
		int day, month, year;

		for (year = 2016; year <= 2099; year++) {
			for (month = 1; month <= 9; month++) {
				for (day = 1; day <= 9; day++) {
					assertEquals(dateParser.parse(Integer.toString(day) + "-" + Integer.toString(month) +
								"-" + Integer.toString(year)), LocalDate.of(year, month, day));
				}
			}
		}
	}
	
	@Test
	public void test_Parse_DashCase11() { // dd-MMM-yyyy
		SingleDateParser dateParser = new SingleDateParser();
		int day, year;
		String[] month = {"Jan", "feb", "MAR", "ApR", "mAy", "juN", "JUl", "auG", "Sep", "OcT", "nOv", "DEC"};

		for (year = 2016; year <= 2099; year++) {
			for (int j = 1; j <= month.length; j++) {
				for (day = 1; day <= 31; day++) {
					if ((day == 31 && j == 4) || (day == 31 && j == 6) || (day == 31 && j == 9) || (day == 31 && j == 11)
							|| (day > 28 && j == 2)) {
						continue;
					}
					else if (day < 10) {
						assertEquals(dateParser.parse("0" + Integer.toString(day) + "-" + month[j - 1] +
								"-" + Integer.toString(year)), LocalDate.of(year, j, day));
					}
					else {
						assertEquals(dateParser.parse(Integer.toString(day) + "-" + month[j - 1] +
								"-" + Integer.toString(year)), LocalDate.of(year, j, day));
					}	
				}
			}
		}
	}
	
	@Test
	public void test_Parse_DashCase12() { // d-MMM-yyyy
		SingleDateParser dateParser = new SingleDateParser();
		int day, year;
		String[] month = {"Jan", "feb", "MAR", "ApR", "mAy", "juN", "JUl", "auG", "Sep", "OcT", "nOv", "DEC"};

		for (year = 2016; year <= 2099; year++) {
			for (int j = 1; j <= month.length; j++) {
				for (day = 1; day <= 9; day++) {
					assertEquals(dateParser.parse(Integer.toString(day) + "-" + month[j - 1] +
								"-" + Integer.toString(year)), LocalDate.of(year, j, day));	
				}
			}
		}
	}
	
	@Test
	public void test_Parse_DashCase13() { // d-MMMM-yyyy
		SingleDateParser dateParser = new SingleDateParser();
		int day, year;
		String[] month = {"January", "february", "MARCH", "ApRiL", "mAy", "juNe", "JUly", "auGUST", "SepTemBER", "OcTobeR", "nOvEMBEr", "DECembeR"};
		
		for (year = 2016; year <= 2099; year++) {
			for (int j = 1; j <= month.length; j++) {
				for (day = 1; day <= 9; day++) {
					assertEquals(dateParser.parse(Integer.toString(day) + "-" + month[j - 1] +
								"-" + Integer.toString(year)), LocalDate.of(year, j, day));
				}
			}
		}
	}
	
	@Test
	public void test_Parse_DashCase14() { // dd-MMMM-yyyy
		SingleDateParser dateParser = new SingleDateParser();
		int day, year;
		String[] month = {"January", "february", "MARCH", "ApRiL", "mAy", "juNe", "JUly", "auGUST", "SepTemBER", "OcTobeR", "nOvEMBEr", "DECembeR"};

		for (year = 2016; year <= 2099; year++) {
			for (int j = 1; j <= month.length; j++) {
				for (day = 1; day <= 31; day++) {
					if ((day == 31 && j == 4) || (day == 31 && j == 6) || (day == 31 && j == 9) || (day == 31 && j == 11)
							|| (day > 28 && j == 2)) {
						continue;
					}
					else if (day < 10) {
						assertEquals(dateParser.parse("0" + Integer.toString(day) + "-" + month[j - 1] +
								"-" + Integer.toString(year)), LocalDate.of(year, j, day));
					}
					else {
						assertEquals(dateParser.parse(Integer.toString(day) + "-" + month[j - 1] +
								"-" + Integer.toString(year)), LocalDate.of(year, j, day));
					}	
				}
			}
		}
	}
	
	@Test
	public void test_Parse_DashCase15() { // dd-MMMM-yy
		SingleDateParser dateParser = new SingleDateParser();
		int day, year;
		String[] month = {"January", "february", "MARCH", "ApRiL", "mAy", "juNe", "JUly", "auGUST", "SepTemBER", "OcTobeR", "nOvEMBEr", "DECembeR"};
		
		for (year = 2016; year <= 2099; year++) {
			for (int j = 1; j <= month.length; j++) {
				for (day = 1; day <= 31; day++) {
					if ((day == 31 && j == 4) || (day == 31 && j == 6) || (day == 31 && j == 9) || (day == 31 && j == 11)
							|| (day > 28 && j == 2)) {
						continue;
					}
					
					else if (day < 10) {
						assertEquals(dateParser.parse("0" + Integer.toString(day) + "-" + month[j - 1] +
								"-" + Integer.toString(year).substring(2)), LocalDate.of(year, j, day));
					}
					else {
						assertEquals(dateParser.parse(Integer.toString(day) + "-" + month[j - 1] +
								"-" + Integer.toString(year).substring(2)), LocalDate.of(year, j, day));
					}	
				}
			}
		}
	}
	
	@Test
	public void test_Parse_DashCase16() { // d-MMMM-yy
		SingleDateParser dateParser = new SingleDateParser();
		int day, year;
		String[] month = {"January", "february", "MARCH", "ApRiL", "mAy", "juNe", "JUly", "auGUST", "SepTemBER", "OcTobeR", "nOvEMBEr", "DECembeR"};
		
		for (year = 2016; year <= 2099; year++) {
			for (int j = 1; j <= month.length; j++) {
				for (day = 1; day <= 9; day++) {
					assertEquals(dateParser.parse(Integer.toString(day) + "-" + month[j - 1] +
								"-" + Integer.toString(year).substring(2)), LocalDate.of(year, j, day));	
				}
			}
		}
	}
}
