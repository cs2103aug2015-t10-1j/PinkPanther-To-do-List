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
import common.Event;

public class EventStorage {
	private static Gson gson=new Gson();
	private static File file=new File("");
	
	
	public static void writeToFile(ArrayList<Event>eventList){
		try{
			BufferedWriter bw=new BufferedWriter(new FileWriter(file));
			for(Event event:eventList){
				String json=gson.toJson(event)+"\n";
				bw.write(json);
			}
			bw.close();
		}
		
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static ArrayList<Event> readFromFile(){
		ArrayList<Event>eventList=new ArrayList<Event>();
		String line="";
		try{
			BufferedReader br=new BufferedReader(new FileReader(file));
			while((line=br.readLine())!=null){
				eventList.add(gson.fromJson(line, Event.class));
			}
			br.close();
		}
		catch (FileNotFoundException e){
			//do nothing since file does not exist
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return eventList;
	}
	

}
