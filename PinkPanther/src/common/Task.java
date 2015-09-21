package common;

public class Task {
	
	private String name;
	private boolean isDone;
	
	public Task(String name){
		this.name=name;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void markAsDone(){
		this.isDone=true;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean isFinished(){
		return isDone;
	}
}
