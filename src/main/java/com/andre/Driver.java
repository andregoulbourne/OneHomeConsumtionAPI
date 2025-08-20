package com.andre;

import com.andre.http.HTTPRequest;
import com.andre.model.Property;
import com.andre.service.WriteOutService;
import com.andre.service.WriteOutServiceImpl;
import com.andre.utility.Constants;
import com.andre.utility.FileUtility;
import com.andre.utility.JSONUtility;
import com.andre.utility.UserInput;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class Driver {
	private static final Logger logger = LogManager.getLogger(Driver.class);

	// java -jar ./build/libs/OneHomeConsumtionAPI-0.0.1-SNAPSHOT.jar
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

		WriteOutService writeOutService = new WriteOutServiceImpl(File.separator+UserInput.takeInUserOutputDirectoryPathInput());

		UserInput.closeScanner();


		writeOutService.writeOutAllReport(result);

		writeOutService.writeOutActiveReport(result);

		writeOutService.writeOutActiveReport14DaysOldOnMarket(result);

		writeOutService.writeOutActiveSoldReport(result);

		writeOutService.writeOutActiveAnalysisInput(result);
		
	}
	
	private static void enrichProperties(List<Property> result) throws IOException, InterruptedException, URISyntaxException {
		String uri = "https://services.onehome.com/graphql?ListingById";
		
		for(Property prop : result) {
			String requestJSONStr = FileUtility.getInstance().getResource("requestByListId.json").replace("<ID>", prop.getId());
			
			String response = HTTPRequest.getHttpRequest(uri, requestJSONStr);
			
			JSONUtility.enrichPropertyObj(prop, response);
		}
	}

}
