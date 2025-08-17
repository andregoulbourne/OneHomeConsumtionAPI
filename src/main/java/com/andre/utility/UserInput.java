package com.andre.utility;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for handling user input in the console.
 * This class provides methods to take user input, process it, and manage whitespace.
 */
public class UserInput {
	private static final Logger logger = LogManager.getLogger(UserInput.class);

	private static final char WHITE_SPACE_CHARACTER = ' ';
	
	private static boolean isWhitespaceAtBeginningAndEndOk;

	private static Scanner in;
	
	static {
		isWhitespaceAtBeginningAndEndOk = true;
	}
	
	private UserInput() {
	}

	/**
	 * Initializes the scanner for user input.
	 * This method should be called before taking any user input.
	 */
	public static void initScanner() {
		in = new Scanner(System.in);
	}


	/**
	 * Takes user input for the output directory path.
	 * If the user inputs "Q", it returns an empty string.
	 *
	 * @return the user input as a string, or an empty string if "Q" is entered
	 */
	public static String takeInUserOutputDirectoryPathInput() {
		var result = scanString(in);
		
		logger.warn("input: [ {} ]", result);
		
		if(result.equalsIgnoreCase("Q"))
			result = "";
		
		return result;
	}

	/**
	 * Closes the scanner to release resources.
	 * This method should be called when user input is no longer needed.
	 */
	public static void closeScanner() {
		in.close();
	}
	
	private static String scanString(Scanner in) {
		try {
			StringBuilder string = new StringBuilder();
			String temp = in.nextLine();
			while(StringUtils.isNotBlank(temp)){
				string.append(temp);
				temp = in.nextLine();
			}
			return isWhitespaceAtBeginningAndEndOk ? string.toString() : getRidOfWhiteSpace(string.toString());
		} catch(Exception e) {
			logger.error(e);
			return "";
		}
	}
	
	private static String getRidOfWhiteSpace(String string) {
		StringBuilder result = new StringBuilder();
		
		
		if(containsWhiteSpace(string)) {
			boolean flag = false;
			int countWhitespace = 0;
			for(int i=0; i<string.length(); i++) {
				if(isValidForInput(string.charAt(i)) && !flag) {
					flag = true;
					countWhitespace = 0;
				}
				if(countWhitespace > 1 && flag) break;
				if(WHITE_SPACE_CHARACTER == string.charAt(i) && flag) countWhitespace +=1;
				if(flag && isValidForInput(string.charAt(i)) || countWhitespace == 1) {
					result.append(string.charAt(i));
					if(countWhitespace != 0 && isValidForInput(string.charAt(i))) countWhitespace = 0;
				}	
			}
			
		} else {
			return string;
		}
		String myAnswer = result.toString();
		
		if(myAnswer.charAt(myAnswer.length()-1)== WHITE_SPACE_CHARACTER) return cutLastCharacter(myAnswer);
		return myAnswer;
	}
	
	protected static boolean isValidForInput(char a) {
		return ' ' != a;
	}
	
	private static boolean containsWhiteSpace(String string) {
		return string.contains(" ");
	}
	
	private static String cutLastCharacter(String string) {
		return string.substring(0, string.length()-2);
	}

}
