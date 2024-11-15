package com.andre.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.andre.model.Property;
import com.opencsv.CSVWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CSVUtility {
	private static final Logger logger = LogManager.getLogger(CSVUtility.class);
	
	private CSVUtility() {
	}
	
	public static void writeOutListOfProperties(String path, List<Property> properties) {
	    File file = new File(path); 
	    try(CSVWriter writer = new CSVWriter(new FileWriter(file))) { 
	        // adding header to csv 
	        String[] header = { "Address", "Price", "Square Footage", "Beds/Baths", "Open House", "Status", "Days On Market", "Contruction Material", "Year Build", "Flooring", "Heating", "Listing Terms", "Monthly Tax Value"}; 
	        writer.writeNext(header); 
	  
	        // add data to csv 
	        writeOutlListOfProperties(properties, writer);
	    } 
	    catch (IOException e) {
	    	logger.error(e);
	    } 
	}
	
	private static void writeOutlListOfProperties(List<Property> properties, CSVWriter writer) {
		properties.forEach(property -> {
			String[] data = { property.getAddress(), String.valueOf(property.getPrice()), String.valueOf(property.getSquareFootage()), property.getBeds() + "/" + property.getBaths(), property.getOpenHouse(), property.getStandardStatus(), String.valueOf(property.getDaysOnMarket()),
						property.getContructionMaterial(), String.valueOf(property.getYearBuilt()), property.getFlooring(), property.getHeating(), property.getListingTerm(), String.valueOf((property.getTax() / 12))
					}; 
	        writer.writeNext(data); 
		});
	}
	
	public static void writeOutAnalysisInput(String path, List<Property> properties) {
		File file = new File(path); 
		try(CSVWriter writer = new CSVWriter(new FileWriter(file))) { 
			// adding header to csv 
			String[] header = {  "Price", "Address", "", "Tax"}; 
			writer.writeNext(header); 
			
			// add data to csv 
			writeOutAnalysisInput(properties, writer);
		} 
		catch (IOException e) {
			logger.error(e);
		} 
	}
	
	private static void writeOutAnalysisInput(List<Property> properties, CSVWriter writer) {
		properties.forEach(property -> {
			String[] data = { String.valueOf(property.getPrice()), property.getAddress(),  "", String.valueOf((property.getTax() / 12)) }; 
			writer.writeNext(data); 
		});
	}

}
