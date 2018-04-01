/**
 * 
 */
package com.swapi_java.ui;

import com.swapi_java.ui.abstractpages.MainBarAbstractPage;
import org.openqa.selenium.*;

/**
 * @author ystadnik
 *
 */
public class MainPage extends MainBarAbstractPage {

	private By volumeBtnLocator = By.xpath("//*[@id=\"count-total-volume\"]");
	private By volumeTextLocator = By.xpath("//*[@id=\"count-total-volume\"]/div/span[1]");

	public enum MainPageElementsTexts {
    	VOLUNE("VOLUME");
		private final String test;

		private MainPageElementsTexts(final String test) {
			this.test = test;
		}

		public String toString() {
			return test;
		}
    }
	
	public MainPage(WebDriver driver) {
		super(driver);
		waitForElementToBeVisible(volumeBtnLocator, TIMEOUT_SECONDS, POLLING_TIME_MILLIS);
		waitForElementToBeClickable(volumeBtnLocator, TIMEOUT_SECONDS, POLLING_TIME_MILLIS);
		waitForElementContainsText(volumeTextLocator, TIMEOUT_SECONDS, POLLING_TIME_MILLIS);
		if (!"Dashboard — Merchant portal".equals(driver.getTitle())) {
			throw new IllegalStateException("This is not the main page");
		}
	}

	public boolean isVolumeBtnPresent() {
		return isElementPresent(volumeBtnLocator);
	}

	public void clickVolumeBtn() {
		driver.findElement(volumeBtnLocator).click();
	}

	public String getVolumeBtnName() {
		return getText(volumeTextLocator);
	}
	
}
