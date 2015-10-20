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

import com.google.gson.Gson;

import common.Display;
import common.Task;


public class StorageControl {
	// Attributes
	private static File latestDirectoryTextFile = new File("latest directory.txt");
	private File directory;
	private TaskStorage taskFile;
	private Gson gson = new Gson();

	// Error Messages
	//private static final String SUCCESSFUL_CREATE_DIRECTORY_MESSAGE = "Directory created at: \"%1$s\"";
	private static final String SUCCESSFUL_CHANGE_DIRECTORY_MESSAGE = "Directory changed to: \"%1$s\"";
	private static final String DIRECTORY_ALREADY_EXISTS_MESSAGE = "Directory: \"%1$s\" already exists";
	private static final String SECURITY_EXCEPTION_MESSAGE = "Security manager exists and denies write access to: \"%1$s\"";
	private static final String NO_INPUT_DIRECTORY_MESSAGE = "No directory path was entered";
	private static final String IS_NOT_DIRECTORY_MESSAGE = "\"%1$s\" is not a directory";
	private static final String SAVE_UNSUCCESSFUL_MESSAGE = "Save unsuccessful";
	private static final String INVALID_PATH_MESSAGE = "Invalid path entered";
	private static final String COULD_NOT_SET_LATEST_DIRECTORY_MESSAGE = "Error! Could not set the latest directory";
	private static final String SAME_DIRECTORY_MESSAGE = "\"%1$s\" is the current directory, directory remains unchanged";
	//private static final String NEW_DIRECTORY_MODIFIED_MESSAGE = "Directory path was modified to \"%1$s\" to successfully change the directory";
	//private static final String COULD_NOT_MAKE_DIRECTORY_MESSAGE = "Error! Could not make directory.";
	
	public StorageControl() {
	}
	
	public String setStorageEnvironmentFirstTime(String input_FilePath) {
		directory = new File(input_FilePath);
		int numOfExistingFiles = 1;
		while (directory.isDirectory() == true) {
			directory = new File(input_FilePath + " (" + numOfExistingFiles + ")");
			numOfExistingFiles++;
		}
		if (input_FilePath.substring(1, 3).equals(":\\") == false || directory.mkdir() == false) {
			return INVALID_PATH_MESSAGE;
		}
		taskFile = new TaskStorage(directory);
		if (setLatestDirectory() == false) {
			return COULD_NOT_SET_LATEST_DIRECTORY_MESSAGE;
		}
		
		return directory.getPath();
	}

	public String setStorageEnvironmentNormal() {
		directory = getLatestDirectory();
		if (directory == null || directory.isDirectory() == false) {
			int numOfExistingFiles = 1;
			directory = new File("C:\\PPCalendar");
			while (directory.mkdir() == false) {
				directory = new File("C:\\PPCalendar" + " (" + numOfExistingFiles + ")");
				numOfExistingFiles++;
			}
			this.setLatestDirectory();
		}
		taskFile = new TaskStorage(directory);
		return directory.getPath();
	}
	
	public boolean changeDirectory(String input_NewDirectory) {
		File newDirectory = new File(input_NewDirectory);
		int numOfExistingFiles = 1;
		//boolean initialNewDirectoryExists = false;
		
		try {
			if (newDirectory.equals(getLatestDirectory()) == true) {
				Display.setFeedBack(String.format(SAME_DIRECTORY_MESSAGE, directory.getPath()));
				Display.showFeedBack();
				return false;
			}
			else if (newDirectory.isDirectory() == true) {
				//initialNewDirectoryExists = true;
				while (newDirectory.isDirectory() == true) {
					newDirectory = new File(input_NewDirectory + " (" + numOfExistingFiles + ")");
					numOfExistingFiles++;
				}
				newDirectory.mkdir();
			}
			else if (newDirectory.mkdir() == false || input_NewDirectory.substring(1, 3).equals(":\\") == false) {
				Display.setFeedBack(String.format(IS_NOT_DIRECTORY_MESSAGE, input_NewDirectory));
				Display.showFeedBack();
				return false;
			}
			
			File newUndoneTasks = new File(newDirectory.getPath() + "\\Undone tasks.txt");
			File newDoneTasks = new File(newDirectory.getPath() + "\\Done tasks.txt");
			
			taskFile.getUndoneFile().renameTo(newUndoneTasks);
			taskFile.setUndoneFile(newUndoneTasks);
			taskFile.getDoneFile().renameTo(newDoneTasks);
			taskFile.setDoneFile(newDoneTasks);

			directory.delete();
			directory = newDirectory;
			this.setLatestDirectory();
			
			/*if (initialNewDirectoryExists == true) {
				Display.setFeedBack(String.format(NEW_DIRECTORY_MODIFIED_MESSAGE, directory.getPath()));
				Display.showFeedBack();
			}
			else {*/
				Display.setFeedBack(String.format(SUCCESSFUL_CHANGE_DIRECTORY_MESSAGE, directory.getPath()));
				Display.showFeedBack();
			//}

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
		if (taskFile.writeToFile(taskList, isDone)) {
			return true;
		}
		else {
			Display.setFeedBack(SAVE_UNSUCCESSFUL_MESSAGE);
			Display.showFeedBack();
			
			return false;
		}
	}

	public ArrayList<Task> loadTaskList(boolean isDone) {
		return taskFile.readFromFile(isDone);
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
		File checker = getLatestDirectory();
		
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
