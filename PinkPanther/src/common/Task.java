package common;
import java.time.LocalDate;
import java.time.LocalTime;
/*
 * Add class description
 */
public class Task {

	private TaskType type;
	private String name;
	private LocalDate startDate;
	private LocalDate endDate;
	private LocalTime startTime;
	private LocalTime endTime;
	private int status;
	
	public Task(String name){
		this.name=name;
		this.type=TaskType.FLOATING;
	}
	
	
	public Task(String name,LocalDate date,LocalTime time, TaskType type){
		this.name=name;
		if(type==TaskType.TODO){
			this.startDate=date;
			this.startTime=time;
		}
		else if(type==TaskType.DEADLINE){
			this.endDate=date;
			this.endTime=time;
		}
		this.type=type;
	}
	
	public Task(String name,LocalDate startDate,LocalTime startTime,
			LocalDate endDate,LocalTime endTime){
		this.name=name;
		this.startDate=startDate;
		this.startTime=startTime;
		this.endDate=endDate;
		this.endTime=endTime;
		this.type=TaskType.EVENT;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setStartDate(LocalDate date){
		this.startDate=date;
	}
	
	public void setStartTime(LocalTime time){
		this.startTime=time;
	}
	
	public void setEndDate(LocalDate date){
		this.endDate=date;
	}
	
	public void setEndTime(LocalTime time){
		this.endTime=time;
	}
	
	public void setStatus(int status){
		this.status=status;
	}
	
	public String getName(){
		return name;
	}
	
	public LocalDate getStartDate(){
		return startDate;
	}
	
	public LocalTime getStartTime(){
		return startTime;
	}
	public String getStartTimeString(){
		if(startTime == null){
			return "";
		}
		return startTime.toString();
	}
	
	public LocalDate getEndDate(){
		return endDate;
	}
	
	public LocalTime getEndTime(){
		return endTime;
	}
	public String getEndTimeString(){
		if(endTime == null){
			return "";
		}
		return endTime.toString();
	}
	
	public LocalDate getDate(){
		if(startDate!=null){
			return startDate;
		}
		return endDate;
	}
	
	public LocalDate getDate(int startOrEnd){
		if(startOrEnd==0){
			return startDate;
		}
		else return endDate;
		
	}
	
	public LocalTime getTime(){
		if(startTime!=null){
			return startTime;
		}
		return endTime;
	}
		
	public TaskType getTaskType(){
		return type;
	}
	
	public int getStatus(){
		return status;
	}
	
	
}
