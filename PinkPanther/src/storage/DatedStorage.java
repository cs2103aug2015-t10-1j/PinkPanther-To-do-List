package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.TreeMap;
import java.util.ArrayList;
import java.util.SortedMap;

import com.google.gson.Gson;

import common.Task;

import java.time.LocalDate;

public class DatedStorage {
	//Attributes
	private File datedFile_Done;
	private File datedFile_Undone;
	private Gson gson;
	
	protected DatedStorage(File directory) {
		datedFile_Undone = new File(directory, "Undone Dated.txt");
		datedFile_Done = new File(directory, "Done Dated.txt");
		gson = new Gson();
	}
	
	protected boolean writeToFile(SortedMap<LocalDate, ArrayList<Task>> input_ToDoList, boolean isDone) {
		try{
			BufferedWriter bw;
			if (isDone) {
				bw = new BufferedWriter(new FileWriter(datedFile_Done));
			}
			else {
				bw = new BufferedWriter(new FileWriter(datedFile_Undone));
			}
			
			for(LocalDate date: input_ToDoList.keySet()) {
				for(Task task: input_ToDoList.get(date)) {
					performWriting(bw, task);
				}
			}
			bw.close();
			return true;
		}
		catch (IOException e) {
			return false;
		}
	}
	
	private String performWriting(BufferedWriter bw, Task task) throws IOException {
		String json = gson.toJson(task) + "\n";
		bw.write(json);
		
		return json; //Primarily for testing purposes.
	}
	
	protected SortedMap<LocalDate,ArrayList<Task>> readFromFile(boolean isDone){
		SortedMap<LocalDate, ArrayList<Task>> new_TaskList = new TreeMap<LocalDate, ArrayList<Task>>();
		String newLine="";
		try{
			BufferedReader br;
			if (isDone) {
				br = new BufferedReader(new FileReader(datedFile_Done));
			}
			else {
				br = new BufferedReader(new FileReader(datedFile_Undone));
			}
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
			//return null;
		}
		catch (IOException e) {
			e.printStackTrace();
			//return null;
		}
		return new_TaskList;
	}
	
	protected File getUndoneDatedFile() {
		return datedFile_Undone;
	}
	
	protected File getDoneDatedFile() {
		return datedFile_Done;
	}
	
	protected void setUndoneDatedFile(File newDated) {
		datedFile_Undone = newDated;
	}
	
	protected void setDoneDatedFile(File newDated) {
		datedFile_Done = newDated;
	}
}
