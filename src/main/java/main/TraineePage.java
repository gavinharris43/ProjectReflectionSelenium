package main;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TraineePage {

	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div[1]/header")
	private WebElement cohortHeader;
	
	@FindBy(id= "UpdateFirst")
	private WebElement fName;

	@FindBy(id= "UpdateLast")
	private WebElement lName;
	
	@FindBy(id= "emailBox")
	private WebElement email;
	
	@FindBy(id= "passwordBox")
	private WebElement password;
	
	@FindBy(xpath= "//*[@id=\"root\"]/div/div/div[2]/div/fieldset[2]/form/input[2]")
	private WebElement month;
	
	@FindBy(xpath="//*[@id=\"root\"]/div/div/div[2]/div/fieldset[2]/form/div[3]/select")
	private WebElement cohort;
	
	@FindBy(xpath="//*[@id=\"root\"]/div/div/div[2]/div/fieldset[2]/form/div[4]/button[2]")
	private WebElement updateButton;
	
	@FindBy(xpath="//*[@id=\"root\"]/div/div/div[2]/div/fieldset[2]/form/div[4]/button[1]")
	private WebElement delButton;
	
	
	public String heading() {
		return cohortHeader.getText();
		
	}
	
	public void updateUser(String fName, String lName, String email, String password, String month, String year){
		
		this.fName.sendKeys(fName);
		this.lName.sendKeys(lName);
		this.email.sendKeys(email);
		this.password.sendKeys(password);
		this.month.click();
		this.month.sendKeys(month);
		this.month.sendKeys(Keys.TAB);
		this.month.sendKeys(year);
		this.updateButton.click();
			
		
	}
	
	
}
