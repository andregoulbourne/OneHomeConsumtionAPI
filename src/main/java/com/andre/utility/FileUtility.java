package com.andre.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Objects;

import com.andre.Driver;
import com.andre.http.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FileUtility {
	
	private FileUtility() {}
	
	private static class SingletonHelper {
       private static final FileUtility INSTANCE = new FileUtility();
    }
	
	public static FileUtility getInstance() {
		 return SingletonHelper.INSTANCE;
	}
	
	private static final Logger logger = LogManager.getLogger(FileUtility.class);

	public String getResource(String resourceFileName) {
		var result = new StringBuilder();
		
		try (BufferedReader br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(Driver.class.getClassLoader().getResourceAsStream(resourceFileName))))) {
			var sCurrentLine="";
			while((sCurrentLine = br.readLine()) != null) {
				result.append(sCurrentLine);
			}
		} catch(Exception e) {
			logger.error(Constants.EXCEPTION, e);
		}
		
		if("".contentEquals(result))
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
