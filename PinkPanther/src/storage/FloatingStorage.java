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

public class FloatingStorage {
	//Attributes
	private File floatingFile_Undone;
	private File floatingFile_Done;
	private Gson gson;
	
	protected FloatingStorage(File directory) {
		floatingFile_Undone = new File(directory, "Undone Floating.txt");
		floatingFile_Done = new File(directory, "Done Floating.txt");
		gson = new Gson();
	}
	
	protected boolean writeToFile(ArrayList<Task> input_FloatingList, boolean isDone) {
		try {
			BufferedWriter bw;
			if (isDone) {
				bw = new BufferedWriter(new FileWriter(floatingFile_Done));
			}
			else {
				bw = new BufferedWriter(new FileWriter(floatingFile_Undone));
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
				br = new BufferedReader(new FileReader(floatingFile_Done));
			}
			else {
				br = new BufferedReader(new FileReader(floatingFile_Undone));
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
	
	protected File getUndoneFloatingFile() {
		return floatingFile_Undone;
	}
	
	protected File getDoneFloatingFile() {
		return floatingFile_Done;
	}
	
	protected void setUndoneFloatingFile(File newFloating) {
		floatingFile_Undone = newFloating;
	}
	
	protected void setDoneFloatingFile(File newFloating) {
		floatingFile_Done = newFloating;
	}
}
