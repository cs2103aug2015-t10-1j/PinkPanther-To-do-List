package logic;

import java.util.Scanner;

public class LogicController {

	public static void main(String[] args) {
		TaskHandler taskHandler = new TaskHandler();
		
		String[] newInput = getInput();
		
		switch (newInput[0].toLowerCase()) {
		case "add":
			AddCommand newCommand = new AddCommand(taskHandler);
			newCommand.execute(InputParser.addTask(newInput[1]));
		case "edit":
			
			InputParser.editTask(newInput[1]);
		case "delete":
			return InputParser.deleteTask(newInput[1]);
		case "
		}
		
		
	}
	
	protected static String[] getInput() {
		Scanner sc = new Scanner(System.in);
		String rawInput = sc.nextLine();
		sc.close();
		
		String[] splitInput = new String[2];
		splitInput[0] = rawInput.substring(0, rawInput.indexOf(' '));
		splitInput[1] = rawInput.substring(rawInput.indexOf(' ') + 1);
		
		return splitInput;
	}
}
