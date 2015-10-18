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
	
	
	public void addCommand(String command){
    	System.out.println("Called mainController to add command: " + command);
		String commandString=Auxiliary.getFirstWord(command);
		String parameterString=Auxiliary.removeFirstWord(command);
		boolean canSave=true;
		state.setInputBoxText("");
		
		if(taskPair.getFirst()!=null){
			taskPair.setSecond(parser.createTask(command));
			EditCommand edit=new EditCommand(manager);
			if(edit.execute(taskPair)){
				commandStack.addCommand(edit);
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
					Task unmodified=manager.searchTasks(parser.query(parameterString)).get(0);
					if(unmodified!=null){
						taskPair.setFirst(unmodified);
						state.setInputBoxText(unmodified.toString());
						Display.setFeedBack("\"" + unmodified + "\"" + "is being edited. Edit it in text box then press ENTER.");
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
					break;
				case "redo":
					commandStack.redoOperation();
					break;
				case "exit":
					state.setExitState(true);
					canSave=false;
					break;
				case "clear":
					manager.clearAllTasks();
					break;
				default:
					Display.setFeedBack("invalid command");		
			}
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
		Pair<LocalDate,LocalDate>datePair=parser.parseDate(mode);
		if(datePair!=null){
			state.setFLoatingList(null);
			state.setTodoList(manager.getDateRange(datePair.getFirst(), datePair.getSecond()));
		}
		else if(mode.equals("done")){
			state.setFLoatingList(manager.getFloating(true));
			state.setTodoList(manager.getDated(true));
		}
		else if(mode.equals("normal")){
			state.setFLoatingList(manager.getFloating(false));
			state.setTodoList(manager.getTwoWeek());
		}					
	}
	
}
