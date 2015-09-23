package logic;

import common.Task;
import userinterface.PrettyDisplay;

public class Controller {
	
	public static void main(String[] args){
		PrettyDisplay gui=new PrettyDisplay();
		TaskHandler handler=new TaskHandler();
		CommandStack commandStack=new CommandStack();
		AddParser addParser=new AddParser(handler);
		EditParser editParser=new EditParser(handler);
		QueryParser queryParser=new QueryParser(handler);
		
		while(true){
			String userInput=gui.getUserInput();
			String commandString=getFirstWord(userInput);
			String parameterString=removeFirstWord(userInput);
			
			switch(commandString.toLowerCase()){
				case "add":
					AddCommand add = new AddCommand(handler,addParser.parse(parameterString));
					commandStack.addCommand(add);
					break;
				case "edit":
					EditCommand edit = new EditCommand(handler,editParser.parse(parameterString));
					commandStack.addCommand(edit);
				case "done":
					DoneCommand done = new DoneCommand(handler,queryParser.parse(parameterString));
					commandStack.addCommand(done);
				case "delete":
					DoneCommand delete = new DoneCommand(handler,queryParser.parse(parameterString));
					commandStack.addCommand(delete);
				case "exit":
					gui.closeWindow();
					System.exit(0);
					
					
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
