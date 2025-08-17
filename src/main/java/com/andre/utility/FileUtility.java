package com.andre.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Objects;

import com.andre.Driver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility class for handling file operations related to resources.
 * This class provides methods to read resource files and convert them into strings.
 * It follows the Singleton design pattern to ensure only one instance is created.
 */
public class FileUtility {
	private static final Logger logger = LogManager.getLogger(FileUtility.class);

	private FileUtility() {}
	
	private static class SingletonHelper {
       private static final FileUtility INSTANCE = new FileUtility();
    }

	/**
	 * Returns the singleton instance of FileUtility.
	 * This method ensures that only one instance of FileUtility is created and used throughout the application.
	 *
	 * @return the singleton instance of FileUtility
	 */
	public static FileUtility getInstance() {
		 return SingletonHelper.INSTANCE;
	}

	/**
	 * Reads a resource file and returns its content as a string.
	 * If the resource is not found, it attempts to read from a local file.
	 *
	 * @param resourceFileName the name of the resource file to read
	 * @return the content of the resource file as a string
	 */
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
