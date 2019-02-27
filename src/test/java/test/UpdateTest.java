package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
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
import main.TraineePage;
import main.UpdateResult;
import main.loginResult;
import net.bytebuddy.agent.builder.AgentBuilder.RedefinitionStrategy.DiscoveryStrategy.Explicit;

@RunWith(Parameterized.class)
public class UpdateTest {
	
	@Parameters
	public static Collection<Object[]> inputData() throws IOException {
		FileInputStream file = new FileInputStream(Constant.FILELOCATION);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(1);
		
		Object[][] obj = new Object[sheet.getPhysicalNumberOfRows()-1][10];
		
		for (int rowNum = 1; rowNum < sheet.getPhysicalNumberOfRows(); rowNum++) {
				obj[rowNum-1][0] = sheet.getRow(rowNum).getCell(0).getStringCellValue();
				obj[rowNum-1][1] = sheet.getRow(rowNum).getCell(1).getStringCellValue();
				obj[rowNum-1][2] = sheet.getRow(rowNum).getCell(2).getStringCellValue();
				obj[rowNum-1][3] = sheet.getRow(rowNum).getCell(3).getStringCellValue();
				obj[rowNum-1][4] = sheet.getRow(rowNum).getCell(4).getNumericCellValue();
				obj[rowNum-1][5] = sheet.getRow(rowNum).getCell(5).getNumericCellValue();
				obj[rowNum-1][6] = sheet.getRow(rowNum).getCell(6).getStringCellValue();
				obj[rowNum-1][7] = sheet.getRow(rowNum).getCell(7).getStringCellValue();
				obj[rowNum-1][8] = sheet.getRow(rowNum).getCell(8).getStringCellValue();
				obj[rowNum-1][9] = rowNum;
			}
		workbook.close();
		return Arrays.asList(obj);
		
		}
	
	private String email;
	private String fName;
	private String lName;
	private String password;
	private String month;
	private String year;
	private String updatedFname;
	private String expected;
	private String expectedChanged;
	private int rowNum;
	private WebDriver driver;
	
	public UpdateTest(String email,String fName, String lName, String password, Double month, Double year,String updatedFname, String expected, String expectedChanged, int rowNum) {
		this.email = email;
		this.fName= fName;
		this.lName=lName;
		this.password = password;
		this.month=month+"";
		this.year = year+"";
		this.updatedFname= updatedFname;
		this.expected = expected;
		this.expectedChanged=expectedChanged;
		this.rowNum = rowNum;
	}
	
	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", Constant.CHROMEDRIVERLOCATION);
		ChromeOptions chromeoptions = new ChromeOptions();
		chromeoptions.addArguments("--headless");
		driver = new ChromeDriver(chromeoptions);
		driver.get(Constant.LOGINPAGE);
		LoginPage loginPage = PageFactory.initElements(driver, LoginPage.class);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		loginPage.regButtonClick();
	}

	
	@Test
	public void userUpdate() throws IOException, InterruptedException {
		

		driver.get(Constant.DASHBOARD);
		DashboardPage dashboardPage = PageFactory.initElements(driver, DashboardPage.class);

		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
	
		dashboardPage.traineeClick(fName,lName);
		
		
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		UpdateResult uname = PageFactory.initElements(driver, UpdateResult.class);
		uname.userName();
		
		FileInputStream file = new FileInputStream(Constant.FILELOCATION);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(1);
		
		XSSFRow row = sheet.getRow(rowNum);
		XSSFCell cell;
		cell = row.getCell(9);
		if (cell == null) {
			cell = row.createCell(9);
		}
		
		cell.setCellValue(uname.userName());
		
		try {
			assertEquals("Username not found", expected, uname.userName());
			cell = row.getCell(10);
			if (cell == null) {
				cell = row.createCell(10);
			}
			cell.setCellValue("PASS");
		} catch (AssertionError fail) {
			cell = row.getCell(10);
			if (cell == null) {
				cell = row.createCell(10); 
			}
			cell.setCellValue("FAIL");
			fail("test failure");
		}
				
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		TraineePage traineePage = PageFactory.initElements(driver, TraineePage.class);
		traineePage.updateUser(updatedFname, lName, email, password, month, year);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		UpdateResult updateUname = PageFactory.initElements(driver, UpdateResult.class);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		
		
		XSSFRow row2 = sheet.getRow(rowNum);
		XSSFCell cell2;
		cell2 = row2.getCell(11);
		if (cell2 == null) {
			cell2 = row2.createCell(11);
		}
		
		cell2.setCellValue(updateUname.userName());
		
		try {
			assertEquals("User update not successful", expectedChanged, updateUname.userName());

			traineePage.updateUser(fName, lName, email, password, month, year);
			cell2 = row2.getCell(12);
			if (cell2 == null) {
				cell2 = row2.createCell(12);
			}
			cell2.setCellValue("PASS");
		} catch (AssertionError fail) {
			cell2 = row2.getCell(12);
			if (cell2 == null) {
				cell2 = row2.createCell(12); 
			}
			cell2.setCellValue("FAIL");
			fail("test failure");
		} finally {
		
		FileOutputStream fileOut2 = new FileOutputStream(Constant.FILELOCATION);

		workbook.write(fileOut2);
		fileOut2.flush();
		fileOut2.close();
		
		workbook.close();
		file.close();	
		}
	
	}
	

	
	@After
	public void teardown() throws InterruptedException {
		driver.quit();
	}
	
}
