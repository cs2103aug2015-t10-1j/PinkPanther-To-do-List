package logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeMap;

import common.Pair;
import common.Task;
import parser.CommandParser;
import userinterface.PrettyDisplay;

public class Controller {
	PrettyDisplay gui=new PrettyDisplay();
	TaskHandler handler=new TaskHandler();
	CommandStack commandStack=new CommandStack();
	CommandParser parser=new CommandParser();
	
	private static String getFirstWord(String userInput) {
		String commandTypeString = userInput.trim().split("\\s+")[0];
		return commandTypeString;
	}
	
	private static String removeFirstWord(String userInput) {
		String commandContent = userInput.replaceFirst(getFirstWord(userInput), "").trim();
		return commandContent;
	}
	
	public ArrayList<Task> getFloatingList(){
		return handler.getFloatingList();
	}
	
	public TreeMap<LocalDate,ArrayList<Task>> getTodoList(){
		return handler.getTodoList();
	}
	
	public void addCommand(String command){
    	System.out.println("Called mainController to add command: " + command);
		String commandString=getFirstWord(command);
		String parameterString=removeFirstWord(command);
		
		switch(commandString.toLowerCase()){
			case "add":
				AddCommand add = new AddCommand(handler);
				if(add.execute(parser.createTask(parameterString))){
					commandStack.addCommand(add);
				}
				break;

			case "edit":
				EditCommand edit=new EditCommand(handler);
				Pair<Task,Task>taskPair=editTask(parameterString);
				if(taskPair!=null && edit.execute(taskPair)){
					commandStack.addCommand(edit);
				}
				break;
			case "done":
				DoneCommand done = new DoneCommand(handler);
				if(done.execute(parser.query(parameterString))){
					commandStack.addCommand(done);
				}
				break;
			case "del":
			case "delete":
				DeleteCommand delete = new DeleteCommand(handler);
				if(delete.execute(parser.query(parameterString))){
					commandStack.addCommand(delete);
				}
				break;			
			case "undo":
				commandStack.undoOperation();
				break;
			case "redo":
				commandStack.redoOperation();
				break;
			case "exit":
				gui.closeWindow();
				System.exit(0);
			default:
				Display.setFeedBack("Invalid Command");
		}
	}
	
	public Pair<Task,Task> editTask(String userInput){

		Task unmodified=TaskFinder.find(handler, parser.query(userInput));
		if(unmodified!=null){
			//pass the commandstring to ui and get back a modified string
			Display.setFeedBack(unmodified.getCommandString());
			String modifiedString=""; //string from ui
			Task modified=parser.createTask(modifiedString);
			return new Pair<Task,Task>(unmodified,modified);
		}
		return null;
		
	}
}
