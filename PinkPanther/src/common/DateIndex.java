package common;

import java.time.LocalDate;

public class DateIndex {
	private LocalDate date;
	private int index;
	
	public DateIndex(LocalDate date, int index){
		this.date=date;
		this.index=index;
	}
	
	public LocalDate getDate(){
		return date;
	}
	
	public int getIndex(){
		return index;
	}
}
