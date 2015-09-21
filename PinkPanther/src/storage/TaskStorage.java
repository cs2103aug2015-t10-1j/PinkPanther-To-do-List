package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.LocalDate;

import com.google.gson.Gson;

import common.Task;

public class TaskStorage {
	private static Gson gson=new Gson();
	private static File todo=new File("todo.txt");
	private static File floating=new File("floating.txt");
	
	public static void writeToFile(ArrayList<Task>floatingList){
		try{
			BufferedWriter bw=new BufferedWriter(new FileWriter(floating));
			for(Task task:floatingList){
				String json=gson.toJson(task)+"\n";
				bw.write(json);
			}
			bw.close();
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void writeToFile(HashMap<LocalDate,ArrayList<Task>>todoList){
		try{
			BufferedWriter bw=new BufferedWriter(new FileWriter(todo));
			for(LocalDate date:todoList.keySet()){
				for(Task task:todoList.get(date)){
					String json=gson.toJson(task)+"\n";
					bw.write(json);
				}
				
			}
			bw.close();
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static ArrayList<Task> readFromFloatingFile(){
		ArrayList<Task>floatingList=new ArrayList<Task>();
		String line="";
		try{
			BufferedReader br=new BufferedReader(new FileReader(floating));
			while((line=br.readLine())!=null){
				floatingList.add(gson.fromJson(line, Task.class));
			}
			br.close();
		}
		catch (FileNotFoundException e){
			//do nothing since file does not exist
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return floatingList;
	}
	
	public static HashMap<LocalDate,ArrayList<Task>> readFromTodoFile(){
		HashMap<LocalDate,ArrayList<Task>>todoList=new HashMap<LocalDate,ArrayList<Task>>();
		String line="";
		try{
			BufferedReader br=new BufferedReader(new FileReader(todo));
			while((line=br.readLine())!=null){
				Task task=gson.fromJson(line, Task.class);
				LocalDate date=task.getDate();
				if(!todoList.containsKey(date)){
					todoList.put(date,new ArrayList<Task>());
				}
				todoList.get(date).add(task);	
			}
			br.close();
		}
		catch (FileNotFoundException e){
			//do nothing since file does not exist
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return todoList;
	}
}
