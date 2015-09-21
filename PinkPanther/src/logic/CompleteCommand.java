package logic;

import common.Task;

public class CompleteCommand implements Command {
	private Task taskRef;
	private boolean previousState;
	
	public CompleteCommand () {
	}
	
	public String execute(Task task, boolean isComplete) {
		previousState = task.getCompleteState();
		
		if (isComplete) {
			task.setCompleteState(true);
			taskRef = task;
			return task.getName() + " set to 'Complete'.";
		}
		else {
			task.setCompleteState(false);
			taskRef = task;
			return task.getName() + " set to 'Incomplete'.";
		}
	}
	
	public void undo() {
		this.execute(taskRef, previousState);
	}

	public void redo() {
		this.execute(taskRef, previousState);
	}
}
