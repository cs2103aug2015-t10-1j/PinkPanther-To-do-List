/* @@author GB */
package common;

public class Display {
	private static String message;
	
	public static void setFeedBack(String feedback){
		message=feedback;
	}
	
	
	public static String showFeedBack(){
		return message;
	}
}
