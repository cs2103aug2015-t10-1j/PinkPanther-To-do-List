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
	private File floatingFile;
	private Gson gson;
	
	FloatingStorage(File directory) {
		floatingFile = new File(directory, "Floating.txt");
		gson = new Gson();
	}
	
	protected boolean writeToFile(ArrayList<Task> input_FloatingList) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(floatingFile));
			
			for (Task task: input_FloatingList) {
				performWriting(bw, task);
			}
			bw.close();
			return true;
		}
		catch (IOException e) {
			return false;
		}
	}
	
	protected ArrayList<Task> readFromFile() {
		ArrayList<Task> new_TaskList = new ArrayList<Task>();
		String newLine = "";
		try{
			BufferedReader br = new BufferedReader(new FileReader(floatingFile));
			while ((newLine = br.readLine()) != null) {
				new_TaskList.add(gson.fromJson(newLine, Task.class));
			}
			br.close();
		}
		catch (FileNotFoundException e) {
			return null;
		}
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return new_TaskList;
	}
	
	private String performWriting(BufferedWriter bw, Task task) throws IOException {
		String json = gson.toJson(task) + "\n";
		bw.write(json);
		
		return json; //Primarily for testing purposes.
	}
}
