package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import main.AddUserEntry;
import main.Constant;
import main.DashboardPage;
import main.LoginPage;
import main.RegPage;
import main.loginResult;
import net.bytebuddy.agent.builder.AgentBuilder.RedefinitionStrategy.DiscoveryStrategy.Explicit;

@RunWith(Parameterized.class)
public class ParametisedTest {
	
	@Parameters
	public static Collection<Object[]> inputData() throws IOException {
		FileInputStream file = new FileInputStream(Constant.FILELOCATION);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		Object[][] obj = new Object[sheet.getPhysicalNumberOfRows()-1][7];
		
		for (int rowNum = 1; rowNum < sheet.getPhysicalNumberOfRows(); rowNum++) {
				obj[rowNum-1][0] = sheet.getRow(rowNum).getCell(0).getStringCellValue();
				obj[rowNum-1][1] = sheet.getRow(rowNum).getCell(1).getStringCellValue();
				obj[rowNum-1][2] = sheet.getRow(rowNum).getCell(2).getStringCellValue();
				obj[rowNum-1][3] = sheet.getRow(rowNum).getCell(3).getStringCellValue();
				obj[rowNum-1][4] = sheet.getRow(rowNum).getCell(4).getStringCellValue();
				//obj[rowNum-1][5] = sheet.getRow(rowNum).getCell(5).getStringCellValue();
				obj[rowNum-1][6] = rowNum;
			}
		workbook.close();
		return Arrays.asList(obj);
		
		}
	
	private String email;
	private String fName;
	private String lName;
	private String password;
	private String expected;
	private String expectedDel;
	private int rowNum;
	private WebDriver driver;
	
	public ParametisedTest(String email,String fName, String lName, String password, String expected, String expectedDel, int rowNum) {
		this.email = email;
		this.fName= fName;
		this.lName=lName;
		this.password = password;
		this.expected = expected;
		this.expectedDel=expectedDel;
		this.rowNum = rowNum;
	}
	
	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", Constant.CHROMEDRIVERLOCATION);
		ChromeOptions chromeoptions = new ChromeOptions();
		chromeoptions.addArguments("--headless");
		driver = new ChromeDriver(chromeoptions);
	}
	
	@Test 
	public void regPageFromLandingPage() {
		driver.get(Constant.LOGINPAGE);
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		loginPage.regButtonClick();
		RegPage regPage = PageFactory.initElements(driver, RegPage.class);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		assertEquals("Registration page not found","Register An Account",  regPage.heading());
	}
	
	@Test 
	public void loginPageFromDirectLink() {
		driver.get(Constant.LOGINPAGE);
		
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		assertEquals("Login page not found","Login-Button",  loginPage.loginButton());
	}
	
	@Test 
	public void dashboardPageFromDirectLink() {
		driver.get(Constant.DASHBOARD);
		
		DashboardPage dashPage = PageFactory.initElements(driver, DashboardPage.class);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		assertEquals("Dashboard page not found","Cohorts",  dashPage.heading());
	}
	

	
	@Test
	public void trainerLogin() throws IOException, InterruptedException {
		

		driver.get(Constant.LOGINPAGE);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		loginPage.loginUser(email, password);
		
		loginResult loginResult = PageFactory.initElements(driver, loginResult.class);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		Thread.sleep(500);
		
		FileInputStream file = new FileInputStream(Constant.FILELOCATION);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		XSSFRow row = sheet.getRow(rowNum);
		XSSFCell cell;
		cell = row.getCell(6);
		if (cell == null) {
			cell = row.createCell(6);
		}
		
		cell.setCellValue(loginResult.loggedIn());
		
		try {
			assertEquals("User not logged in.", expected, loginResult.loggedIn());
			cell = row.getCell(7);
			if (cell == null) {
				cell = row.createCell(7);
			}
			cell.setCellValue("PASS");
		} catch (AssertionError fail) {
			cell = row.getCell(7);
			if (cell == null) {
				cell = row.createCell(7); 
			}
			cell.setCellValue("FAIL");
			fail("test failure");
		} finally {
		
		FileOutputStream fileOut = new FileOutputStream(Constant.FILELOCATION);

		workbook.write(fileOut);
		fileOut.flush();
		fileOut.close();
		
		workbook.close();
		file.close();	
		}
		
	}
	
//	@Test
//	public void loginDelete() throws InterruptedException, IOException{
//		driver.get(Constant.LOGINPAGE);
//		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
//	
//		loginPage.loginUser(username, password);
//		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
//		loginPage.delUser();
//		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
//		
//		FileInputStream file = new FileInputStream(Constant.FILELOCATION);
//		XSSFWorkbook workbook = new XSSFWorkbook(file);
//		XSSFSheet sheet = workbook.getSheetAt(0);
//		
//		XSSFRow row = sheet.getRow(rowNum);
//		XSSFCell cell;
//		cell = row.getCell(8);
//		if (cell == null) {
//			cell = row.createCell(8);
//		}
//		
//		cell.setCellValue( loginPage.delUserCheck());
//		
//		try {
//			assertEquals("User not deleted.", expectedDel,  loginPage.delUserCheck());
//			cell = row.getCell(9);
//			if (cell == null) {
//				cell = row.createCell(9);
//			}
//			cell.setCellValue("PASS");
//		} catch (AssertionError fail) {
//			cell = row.getCell(9);
//			if (cell == null) {
//				cell = row.createCell(9); 
//			}
//			cell.setCellValue("FAIL");
//			fail("test failure");
//		} finally {
//		
//		FileOutputStream fileOut = new FileOutputStream(Constant.FILELOCATION);
//
//		workbook.write(fileOut);
//		fileOut.flush();
//		fileOut.close();
//		
//		workbook.close();
//		file.close();	
//		}
//	
//	}
//	
	
	@After
	public void teardown() throws InterruptedException {
		driver.quit();
	}
	
}
