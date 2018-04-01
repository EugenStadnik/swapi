/**
 * 
 */
package com.swapi_java.helpers;

/**
 * @author ystadnik
 *
 */

public class TestConfiguration {

	private PropertiesInitialization propertiesInitialization;
	private String swapiDomain;
	private String swapiHost;
	private String swapiPort;
	private String swapiAPIURL;
	private String swapiDomainBaseURL;
	private String swapiHostBaseURL;
	private String loginDomain;
	private String loginHost;
	private String loginPort;
	private String loginSigninURL;
	private String login;
	private String password;
	private String loginDomainBaseURL;
	private String loginHostBaseURL;
	private String chromeDriverPathWin;
	private String chromeDriverPathPosix;
	
	public TestConfiguration(String pathToFile) {
		propertiesInitialization = PropertiesInitialization.getInstance(pathToFile);
		initProperties();
	}
	
	public PropertiesInitialization getPropertiesInitialization() {
		return propertiesInitialization;
	}

	public String getSwapiDomain() {
		return swapiDomain;
	}

	public String getSwapiHost() {
		return swapiHost;
	}

	public String getSwapiPort() {
		return swapiPort;
	}

	public String getSwapiAPIURL() {
		return swapiAPIURL;
	}

	public String getSwapiDomainBaseURL() {
		return swapiDomainBaseURL;
	}

	public String getSwapiHostBaseURL() {
		return swapiHostBaseURL;
	}

	public String getLoginDomain() {
		return loginDomain;
	}

	public String getLoginHost() {
		return loginHost;
	}

	public String getLoginPort() {
		return loginPort;
	}

	public String getLoginSigninURL() {
		return loginSigninURL;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public String getLoginDomainBaseURL() {
		return loginDomainBaseURL;
	}

	public String getLoginHostBaseURL() {
		return loginHostBaseURL;
	}
	
	public String getChromeDriverPathWin() {
		return chromeDriverPathWin;
	}

	public String getChromeDriverPathPosix() {
		return chromeDriverPathPosix;
	}

	private void initProperties() {
		swapiDomain = propertiesInitialization.getPropertyConfiguration("swapi.domain");
		swapiHost = propertiesInitialization.getPropertyConfiguration("swapi.host");
		swapiPort = propertiesInitialization.getPropertyConfiguration("swapi.port");
		swapiAPIURL = propertiesInitialization.getPropertyConfiguration("swapi.api.url");
		swapiDomainBaseURL="https://" + swapiDomain + swapiAPIURL;
		swapiHostBaseURL = "https://" + swapiHost + ":" + swapiPort + swapiAPIURL;
		loginDomain = propertiesInitialization.getPropertyConfiguration("login.domain");
		loginHost = propertiesInitialization.getPropertyConfiguration("login.host");
		loginPort = propertiesInitialization.getPropertyConfiguration("login.port");
		loginSigninURL = propertiesInitialization.getPropertyConfiguration("login.signin.url");
		login = propertiesInitialization.getPropertyConfiguration("login");
		password = propertiesInitialization.getPropertyConfiguration("password");
		loginDomainBaseURL="https://" + loginDomain + loginSigninURL;
		loginHostBaseURL = "https://" + loginHost + ":" + loginPort + loginSigninURL;
		chromeDriverPathWin = propertiesInitialization.getPropertyConfiguration("chrome.driver.path.win");
		chromeDriverPathPosix = propertiesInitialization.getPropertyConfiguration("chrome.driver.path.posix");
	}

}