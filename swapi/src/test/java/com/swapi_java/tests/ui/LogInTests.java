/**
 * 
 */
package com.swapi_java.tests.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;

import com.swapi_java.helpers.*;
import com.swapi_java.ui.*;
import com.swapi_java.ui.LoginPage.*;
import com.swapi_java.ui.MainPage.*;
import com.swapi_java.ui.abstractpages.MainBarAbstractPage.*;

/**
 * @author ystadnik
 * this test suite tests login page case from
 * https://www.signalhire.com/jobs/maxpay/automation-qa-engineer-2/djo5MTkyO2M6ODkyOA==/file/86e87fc19e75c0043f40beb20baea3dd/download
 * task
 *
 */
public class LogInTests extends TestConfiguration {
	
	private static String propertiesFile = "./config.properties";
	private String baseURL = getLoginDomainBaseURL();
	private String login = getLogin();
	private String password = getPassword();
    private WebDriver driver;
    private LoginPage loginPage;
	
	public LogInTests() {		
		super(propertiesFile);
		System.out.println("LogIn base URL: " + baseURL);
	}
	
	private void verifyMainLoginPageAttributes(LoginPage loginPage) {
		assertThat(loginPage.getWelcomeText(), equalTo(LoginPageElementsTexts.WELLCOME.toString()));
		assertThat(loginPage.getUsernamePlaceholderText(), equalTo(LoginPageElementsTexts.USER_PLACEHOLDER.toString()));
		assertThat(loginPage.getPasswordPlaceholderText(), equalTo(LoginPageElementsTexts.PASSWORD_PLACEHOLDER.toString()));
		assertThat(loginPage.getLogInBtnName(), equalTo(LoginPageElementsTexts.LOGIN_BUTTON.toString()));
	}
	
	@BeforeClass
	public static void oneTimeSetUp() throws Exception {
		System.out.println("@BeforeClass method processing...: ");
		TestConfiguration config = new TestConfiguration(propertiesFile);
		String chromeDriverPath = config.getChromeDriverPathWin();
		System.out.println("The path to Chrome Driver is: " + chromeDriverPath);
		System.setProperty("webdriver.chrome.driver", chromeDriverPath);
	}
	
	@Before
	public void setUp() {
		System.out.println("@Before method processing...: ");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(baseURL);
		loginPage = new LoginPage(driver);
	}

	@After
	public void tearDown() {
		System.out.println("@After method processing...: ");
		if (driver != null)
			driver.quit();
	}

	@AfterClass
	public static void oneTimeTearDown() throws Exception {
		System.out.println("@AfterClass method processing...: ");
	}
	
	@Test
	public void test01_loginPagePositive() {
		verifyMainLoginPageAttributes(loginPage);
		MainPage mainPage = loginPage.logIn(login, password);
		assertThat(mainPage.getDashboardBtnName(), equalTo(BarElementsTexts.DASHBOARD.toString()));
		assertThat(mainPage.getVolumeBtnName(), equalTo(MainPageElementsTexts.VOLUNE.toString()));
	}
	
	@Test
	public void test02_loginPageNoDataNegative() {
		loginPage.clickLogin();
		verifyMainLoginPageAttributes(loginPage);
		assertThat(loginPage.isErrorMessageVisible(), equalTo(false));
	}
	
	@Test
	public void test03_loginPageInvalidDataNegative() {
		loginPage.logInNegative("invalid", password);
		verifyMainLoginPageAttributes(loginPage);
		System.out.println("Login page attributes verified.");
		assertThat(loginPage.isErrorMessageVisible(), equalTo(false));
	}
	
	@Test
	public void test04_loginPageInvalidUserNegative() {
		loginPage.logInNegative("invalid@invalid.com", password);
		verifyMainLoginPageAttributes(loginPage);
		assertThat(loginPage.isErrorMessageVisible(), equalTo(true));
		assertThat(loginPage.getErrorMessageText()
				, equalTo(LoginPageElementsTexts.INVALID_CREDENTIALS_MESSAGE.toString()));
	}
	
	@Test
	public void test05_loginPageInvalidPasswordNegative() {
		loginPage.logInNegative(login, "invalid");
		verifyMainLoginPageAttributes(loginPage);
		assertThat(loginPage.isErrorMessageVisible(), equalTo(true));
		assertThat(loginPage.getErrorMessageText()
				, equalTo(LoginPageElementsTexts.INVALID_CREDENTIALS_MESSAGE.toString()));
	}
	
	@Test
	public void test06_loginPageInvalidLoginPasswordNegative() {
		loginPage.logInNegative("invalid@invalid.com", "invalid");
		verifyMainLoginPageAttributes(loginPage);
		assertThat(loginPage.isErrorMessageVisible(), equalTo(true));
		assertThat(loginPage.getErrorMessageText()
				, equalTo(LoginPageElementsTexts.INVALID_CREDENTIALS_MESSAGE.toString()));
	}
	
}
