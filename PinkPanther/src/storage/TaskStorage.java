package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.time.LocalDate;

import com.google.gson.Gson;

import common.Task;

public class TaskStorage {
	private Gson gson;
	private String taskList_Name;
	private File taskList_File;
	
	private static final String SUCCESSFUL_WRITE_TO_FILE_MESSAGE = "Saved as %1$s.";
	private static final String UNSUCCESSFUL_WRITE_TO_FILE_MESSAGE = "Save unsuccessful.";
	
	public TaskStorage() {
		gson = new Gson();
		this.setName("My New list.txt");
		taskList_File = new File(taskList_Name);
	}
	
	public TaskStorage(String input_TaskList_Name) {
		gson = new Gson();
		this.setName(input_TaskList_Name + ".txt");
		taskList_File = new File(taskList_Name);
	}
	
	public void setName(String input_TaskList_Name) {
		taskList_Name = new String(input_TaskList_Name + ".txt");
	}
	
	public String writeToFile(ArrayList<Task> input_FloatingList) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(taskList_File));
			
			for (Task task: input_FloatingList) {
				performWriting(bw, task);
			}
			bw.close();
			return String.format(SUCCESSFUL_WRITE_TO_FILE_MESSAGE, taskList_Name);
		}
		catch (IOException e) {
			return UNSUCCESSFUL_WRITE_TO_FILE_MESSAGE;
		}
	}

	public String writeToFile(TreeMap<LocalDate, ArrayList<Task>> input_ToDoList) {
		try{
			BufferedWriter bw = new BufferedWriter(new FileWriter(taskList_File));
			
			for(LocalDate date: input_ToDoList.keySet()) {
				for(Task task: input_ToDoList.get(date)) {
					performWriting(bw, task);
				}
			}
			bw.close();
			return String.format(SUCCESSFUL_WRITE_TO_FILE_MESSAGE, taskList_Name);
		}
		catch (IOException e) {
			return UNSUCCESSFUL_WRITE_TO_FILE_MESSAGE;
		}
	}
	
	private String performWriting(BufferedWriter bw, Task task) throws IOException {
		String json = gson.toJson(task) + "\n";
		bw.write(json);
		
		return json; //Primarily for testing purposes.
	}

	public ArrayList<Task> readFromFloatingFile() {
		ArrayList<Task> new_TaskList = new ArrayList<Task>();
		String newLine = "";
		try{
			BufferedReader br = new BufferedReader(new FileReader(taskList_File));
			while ((newLine = br.readLine()) != null) {
				new_TaskList.add(gson.fromJson(newLine, Task.class));
			}
			br.close();
		}
		catch (FileNotFoundException e) {
			//Do nothing since file does not exist
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return new_TaskList;
	}
	
	public TreeMap<LocalDate,ArrayList<Task>> readFromTodoFile(){
		TreeMap<LocalDate, ArrayList<Task>> new_TaskList = new TreeMap<LocalDate, ArrayList<Task>>();
		String newLine="";
		try{
			BufferedReader br = new BufferedReader(new FileReader(taskList_Name));
			while ((newLine=br.readLine()) != null) {
				Task task = gson.fromJson (newLine, Task.class);
				LocalDate date = task.getDate();
				if (!new_TaskList.containsKey(date)) {
					new_TaskList.put(date, new ArrayList<Task>());
				}
				new_TaskList.get(date).add(task);	
			}
			br.close();
		}
		catch (FileNotFoundException e) {
			//Do nothing since file does not exist
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return new_TaskList;
	}
}
