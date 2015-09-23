package logic;

import common.Task;
import parser.AddStringParser;
import userinterface.PrettyDisplay;

public class Controller {
	
	public static void main(String[] args){
		PrettyDisplay gui=new PrettyDisplay();
		TaskHandler handler=new TaskHandler();
		CommandStack commandStack=new CommandStack();
		AddStringParser addParser=new AddStringParser();
		EditParser editParser=new EditParser(handler);
		QueryParser queryParser=new QueryParser(handler);
		
		while(true){
			String userInput=gui.getUserInput();
			String commandString=getFirstWord(userInput);
			String parameterString=removeFirstWord(userInput);
			
			switch(commandString.toLowerCase()){
				case "add":
					AddCommand add = new AddCommand(handler);
					if(add.execute(addParser.parse(parameterString))){
						commandStack.addCommand(add);
					}
					break;
				case "edit":
					EditCommand edit = new EditCommand(handler);
					if(edit.execute(editParser.parse(parameterString))){
						commandStack.addCommand(edit);
					}
					
					break;
				case "done":
					DoneCommand done = new DoneCommand();
					if(done.execute(queryParser.parse(parameterString))){
						commandStack.addCommand(done);
					}
					break;
				case "delete":
					DeleteCommand delete = new DeleteCommand(handler);
					if(delete.execute(queryParser.parse(parameterString))){
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
					//display invalid command message
					
			}
		}
		
			
	}
	
	private static String getFirstWord(String userInput) {
		String commandTypeString = userInput.trim().split("\\s+")[0];
		return commandTypeString;
	}
	
	private static String removeFirstWord(String userInput) {
		String commandContent = userInput.replaceFirst(getFirstWord(userInput), "").trim();
		return commandContent;
	}
}
