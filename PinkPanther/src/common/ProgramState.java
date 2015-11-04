package common;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

public class ProgramState {
	private ArrayList<Task>floatingList;
	private SortedMap<LocalDate,ArrayList<Task>>todoList;
	private SortedMap<LocalDate,ArrayList<Task>>overdue;
	private ArrayList<String>commandHistory;
	private String title;
	private String inputBoxText;
	private boolean isExit;
	
	public ProgramState(){
		this.floatingList = new ArrayList<Task>();
		this.todoList = new TreeMap<LocalDate,ArrayList<Task>>();
		this.overdue = new TreeMap<LocalDate,ArrayList<Task>>();
		this.commandHistory=new ArrayList<String>();
		this.inputBoxText = "";
		this.isExit = false;
		this.title = "                      Your Calendar";
	}
	
	public void setFLoatingList(ArrayList<Task>floatingList){
		this.floatingList=floatingList;
	}
	
	public void setTodoList(SortedMap<LocalDate,ArrayList<Task>>todoList){
		this.todoList=todoList;
	}
	
	public void setOverDueList(SortedMap<LocalDate, ArrayList<Task>>overdue){
		this.overdue=overdue;
	}
	
	public void setInputBoxText(String text){
		this.inputBoxText=text;
	}
	
	public void setExitState(boolean isExit){
		this.isExit=isExit;
	}
	
	public void setTitle(String title){
		this.title=title;
	}
	
	public void addCommandToHistory(String commandString){
		this.commandHistory.add(commandString);
	}
	
	public ArrayList<Task>getFloatingList(){
		return floatingList;
	}
	
	public SortedMap<LocalDate,ArrayList<Task>>getTodoList(){
		return todoList;
	}
	
	public SortedMap<LocalDate, ArrayList<Task>>getOverdueList(){
		return overdue;
	}
	
	public ArrayList<String>getCommandHistory(){
		return commandHistory;
	}
	
	public String getInputBoxText(){
		return inputBoxText;
	}
	
	public String getTitle(){
		return title;
	}
	
	public boolean getExitState(){
		return isExit;
	}
	
}
