package common;

public class Item {
	String name;
	boolean isDone;
	
	public Item(String name){
		this.name=name;
		isDone=false;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void markAsDone(){
		isDone=true;
	}
	
	public String getName(){
		return name;
	}
	
	public boolean isFinished(){
		return isDone;
	}
}
