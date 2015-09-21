package logic;

import java.time.LocalDate;
import java.util.Scanner;

import common.Task;

public class LogicController {

	private static final int TASK_DATE_HAS_PASSED = -1;

	public static void main(String[] args) {
		TaskHandler taskHandler = new TaskHandler();
		CommandStack commandStack = new CommandStack();

		checkForPassedTasks(taskHandler);
		
		while (true) {
			String[] newInput = getInput();

			switch (newInput[0].toLowerCase()) {
			case "add":
				Task newTask = InputParser.addTask(newInput[1]);
				AddCommand newCommand = new AddCommand(taskHandler);
				newCommand.execute(newTask);
				commandStack.addCommand(newCommand);

				break;
			case "edit":
			case "complete":
				StubClass newEditStub = InputParser.EditTask(newInput[1]);	
				EditCommand newEditCommand = new EditCommand(taskHandler);
				newEditCommand.execute(newEditStub.getDate(), newEditStub.getTaskIndex(), newEditStub.getTaskWithModifications());
				commandStack.addCommand(newCommand);
				
				break;
			case "delete":
				StubClass newDeleteStub = InputParser.DeleteTask(newInput[1]);
				DeleteCommand newDeleteCommand = new DeleteCommand(taskHandler);
				newDeleteCommand.execute(newDeleteStub.getDate(), newDeleteStub.getTaskIndex());
				commandStack.addCommand(newCommand);

				break;
			case "undo":
				commandStack.undoOperation();
				
				break;
			case "redo":
				commandStack.redoOperation();
				
				break;
			case "exit":
				System.exit(1);
			default:
				showToUser(INVALID_COMMAND_MESSAGE);
			}
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
	
	protected static void checkForPassedTasks(TaskHandler taskHandler) {
		for (Task task: taskHandler.getTodoList()) {
			if (task.getDate(false).isBefore(LocalDate.now())) {
				task.setIsDone(TASK_DATE_HAS_PASSED);
			}
			else {
				break;
			}
		}
	}
}
