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
	private static int TODO_LIST=0;
	private static int FLOATING_LIST=1;
	
	private static Gson gson=new Gson();
	private static File todo=new File("todo.txt");
	private static File floating=new File("floating.txt");
	
	public static void writeToFile(ArrayList<Task>taskList,int taskType){
		File file=(taskType==TODO_LIST)?todo:floating;
		try{
			BufferedWriter bw=new BufferedWriter(new FileWriter(file));
			for(Task task:taskList){
				String json=gson.toJson(task)+"\n";
				bw.write(json);
			}
			bw.close();
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static ArrayList<Task> readFromFile(int taskType){
		File file=(taskType==TODO_LIST)?todo:floating;
		ArrayList<Task>taskList=new ArrayList<Task>();
		String line="";
		try{
			BufferedReader br=new BufferedReader(new FileReader(file));
			while((line=br.readLine())!=null){
				taskList.add(gson.fromJson(line, Task.class));
			}
			br.close();
		}
		catch (FileNotFoundException e){
			//do nothing since file does not exist
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return taskList;
	}
}
