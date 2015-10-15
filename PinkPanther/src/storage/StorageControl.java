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
	private static File latestDirectoryTextFile = new File("latestDirectory.txt");
	private File directory;
	private FloatingStorage floating_File;
	private DatedStorage dated_File;
	private Gson gson;

	private static boolean hasStorageControl = false;

	// Error Messages
	private static final String SUCCESSFUL_CREATE_DIRECTORY_MESSAGE = "Directory created at: \"%1$s\"";
	private static final String SUCCESSFUL_CHANGE_DIRECTORY_MESSAGE = "Directory changed to: \"%1$s\"";
	private static final String DIRECTORY_ALREADY_EXISTS_MESSAGE = "Directory: \"%1$s\" already exists";
	private static final String SECURITY_EXCEPTION_MESSAGE = "Security manager exists and denies write access to: \"%1$s\"";
	private static final String NO_INPUT_DIRECTORY_MESSAGE = "No directory path was entered";
	private static final String IS_NOT_DIRECTORY_MESSAGE = "\"%1$s\" is not a directory";
	private static final String SAVE_UNSUCCESSFUL_MESSAGE = "Save unsuccessful";

	//"Default Constructor". Implements Singleton pattern.
	public static StorageControl createStorageControl() {
		if (!hasStorageControl) {
			hasStorageControl = true;
			return new StorageControl();
		}
		return null;
	}

	//"Constructor" used during clean starts, when user is prompted to input the directory he wants to save the files in.
	public static StorageControl createStorageControl(String input_FilePath) {
		if (!hasStorageControl) {
			hasStorageControl = true;
			return new StorageControl(input_FilePath);
		}
		return null;
	}

	private StorageControl() {
		gson = new Gson();
	}

	private StorageControl(String input_FilePath) {
		gson = new Gson();

		directory = new File(input_FilePath);
		this.createDirectory();
		this.setLatestDirectory();

		floating_File = new FloatingStorage(directory);
		dated_File = new DatedStorage(directory);
	}
	
	public static boolean checkLatestDirectory() {
		return latestDirectoryTextFile.exists();
	}

	public String createDirectory() {
		try {
			directory = this.getLatestDirectory();
			if (directory == null || (directory.isDirectory() == false && directory.exists() == false)) {
				directory = new File("C:\\PPCalendar");
			}
			
			if (directory.isDirectory() == false) {
				directory.mkdir();
				if (directory.exists() == false) {
					return "Invalid File Path";
				}
			}
			floating_File = new FloatingStorage(directory);
			dated_File = new DatedStorage(directory);
			
			Display.setFeedBack(String.format(SUCCESSFUL_CREATE_DIRECTORY_MESSAGE, directory.getAbsolutePath()));
			Display.showFeedBack();
			this.setLatestDirectory();
			return directory.getAbsolutePath();
		}
		catch (SecurityException e) {
			Display.setFeedBack(String.format(SECURITY_EXCEPTION_MESSAGE, directory.getAbsolutePath()));
			Display.showFeedBack();
			return "Security Exception encountered.";
		}
	}
	
	public String createDirectory(String input_FilePath) {
		try {
			directory = this.getLatestDirectory();
			if (directory == null || (directory.isDirectory() == false && directory.exists() == false)) {
				directory = new File(input_FilePath);
			}
			
			if (directory.isDirectory() == false) {
				directory.mkdir();
				if (directory.exists() == false) {
					return "Invalid File Path";
				}
			}
			floating_File = new FloatingStorage(directory);
			dated_File = new DatedStorage(directory);
			
			Display.setFeedBack(String.format(SUCCESSFUL_CREATE_DIRECTORY_MESSAGE, directory.getAbsolutePath()));
			Display.showFeedBack();
			this.setLatestDirectory();
			return directory.getAbsolutePath();
		}
		catch (SecurityException e) {
			Display.setFeedBack(String.format(SECURITY_EXCEPTION_MESSAGE, directory.getAbsolutePath()));
			Display.showFeedBack();
			return "Security Exception encountered.";
		}
	}

	public boolean changeDirectory(String input_NewDirectory) {
		File newDirectory = new File(input_NewDirectory);
		File newUndoneFloating = new File(input_NewDirectory + "\\Undone Floating.txt");
		File newDoneFloating = new File(input_NewDirectory + "\\Done Floating.txt");
		File newUndoneDated = new File(input_NewDirectory + "\\Undone Dated.txt");
		File newDoneDated = new File(input_NewDirectory + "\\Done Dated.txt");
		
		try {
			if (directory.isDirectory()) {
				newDirectory.mkdir();
				if (newDirectory.exists() == false || input_NewDirectory.substring(1, 3).equals(":\\") == false) {
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
			else {
				Display.setFeedBack(String.format(IS_NOT_DIRECTORY_MESSAGE, input_NewDirectory));
				Display.showFeedBack();
				return false;
			}
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
			//Do nothing since file does not exist
		}
		catch (IOException e) {
			e.printStackTrace();
			//return null;
		}
		return latestDirectory;
	}

	private void setLatestDirectory() {
		try {
			/*if (Files.isHidden(latestDirectoryTextFile.toPath())) {
			Files.setAttribute(latestDirectoryTextFile.toPath(), "dos:hidden", false);
			*/
			BufferedWriter bw = new BufferedWriter(new FileWriter(latestDirectoryTextFile));
			String json = gson.toJson(directory);
			bw.write(json);
			bw.close();

			//Files.setAttribute(latestDirectoryTextFile.toPath(), "dos:hidden", true);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
