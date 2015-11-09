/* @@author A0126473E */
package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import common.Display;
import common.Pair;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import parser.QueryParser;

@SuppressWarnings("unchecked")
public class QueryParserTest {
	
	QueryParser qp = new QueryParser();
	
	@Test
	public void testQueryParsingOneIndex() {
		String[] testInput = {"11 Jul 2017, 1", "4/1/1991, 0", "2-11, 12", 
				"tomorrow, 299", "undAted, 20"};
		LocalDate[] expectedOutputFirst = {LocalDate.of(2017, 7, 11), 
				LocalDate.of(1991, 1, 4), LocalDate.of(2015, 11, 2),
				LocalDate.now().plusDays(1), null};
		Integer[] expectedOutputSecond = {1, 0, 12, 299, 20};
		Pair <LocalDate, ArrayList<Integer>>[] testOutput = new Pair [testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = qp.parse(testInput[i]);
			assertEquals(expectedOutputFirst[i], testOutput[i].getFirst());
			assertEquals(expectedOutputSecond[i], testOutput[i].getSecond().get(0));
		}
	}
	
	@Test
	public void testQueryParsingMultipleIndices() {
		String[] testInput = {"11 Jul 2017, 2 7 9 3", "undated, 12 45 10234"};
		LocalDate[] expectedOutputFirst = {LocalDate.of(2017, 7, 11), 
				 null};
		Integer[][] expectedOutputSecond = {{2, 7, 9, 3}, {12, 45, 10234}};
		Pair <LocalDate, ArrayList<Integer>>[] testOutput = new Pair [testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = qp.parse(testInput[i]);
			assertEquals(expectedOutputFirst[i], testOutput[i].getFirst());
			ArrayList<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(expectedOutputSecond[i]));
			assertEquals(arrayList, testOutput[i].getSecond());
		}
	}
	
	@Test
	public void testQueryParsingAllIndices() {
		String[] testInput = {"4/5, all", "undated, all"};
		LocalDate[] expectedOutputFirst = {LocalDate.of(2015, 5, 4), 
				 null};
		Pair <LocalDate, ArrayList<Integer>>[] testOutput = new Pair [testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = qp.parse(testInput[i]);
			assertEquals(expectedOutputFirst[i], testOutput[i].getFirst());
			assertEquals(null, testOutput[i].getSecond());
		}
	}

	@Test
	public void testQueryParsingNegativeDueToCommas() {
		String[] testInput = {"4/5, 5/6, 9, 10", "17 nov 2015", ",,,,", ",", ""};
		Pair <LocalDate, ArrayList<Integer>>[] testOutput = new Pair [testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = qp.parse(testInput[i]);
			assertEquals(null, testOutput[i]);
			assertEquals("Invalid input format.",
					Display.showFeedBack());
		}
	}
	
	@Test
	public void testQueryParsingNegativeInvalidIndex() {
		String[] testInput = {"31 Mar 2015, 0 1 2 hello", "17 nov 2015, test",
				"20/9, "};
		Pair <LocalDate, ArrayList<Integer>>[] testOutput = new Pair [testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = qp.parse(testInput[i]);
			assertEquals(null, testOutput[i]);
			assertEquals("Invalid index entered.", Display.showFeedBack());
		}
	}
	
	@Test
	public void testQueryParsingNegativeInvalidDate() {
		String[] testInput = {"31Mar, 0", "21/20/1999, 17", "14, hello"};
		Pair <LocalDate, ArrayList<Integer>>[] testOutput = new Pair [testInput.length];
		for (int i = 0; i < testInput.length; i++) {
			testOutput[i] = qp.parse(testInput[i]);
			assertEquals(null, testOutput[i]);
			assertEquals("Invalid keyword or date entered.",
					Display.showFeedBack());
		}
	}
}
