package common;

import java.time.LocalDate;
import java.time.LocalTime;

/*
 * Add class description
 */
public class Event extends Task implements Comparable<Event>{
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	
	public Event(String name,LocalDate date,LocalTime time){
		super(name);
		this.startDate=startDate;
		this.startTime=startTime;
	}
	
	public Event(String name,LocalDate startDate, LocalDate endDate, 
			LocalTime startTime,LocalTime endTime){
		
		this(name,startDate,startTime);
		this.endTime=endTime;
		this.endDate=endDate;
	}
	
	public void setStartDate(LocalDate startDate){
		this.startDate=startDate;
	}
	
	public void setEndDate(LocalDate endDate){
		this.endDate=endDate;
	}
	
	public void setStartTime(LocalTime startTime){
		this.startTime=startTime;
	}
	
	public void setEndTime(LocalTime endTime){
		this.endTime=endTime;
	}
	
	public LocalDate getStartDate(){
		return startDate;
	}
	
	public LocalDate getEndDate(){
		return endDate;
	}
	
	public LocalTime getStartTime(){
		return startTime;
	}
	
	public LocalTime getEndTime(){
		return endTime;
	}
	
	
	public int compareTo(Event other){
		int dateDiff=this.getStartDate().compareTo(other.getStartDate());
		if(dateDiff==0){
			return this.getStartTime().compareTo(other.getStartTime());
		}
		return dateDiff;
	}
	

}
