package com.andre;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.andre.http.Constants;
import com.andre.http.Constants.PropertyCriteria;
import com.andre.http.Constants.ReportType;
import com.andre.http.HTTPRequest;
import com.andre.model.Property;
import com.andre.utility.FileUtility;
import com.andre.utility.JSONUtility;
import com.andre.utility.UserInput;
import com.andre.writer.CSVUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Driver {
	private static final Logger logger = LogManager.getLogger(Driver.class);
	
	private static String outputPathDir = "";
	
	// java -jar C:\Users\Andre\git\ConsumeAPI\target\ConsumeAPI-0.0.1-SNAPSHOT-jar-with-dependencies.jar
	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
		logger.info("--------------------------Starting Property Analysis-------------------------------------------------------------");
		String uri = "https://services.onehome.com/graphql?GetSavedListings";
	
		
		var requestPayload1 = FileUtility.getInstance().getResource("request.json")
				.replace(Constants.RequestJson.REQUEST_JSON_LISTING_IDS.getName(), HTTPRequest.getSavedList())
				.replace(Constants.RequestJson.REQUEST_JSON_PAGE_SIZE.getName(), "300");
		String response = HTTPRequest.getHttpRequest(uri, requestPayload1);
		
		logger.info("---------------------------------------------------------------------------------------");
		logger.info(response);
		
		var listings = JSONUtility.getJSONData(response, "listings");
		var properties = JSONUtility.getJSONData(listings.get(0));
		
		List<Property> result = JSONUtility.convertJsonToPropertyObj(properties);
		enrichProperties(result);
		
		
		logger.warn("Please enter an output directory and Q for default local run location and hit enter twice once input is complete");
		
		UserInput.initScanner();
		
		outputPathDir = File.separator+UserInput.takeInUserOutputDirectoryPathInput();
		
		UserInput.closeScanner();
		
		
		writeOutAllReport(result);
		
		writeOutActiveReport(result);
		
		writeOutActiveReport10DaysOldOnMarket(result);
		
		writeOutActiveSoldReport(result);
		
		writeOutActiveAnalysisInput(result);
		
	}
	
	private static void enrichProperties(List<Property> result) throws IOException, InterruptedException, URISyntaxException {
		String uri = "https://services.onehome.com/graphql?ListingById";
		
		for(Property prop : result) {
			String requestJSONStr = FileUtility.getInstance().getResource("requestByListId.json").replace("<ID>", prop.getId());
			
			String response = HTTPRequest.getHttpRequest(uri, requestJSONStr);
			
			JSONUtility.enrichPropertyObj(prop, response);
		}
	
		
	}
	
	private static void writeOutAllReport(List<Property> result) {
		logger.info("START generate report type: 'ALL'");
		
		List<Property> filterList = result.stream()
				.filter(obj -> isInDesiredZip(obj.getAddress()))
				.filter(obj -> obj.getPrice() <= (int) PropertyCriteria.PRICE_MAX.getValue())
				.filter(obj -> obj.getBeds() > (int) PropertyCriteria.BED_MIN.getValue())
				.filter(obj -> obj.getSquareFootage() <= (int) PropertyCriteria.SQUARE_FOOTAGE_MAX.getValue())
				.filter(obj -> obj.getDaysOnMarket() <= (int) PropertyCriteria.DAYS_ON_MARKET_MAX.getValue())
				.filter(obj -> obj.getTax() <= (Double) PropertyCriteria.YEARLY_TAX_MAX.getValue())
				.sorted((a, b) -> b.getPrice().compareTo(a.getPrice()))
				.toList();
			
	
		finishingWriteoutSteps(filterList, ReportType.REPORT_ALL.getValue());
	}
	
	
	private static void writeOutActiveReport(List<Property> result) {
		logger.info("START generate report type: 'Active'");
		
		List<Property> filterList = result.stream()
				.filter(obj -> isInDesiredZip(obj.getAddress()))
				.filter(obj -> obj.getPrice() <= (int) PropertyCriteria.PRICE_MAX.getValue())
				.filter(obj -> obj.getBeds() > (int) PropertyCriteria.BED_MIN.getValue())
				.filter(obj -> obj.getSquareFootage() <= (int) PropertyCriteria.SQUARE_FOOTAGE_MAX.getValue())
				.filter(obj -> PropertyCriteria.ACTIVE_STATUS.getValue().equals(obj.getStandardStatus()))
				.filter(obj -> obj.getDaysOnMarket() <= (int) PropertyCriteria.DAYS_ON_MARKET_MAX.getValue())
				.filter(obj -> obj.getTax() <= (Double) PropertyCriteria.YEARLY_TAX_MAX.getValue())
				.filter(obj -> hasDesiredSiding(obj.getContructionMaterial()))
				.sorted((a, b) -> b.getPrice().compareTo(a.getPrice()))
				.toList();
		
		finishingWriteoutSteps(filterList, ReportType.REPORT_ACTIVE.getValue());
	}
	
	private static void writeOutActiveReport10DaysOldOnMarket(List<Property> result) {
		logger.info("START generate report type: 'Active' 14Days old");
		
		List<Property> filterList = result.stream()
				.filter(obj -> isInDesiredZip(obj.getAddress()))
				.filter(obj -> obj.getPrice() <= (int) PropertyCriteria.PRICE_MAX.getValue())
				.filter(obj -> obj.getBeds() > (int) PropertyCriteria.BED_MIN.getValue())
				.filter(obj -> obj.getSquareFootage() <= (int) PropertyCriteria.SQUARE_FOOTAGE_MAX.getValue())
				.filter(obj -> PropertyCriteria.ACTIVE_STATUS.getValue().equals(obj.getStandardStatus()))
				.filter(obj -> obj.getDaysOnMarket() <= 14)
				.filter(obj -> obj.getTax() <= (Double) PropertyCriteria.YEARLY_TAX_MAX.getValue())
				.filter(obj -> hasDesiredSiding(obj.getContructionMaterial()))
				.sorted((a, b) -> b.getPrice().compareTo(a.getPrice()))
				.toList();
		
		finishingWriteoutSteps(filterList, ReportType.REPORT_ACTIVE_14DAYS.getValue());
	}
	
	private static void writeOutActiveSoldReport(List<Property> result) {
		logger.info("START generate report type: 'Sold'");
		
		List<Property> filterList = result.stream()
				.filter(obj -> isInDesiredZip(obj.getAddress()))
				.filter(obj -> obj.getPrice() <= (int) PropertyCriteria.PRICE_MAX.getValue())
				.filter(obj -> obj.getBeds() > (int) PropertyCriteria.BED_MIN.getValue())
				.filter(obj -> obj.getSquareFootage() <= (int) PropertyCriteria.SQUARE_FOOTAGE_MAX.getValue())
				.filter(obj -> !PropertyCriteria.ACTIVE_STATUS.getValue().equals(obj.getStandardStatus()))
				.filter(obj -> obj.getDaysOnMarket() <= (int) PropertyCriteria.DAYS_ON_MARKET_MAX.getValue())
				.filter(obj -> obj.getTax() <= (Double) PropertyCriteria.YEARLY_TAX_MAX.getValue())
				.sorted((a, b) -> b.getPrice().compareTo(a.getPrice()))
				.toList();
		
		finishingWriteoutSteps(filterList, ReportType.REPORT_SOLD.getValue());
	}
	
	private static void writeOutActiveAnalysisInput(List<Property> result) {
		logger.info("START generate 'Active' analysis input");
		
		List<Property> filterList = result.stream()
				.filter(obj -> isInDesiredZip(obj.getAddress()))
				.filter(obj -> obj.getPrice() <= (int) PropertyCriteria.PRICE_MAX.getValue())
				.filter(obj -> obj.getBeds() > (int) PropertyCriteria.BED_MIN.getValue())
				.filter(obj -> obj.getSquareFootage() <= (int) PropertyCriteria.SQUARE_FOOTAGE_MAX.getValue())
				.filter(obj -> PropertyCriteria.ACTIVE_STATUS.getValue().equals(obj.getStandardStatus()))
				.filter(obj -> obj.getDaysOnMarket() <= (int) PropertyCriteria.DAYS_ON_MARKET_MAX.getValue())
				.filter(obj -> obj.getTax() <= (Double) PropertyCriteria.YEARLY_TAX_MAX.getValue())
				.filter(obj -> hasDesiredSiding(obj.getContructionMaterial()))
				.sorted((a, b) -> a.getPrice().compareTo(b.getPrice()))
				.toList();
		
		finishingWriteoutStepsAnalysisInput(filterList, ReportType.ACTIVE_ANALYSIS_INPUT.getValue());
	}
	
	private static void finishingWriteoutSteps(List<Property> filterList, String path) {
		filterList.forEach(logger::info);
		
		if(StringUtils.isBlank(outputPathDir.replace(File.separator, "")))
			CSVUtility.writeOutListOfProperties("./src/main/resources/output/"+path, filterList);
		else
			CSVUtility.writeOutListOfProperties(outputPathDir+path, filterList);
	}
	
	private static void finishingWriteoutStepsAnalysisInput(List<Property> filterList, String path) {
		filterList.forEach(logger::info);
		
		if(StringUtils.isBlank(outputPathDir.replace(File.separator, "")))
			CSVUtility.writeOutAnalysisInput("./src/main/resources/output/"+path, filterList);
		else
			CSVUtility.writeOutAnalysisInput(outputPathDir+path, filterList);
	}
	
	private static boolean isInDesiredZip(String address) {
		String[] neighborHoods = {"14609", "14613", "14621", "14620", "14606", "14607", "14608", "14611"};
		
		return isInDesired(neighborHoods, address);
	}
	
	private static boolean hasDesiredSiding(String buildMaterial) {
		String[] sidings = {"Composite", "Aluminum", "Steel", "Vinyl"};
		
		return isInDesired(sidings, buildMaterial);
	}
	
	private static boolean isInDesired(String[] desired, String input) {
		for(String desiredEntry : desired) {
			if(input.contains(desiredEntry))
				return true;
		}
		
		return false;
	}

}
