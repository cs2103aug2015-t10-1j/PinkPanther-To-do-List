package parser;

public class Auxiliary {
	
	public static String getFirstWord(String userInput) {
		String commandTypeString = userInput.trim().split("\\s+")[0];
		return commandTypeString;
	}
	
	public static String removeFirstWord(String userInput) {
		String commandContent = userInput.replaceFirst(getFirstWord(userInput), "").trim();
		return commandContent;
	}
	
	public static String[] trimStringArray(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = arr[i].trim();
		}
		return arr;
	}
	
	public static boolean isNumber(String possiblyNumber) {
		return !possiblyNumber.isEmpty() && possiblyNumber.replaceAll("[0-9]","").isEmpty();
	}
}
