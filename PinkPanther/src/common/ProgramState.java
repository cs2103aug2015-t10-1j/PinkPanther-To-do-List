package common;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeMap;

public class ProgramState {
	private ArrayList<Task>floatingList;
	private TreeMap<LocalDate,ArrayList<Task>>todoList;
	private String inputBoxText;
	private boolean isExit;
	
	public void setFLoatingList(ArrayList<Task>floatingList){
		this.floatingList=floatingList;
	}
	
	public void setTodoList(TreeMap<LocalDate,ArrayList<Task>>todoList){
		this.todoList=todoList;
	}
	
	public void setInputBoxText(String text){
		this.inputBoxText=text;
	}
	
	public void setExitState(boolean isExit){
		this.isExit=isExit;
	}
	
	public ArrayList<Task>getFloatingList(){
		return floatingList;
	}
	
	public TreeMap<LocalDate,ArrayList<Task>>getTodoList(){
		return todoList;
	}
	
	public String getInputBoxText(){
		return inputBoxText;
	}
	
	public boolean getExitState(){
		return isExit;
	}
}
