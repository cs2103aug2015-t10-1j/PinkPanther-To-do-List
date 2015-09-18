package storage;

import java.util.ArrayList;

import com.google.gson.Gson;

import common.Event;

public class EventStorage {
	ArrayList<Event>eventContainer;
	Gson gson=new Gson();
	
	public EventStorage(){
		eventContainer=new ArrayList<Event>();
		readFromFile();
	}
	
	public void writeToFile(){
		
	}
	
	public void readFromFile(){
		
	}
	
	public ArrayList<Event>getEvents(){
		return eventContainer;
	}

}
