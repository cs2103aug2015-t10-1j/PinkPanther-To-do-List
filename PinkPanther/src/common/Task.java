/* @@author GB */
package common;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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
	private boolean isDone;
	private boolean hasClash;
	private String[] displayName; 
	
	public Task(String name){
		this.name=name;
		this.displayName = formatName(this.name, 55);
		this.type=TaskType.FLOATING;
	}
	
	
	public Task(String name,LocalDate date,LocalTime time, TaskType type){
		this.name = name;
		this.displayName = formatName(this.name, 55);
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
		this.displayName=formatName(this.name, 55);
		this.startDate=startDate;
		this.startTime=startTime;
		this.endDate=endDate;
		this.endTime=endTime;
		this.type=TaskType.EVENT;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setStartDate(LocalDate date) {
		this.startDate=date;
	}
	
	public void setStartTime(LocalTime time) {
		this.startTime=time;
	}
	
	public void setEndDate(LocalDate date) {
		this.endDate=date;
	}
	
	public void setEndTime(LocalTime time) {
		this.endTime=time;
	}
	
	public void setDoneStatus(boolean isDone) {
		this.isDone=isDone;
	}
	
	public void setClash(boolean hasClash) {
		this.hasClash=hasClash;
	}
	
	public void setDisplayName(String[] displayName) {
		this.displayName = displayName;
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
	
	public boolean getDoneStatus(){
		return isDone;
	}
	
	public boolean getClash(){
		return hasClash;
	}
	
	public String[] getDisplayName(int length) {
		return formatName(this.name, length);
	}
	
	private String[] formatName(String taskName, int maxChars) {
		ArrayList<String> formattedName = new ArrayList<String>();
		String[] nameTokens = taskName.split(" ");
		String currentLine = "";
		for (int i = 0; i < nameTokens.length; i++) {
			String temp = currentLine + nameTokens[i];
			
			if (temp.length() <= maxChars-1) {
				currentLine = temp + " ";
			} else if (temp.length() == maxChars) {
				currentLine = temp;
			} else {
				if (currentLine.length() > 0) {
					formattedName.add(currentLine);
				}	
				if (nameTokens[i].length() < maxChars+1) {
					currentLine = nameTokens[i];
				} else {
					while (nameTokens[i].length() > maxChars) {
						formattedName.add(nameTokens[i].substring(0,maxChars-1) + "-");
						nameTokens[i] = nameTokens[i].substring(maxChars-1, nameTokens[i].length()-1);
					}
				}
				currentLine = nameTokens[i] + " ";
			}
		}
		formattedName.add(currentLine);
		return formattedName.toArray(new String[formattedName.size()]);
	}
	
	public String toString(){
		DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("d/M/yy");
		DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("h.mma");
		if(type==TaskType.FLOATING){
			return name;
		}
		else if(type==TaskType.DEADLINE){
			if(endTime!=null){
				return name+", by "+endTime.format(timeformatter)+", "+endDate.format(dateformatter);
			}
			return name+", by "+endDate.format(dateformatter);
		}
		else if(type==TaskType.TODO){
			if(startTime!=null){
				return name+", at "+startTime.format(timeformatter)+", "+startDate.format(dateformatter);
			}
			return name+", at "+startDate.format(dateformatter);
			
		}
		else{
			
			if (startTime == null && endTime == null) {
				return name + ", " + startDate.format(dateformatter) + " to " + endDate.format(dateformatter);
			} else if (startDate.isEqual(endDate)) {
				return name + ", " + startTime.format(timeformatter) + " to " + endTime.format(timeformatter)
					+ ", " + startDate.format(dateformatter);
			} else {
				return name + ", " + startTime.format(timeformatter) + ", " + startDate.format(dateformatter) 
					+ ", " + endTime.format(timeformatter) + ", " + endDate.format(dateformatter);
			}
		}
	}
	
	public boolean isEqual(Task other){		
		if(!this.getName().equals(other.getName()) || this.getTaskType() != other.getTaskType()){
			return false;
		}
		
		if(this.getTaskType() == TaskType.FLOATING){
			return true;
		} else if(this.getTaskType() == TaskType.DEADLINE){
			return this.getEndDate().equals(other.getEndDate()) && this.getEndTime().equals(other.getEndTime());
		} else if(this.getTaskType() == TaskType.TODO){
			return this.getStartDate().equals(other.getStartDate()) && this.getStartTime().equals(other.getStartTime());
		} else{
			boolean sameDate = this.getStartDate().equals(other.getStartDate()) && 
					this.getEndDate().equals(other.getEndDate());
			if(this.getStartTime() == null && other.getStartTime() == null){
				return sameDate;
			}
			else if(this.getStartTime() != null && other.getStartTime() != null){
				return sameDate && this.getStartTime().equals(other.getStartTime()) &&
						this.getEndTime().equals(other.getEndTime());
			}else{
				return false;
			}
		}
	}	
}
