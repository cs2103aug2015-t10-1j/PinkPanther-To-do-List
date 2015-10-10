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
	private File latestDirectoryTextFile;
	private File directory;
	private FloatingStorage floating_File;
	private ToDoStorage toDo_File;
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
		latestDirectoryTextFile = new File("latestDirectory.txt");
		directory = this.getLatestDirectory();

		if (directory == null || !directory.isDirectory()) {
			directory = new File("C:\\PPCalendar");
			this.createDirectory();
		}
		floating_File = new FloatingStorage(directory);
		toDo_File = new ToDoStorage(directory);

		this.setLatestDirectory();
	}

	private StorageControl(String input_FilePath) {
		gson = new Gson();

		directory = new File(input_FilePath);
		this.createDirectory();
		this.setLatestDirectory();

		floating_File = new FloatingStorage(directory);
		toDo_File = new ToDoStorage(directory);
	}

	public boolean createDirectory() {
		try {
			if (!directory.exists()) {
				Display.setFeedBack(String.format(SUCCESSFUL_CREATE_DIRECTORY_MESSAGE, directory.getAbsolutePath()));
				Display.showFeedBack();
				return directory.mkdir();
			}
			else {
				Display.setFeedBack(String.format(DIRECTORY_ALREADY_EXISTS_MESSAGE, directory.getAbsolutePath()));
				Display.showFeedBack();
				return false;
			}
		}
		catch (SecurityException e) {
			Display.setFeedBack(String.format(SECURITY_EXCEPTION_MESSAGE, directory.getAbsolutePath()));
			Display.showFeedBack();
			return false;
		}
	}

	public boolean changeDirectory(String input_NewDirectory) {
		File newDirectory = new File(input_NewDirectory);
		File newFloating = new File(input_NewDirectory);
		File newToDo = new File(input_NewDirectory);
		try {
			if (directory.isDirectory()) {
				Display.setFeedBack(String.format(SUCCESSFUL_CHANGE_DIRECTORY_MESSAGE, directory.getPath()));
				Display.showFeedBack();
				if (directory.renameTo(newDirectory)) {
					return floating_File.getFloatingFile().renameTo(newFloating) && toDo_File.getToDoFile().renameTo(newToDo);
				}
				else {
					return false;
				}
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

	public boolean save(ArrayList<Task> taskList) {
		if (floating_File.writeToFile(taskList)) {
			return true;
		}
		else {
			Display.setFeedBack(SAVE_UNSUCCESSFUL_MESSAGE);
			Display.showFeedBack();
			return false;
		}
	}

	public boolean save(SortedMap<LocalDate, ArrayList<Task>> taskList) {
		if (toDo_File.writeToFile(taskList)) {
			return true;
		}
		else {
			Display.setFeedBack(SAVE_UNSUCCESSFUL_MESSAGE);
			Display.showFeedBack();
			return false;
		}
	}

	public ArrayList<Task> loadFloating() {
		return floating_File.readFromFile();
	}
	
	public SortedMap<LocalDate, ArrayList<Task>> loadToDo() {
		return toDo_File.readFromFile();
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
