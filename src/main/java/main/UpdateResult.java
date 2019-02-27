package main;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UpdateResult {

	@FindBy(xpath="//*[@id=\"root\"]/div/div/div[2]/div/fieldset[1]/legend")
	private WebElement sucessful;


	public String userName() {
		return sucessful.getText();
		
	}

}
