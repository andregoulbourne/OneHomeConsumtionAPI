package com.andre.utility;

import static com.andre.json.utility.JSONUtility.*;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.andre.model.Property;

/**
 * Utility class for handling JSON data related to properties.
 * This class provides methods to extract property information from JSON strings,
 * convert JSON data into Property objects, and enrich Property objects with additional data.
 */
public class JSONUtility {
	private static final Logger logger = LogManager.getLogger(com.andre.utility.JSONUtility.class);

	private JSONUtility() {
	}

	/**
	 * Extracts JSON data from a given JSON string.
	 *
	 * @param json the JSON string to extract data from
	 * @return a list of strings representing the extracted JSON data
	 */
	public static List<String> getJSONData(String json){
		return com.andre.json.utility.JSONUtility.getJSONData(json);
	}

	/**
	 * Extracts specific field data from a given JSON string.
	 *
	 * @param json the JSON string to extract data from
	 * @param field the field name to extract data for
	 * @return a list of strings representing the extracted field data
	 */
	public static List<String> getJSONData(String json, String field){
		return com.andre.json.utility.JSONUtility.getJSONData(json, field);
	}

	/**
	 * Extacts a list of Property objects from a list of JSON strings.
	 *
	 * @param propertyJSONs the list of JSON strings representing properties
	 * @return a list of {@link Property} objects created from the JSON strings
	 */
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

	/**
	 * Enriches a Property object with additional data from a JSON string.
	 * This method adds fields to the Property object based on annotations.
	 *
	 * @param property the Property object to enrich
	 * @param propertyJSON the JSON string containing additional data for the Property
	 */
	public static void enrichPropertyObj(Property property, String propertyJSON){
		addFieldsFromAnnotations(property, propertyJSON, true);
	}
}
