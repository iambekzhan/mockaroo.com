package com.mockaroo;

import org.openqa.selenium.WebDriver;

public class Page {
	
	public static void SwitchWindowToNext(WebDriver driver) {
		String current = driver.getWindowHandle();
		for (String winHandle : driver.getWindowHandles()) {
			if(!winHandle.equals(current)) {
				driver.switchTo().window(winHandle);
				break;
			}
        }
	}
}
