package com.andre.utility;

import static com.andre.json.utility.JSONUtility.*;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andre.model.Property;

public class JSONUtility {
	private JSONUtility() {
	}
	
	private static final Logger logger = LogManager.getLogger(com.andre.utility.JSONUtility.class);
	
	public static List<String> getJSONData(String json){
		return com.andre.json.utility.JSONUtility.getJSONData(json);
	}
	
	public static List<String> getJSONData(String json, String field){
		return com.andre.json.utility.JSONUtility.getJSONData(json, field);
	}
	
	public static List<Property> convertJsonToPropertyObj(List<String> propertyJSONs){
		return propertyJSONs.stream()
			.map(propertyJSON -> {
				Property property = new Property();
				property.setAddress(getAddress(propertyJSON));
				property.setOpenHouse(getOpenHouse(propertyJSON));
				addFieldsFromAnnotations(property, propertyJSON, false);
				return property;
			}).toList();
	}
	
	private static String getAddress(String propertyJSON) {
		var streetNumber = santizeResult(getJSONData(propertyJSON, "StreetNumber").get(0));
		var streetName = santizeResult(getJSONData(propertyJSON, "StreetName").get(0));
		var streetSuffix = santizeResult(getJSONData(propertyJSON, "StreetSuffix").get(0));
		var postalCity = santizeResult(getJSONData(propertyJSON, "PostalCity").get(0));
		var city = santizeResult(getJSONData(propertyJSON, "City").get(0));
		var state = santizeResult(getJSONData(propertyJSON, "StateOrProvince").get(0));
		var postalCode = santizeResult(getJSONData(propertyJSON, "PostalCode").get(0));
		
		return String.format("%s %s %s %s %s %s %s", streetNumber, streetName, streetSuffix, postalCity, city, state, postalCode);
		
	}
	
	private static String getOpenHouse(String propertyJSON) {
		var result = "";
		
		var openHouseJSONStrings = getJSONData(propertyJSON, "openHouse");
		
		if(!openHouseJSONStrings.isEmpty()) {
			var openHouse = openHouseJSONStrings.get(0);
			if(!openHouse.equals("null")) {
				try {
					var date = santizeResult(getJSONData(propertyJSON, "OpenHouseDate").get(0));
					var endTime = santizeResult(getJSONData(propertyJSON, "OpenHouseEndTime").get(0));
					var startTime = santizeResult(getJSONData(propertyJSON, "OpenHouseStartTime").get(0));
					
					result = String.format("%s | %s | %s", date, endTime, startTime);
				} catch (Exception e) {
					logger.error(e);
				}
				
			}
		}
		
		return result;
	}
	
	public static void enrichPropertyObj(Property property, String propertyJSON){
		addFieldsFromAnnotations(property, propertyJSON, true);
	}
}
