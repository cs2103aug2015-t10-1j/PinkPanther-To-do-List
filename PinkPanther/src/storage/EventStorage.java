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

public class EventStorage {
	private static Gson gson=new Gson();
	private static File file=new File("storage.txt");
	
	
	public static void writeToFile(ArrayList<Task>taskList){
		
	}
	
	public static ArrayList<Task> readFromFile(){
		
	}
	

}
