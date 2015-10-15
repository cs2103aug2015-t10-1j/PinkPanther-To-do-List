package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//import java.nio.file.Files;

import java.util.ArrayList;
import java.util.SortedMap;

import com.google.gson.Gson;

import common.Display;
import common.Task;

import java.time.LocalDate;

public class StorageControl {
	// Attributes
	private static File latestDirectoryTextFile = new File("latest directory.txt");
	private File directory;
	private FloatingStorage floating_File;
	private DatedStorage dated_File;
	private Gson gson = new Gson();

	// Error Messages
	//private static final String SUCCESSFUL_CREATE_DIRECTORY_MESSAGE = "Directory created at: \"%1$s\"";
	private static final String SUCCESSFUL_CHANGE_DIRECTORY_MESSAGE = "Directory changed to: \"%1$s\"";
	private static final String DIRECTORY_ALREADY_EXISTS_MESSAGE = "Directory: \"%1$s\" already exists";
	private static final String SECURITY_EXCEPTION_MESSAGE = "Security manager exists and denies write access to: \"%1$s\"";
	private static final String NO_INPUT_DIRECTORY_MESSAGE = "No directory path was entered";
	private static final String IS_NOT_DIRECTORY_MESSAGE = "\"%1$s\" is not a directory";
	private static final String SAVE_UNSUCCESSFUL_MESSAGE = "Save unsuccessful";
	private static final String INVALID_PATH_MESSAGE = "Invalid path entered.";
	private static final String COULD_NOT_SET_LATEST_DIRECTORY_MESSAGE = "Error! Could not set the latest directory.";
	//private static final String COULD_NOT_MAKE_DIRECTORY_MESSAGE = "Error! Could not make directory.";
	
	public StorageControl() {
	}
	
	public String setStorageEnvironmentFirstTime(String input_FilePath) {
		this.directory = new File(input_FilePath);
		int numOfExistingFiles = 1;
		while (this.directory.isDirectory() == true) {
			this.directory = new File(input_FilePath + " (" + numOfExistingFiles + ")");
			numOfExistingFiles++;
		}
		if (input_FilePath.substring(1, 3).equals(":\\") == false || this.directory.mkdir() == false) {
			return INVALID_PATH_MESSAGE;
		}
		floating_File = new FloatingStorage(directory);
		dated_File = new DatedStorage(directory);
		if (this.setLatestDirectory() == false) {
			return COULD_NOT_SET_LATEST_DIRECTORY_MESSAGE;
		}
		
		return directory.getPath();
	}

	public String setStorageEnvironmentNormal() {
		this.directory = this.getLatestDirectory();
		if (directory == null || directory.isDirectory() == false) {
			int numOfExistingFiles = 1;
			directory = new File("C:\\PPCalendar");
			while (directory.mkdir() == false) {
				directory = new File("C:\\PPCalendar" + " (" + numOfExistingFiles + ")");
				numOfExistingFiles++;
			}
			this.setLatestDirectory();
		}
		floating_File = new FloatingStorage(directory);
		dated_File = new DatedStorage(directory);
		
		return directory.getPath();
	}
	
	public boolean changeDirectory(String input_NewDirectory) {
		File newDirectory = new File(input_NewDirectory);
		File newUndoneFloating = new File(input_NewDirectory + "\\Undone Floating.txt");
		File newDoneFloating = new File(input_NewDirectory + "\\Done Floating.txt");
		File newUndoneDated = new File(input_NewDirectory + "\\Undone Dated.txt");
		File newDoneDated = new File(input_NewDirectory + "\\Done Dated.txt");
		
		try {
			if (newDirectory.mkdir() == false || input_NewDirectory.substring(1, 3).equals(":\\") == false) {
				Display.setFeedBack(String.format(IS_NOT_DIRECTORY_MESSAGE, input_NewDirectory));
				Display.showFeedBack();
				return false;
			}
			floating_File.getUndoneFloatingFile().renameTo(newUndoneFloating);
			floating_File.setUndoneFloatingFile(newUndoneFloating);
			floating_File.getDoneFloatingFile().renameTo(newDoneFloating);
			floating_File.setDoneFloatingFile(newDoneFloating);
			dated_File.getUndoneDatedFile().renameTo(newUndoneDated);
			dated_File.setUndoneDatedFile(newUndoneDated);
			dated_File.getDoneDatedFile().renameTo(newDoneDated);
			dated_File.setDoneDatedFile(newDoneDated);

			directory.delete();
			directory = newDirectory;
			this.setLatestDirectory();

			Display.setFeedBack(String.format(SUCCESSFUL_CHANGE_DIRECTORY_MESSAGE, directory.getPath()));
			Display.showFeedBack();

			return true;
		}
		catch (SecurityException e) {
			Display.setFeedBack(String.format(SECURITY_EXCEPTION_MESSAGE, input_NewDirectory));
			Display.showFeedBack();
			return false;
		}
		catch (NullPointerException e) {
			Display.setFeedBack(NO_INPUT_DIRECTORY_MESSAGE);
			Display.showFeedBack();
			return false;
		}
	}

	public boolean save(ArrayList<Task> taskList, boolean isDone) {
		if (floating_File.writeToFile(taskList, isDone)) {
			return true;
		}
		else {
			Display.setFeedBack(SAVE_UNSUCCESSFUL_MESSAGE);
			Display.showFeedBack();
			
			return false;
		}
	}

	public boolean save(SortedMap<LocalDate, ArrayList<Task>> taskList, boolean isDone) {
		if (dated_File.writeToFile(taskList, isDone)) {
			return true;
		}
		else {
			Display.setFeedBack(SAVE_UNSUCCESSFUL_MESSAGE);
			Display.showFeedBack();
			return false;
		}
	}

	public ArrayList<Task> loadFloating(boolean isDone) {
		return floating_File.readFromFile(isDone);
	}
	
	public SortedMap<LocalDate, ArrayList<Task>> loadDated(boolean isDone) {
		return dated_File.readFromFile(isDone);
	}
	
	/*private StorageControl(String input_FilePath) {
	gson = new Gson();
	
	directory = new File(input_FilePath);
	this.createDirectory();
	this.setLatestDirectory();
	
	floating_File = new FloatingStorage(directory);
	dated_File = new DatedStorage(directory);
	}*/
	
	public boolean checkLatestDirectoryState() {
		File checker = this.getLatestDirectory();
		
		return (latestDirectoryTextFile.exists() == false || checker.equals(null) == true);
	}

	private File getLatestDirectory() {
		String newLine;
		File latestDirectory = null;
		try{
			BufferedReader br = new BufferedReader(new FileReader(latestDirectoryTextFile));
			newLine = br.readLine();
			latestDirectory = gson.fromJson(newLine, File.class);
			br.close();
		}
		catch (FileNotFoundException e) {
			return null;
		}
		catch (IOException e) {
			return null;
		}
		return latestDirectory;
	}

	private boolean setLatestDirectory() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(latestDirectoryTextFile));
			String json = gson.toJson(directory);
			bw.write(json);
			bw.close();
			
			return true;
		}
		catch (IOException e) {
			return false;
		}
	}
}
