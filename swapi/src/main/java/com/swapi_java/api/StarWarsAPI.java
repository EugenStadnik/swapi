/**
 * 
 */
package com.swapi_java.api;

import static io.restassured.RestAssured.given;

import com.google.common.net.MediaType;

import io.netty.handler.codec.http.HttpMethod;
import io.restassured.response.Response;

/**
 * @author ystadnik
 *
 */
public class StarWarsAPI {
	
	private String baseURL;
	private String peopleResource = "people";
	private String planetsResource = "planets";
	private String filmsResource = "films";
	
	public StarWarsAPI(String baseURL) {
		this.baseURL = baseURL;
	}
	
	public Response queryToAPI(String METHOD, String resource, String schema, String contentType) {
		String requestURL = baseURL + "/" + resource + "/" + schema;
		Response response;
		System.out.println("Request to query " + resource + " resource by " + schema + " schema:\n\t"
				+ METHOD + " " + requestURL);
		response = given().contentType(contentType).request(METHOD, requestURL);
		System.out.println("Response to query " + resource + " resource by " + schema + " schema:\n\t"
				+ response.getStatusLine() + "\n\t"
				+ response.then().extract().response().asString());
		return response;
	}
	
	public Response queryToAPI(String METHOD, String requestURL) {
		Response response;
		System.out.println("Request to query using this URL:\n\t"
				+ METHOD + " " + requestURL);
		response = given().contentType(MediaType.FORM_DATA.toString()).request(METHOD, requestURL);
		System.out.println("Response to query using URL:\n\t"
				+ response.getStatusLine() + "\n\t"
				+ response.then().extract().response().asString());
		return response;
	}
	
	public Response queryPersonByID(String id) {
		return queryPersonByID(HttpMethod.GET.toString(), id);
	}
	
	public Response queryPersonByID(String METHOD, String id) {
		return queryToAPI(METHOD, peopleResource, id, MediaType.FORM_DATA.toString());
	}
	
	public Response queryPlanetByID(String id) {
		return queryPlanetByID(HttpMethod.GET.toString(), id);
	}
	
	public Response queryPlanetByID(String METHOD, String id) {
		return queryToAPI(METHOD, planetsResource, id, MediaType.FORM_DATA.toString());
	}
	
	public Response queryFilmByID(String id) {
		return queryFilmByID(HttpMethod.GET.toString(), id);
	}
	
	public Response queryFilmByID(String METHOD, String id) {
		return queryToAPI(METHOD, filmsResource, id, MediaType.FORM_DATA.toString());
	}
	
}
