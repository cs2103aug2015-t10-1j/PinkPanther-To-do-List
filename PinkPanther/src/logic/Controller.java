package logic;

//import java.time.LocalDate;

//import java.util.ArrayList;
//import java.util.TreeMap;

import common.Pair;
import common.ProgramState;
import common.Task;
import parser.CommandParser;
import storage.StorageControl;
import common.Auxiliary;
import common.Display;

public class Controller {
	private static Pair<Task,Task>taskPair=new Pair<Task,Task>(null,null);
	
	private TaskHandler handler;
	private CommandStack commandStack;
	private CommandParser parser;
	private StorageControl storage;
	private ProgramState state;
	
	public Controller(){
		storage=StorageControl.createStorageControl();
		handler=new TaskHandler(storage);
		commandStack=new CommandStack();
		parser=new CommandParser();
		state=new ProgramState();
		initializeProgramState();
	}
	
	public void initializeProgramState(){
		state.setFLoatingList(handler.getFloating());
		state.setTodoList(handler.getTwoWeekTodo());
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
			EditCommand edit=new EditCommand(handler);
			if(edit.execute(taskPair)){
				commandStack.addCommand(edit);
			}
			taskPair.setFirst(null);
			taskPair.setSecond(null);
		}
		
		else{
			switch(commandString.toLowerCase()){
				case "add":
					AddCommand add = new AddCommand(handler);
					if(add.execute(parser.createTask(parameterString))){
						commandStack.addCommand(add);
					}
					break;
				case "edit":
					Task unmodified=handler.searchTasks(parser.query(parameterString)).get(0);
					if(unmodified!=null){
						taskPair.setFirst(unmodified);
						state.setInputBoxText(unmodified.toString());
						Display.setFeedBack("\"" + unmodified + "\"" + "is being edited. Edit it in text box then press ENTER.");
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
				case "search":
					state.setFLoatingList(handler.getMatchedFloating(parameterString));
					state.setTodoList(handler.getMatchedTodo(parameterString));
					canSave=false;
					break;
				case "display":
					if(parameterString.equals("completed")){
						state.setFLoatingList(handler.getDoneFloating());
						state.setTodoList(handler.getDoneTodo());
					}
					else if(parameterString.equals("normal")){
						state.setFLoatingList(handler.getFloating());
						state.setTodoList(handler.getTodo());
					}					
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
					handler.clearAllTasks();
					break;
				default:
					Display.setFeedBack("invalid command");		
			}
		}
		
		if(canSave){
			storage.save(handler.getTodo());
			storage.save(handler.getFloating());
		}
		
	}
	
}
