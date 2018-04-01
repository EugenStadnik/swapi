/**
 * 
 */
package com.swapi_java.ui;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.openqa.selenium.*;

import com.swapi_java.ui.abstractpages.AbstractPage;

/**
 * @author ystadnik
 *
 */
public class LoginPage extends AbstractPage {
    private By logoImgLocator = By.xpath("/html/body/section/div/div/div/div/div/div[1]/div");
    private By welcomeLocator = By.xpath("/html/body/section/div/div/div/div/div/div[2]/a");
    private By usernamePlaceholderLocator = By.xpath("/html/body/section/div/div/div/div/div/form[1]/div[1]/div/div[1]/label");
    private By passwordPlaceholderLocator = By.xpath("/html/body/section/div/div/div/div/div/form[1]/div[2]/div/div[1]/label");
    private By usernameLocator = By.xpath("//*[@id=\"login-email\"]");
    private By passwordLocator = By.xpath("//*[@id=\"login-password\"]");
    private By loginButtonLocator = By.xpath("/html/body/section/div/div/div/div/div/form[1]/div[5]/div/button");
    private By errMsgLocator = By.xpath("/html/body/section/div/div/div/div/div/form[1]/div[3]/div/div/p");

    public enum LoginPageElementsTexts {
    	WELLCOME("Sign up here"),
		USER_PLACEHOLDER("Email address"),
		PASSWORD_PLACEHOLDER("Password"),
		LOGIN_BUTTON("Sign in"),
		INVALID_CREDENTIALS_MESSAGE("Password or email are incorrect");
		private final String test;

		private LoginPageElementsTexts(final String test) {
			this.test = test;
		}

		public String toString() {
			return test;
		}
    }
    
    public LoginPage(WebDriver driver) {
        super(driver);
        waitForElementToBeVisible(logoImgLocator, TIMEOUT_SECONDS, POLLING_TIME_MILLIS);
        if (!"Login page — Merchant portal".equals(driver.getTitle())) {
            throw new IllegalStateException("This is not the login page");
        }
    }

    public MainPage logIn(String username, String password) {
        typeUsername(username);
        typePassword(password);
        clickLogin();
        return new MainPage(driver);
    }

    // utility methods
    public LoginPage typeUsername(String username) {
        WebElement element = driver.findElement(usernameLocator);
        element.clear();
        driver.findElement(usernameLocator).sendKeys(username);
        return this;
    }

    public LoginPage typePassword(String password) {
        WebElement element = driver.findElement(passwordLocator);
        element.clear();
        driver.findElement(passwordLocator).sendKeys(password);
        return this;
    }

    public void clickLogin() {
        driver.findElement(loginButtonLocator).click();
    }

    public String getErrorMessage() {
        waitForElementToBeVisible(errMsgLocator, TIMEOUT_SECONDS, POLLING_TIME_MILLIS);
        return getText(errMsgLocator);
    }

    public boolean isErrorMessagePresent() {
        return isElementPresent(errMsgLocator);
    }

    public String getWelcomeText() {
        return getText(welcomeLocator);
    }

    public String getUsernamePlaceholderText() {
        return getTextFromPlaceholder(usernamePlaceholderLocator);
    }

    public String getPasswordPlaceholderText() {
        return getTextFromPlaceholder(passwordPlaceholderLocator);
    }

    public String getLogInBtnName() {
        return getText(loginButtonLocator);
    }

}
