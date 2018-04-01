/**
 * 
 */
package com.swapi_java.tests.api;

import org.junit.runners.MethodSorters;

import com.google.gson.*;
import com.swapi_java.api.StarWarsAPI;
import com.swapi_java.helpers.TestConfiguration;
import com.swapi_java.responses.StarWarsAPIResponse;
import com.swapi_java.responses.BaseResponse.*;
import com.swapi_java.responses.StarWarsAPIResponse.*;

import io.netty.handler.codec.http.HttpMethod;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.*;

/**
 * @author ystadnik
 * This test suit tests http://swapi.co/documentation API
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StarWarsAPITests extends TestConfiguration {
	
	private static String path = "./config.properties";
	private static JsonParser parser = new JsonParser();
	private StarWarsAPI starWarsAPI;
	private StarWarsAPIResponse starWarsAPIResponse;
	private JsonObject personJsonObject;
	private String LukeSkiwalkersID = "1";
	
	public StarWarsAPITests() {
		super(path);
		String baseURL = getSwapiDomainBaseURL();
		System.out.println("Star Wars API base URL: " + baseURL);
		starWarsAPI = new StarWarsAPI(baseURL);
		starWarsAPIResponse = new StarWarsAPIResponse();
		RestAssured.defaultParser = Parser.JSON;
	}
	
	private JsonObject findValidPersonByID(String id) {
		Response queryPersonResponse = starWarsAPI.queryPersonByID(LukeSkiwalkersID);
		queryPersonResponse.then().assertThat().statusLine(StatusLines._200_OK.toString());
		return (JsonObject)parser.parse(queryPersonResponse.getBody().asString());
	}
	
	private JsonObject getValidJSONObject(String URL) {
		Response queryResponse = starWarsAPI.queryToAPI(HttpMethod.GET.toString(), URL);
		queryResponse.then().assertThat().statusLine(StatusLines._200_OK.toString());
		return (JsonObject)parser.parse(queryResponse.getBody().asString());
	}
	
	@BeforeClass
	public static void oneTimeSetUp() throws Exception {
		System.out.println("@BeforeClass method processing...: ");		
	}
	
	@Before
	public void setUp() throws Exception {
		System.out.println("@Before method processing...: ");
		personJsonObject = findValidPersonByID(LukeSkiwalkersID);
	}

	@After
	public void tearDown() {
		System.out.println("@After method processing...: ");
	}
	
	@AfterClass
	public static void oneTimeTearDown() throws Exception {
		System.out.println("@AfterClass method processing...: ");
	}
	
	/**
	 * this test tests first case from
	 * https://www.signalhire.com/jobs/maxpay/automation-qa-engineer-2/djo5MTkyO2M6ODkyOA==/file/86e87fc19e75c0043f40beb20baea3dd/download
	 * task
	 */
	@Test
	public void test01_checkLukeSkywalker() throws Exception {
		assertThat(personJsonObject.get(PersonValidKeys.NAME.toString()).getAsString()
				, equalTo(starWarsAPIResponse.getValidPersons().get(LukeSkiwalkersID).get(PersonValidKeys.NAME.toString()).getAsString()));
	}
	
	/**
	 * this test tests second case from
	 * https://www.signalhire.com/jobs/maxpay/automation-qa-engineer-2/djo5MTkyO2M6ODkyOA==/file/86e87fc19e75c0043f40beb20baea3dd/download
	 * task
	 */
	@Test
	public void test02_checkLukeSkywalkersPlanet() throws Exception {
		String TatooineID = "1";
		String homeworldURL = personJsonObject.get(PersonValidKeys.HOMEWORLD.toString()).getAsString();
		JsonObject homeworldJsonObject = getValidJSONObject(homeworldURL);
		assertThat(homeworldJsonObject.get(PlanetValidKeys.NAME.toString()).getAsString()
				, equalTo(starWarsAPIResponse.getValidPlanets().get(TatooineID).get(PlanetValidKeys.NAME.toString()).getAsString()));
		assertThat(homeworldJsonObject.get(PlanetValidKeys.POPULATION.toString()).getAsString()
				, equalTo(starWarsAPIResponse.getValidPlanets().get(TatooineID).get(PlanetValidKeys.POPULATION.toString()).getAsString()));
	}
	
	/**
	 * this test tests third case from
	 * https://www.signalhire.com/jobs/maxpay/automation-qa-engineer-2/djo5MTkyO2M6ODkyOA==/file/86e87fc19e75c0043f40beb20baea3dd/download
	 * task
	 */
	@Test
	public void test03_checkClonesAttackAttributes() throws Exception {
		String attackClonesID = "5";
		String homeworldURL = personJsonObject.get(PersonValidKeys.HOMEWORLD.toString()).getAsString();
		JsonObject homeworldJsonObject = getValidJSONObject(homeworldURL);
		String filmURL = homeworldJsonObject.getAsJsonArray(PlanetValidKeys.FILMS.toString()).get(0).getAsString();
		JsonObject filmJsonObject = getValidJSONObject(filmURL);
		assertThat(filmJsonObject.get(FilmValidKeys.TITLE.toString()).getAsString()
				, equalTo(starWarsAPIResponse.getValidFilms().get(attackClonesID).get(FilmValidKeys.TITLE.toString()).getAsString()));
		assertThat(filmJsonObject.getAsJsonArray(FilmValidKeys.PLANETS.toString()).toString()
				, containsString(homeworldJsonObject.get(PlanetValidKeys.URL.toString()).getAsString()));
		assertThat(filmJsonObject.getAsJsonArray(FilmValidKeys.CHARACTERS.toString()).toString()
				, containsString(personJsonObject.get(PlanetValidKeys.URL.toString()).getAsString()));
	}
	
}
