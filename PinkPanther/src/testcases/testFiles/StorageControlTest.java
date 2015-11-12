/* @@author A0125250W */

package testcases.testFiles;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import com.google.gson.Gson;

import storage.StorageControl;

public class StorageControlTest {
	
	/* WARNING: Do not use these test cases if you're actually using Pink Panther. If you really want to,
	 * 			back up the task list text files somewhere else before running these tests.
	 */
	
	@Test
	public void setStorageEnvironmentTest_NonBoundary_NoLatestDirectory() {
		StorageControl testStorageControl = new StorageControl();
		
		assertEquals(testStorageControl.setEnvironment(), "C:\\PPCalendar");
		testStorageControl.deleteAllFiles();
	}
	
	@Test
	public void setStorageEnvironmentTest_NonBoundary_HaveLatestDirectory() {
		StorageControl testStorageControl = new StorageControl();
		Gson gsonWrite = new Gson();
		File testLatestDirectory = new File("C:\\TestTest");
		testLatestDirectory.mkdir();
		String json;
		
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(testStorageControl.getLatestDirectoryTextFile()));
			json = gsonWrite.toJson(testLatestDirectory);
			bw.write(json);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals(testStorageControl.setEnvironment(), "C:\\TestTest");
		testStorageControl.deleteAllFiles();
	}
	
	@Test
	public void setStorageEnvironmentTest_Boundary_LatestDirectoryNotDirectory() {
		StorageControl testStorageControl = new StorageControl();
		
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(testStorageControl.getLatestDirectoryTextFile()));
			bw.write("{\"path\":\"C:\\\\hetjhertj\"}");
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertEquals(testStorageControl.setEnvironment(), "C:\\PPCalendar");
		testStorageControl.deleteAllFiles();
	}
	
	@Test
	public void changeDirectoryTest_NonBoundary_ValidDirectory() {
		StorageControl testStorageControl = new StorageControl();
		testStorageControl.setEnvironment();
		
		assertEquals(testStorageControl.changeDirectory("C:\\PinkPantherCalendar"), true);
		testStorageControl.deleteAllFiles();
	}
	
	@Test
	public void changeDirectoryTest_Boundary_SameAsCurrentDirectory() {
		StorageControl testStorageControl = new StorageControl();
		testStorageControl.setEnvironment();
		
		assertEquals(testStorageControl.changeDirectory("C:\\PPCalendar"), false);
		testStorageControl.deleteAllFiles();
	}
	
	@Test
	public void changeDirectoryTest_Boundary_DirectoryExists() {
		StorageControl testStorageControl = new StorageControl();
		testStorageControl.setEnvironment();
		
		File PPCalendarTest = new File("C:\\TestCalendar");
		PPCalendarTest.mkdir();
		
		assertEquals(testStorageControl.changeDirectory("C:\\TestCalendar"), true);
		testStorageControl.deleteAllFiles();
	}
	
	@Test
	public void changeDirectoryTest_Boundary_InvalidDirectory() {
		StorageControl testStorageControl = new StorageControl();
		testStorageControl.setEnvironment();
		
		assertEquals(testStorageControl.changeDirectory("testing"), false);
		testStorageControl.deleteAllFiles();
	}
	
}
