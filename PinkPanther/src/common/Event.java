package common;
import java.time.LocalDate;
import java.time.LocalTime;
/*
 * Add class description
 */
public class Event extends Item implements Comparable<Event>{
	
	LocalDate date;
	LocalTime startTime;
	LocalTime endTime;
	
	public Event(String name,LocalDate date,LocalTime time){
		super(name);
		this.startTime=time;
		this.endTime=time;
		this.date=date;
	}
	
	public Event(String name,LocalDate date,LocalTime startTime,LocalTime endTime){
		this(name,date,startTime);
		this.endTime=endTime;
	}
	
	public void setDate(LocalDate date){
		this.date=date;
	}
	
	public void setStartTime(LocalTime time){
		this.startTime=time;
	}
	
	public void setEndTime(LocalTime time){
		this.endTime=time;
	}
	
	public LocalDate getDate(){
		return date;
	}
	
	public LocalTime getStartTime(){
		return startTime;
	}
	
	public LocalTime getEndTime(){
		return endTime;
	}
	
	
	public int compareTo(Event other){
		int dateDiff=this.getDate().compareTo(other.getDate());
		if(dateDiff==0){
			return this.getStartTime().compareTo(other.getStartTime());
		}
		return dateDiff;
	}
	
	
	
	
	
	
	
}
