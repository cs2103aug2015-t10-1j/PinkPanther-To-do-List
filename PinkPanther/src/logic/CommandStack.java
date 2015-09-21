package logic;
/*
 * add description
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
	
	public boolean undoOperation(){
		if(undoStack.peek()==null){
			return false;
		}
		undoStack.peek().undo();
		redoStack.push(undoStack.pop());
		undoStackSize--;
		return true;
	}
	
	public boolean redoOperation(){
		if(redoStack.peek()==null){
			return false;
		}
		redoStack.peek().redo();
		undoStack.push(redoStack.pop());
		undoStackSize++;
		return true;
	}
	
	
	
}



