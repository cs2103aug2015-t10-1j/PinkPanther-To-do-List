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

import common.Task;

public class TaskStorage {
	// Attributes
	private File currentDirectory;
	private ArrayList<File> taskLists;
	private static Logger logger = Logger.getLogger("TaskStorage");
	
	// Logging Messages
	private static final String MESSAGE_LOG_START_SETLATESTFILES = "Obtained files in current directory, start setting latest files";
	private static final String MESSAGE_LOG_NO_FILES_FOUND_CREATING_DEFAULT = "No files were found in current directory, creating required default textfiles";
	private static final String MESSAGE_LOG_END_SETLATESTFILES = "End setting latest files";
	private static final String MESSAGE_LOG_START_ARRANGETASKLISTS = "Start arranging task lists";
	private static final String MESSAGE_LOG_BOTH_TEMPLISTS_EMPTY = "Both templists are empty";
	private static final String MESSAGE_LOG_TEMPLIST1_EMPTY = "Only tempList1 is empty";
	private static final String MESSAGE_LOG_TEMPLIST2_EMPTY = "Only tempList2 is empty";
	private static final String MESSAGE_LOG_END_ARRANGETASKLISTS = "End arranging task lists";
	private static final String MESSAGE_LOG_START_WRITETOFILE = "Start writing to file";
	private static final String MESSAGE_LOG_WRITING_TO_DONE_TEXTFILE = "Writing to done textfile";
	private static final String MESSAGE_LOG_WRITING_TO_UNDONE_TEXTFILE = "Writing to undone textfile";
	private static final String MESSAGE_LOG_END_WRITETOFILE = "End writing to file";
	private static final String MESSAGE_LOG_START_READFROMFILE = "Start reading from file";
	private static final String MESSAGE_LOG_READING_FROM_DONE_TEXTFILE = "Reading from done textfile";
	private static final String MESSAGE_LOG_READING_FROM_UNDONE_TEXTFILE = "Reading from undone textfile";
	private static final String MESSAGE_LOG_FILE_NOT_FOUND_EXCEPTION = "File not found exception encountered";
	private static final String MESSAGE_LOG_INPUT_OUTPUT_EXCEPTION = "Input output exception encountered";
	private static final String MESSAGE_LOG_END_READFROMFILE = "End reading from file";
	private static final String MESSAGE_LOG_START_MOVEFILES = "Start moving files";
	private static final String MESSAGE_LOG_RENAMING_FILES = "Renaming files";
	private static final String MESSAGE_LOG_END_MOVEFILES = "End moving files";
	
	// Assertion Messages
	private static final String MESSAGE_ASSERTION_NULL_PARAMETER = "StorageControl error. Null input passed in as parameter!";
	
	protected TaskStorage(File directory) {
		taskLists = new ArrayList<File>();
		currentDirectory = directory;
		setLatestFiles(currentDirectory);
	}
	
	/**
	 * Sets the current textfiles being tracked in the data structure.
	 * Default textfiles are created and tracked if no existing textfiles were found in the current directory.
	 * 
	 * @param The current directory in which the textfiles are saved.
	 * @return	Void.
	 */
	private void setLatestFiles(File currentDirectory) {
		assert currentDirectory != null : MESSAGE_ASSERTION_NULL_PARAMETER;
		File[] listOfFiles = currentDirectory.listFiles();
		logger.log(Level.FINE, MESSAGE_LOG_START_SETLATESTFILES);
		
		if (listOfFiles.length == 0) {
			logger.log(Level.INFO, MESSAGE_LOG_NO_FILES_FOUND_CREATING_DEFAULT);
			taskLists.add(new File(currentDirectory, "Undone tasks.txt"));
			taskLists.add(new File(currentDirectory, "Done tasks.txt"));
			
			try {
				taskLists.get(0).createNewFile();
				taskLists.get(1).createNewFile();
			}
			catch (IOException e) {
				logger.log(Level.WARNING, MESSAGE_LOG_INPUT_OUTPUT_EXCEPTION);
				e.printStackTrace();
			}
			
			return;
		}
		
		taskLists.clear();
		taskLists.add(listOfFiles[0]);
		taskLists.add(listOfFiles[1]);
		
		arrangeTaskLists();
		logger.log(Level.FINE, MESSAGE_LOG_END_SETLATESTFILES);
	}
	
	/**
	 * Arranges the files saved in ArrayList<File> taskLists to ensure that the undone file has index 0
	 * and the done file has index 1.
	 * 
	 * @param Void.
	 * @return	Void.
	 */
	private void arrangeTaskLists() {
		logger.log(Level.FINE, MESSAGE_LOG_START_ARRANGETASKLISTS);
		ArrayList<Task> tempList1 = new ArrayList<Task>();
		ArrayList<Task> tempList2 = new ArrayList<Task>();
		tempList1 = readFromFile(false);
		tempList2 = readFromFile(true);
		
		if (tempList1.isEmpty() == true && tempList2.isEmpty() == true) {
			logger.log(Level.FINE, MESSAGE_LOG_BOTH_TEMPLISTS_EMPTY);
			if (taskLists.get(0).getName().equalsIgnoreCase("Done tasks.txt")) {
				taskLists.add(taskLists.get(0));
				taskLists.set(0, taskLists.get(1));
				taskLists.set(1, taskLists.get(2));
				taskLists.remove(2);
			}
			else {
				logger.log(Level.FINE, MESSAGE_LOG_END_ARRANGETASKLISTS);
				return;
			}
		}
		else if (tempList1.isEmpty() == true) {
			logger.log(Level.FINE, MESSAGE_LOG_TEMPLIST1_EMPTY);
			if (tempList2.get(0).getDoneStatus() == true) {
				logger.log(Level.FINE, MESSAGE_LOG_END_ARRANGETASKLISTS);
				return;
			}
			else {
				taskLists.add(taskLists.get(0));
				taskLists.set(0, taskLists.get(1));
				taskLists.set(1, taskLists.get(2));
				taskLists.remove(2);
			}
		}
		else {
			logger.log(Level.FINE, MESSAGE_LOG_TEMPLIST2_EMPTY);
			if (tempList1.get(0).getDoneStatus() == true) {
				taskLists.add(taskLists.get(0));
				taskLists.set(0, taskLists.get(1));
				taskLists.set(1, taskLists.get(2));
				taskLists.remove(2);
			}
			else {
				logger.log(Level.FINE, MESSAGE_LOG_END_ARRANGETASKLISTS);
				return;
			}
		}
		logger.log(Level.FINE, MESSAGE_LOG_END_ARRANGETASKLISTS);
	}
	
	protected boolean writeToFile(ArrayList<Task> taskList, boolean isDone) {
		assert taskList != null : MESSAGE_ASSERTION_NULL_PARAMETER;
		logger.log(Level.FINER, MESSAGE_LOG_START_WRITETOFILE);
		setLatestFiles(currentDirectory);
		try {
			BufferedWriter bw;
			if (isDone == true) {
				logger.log(Level.FINER, MESSAGE_LOG_WRITING_TO_DONE_TEXTFILE);
				bw = new BufferedWriter(new FileWriter(taskLists.get(1)/*taskFile_Done*/));
			}
			else {
				logger.log(Level.FINER, MESSAGE_LOG_WRITING_TO_UNDONE_TEXTFILE);
				bw = new BufferedWriter(new FileWriter(taskLists.get(0)/*taskFile_Undone*/));
			}
			for (Task task: taskList) {
				performWriting(bw, task);
			}
			bw.close();
			
			logger.log(Level.FINER, MESSAGE_LOG_END_WRITETOFILE);
			return true;
		}
		catch (IOException e) {
			logger.log(Level.WARNING, MESSAGE_LOG_INPUT_OUTPUT_EXCEPTION);
			e.printStackTrace();
			return false;
		}
	}
	
	protected ArrayList<Task> readFromFile(boolean isDone) {
		logger.log(Level.FINER, MESSAGE_LOG_START_READFROMFILE);
		ArrayList<Task> new_TaskList = new ArrayList<Task>();
		String newLine = "";
		Gson gsonRead = new Gson();
		try{
			BufferedReader br;
			if (isDone == true) {
				logger.log(Level.FINER, MESSAGE_LOG_READING_FROM_DONE_TEXTFILE);
				br = new BufferedReader(new FileReader(taskLists.get(1)));
			}
			else {
				logger.log(Level.FINER, MESSAGE_LOG_READING_FROM_UNDONE_TEXTFILE);
				br = new BufferedReader(new FileReader(taskLists.get(0)));
				
			}
			while ((newLine = br.readLine()) != null) {
				new_TaskList.add(gsonRead.fromJson(newLine, Task.class));
			}
			br.close();
		}
		catch (FileNotFoundException e) {
			logger.log(Level.WARNING, MESSAGE_LOG_FILE_NOT_FOUND_EXCEPTION);
			return new ArrayList<Task>();
		}
		catch (IOException e) {
			logger.log(Level.WARNING, MESSAGE_LOG_INPUT_OUTPUT_EXCEPTION);
			e.printStackTrace();
			return null;
		}
		
		logger.log(Level.FINER, MESSAGE_LOG_END_READFROMFILE);
		return new_TaskList;
	}
	
	private String performWriting(BufferedWriter bw, Task task) throws IOException {
		Gson gsonWrite = new Gson();
		String json = gsonWrite.toJson(task) + "\n";
		bw.write(json);
		
		return json;
	}
	
	/**
	 * Moves the textfiles from the current directory to the new directory specified by newDirectory.
	 * 
	 * 
	 * @param The new directory in which the textfiles are to be saved.
	 * @return	Void.
	 */
	protected void moveFiles(File newDirectory) {
		assert newDirectory != null : MESSAGE_ASSERTION_NULL_PARAMETER;
		logger.log(Level.FINER, MESSAGE_LOG_START_MOVEFILES);
		setLatestFiles(currentDirectory);
		currentDirectory = newDirectory;
		
		File newUndoneTasks = new File(newDirectory.getPath() + "\\" + taskLists.get(0).getName());
		File newDoneTasks = new File(newDirectory.getPath() + "\\" + taskLists.get(1).getName());
		
		logger.log(Level.FINER, MESSAGE_LOG_RENAMING_FILES);
		getUndoneFile().renameTo(newUndoneTasks);
		getDoneFile().renameTo(newDoneTasks);
		logger.log(Level.FINER, MESSAGE_LOG_END_MOVEFILES);
	}
	
	protected File getUndoneFile() {
		return taskLists.get(0);
	}
	
	protected File getDoneFile() {
		return taskLists.get(1);
	}
	
	protected void setUndoneFile(File taskFile) {
		taskLists.set(0, taskFile);
	}
	
	protected void setDoneFile(File taskFile) {
		taskLists.set(1, taskFile);
	}
}
