package main;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage {

	@FindBy(id = "email")
	private WebElement email;
	
	@FindBy(id = "password")
	private WebElement password;
	
	@FindBy(id = "Login-Button")
	private WebElement button;
	
	@FindBy(id = "Register-Button")
	private WebElement buttonReg;
	
	
	
	
	
	public void loginUser(String email, String password) {
		this.email.sendKeys(email);
		this.password.sendKeys(password);
		button.click();
	}
	
	public void regUser(String email, String password) {
		this.email.sendKeys(email);
		this.password.sendKeys(password);
		buttonReg.click();
	}
	public String loginButton() {
		return button.getAttribute("id");
	}
	public void regButtonClick() {
		buttonReg.click();
	}
	
	
}
