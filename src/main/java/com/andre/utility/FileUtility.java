package com.andre.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.andre.Driver;
import com.andre.http.Constants;

public class FileUtility {
	
	private FileUtility() {}
	
	private static class SingletonHelper {
       private static final FileUtility INSTANCE = new FileUtility();
    }
	
	public static FileUtility getInstance() {
		 return SingletonHelper.INSTANCE;
	}
	
	private static final Logger logger = Logger.getLogger(FileUtility.class);

	public String getResource(String resourceFileName) {
		var result = new StringBuilder();
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(Driver.class.getClassLoader().getResourceAsStream(resourceFileName)))) {
			var sCurrentLine="";
			while((sCurrentLine = br.readLine()) != null) {
				result.append(sCurrentLine);
			}
		} catch(Exception e) {
			logger.error(Constants.EXCEPTION, e);
		}
		
		if("".equals(result.toString()))
			return getJsonString(new File("./src/main/resources/"+resourceFileName));
		else
			return result.toString();
	}
	
	private String getJsonString(File file) {
		var result = new StringBuilder();
		
		try (BufferedReader br = new BufferedReader(new FileReader(file.getPath()))) {
			var sCurrentLine="";
			while((sCurrentLine = br.readLine()) != null) {
				result.append(sCurrentLine);
			}
		} catch(Exception e) {
			logger.error(Constants.EXCEPTION, e);
		}
		
		return result.toString();
	}
	
}
