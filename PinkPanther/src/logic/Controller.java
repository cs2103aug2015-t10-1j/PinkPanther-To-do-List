/* @@author GB */
package logic;

import common.Pair;
import common.ProgramState;
import common.Task;
import parser.CommandParser;
import storage.StorageControl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import common.Auxiliary;
import common.Display;

public class Controller {
	private static Pair<Task,Task>taskPair = new Pair<Task,Task>(null,null);
	private static final Logger log = Logger.getLogger("controller");
	
	private TaskManager manager;
	private CommandStack commandStack;
	private CommandParser parser;
	private StorageControl storage;
	private ProgramState state;
	
	public Controller(){
		storage = new StorageControl();
		manager = new TaskManager(storage);
		commandStack = new CommandStack();
		parser = new CommandParser();
		state = new ProgramState();
		initializeProgramState();
	}
	
	public void initializeProgramState(){
		state.setFLoatingList(manager.getFloating(false));
		state.setTodoList(manager.getTwoWeek());
		state.setOverDueList(manager.getDatedPrevious());
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
		String commandString = Auxiliary.getFirstWord(command);
		String parameterString = Auxiliary.removeFirstWord(command);
		boolean canSave = true;
		state.setInputBoxText("");
		
		if(taskPair.getFirst() != null){
			Task task = parser.createTask(command);
			if(task!=null){
				taskPair.setSecond(parser.createTask(command));
				log.log(Level.FINE, "called parser to create a task");
				EditCommand edit = new EditCommand(manager);
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
					log.log(Level.FINE, "called parser to create a task");
					break;
				case "edit":
					ArrayList<Task>taskList = manager.searchTasks(parser.query(parameterString));
					if(taskList != null && taskList.get(0) != null){
						Task unmodified = taskList.get(0);
						taskPair.setFirst(unmodified);
						state.setInputBoxText(unmodified.toString());
						Display.setFeedBack("Edit the task in text box, then press ENTER.");
					} else{
						state.setInputBoxText(command);
					}
					log.log(Level.FINE, "called parser to create date index pair");
					break;
				case "done":
					DoneCommand done = new DoneCommand(manager);
					if(done.execute(parser.query(parameterString))){
						commandStack.addCommand(done);
					} else{
						state.setInputBoxText(command);
					}
					log.log(Level.FINE, "called parser to create date index pair");
					break;
				case "del":
				case "delete":
					DeleteCommand delete = new DeleteCommand(manager);
					if(delete.execute(parser.query(parameterString))){
						commandStack.addCommand(delete);
					} else{
						state.setInputBoxText(command);
					}
					log.log(Level.FINE, "called parser to create date index pair");
					break;	
				case "search":
					state.setFLoatingList(manager.getMatchedFloating(parameterString));
					state.setTodoList(manager.getMatchedDated(parameterString));
					state.setTitle("         ● Searching: [" + parameterString + "] ●");
					Display.setFeedBack("Input 'view normal' to return to main calendar.");
					canSave = false;
					break;
				case "view":
					if(!changeDisplayMode(parameterString)){
						state.setInputBoxText(command);
					}
					canSave = false;
					break;
				case "save":
					storage.changeDirectory(parameterString);
					canSave = false;
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
					canSave = false;
					break;
				case "clear":
					manager.clearAllTasks();
					state.setFLoatingList(manager.getFloating(false));
					state.setTodoList(manager.getDated(false));
					Display.setFeedBack("All tasks have been cleared");
					break;
				default:
					Display.setFeedBack("Unrecognized command. Press F2 for Help Screen.");
					state.setInputBoxText(command);
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
	
	private boolean changeDisplayMode(String mode){
		boolean canView = true;
		if(mode.equals("done")){
			state.setFLoatingList(manager.getFloating(true));
			state.setTodoList(manager.getDated(true));
			state.setTitle("              ● Viewing: Done Tasks ●");
			Display.setFeedBack("Input 'view normal' to return to main calendar.");
		} else if(mode.equals("normal") || mode.equals("norm")){
			state.setFLoatingList(manager.getFloating(false));
			state.setTodoList(manager.getTwoWeek());
			state.setTitle("                      Your Calendar");
			Display.setFeedBack("Input command into the field above");
		} else if(mode.equals("all")){
			state.setFLoatingList(manager.getFloating(false));
			state.setTodoList(manager.getDated(false));
			state.setTitle("              ● Viewing: All Tasks ●");
			Display.setFeedBack("Input 'view normal' to return to main calendar.");
		} else if(mode.equals("previous") || mode.equals("prev") || mode.equals("overdue")){
			state.setFLoatingList(null);
			state.setTodoList(manager.getDatedPrevious());
			state.setTitle("              ● Viewing: Overdue Tasks ●");
			Display.setFeedBack("Input 'view normal' to return to main calendar.");
		} else if(parser.queryDateRange(mode) != null){
			Pair<LocalDate,LocalDate>datePair = parser.queryDateRange(mode);
			log.log(Level.FINE, "called parser to create date pair");
			state.setFLoatingList(null);
			state.setTodoList(manager.getDateRange(datePair.getFirst(), datePair.getSecond()));
			String dateRange = datePair.getFirst().toString();
			if (datePair.getFirst().equals(datePair.getSecond())){
				dateRange = datePair.getFirst().toString();
				state.setTitle("     ● Viewing: Tasks on " + dateRange + " ●");
			} else {
				dateRange = "" + datePair.getFirst() + " - " +  datePair.getSecond();
				state.setTitle("● Viewing: " + dateRange + " ●");
			
			}
			Display.setFeedBack("Input 'view normal' to return to main calendar.");
		} else{
			Display.setFeedBack("Invalid view range specified!");
			canView = false;
		}
		return canView;
	}
	
}
