/* @@author GB */
package common;

public class Pair<A,B> {
	private A first;
	private B second;
	
	public Pair(A input1, B input2){
		this.first=input1;
		this.second=input2;
	}
	
	public void setFirst(A input){
		this.first=input;
	}
	
	public void setSecond(B input){
		this.second=input;
	}
	
	public A getFirst(){
		return first;
	}
	
	public B getSecond(){
		return second;
	}
}
