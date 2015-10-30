package logic;

//import java.time.LocalDate;

//import java.util.ArrayList;
//import java.util.TreeMap;

import common.Pair;
import common.ProgramState;
import common.Task;
import parser.CommandParser;
import storage.StorageControl;

import java.time.LocalDate;
import java.util.ArrayList;

import common.Auxiliary;
import common.Display;

public class Controller {
	private static Pair<Task,Task>taskPair=new Pair<Task,Task>(null,null);
	
	private TaskManager manager;
	private CommandStack commandStack;
	private CommandParser parser;
	private StorageControl storage;
	private ProgramState state;
	
	public Controller(){
		storage=new StorageControl();
		manager=new TaskManager(storage);
		commandStack=new CommandStack();
		parser=new CommandParser();
		state=new ProgramState();
		initializeProgramState();
	}
	
	public void initializeProgramState(){
		state.setFLoatingList(manager.getFloating(false));
		state.setTodoList(manager.getTwoWeek());
	}
	
	public ProgramState getProgramState(){
		return state;
	}
	
	public Task findTask(String string){
		
		ArrayList<Task> output = manager.searchTasks(parser.query(string));
		if(output == null){
			return null;
		}
		return output.get(0);
	}
	
	
	public void addCommand(String command){
    	System.out.println("Called mainController to add command: " + command);
		String commandString=Auxiliary.getFirstWord(command);
		String parameterString=Auxiliary.removeFirstWord(command);
		boolean canSave=true;
		state.setInputBoxText("");
		
		if(taskPair.getFirst()!=null){
			Task task=parser.createTask(command);
			if(task!=null){
				taskPair.setSecond(parser.createTask(command));
				EditCommand edit=new EditCommand(manager);
				if(edit.execute(taskPair)){
					commandStack.addCommand(edit);
				}
			}
			taskPair.setFirst(null);
			taskPair.setSecond(null);
		}
		
		else{
			switch(commandString.toLowerCase()){
				case "add":
					AddCommand add = new AddCommand(manager);
					if(add.execute(parser.createTask(parameterString))){
						commandStack.addCommand(add);
					}
					break;
				case "edit":
					ArrayList<Task>taskList=manager.searchTasks(parser.query(parameterString));
					if(taskList!=null && taskList.get(0)!=null){
						Task unmodified=taskList.get(0);
						taskPair.setFirst(unmodified);
						state.setInputBoxText(unmodified.toString());
						Display.setFeedBack("Edit the task in text box, then press ENTER.");
					}
					break;
				case "done":
					DoneCommand done = new DoneCommand(manager);
					if(done.execute(parser.query(parameterString))){
						commandStack.addCommand(done);
					}
					break;
				case "del":
				case "delete":
					DeleteCommand delete = new DeleteCommand(manager);
					if(delete.execute(parser.query(parameterString))){
						commandStack.addCommand(delete);
					}
					break;	
				case "search":
					state.setFLoatingList(manager.getMatchedFloating(parameterString));
					state.setTodoList(manager.getMatchedDated(parameterString));
					state.setTitle("Search result");
					canSave=false;
					break;
				case "view":
					changeDisplayMode(parameterString);
					canSave=false;
					break;
				case "save":
					storage.changeDirectory(parameterString);
					canSave=false;
					break;
				case "undo":
					commandStack.undoOperation();
					Display.setFeedBack("");
					break;
				case "redo":
					commandStack.redoOperation();
					Display.setFeedBack("");
					break;
				case "exit":
					state.setExitState(true);
					canSave=false;
					break;
				case "clear":
					manager.clearAllTasks();
					state.setFLoatingList(manager.getFloating(false));
					state.setTodoList(manager.getDated(false));
					Display.setFeedBack("All tasks have been cleared");
					break;
				default:
					Display.setFeedBack("Invalid command. Press PageUp for a list of valid commands");		
			}
			state.addCommandToHistory(command);
		}
		
		
		if(canSave){
			saveToStorage();
		}
		
	}
	
	private void saveToStorage(){
		storage.save(manager.getTaskArray(true),true);
		storage.save(manager.getTaskArray(false),false);
	}
	
	private void changeDisplayMode(String mode){
		if(mode.equals("done")){
			state.setFLoatingList(manager.getFloating(true));
			state.setTodoList(manager.getDated(true));
			state.setTitle("Done tasks");
			Display.setFeedBack("");
		}
		else if(mode.equals("normal")){
			state.setFLoatingList(manager.getFloating(false));
			state.setTodoList(manager.getTwoWeek());
			state.setTitle("Calendar");
			Display.setFeedBack("");
		}	
		else if(parser.queryDateRange(mode)!=null){
			Pair<LocalDate,LocalDate>datePair=parser.queryDateRange(mode);
			state.setFLoatingList(null);
			state.setTodoList(manager.getDateRange(datePair.getFirst(), datePair.getSecond()));
			state.setTitle(datePair.getFirst()+" to "+datePair.getSecond());
			Display.setFeedBack("");
		}
		else{
			Display.setFeedBack("Invalid input");
		}
	}
	
}
