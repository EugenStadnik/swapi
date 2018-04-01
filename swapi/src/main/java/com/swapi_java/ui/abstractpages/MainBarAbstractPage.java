/**
 * 
 */
package com.swapi_java.ui.abstractpages;

import com.swapi_java.ui.*;
import org.openqa.selenium.*;

/**
 * @author ystadnik
 * 
 */
public abstract class MainBarAbstractPage extends AbstractPage {

	private By navBarLocator = By.xpath("//*[@id=\"sidebar-scroll\"]/div/div[2]/ul");
	private By homeImgLocator = By.xpath("//*[@id=\"sidebar-scroll\"]/div/div[1]/a/span[1]");
	private By homeBtnLocator = By.xpath("//*[@id=\"sidebar-scroll\"]/div/div[1]/a");
	private By dashboardBtnLocator = By.xpath("//*[@id=\"sidebar-dashboard\"]/span");
	private By userBtnLocator = By.xpath("//*[@id=\"setting\"]");
	private By userNameLocator = By.xpath("//*[@id=\"header-navbar\"]/ul[1]/li[3]/div/ul/li[1]/span");
	private By logOutBtnLocator = By.xpath("//*[@id=\"header-navbar\"]/ul[1]/li[3]/div/ul/li[5]");

	public enum BarElementsTexts {
    	DASHBOARD("Dashboard"),
		LOGOUT("Log out");
		private final String test;

		private BarElementsTexts(final String test) {
			this.test = test;
		}

		public String toString() {
			return test;
		}
    }
	
	public MainBarAbstractPage(WebDriver driver) {
		super(driver);
		waitForElementToBeVisible(homeImgLocator, TIMEOUT_SECONDS, POLLING_TIME_MILLIS);
		waitForElementToBeClickable(homeBtnLocator, TIMEOUT_SECONDS, POLLING_TIME_MILLIS);
		waitForElementContainsText(dashboardBtnLocator, TIMEOUT_SECONDS, POLLING_TIME_MILLIS);
		waitForElementToBeClickable(userBtnLocator, TIMEOUT_SECONDS, POLLING_TIME_MILLIS);
		if (!driver.findElement(navBarLocator).isDisplayed()) {
			throw new IllegalStateException("Navigation Bar isn't displayed!!!");
		}
	}

	public void clickHome() {
		driver.findElement(homeBtnLocator).click();
	}

	public MainPage goToHomeUIPage() {
		clickHome();
		return new MainPage(driver);
	}

	public String getDashboardBtnName() {
		return getTextFromSpan(dashboardBtnLocator);
	}
	
	public void clickUserBtn() {
		driver.findElement(userBtnLocator).click();
	}

	public String getUsername() {
		return getTextFromSpan(userNameLocator);
	}

	public void clickLogOut() {
		clickUserBtn();
		driver.findElement(logOutBtnLocator).click();
	}

	public LoginPage logOut() {
		clickLogOut();
		return new LoginPage(driver);
	}

	public String getLogOutBtnName() {
		clickUserBtn();
		return getText(logOutBtnLocator);
	}

}
