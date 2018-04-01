/**
 * 
 */
package com.swapi_java.responses;

/**
 * @author ystadnik
 *
 */
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.restassured.response.Response;

public class BaseResponse {

	protected JsonParser parser;

	public BaseResponse() {
		this.parser = new JsonParser();
	}

	/**
	 *  The org.restlet.data.Status retrieves only code and reason
	 *  But io.restassured.response.Response has no method to return reason only
	 * 
	 * @author ystadnik
	 *
	 */
	public enum StatusLines {
		_200_OK("HTTP/1.1 200 OK"),
		_201_CREATED("HTTP/1.1 201 Created"),
		_204_NO_CONTENT("HTTP/1.1 204 No Content"),
		_400_BAD_REQUEST("HTTP/1.1 400 Bad Request"),
		_401_UNAUTHORIZED("HTTP/1.1 401 Unauthorized"),
		_403_FORBIDDEN("HTTP/1.1 403 Forbidden"),
		_404_NOT_FOUND("HTTP/1.1 404 Not Found"),
		_405_METHOD_NOT_ALLOWED("HTTP/1.1 405 Method Not Allowed"),
		_410_GONE("HTTP/1.1 410 Gone"),
		_429_TOO_MANY_REQUEST("HTTP/1.1 429 Too Many Requests"),
		_500_INTERNAL_SERVER_ERROR("HTTP/1.1 500 Internal Server Error");
		private final String statusLine;

		private StatusLines(final String statusLine) {
			this.statusLine = statusLine;
		}

		public String toString() {
			return statusLine;
		}
	}

	public enum Bodies {
		NOT_FOUND("Not Found"),
		METHOD_NOT_ALLOWED("Method Not Allowed");
		private final String body;

		private Bodies(final String body) {
			this.body = body;
		}

		public String toString() {
			return body;
		}
	}

	public String getRequestMethodXNotSupportedStatusLine(String METHOD) {
		return "HTTP/1.1 405 Request method '" + METHOD + "' not supported";
	}

	public String parceValueFromBodyByKey(Response response, String key) {
		return ((JsonObject) parser.parse(response.body().asString())).get(key).getAsString();
	}

	/**
	 * Verifies {@code 200 OK} status Line with empty body
	 *
	 * @param response
	 *            A {@code Response}
	 */
	public void verify200OK(Response response) {
		response.then().assertThat().statusLine(StatusLines._200_OK.toString());
		assertThat(response.body().asString(), equalTo(""));
	}

	/**
	 * Verifies {@code 404 Not Found} status Line with standard {@code Not Found}
	 * body.
	 *
	 * @param response
	 *            A {@code Response}
	 */
	public void verify404NotFound(Response response) {
		response.then().assertThat().statusLine(StatusLines._404_NOT_FOUND.toString());
		assertThat(response.body().asString(), equalTo(Bodies.NOT_FOUND.toString()));
	}

	/**
	 * Verifies {@code 405 Method Not Allowed} status Line with standard
	 * {@code Method Not Allowed} body.
	 *
	 * @param response
	 *            A {@code Response}
	 */
	public void verify405MethodNotAllowed(Response response) {
		assertThat(response.statusLine(), equalTo(StatusLines._405_METHOD_NOT_ALLOWED.toString()));
		assertThat(response.body().asString(), equalTo(Bodies.METHOD_NOT_ALLOWED.toString()));
	}

	/**
	 * Verifies {@code 405 Request method '$METHOD' not supported} status Line with
	 * empty body.
	 *
	 * @param response
	 *            A {@code Response}
	 * 
	 * @param METHOD
	 *            A {@code String}
	 */
	public void verify405RequestMethodXNotSupported(Response response, String METHOD) {
		assertThat(response.statusLine(), equalTo(getRequestMethodXNotSupportedStatusLine(METHOD)));
		assertThat(response.body().asString(), equalTo(""));
	}

}