package com.andre.utility;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class UserInput {
	
	private static final Logger logger = Logger.getLogger(UserInput.class);
	
	private static boolean isWhitespaceAtBeginningAndEndOk;
	
	static {
		isWhitespaceAtBeginningAndEndOk = true;
	}
	
	private UserInput() {
	}
	
	private static Scanner in;
		
	public static void initScanner() {
		in = new Scanner(System.in);
	}

	
	public static String takeInUserOutputDirectoryPathInput() {
		var result = scanString(in);
		
		logger.warn("input: [" + result + "]");
		
		if(result.equalsIgnoreCase("Q"))
			result = "";
		
		return result;
	}
	
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
	
	private static final char WHITESPACECHARACTER = " ".charAt(0);
	
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
				if(WHITESPACECHARACTER == string.charAt(i) && flag) countWhitespace +=1;
				if(flag && isValidForInput(string.charAt(i)) || countWhitespace == 1) {
					result.append(string.charAt(i));
					if(countWhitespace != 0 && isValidForInput(string.charAt(i))) countWhitespace = 0;
				}	
			}
			
		} else {
			return string;
		}
		String myAnswer = result.toString();
		
		if(myAnswer.charAt(myAnswer.length()-1)==WHITESPACECHARACTER) return cutLastCharacter(myAnswer);
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


	public static void setWhitespaceAtBeginningAndEndOk(boolean isWhitespaceAtBeginningAndEndOk) {
		UserInput.isWhitespaceAtBeginningAndEndOk = isWhitespaceAtBeginningAndEndOk;
	}

}
