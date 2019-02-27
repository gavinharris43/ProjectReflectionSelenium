package main;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegPage {

	@FindBy(id = "emailBox")
	private WebElement username;
	
	@FindBy(id = "passwordBox")
	private WebElement password;
	
	@FindBy(id = "passwordBoxConfirm")
	private WebElement passwordConfirm;
	
	@FindBy(id = "Cancel-Button")
	private WebElement buttonCancel;
	
	@FindBy(id = "Register-Button-Two")
	private WebElement buttonReg;

	@FindBy(xpath = "//*[@id=\"RegisterPage\"]/header")
	private WebElement heading;
	
	
	
	public void regUser(String user, String pass) {
		username.sendKeys(user);
		password.sendKeys(pass);
		buttonReg.click();
	}
	
	public void cancelButtonClick() {
		buttonCancel.click();
	}
	public void regButtonClick() {
		buttonReg.click();
	}
	
	public String heading() {
		return heading.getText();
		
	}
	
	
}
