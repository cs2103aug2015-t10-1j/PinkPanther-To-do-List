/* @@author Brayton */

package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.logging.*;

import com.google.gson.Gson;

import common.Display;
import common.Task;


public class StorageControl {
	// Attributes
	private static File latestDirectoryTextFile = new File("Latest directory.txt");
	private File directory;
	private TaskStorage taskFile;
	private Gson gson = new Gson();
	private static Logger logger = Logger.getLogger("StorageControl");

	// Feedback Messages
	private static final String MESSAGE_FEEDBACK_SUCCESSFUL_DIRECTORY_CHANGE = "Directory changed to: \"%1$s\"";
	private static final String MESSAGE_FEEDBACK_SECURITY_EXCEPTION = "Security manager exists and denies write access to: \"%1$s\"";
	private static final String MESSAGE_FEEDBACK_NO_INPUT_DIRECTORY = "No directory path was entered";
	private static final String MESSAGE_FEEDBACK_IS_NOT_DIRECTORY = "\"%1$s\" is not a directory";
	private static final String MESSAGE_FEEDBACK_SAVE_UNSUCCESSFUL = "Save unsuccessful";
	private static final String MESSAGE_FEEDBACK_SAME_DIRECTORY = "\"%1$s\" is the current directory, directory remains unchanged";
	
	// Logging Messages
	private static final String MESSAGE_LOG_WROTE_LATEST_DIRECTORY = "Wrote latest directory from \"Latest Directory.txt\"";
	private static final String MESSAGE_LOG_WRITING_LATEST_DIRECTORY = "Writing latest directory to \"Latest Directory.txt\"";
	private static final String MESSAGE_LOG_INPUT_OUTPUT_EXCEPTION = "Input ouput exception encountered";
	private static final String MESSAGE_LOG_FILE_NOT_FOUND_EXCEPTION = "File not found exception encountered";
	private static final String MESSAGE_LOG_READ_LATEST_DIRECTORY = "Read latest directory from \"Latest Directory.txt\"";
	private static final String MESSAGE_LOG_READING_LATEST_DIRECTORY = "Reading latest directory from \"Latest Directory.txt\"";
	private static final String MESSAGE_LOG_LOADING_TEXTFILES = "Loading textfiles";
	private static final String MESSAGE_LOG_UNSUCCESSFULLY_SAVED_TEXTFILES = "Textfile unsuccessfully saved";
	private static final String MESSAGE_LOG_SUCCESSFULLY_SAVED_TEXTFILES = "Textfile successfully saved";
	private static final String MESSAGE_LOG_SAVING_TEXTFILES = "Saving textfiles";
	private static final String MESSAGE_LOG_NULL_POINTER_EXCEPTION = "Null pointer exception encountered";
	private static final String MESSAGE_LOG_SECURITY_EXCEPTION = "Security exception encountered";
	private static final String MESSAGE_LOG_END_CHANGE_DIRECTORY = "End of directory change";
	private static final String MESSAGE_LOG_REPLACED_CURRENT_DIRECTORY = "Current directory replaced";
	private static final String MESSAGE_LOG_REPLACING_CURRENT_DIRECTORY = "Replacing current directory";
	private static final String MESSAGE_LOG_TEXTFILES_MOVED_FROM_PREVIOUS = "Textfiles from previous directory moved";
	private static final String MESSAGE_LOG_MOVING_TEXTFILES_FROM_PREVIOUS = "Moving textfiles from previous directory";
	private static final String MESSAGE_LOG_INVALID_TARGET_DIRECTORY = "Invalid target directory";
	private static final String MESSAGE_LOG_DIRECTORY_EXISTS_CREATING_ALTERNATIVE = "Target directory already exists, creating alternative filepath";
	private static final String MESSAGE_LOG_TARGET_DIRECTORY_SAME_CURRENT_DIRECTORY = "Target directory is the same as the current directory";
	private static final String MESSAGE_LOG_START_CHANGE_DIRECTORY = "Start of directory change";
	private static final String MESSAGE_LOG_END_SET_ENVIRONMENT = "TaskStorage instantiated, end of set environment";
	private static final String MESSAGE_LOG_LATEST_DIRECTORY_SET = "Set the latest directory";
	private static final String MESSAGE_LOG_DEFAULT_DIRECTORY_CREATED = "Default directory C:\\PPCalendar created";
	private static final String MESSAGE_LOG_NO_OR_INVALID_LATEST_DIRECTORY = "No latest directory OR invalid latest directory obtained";
	private static final String MESSAGE_LOG_START_SET_ENVIRONMENT = "Obtained the latest directory, start of set environment";
	
	// Assertion Messages
	private static final String MESSAGE_ASSERTION_NULL_PARAMETER = "Logic error. Null input passed in as parameter!";
	
	public StorageControl() {
	}
	
	/**
	 * Sets and returns the filepath of the directory in which the files are to be saved in.
	 * A default filepath is set and returned if no directory is specified in "Latest Directory.txt".
	 * 
	 * @param Void.
	 * @return	String of the current directory's filepath
	 */
	public String setEnvironment() {
		directory = getLatestDirectory();
		logger.log(Level.FINE, MESSAGE_LOG_START_SET_ENVIRONMENT);
		
		if (directory == null || directory.isDirectory() == false) {
			logger.log(Level.WARNING, MESSAGE_LOG_NO_OR_INVALID_LATEST_DIRECTORY);
			int numOfExistingFiles = 1;
			directory = new File("C:\\PPCalendar");
			logger.log(Level.INFO, MESSAGE_LOG_DEFAULT_DIRECTORY_CREATED);
			while (directory.mkdir() == false) {
				directory = new File("C:\\PPCalendar" + " (" + numOfExistingFiles + ")");
				numOfExistingFiles++;
			}
			this.setLatestDirectory();
			logger.log(Level.FINE, MESSAGE_LOG_LATEST_DIRECTORY_SET);
		}
		taskFile = new TaskStorage(directory);
		logger.log(Level.FINE, MESSAGE_LOG_END_SET_ENVIRONMENT);
		return directory.getPath();
	}
	
	/**
	 * Returns a boolean value depending on the success of the save directory change.
	 * This method changes the directory in which the task list textfiles are to be saved.
	 * All the textfiles from the previous directory are moved to the new directory if successful.
	 * 
	 * @param String of the new directory's filepath.
	 * @return Boolean variable indicating the success of the save directory change.
	 */
	public boolean changeDirectory(String input_NewDirectory) {
		assert input_NewDirectory != null : MESSAGE_ASSERTION_NULL_PARAMETER;
		File newDirectory = new File(input_NewDirectory);
		int numOfExistingFiles = 1;
		logger.log(Level.FINE, MESSAGE_LOG_START_CHANGE_DIRECTORY);
		
		try {
			if (newDirectory.equals(getLatestDirectory()) == true) {
				logger.log(Level.WARNING, MESSAGE_LOG_TARGET_DIRECTORY_SAME_CURRENT_DIRECTORY);
				Display.setFeedBack(String.format(MESSAGE_FEEDBACK_SAME_DIRECTORY, directory.getPath()));
				return false;
			}
			else if (newDirectory.isDirectory() == true) {
				logger.log(Level.WARNING, MESSAGE_LOG_DIRECTORY_EXISTS_CREATING_ALTERNATIVE);
				while (newDirectory.isDirectory() == true) {
					newDirectory = new File(input_NewDirectory + " (" + numOfExistingFiles + ")");
					numOfExistingFiles++;
				}
				newDirectory.mkdir();
			}
			else if (input_NewDirectory.substring(1, 3).equals(":\\") == false || newDirectory.mkdir() == false) {
				logger.log(Level.WARNING, MESSAGE_LOG_INVALID_TARGET_DIRECTORY);
				Display.setFeedBack(String.format(MESSAGE_FEEDBACK_IS_NOT_DIRECTORY, input_NewDirectory));
				return false;
			}
			
			logger.log(Level.FINE, MESSAGE_LOG_MOVING_TEXTFILES_FROM_PREVIOUS);
			taskFile.moveFiles(newDirectory);
			logger.log(Level.FINE, MESSAGE_LOG_TEXTFILES_MOVED_FROM_PREVIOUS);
			
			logger.log(Level.FINE, MESSAGE_LOG_REPLACING_CURRENT_DIRECTORY);
			directory.delete();
			directory = newDirectory;
			logger.log(Level.FINE, MESSAGE_LOG_REPLACED_CURRENT_DIRECTORY);
			this.setLatestDirectory();
			
			Display.setFeedBack(String.format(MESSAGE_FEEDBACK_SUCCESSFUL_DIRECTORY_CHANGE, directory.getPath()));
			
			logger.log(Level.FINE, MESSAGE_LOG_END_CHANGE_DIRECTORY);
			return true;
		}
		catch (SecurityException e) {
			logger.log(Level.WARNING, MESSAGE_LOG_SECURITY_EXCEPTION);
			Display.setFeedBack(String.format(MESSAGE_FEEDBACK_SECURITY_EXCEPTION, input_NewDirectory));
			return false;
		}
		catch (NullPointerException e) {
			logger.log(Level.WARNING, MESSAGE_LOG_NULL_POINTER_EXCEPTION);
			Display.setFeedBack(MESSAGE_FEEDBACK_NO_INPUT_DIRECTORY);
			return false;
		}
	}

	public boolean save(ArrayList<Task> taskList, boolean isDone) {
		assert taskList != null : MESSAGE_ASSERTION_NULL_PARAMETER;
		logger.log(Level.FINE, MESSAGE_LOG_SAVING_TEXTFILES);
		if (taskFile.writeToFile(taskList, isDone)) {
			logger.log(Level.FINE, MESSAGE_LOG_SUCCESSFULLY_SAVED_TEXTFILES);
			return true;
		}
		else {
			logger.log(Level.WARNING, MESSAGE_LOG_UNSUCCESSFULLY_SAVED_TEXTFILES);
			Display.setFeedBack(MESSAGE_FEEDBACK_SAVE_UNSUCCESSFUL);
			
			return false;
		}
	}

	public ArrayList<Task> loadTaskList(boolean isDone) {
		logger.log(Level.FINE, MESSAGE_LOG_LOADING_TEXTFILES);
		return taskFile.readFromFile(isDone);
	}

	private File getLatestDirectory() {
		String newLine;
		File latestDirectory = null;
		try{
			logger.log(Level.FINER, MESSAGE_LOG_READING_LATEST_DIRECTORY);
			BufferedReader br = new BufferedReader(new FileReader(latestDirectoryTextFile));
			newLine = br.readLine();
			latestDirectory = gson.fromJson(newLine, File.class);
			logger.log(Level.FINER, MESSAGE_LOG_READ_LATEST_DIRECTORY);
			br.close();
		}
		catch (FileNotFoundException e) {
			logger.log(Level.WARNING, MESSAGE_LOG_FILE_NOT_FOUND_EXCEPTION);
			return null;
		}
		catch (IOException e) {
			logger.log(Level.WARNING, MESSAGE_LOG_INPUT_OUTPUT_EXCEPTION);
			return null;
		}
		return latestDirectory;
	}
	
	/**
	 * Returns a boolean value depending on the success of setting the latest directory.
	 * This method writes the latest directory in which the textfiles were saved in, into "Latest directory.txt".
	 * 
	 * @param Void.
	 * @return	Boolean value indicating the success of setting the latest directory.
	 */
	private boolean setLatestDirectory() {
		try {
			logger.log(Level.FINER, MESSAGE_LOG_WRITING_LATEST_DIRECTORY);
			BufferedWriter bw = new BufferedWriter(new FileWriter(latestDirectoryTextFile));
			String json = gson.toJson(directory);
			bw.write(json);
			logger.log(Level.FINER, MESSAGE_LOG_WROTE_LATEST_DIRECTORY);
			bw.close();
			
			return true;
		}
		catch (IOException e) {
			logger.log(Level.WARNING, MESSAGE_LOG_INPUT_OUTPUT_EXCEPTION);
			return false;
		}
	}
	
	//For testing only.
	public void deleteAllFiles() {
		taskFile.getDoneFile().delete();
		taskFile.getUndoneFile().delete();
		getLatestDirectory().delete();
		latestDirectoryTextFile.delete();
	}
	
	// For testing only.
	public File getLatestDirectoryTextFile() {
		return latestDirectoryTextFile;
	}
}
