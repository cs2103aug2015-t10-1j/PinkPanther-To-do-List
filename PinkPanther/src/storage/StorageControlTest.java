package storage;

import static org.junit.Assert.*;

import org.junit.Test;

public class StorageControlTest {
	
	
	@Test
	public void test_SetStorageEnvironmentFirstTime_Positive() {
		StorageControl testStorage = new StorageControl();
		assertEquals(testStorage.setStorageEnvironmentFirstTime("C:\\MyCalendar"), "C:\\MyCalendar");
	}
	
	//@Test
	public void test_SetStorageEnvironmentFirstTime_Negative1() {
		StorageControl testStorage = new StorageControl();
		assertEquals(testStorage.setStorageEnvironmentFirstTime("abc"), "Invalid path entered");
	}
	
	//@Test
	public void test_SetStorageEnvironmentFirstTime_Negative2() {
		StorageControl testStorage = new StorageControl();
		assertEquals(testStorage.setStorageEnvironmentFirstTime("A:\\PPCalendar"), "Invalid path entered");
	}
	
	//@Test
	public void test_SetStorageEnvironmentNormal_Positive() {
		StorageControl testStorage = new StorageControl();
		assertEquals(testStorage.setStorageEnvironmentNormal(), "C:\\MyCalendar");
	}
	
	//@Test
	public void test_ChangeDirectory_Positive() {
		StorageControl testStorage = new StorageControl();
		assertEquals(testStorage.setStorageEnvironmentFirstTime("C:\\MyCalendar"), "C:\\MyCalendar");
		assertEquals(testStorage.changeDirectory("C:\\PPCalendar"), true);
	}
	
	//@Test
	public void test_ChangeDirectory_Negative2() {
		StorageControl testStorage = new StorageControl();
		assertEquals(testStorage.changeDirectory("L:\\PPCalendar"), false);
	}
}
