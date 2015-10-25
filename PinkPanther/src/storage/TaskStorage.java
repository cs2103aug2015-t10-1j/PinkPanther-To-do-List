package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;

import com.google.gson.Gson;

import common.Task;

public class TaskStorage {
	//Attributes
	//private File taskFile_Undone;
	//private File taskFile_Done;
	//private File latestTaskListsTextFile = new File("Latest Tasklists.txt");
	private File currentDirectory;
	private ArrayList<File> taskLists;
	
	protected TaskStorage(File directory) {
		//taskFile_Undone = new File(directory, "Undone tasks.txt");
		//taskFile_Done = new File(directory, "Done tasks.txt");
		taskLists = new ArrayList<File>();
		currentDirectory = directory;
		setLatestFiles(currentDirectory);
	}
	
	/*private boolean getLatestFiles() {
		File[] listOfFiles = currentDirectory.listFiles();
		assert(listOfFiles.length == 2);
		
		
		
		/*String newLine;
		try{
			BufferedReader br = new BufferedReader(new FileReader(latestTaskListsTextFile));
			while ((newLine = br.readLine()) != null) {
				taskLists.add(gson.fromJson(newLine, File.class));
			}	
			br.close();
			arrangeTaskLists();
		}
		catch (FileNotFoundException e) {
			return false;
		}
		catch (IOException e) {
			return false;
		}
		if (taskLists.isEmpty() == true) {
			return false;
		}
		else {
			return true;
		}
	}*/
	
	private void setLatestFiles(File currentDirectory) {
		File[] listOfFiles = currentDirectory.listFiles();
		//System.out.println("listOfFiles size = " + listOfFiles.length);
		if (listOfFiles.length == 0) {
			taskLists.add(new File(currentDirectory, "Undone tasks.txt"));
			taskLists.add(new File(currentDirectory, "Done tasks.txt"));
			
			try {
				taskLists.get(0).createNewFile();
				taskLists.get(1).createNewFile();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			
			return;
		}
		/*else if (listOfFiles.length == 1) {
			if (checkMissingFile(listOfFiles) == true) {
				taskLists.add(new File(currentDirectory, "Undone tasks.txt"));
			}
			else {
				taskLists.add(new File(currentDirectory, "Done tasks.txt"));
			}
			
			return;
		}*/
		//assert(listOfFiles.length == 2);
		
		taskLists.clear();
		taskLists.add(listOfFiles[0]);
		taskLists.add(listOfFiles[1]);
		//System.out.println("taskLists' size = " + taskLists.size());
		arrangeTaskLists();
		/*try {
			BufferedWriter bw;
			bw = new BufferedWriter(new FileWriter(latestTaskListsTextFile));
			
			for (File file: listOfFiles) {
				performWriting(bw, file);
			}
			bw.close();
			return true;
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}*/
	}
	
	/*private boolean checkMissingFile(File[] listOfFiles) {
		ArrayList<Task> tempList = unpackTaskList(listOfFiles, 0);
		if (tempList.isEmpty() == true) {
			return false;
		}
		
		if (tempList.get(0).getDoneStatus() == true) {
			return true;
		}
		else {
			return false;
		}
	}*/

	/*private ArrayList<Task> unpackTaskList(File[] listOfFiles, int index) {
		ArrayList<Task> tempList = new ArrayList<Task>();
		String newLine = "";
		try{
			BufferedReader br;
			br = new BufferedReader(new FileReader(listOfFiles[index]));
			
			while ((newLine = br.readLine()) != null) {
				tempList.add(gson.fromJson(newLine, Task.class));
			}
			br.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return tempList;
	}
	
	private ArrayList<Task> unpackTaskList(int index) {
		ArrayList<Task> tempList = new ArrayList<Task>();
		String newLine = "";
		try{
			BufferedReader br;
			br = new BufferedReader(new FileReader(taskLists.get(index)));
			
			newLine = br.readLine();
			while (newLine != null)  {
				tempList.add(gson.fromJson(newLine, Task.class));
				newLine = br.readLine();
			}
			br.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return tempList;
	}*/
	
	private void arrangeTaskLists() {
		ArrayList<Task> tempList1 = new ArrayList<Task>();
		ArrayList<Task> tempList2 = new ArrayList<Task>();
		tempList1 = readFromFile(false);
		tempList2 = readFromFile(true);
		
		if (tempList1.isEmpty() == true && tempList2.isEmpty() == true) {
			if (taskLists.get(0).getName().equalsIgnoreCase("Done tasks.txt")) {
				taskLists.add(taskLists.get(0));
				taskLists.set(0, taskLists.get(1));
				taskLists.set(1, taskLists.get(2));
				taskLists.remove(2);
			}
			else {
				return;
			}
		}
		else if (tempList1.isEmpty() == true) {
			if (tempList2.get(0).getDoneStatus() == true) {
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
			if (tempList1.get(0).getDoneStatus() == true) {
				taskLists.add(taskLists.get(0));
				taskLists.set(0, taskLists.get(1));
				taskLists.set(1, taskLists.get(2));
				taskLists.remove(2);
			}
			else {
				return;
			}
		}
	}
	
	protected boolean writeToFile(ArrayList<Task> taskList, boolean isDone) {
		setLatestFiles(currentDirectory);
		try {
			BufferedWriter bw;
			if (isDone == true) {
				bw = new BufferedWriter(new FileWriter(taskLists.get(1)/*taskFile_Done*/));
			}
			else {
				bw = new BufferedWriter(new FileWriter(taskLists.get(0)/*taskFile_Undone*/));
			}
			for (Task task: taskList) {
				performWriting(bw, task);
			}
			bw.close();
			
			return true;
		}
		catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	protected ArrayList<Task> readFromFile(boolean isDone) {
		ArrayList<Task> new_TaskList = new ArrayList<Task>();
		String newLine = "";
		Gson gsonRead = new Gson();
		try{
			BufferedReader br;
			if (isDone == true) {
				br = new BufferedReader(new FileReader(taskLists.get(1)/*taskFile_Done*/));
				//System.out.println("taskLists.get(1) = " + taskLists.get(1).getPath());
			}
			else {
				br = new BufferedReader(new FileReader(taskLists.get(0)/*taskFile_Undone*/));
				//System.out.println("taskLists.get(0) = " + taskLists.get(0).getPath());
			}
			while ((newLine = br.readLine()) != null) {
				//System.out.println(newLine);
				new_TaskList.add(gsonRead.fromJson(newLine, Task.class));
			}
			br.close();
		}
		catch (FileNotFoundException e) {
			return new ArrayList<Task>();
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return new_TaskList;
	}
	
	private String performWriting(BufferedWriter bw, Task task) throws IOException {
		Gson gsonWrite = new Gson();
		String json = gsonWrite.toJson(task) + "\n";
		bw.write(json);
		
		return json; //Primarily for testing purposes.
	}
	
	/*private String performWriting(BufferedWriter bw, File file) throws IOException {
		String json = gson.toJson(file) + "\n";
		bw.write(json);
		
		return json; //Primarily for testing purposes.
	}*/
	
	protected void moveFiles(File newDirectory) {
		setLatestFiles(currentDirectory);
		currentDirectory = newDirectory;
		//File newUndoneTasks = new File(newDirectory.getPath() + "\\Undone tasks.txt");
		//File newDoneTasks = new File(newDirectory.getPath() + "\\Done tasks.txt");
		
		File newUndoneTasks = new File(newDirectory.getPath() + "\\" + taskLists.get(0).getName());
		File newDoneTasks = new File(newDirectory.getPath() + "\\" + taskLists.get(1).getName());
		//System.out.println("Undone TaskList = " + newUndoneTasks.getPath());
		//System.out.println("Done TaskList = " + newDoneTasks.getPath());

		getUndoneFile().renameTo(newUndoneTasks);
		//setUndoneFile(newUndoneTasks);
		getDoneFile().renameTo(newDoneTasks);
		//setDoneFile(newDoneTasks);
		//System.out.println("Undone TaskList = " + taskLists.get(0).getPath());
		//System.out.println("Done TaskList = " + taskLists.get(1).getPath());
	}
	
	protected File getUndoneFile() {
		//return taskFile_Undone;
		//setLatestFiles(currentDirectory);
		return taskLists.get(0);
	}
	
	protected File getDoneFile() {
		//return taskFile_Done;
		//setLatestFiles(currentDirectory);
		return taskLists.get(1);
	}
	
	protected void setUndoneFile(File taskFile) {
		//taskFile_Undone = taskFile;
		taskLists.set(0, taskFile);
	}
	
	protected void setDoneFile(File taskFile) {
		//taskFile_Done = taskFile;
		taskLists.set(1, taskFile);
	}
}
