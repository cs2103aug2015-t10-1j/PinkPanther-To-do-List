package logic;
/*
 * add class description
 */
import java.util.LinkedList;

public class CommandStack {
	private LinkedList<Command> undoStack;
	private LinkedList<Command> redoStack;
	private int undoStackSize;
	private static final int maxStackSize=5;
	
	
	public CommandStack(){
		undoStackSize=0;
		undoStack=new LinkedList<Command>();
		redoStack=new LinkedList<Command>();
	}
	
	public void addCommand(Command command){
		if(undoStackSize>=maxStackSize){
			undoStack.poll();
		}
		undoStack.push(command);
		undoStackSize++;
		//if user enter a new command after undo, he is unable to revert back to the state before undo
		if(!redoStack.isEmpty()){
			redoStack.clear();
		}
	}
	
	public void undoOperation(){
		undoStack.peek().undo();
		redoStack.push(undoStack.pop());
		undoStackSize--;
	}
	
	public void redoOperation(){
		redoStack.peek().redo();
		undoStack.push(redoStack.pop());
		undoStackSize++;
	}
	
	
	
}



