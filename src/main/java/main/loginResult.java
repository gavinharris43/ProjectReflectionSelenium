package main;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class loginResult {

	@FindBy(tagName="header")
	private WebElement sucessful;


	public String loggedIn() {
		return sucessful.getText();
		
	}

}
