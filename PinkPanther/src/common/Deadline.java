package common;

import java.time.LocalDate;
import java.time.LocalTime;

public class Deadline extends Task implements Comparable<Deadline>{
	LocalDate date;
	LocalTime time;
	
	public Deadline(String name,LocalDate date,LocalTime time){
		super(name);
		this.date=date;
		this.time=time;
	}
	
	public void setDate(LocalDate date){
		this.date=date;
	}
	
	public void setTime(LocalTime time){
		this.time=time;
	}
	
	public LocalDate getDate(){
		return date;
	}
	
	public LocalTime getTime(){
		return time;
	}
	
	public int compareTo(Deadline other){
		int dateDiff=this.getDate().compareTo(other.getDate());
		if(dateDiff==0){
			return this.getTime().compareTo(other.getTime());
		}
		return dateDiff;
	}
	
	
}
