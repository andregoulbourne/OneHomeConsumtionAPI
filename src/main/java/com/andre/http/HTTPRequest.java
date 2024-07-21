package com.andre.http;

import static com.andre.json.utility.JSONUtility.santizeResult;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import org.apache.log4j.Logger;

import com.andre.utility.FileUtility;
import com.andre.utility.JSONUtility;

public class HTTPRequest {
	
	private HTTPRequest() {
	}
	
	private static String[] authorization;
	
	private static String apiKey;
	
	private static final Logger logger = Logger.getLogger(HTTPRequest.class);

	public static String getHttpRequest(String uri, String jsonString) throws IOException, InterruptedException, URISyntaxException {
		var httpRequest = HttpRequest.newBuilder()
				.uri(new URI(uri))
				.header("Authorization", apiKey)
				.POST(BodyPublishers.ofString(jsonString))
				.build();
		
		
		var httpClient = HttpClient.newHttpClient();	
		
		var httpResponse = httpClient.send(httpRequest, BodyHandlers.ofString());
	
		logger.debug("HTTP Response {}" + httpResponse.body());
		
		return httpResponse.body();
	}
	
	private static String setAuthorization1() throws IOException, InterruptedException, URISyntaxException {
		authorization = FileUtility.getInstance().getResource("authorization").split(Constants.PIPE_DELIM);
		
		// run http to get the token
		var httpRequest = HttpRequest.newBuilder()
				.uri(new URI(authorization[0]))
				.build();
		
		
		var httpClient = HttpClient.newHttpClient();	
		
		var httpResponse = httpClient.send(httpRequest, BodyHandlers.ofString());
		
		return httpResponse.body();
	}
	
	private static String setAuthorization2() throws IOException, InterruptedException, URISyntaxException {
		// run http to get the token
		var httpRequest = HttpRequest.newBuilder()
				.uri(new URI(authorization[1]))
				.header("Content-Type", "application/json")
				.POST(BodyPublishers.ofString(authorization[2]))
				.build();
		
		
		var httpClient = HttpClient.newHttpClient();	
		
		var httpResponse = httpClient.send(httpRequest, BodyHandlers.ofString());
		
		return httpResponse.body();
	}
	
	public static String getSavedList() throws IOException, InterruptedException, URISyntaxException {
		setAuthorization1();
		
		var session = setAuthorization2();
		
		apiKey = "Bearer " + santizeResult(JSONUtility.getJSONData(session, "sessionToken").get(0));
		
		
		var httpRequest = HttpRequest.newBuilder()
				.uri(new URI(authorization[3]))
				.header("Authorization", apiKey)
				.POST(BodyPublishers.ofString(authorization[4]))
				.build();
		
		
		var httpClient = HttpClient.newHttpClient();	
		
		var httpResponse = httpClient.send(httpRequest, BodyHandlers.ofString());
		
		return JSONUtility.getJSONData(httpResponse.body(), "listingIds").get(0);
	}
	
}
