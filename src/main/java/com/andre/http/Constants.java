package com.andre.http;

public class Constants {
	
	private Constants(){
	}

	public static final String EXCEPTION = "An exception occurred ...";
	
	public enum ReportType {
		REPORT_ALL("report_all.csv"),REPORT_ACTIVE("report_active.csv"),REPORT_ACTIVE_14DAYS("report_active_14_days.csv"),REPORT_SOLD("report_sold.csv"),ACTIVE_ANALYSIS_INPUT("analysis_input.csv");
		
		private final String value;
		
		public String getValue(){
			return value;
		}
		
		ReportType(String value){
			this.value = value;
		}
	}
	
	
	public enum PropertyCriteria {
		SQUARE_FOOTAGE_MAX(2500),PRICE_MAX(122000),YEARLY_TAX_MAX((double) 300 * 12),BED_MIN(3),DAYS_ON_MARKET_MAX(120),ACTIVE_STATUS("Active");
		
		private final Object value;
		
		public Object getValue(){
			return value;
		}
		
		PropertyCriteria(Object value){
			this.value = value;
		}
	}
	
	
	public enum RequestJson {
		REQUEST_JSON_LISTING_IDS("<REQUEST_JSON_LISTING_IDS>"), REQUEST_JSON_PAGE_SIZE("<REQUEST_JSON_PAGE_SIZE>"),REQUEST_JSON_GROUP_ID("<REQUEST_JSON_GROUP_ID>");
		
		private final String name;
		
		public String getName() {
			return name;
		}
		
		RequestJson(String name) {
			this.name=name;
		}
	}
	
	public static final String PIPE_DELIM = "\\|";
	
}
