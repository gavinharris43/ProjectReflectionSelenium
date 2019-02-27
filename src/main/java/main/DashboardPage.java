package main;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;


public class DashboardPage {


	@FindBy(xpath = "//*[@id=\"root\"]/div/div/div[1]/header")
	private WebElement cohortHeader;
	
	@FindAll({
			@FindBy(tagName="a")
			})
	private List<WebElement> trainees;
	
	
	public String heading() {
		return cohortHeader.getText(); 
		
	}
	
	public void traineeClick(String fname,String lname){
		
		
	try {	
	WebElement trainee=trainees.stream().filter(a -> (fname+" "+lname).equals(a.getText())).findFirst().get();
	trainee.click();
	}catch (NoSuchElementException fail){
		traineeClick(fname,lname);
	}
		
	}

	
}
