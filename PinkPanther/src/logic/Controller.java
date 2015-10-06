package logic;

import java.time.LocalDate;
import common.Auxiliary;
import java.util.ArrayList;
import java.util.TreeMap;

import common.Pair;
import common.Task;
import parser.CommandParser;
import userinterface.PrettyDisplay;

public class Controller {
	PrettyDisplay gui;
	TaskHandler handler=new TaskHandler();
	CommandStack commandStack=new CommandStack();
	CommandParser parser=new CommandParser();
	Pair<Task,Task>taskPair=new Pair<Task,Task>(null,null);
	
	public void setGui(PrettyDisplay gui){
		this.gui=gui;
	}
	
	public ArrayList<Task> getFloatingList(){
		return handler.getFloatingList();
	}
	
	public TreeMap<LocalDate,ArrayList<Task>> getTodoList(){
		return handler.getTodoList();
	}
	
	public void addCommand(String command){
    	System.out.println("Called mainController to add command: " + command);
    	handler.resetMatchStatus();
		String commandString=Auxiliary.getFirstWord(command);
		String parameterString=Auxiliary.removeFirstWord(command);
		boolean canClear=true;
		
		switch(commandString.toLowerCase()){
			case "add":
				AddCommand add = new AddCommand(handler);
				if(add.execute(parser.createTask(parameterString))){
					commandStack.addCommand(add);
				}
				break;
			case "edit":
				Task unmodified=TaskFinder.find(handler, parser.query(parameterString));
				if(unmodified!=null){
					taskPair.setFirst(unmodified);
					canClear=false;
					gui.setUserTextField(unmodified.toString());	
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
				if(handler.findNameMatch(parameterString)){
					Display.setFeedBack("match found");
				}
				else{
					Display.setFeedBack("no match found");
				}
				break;
			case "saveas":
				
			
			case "undo":
				commandStack.undoOperation();
				break;
			case "redo":
				commandStack.redoOperation();
				break;
			case "exit":
				gui.closeWindow();
				System.exit(0);
			case "clear":
				handler.clearAllTasks();
				break;
			default:
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
					Display.setFeedBack("invalid command");
			   }		
		}
		if(canClear){
			gui.clearTextField();
		}
	}
	
}
