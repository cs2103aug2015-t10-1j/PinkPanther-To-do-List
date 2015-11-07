/* @@author A0126473E */
package common;

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
	
	public static boolean isEmptyArray(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (!(arr[i].trim().isEmpty())){
				return false;
			}
		}
		return true;
	}
	
	public static boolean isNumber(String possiblyNumber) {
		return !possiblyNumber.isEmpty() && possiblyNumber.replaceAll("[0-9]","").isEmpty();
	}
	
	public static String concatArray(String[] arr) {
		String arrContents = "";
		if (arr != null) {
			arrContents += arr[0];
			for (int i = 1; i < arr.length; i++) {
				arrContents += ", ";
				arrContents += arr[i];
			}
		}
		return arrContents;
	}
	
	public static void displayErrorMessage(String errorMessage) {
		System.out.println(errorMessage);
	}
}
