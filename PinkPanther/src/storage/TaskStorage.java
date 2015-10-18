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
	private File taskFile_undone;
	private File taskFile_done;
	private Gson gson;
	
	protected TaskStorage(File directory) {
		taskFile_undone = new File(directory, "undone tasks.txt");
		taskFile_done = new File(directory, "done tasks.txt");
		gson = new Gson();
	}
	
	protected boolean writeToFile(ArrayList<Task> input_FloatingList, boolean isDone) {
		try {
			BufferedWriter bw;
			if (isDone) {
				bw = new BufferedWriter(new FileWriter(taskFile_done));
			}
			else {
				bw = new BufferedWriter(new FileWriter(taskFile_undone));
			}
			for (Task task: input_FloatingList) {
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
		try{
			BufferedReader br;
			if (isDone) {
				br = new BufferedReader(new FileReader(taskFile_done));
			}
			else {
				br = new BufferedReader(new FileReader(taskFile_undone));
			}
			while ((newLine = br.readLine()) != null) {
				new_TaskList.add(gson.fromJson(newLine, Task.class));
			}
			br.close();
		}
		catch (FileNotFoundException e) {
			//return null;
		}
		catch (IOException e) {
			e.printStackTrace();
			//return null;
		}
		return new_TaskList;
	}
	
	private String performWriting(BufferedWriter bw, Task task) throws IOException {
		String json = gson.toJson(task) + "\n";
		bw.write(json);
		
		return json; //Primarily for testing purposes.
	}
	
	protected File getUndoneFile() {
		return taskFile_undone;
	}
	
	protected File getDoneFile() {
		return taskFile_done;
	}
	
	protected void setUndoneFile(File taskFile) {
		taskFile_undone = taskFile;
	}
	
	protected void setDoneFile(File taskFile) {
		taskFile_done = taskFile;
	}
}
