package common;
import java.time.LocalDate;
import java.time.LocalTime;

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
	
	public void setTime(LocalTime time){
		this.startTime=time;
		this.endTime=time;
	}
	
	public void setTime(LocalTime startTime,LocalTime endTime){
		this.startTime=startTime;
		this.endTime=endTime;
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
