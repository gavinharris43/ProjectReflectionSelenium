package main;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AddUserEntry {
	
	@FindBy(id = "username")
	private WebElement username;
	
	@FindBy(id = "firstName")
	private WebElement fName;
	
	@FindBy(id = "lastName")
	private WebElement lName;
	
	@FindBy(id = "password")
	private WebElement password;
	
	@FindBy(xpath = "//*[@id=\"root\"]/div/div[3]/div/div/form/button")
	private WebElement submit;
	
	public void addUser(String user,String fname, String lname, String pass) {
		username.sendKeys(user);
		password.sendKeys(pass);
		fName.sendKeys(fname);
		lName.sendKeys(lname);
		submit.click();
	}

}
