package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import resouces.Config;

public class TestBase {

	//initializing the property file reading
	public static Properties CONFIG=null;
	public static Properties OR=null;
	public static WebDriver dr=null;
	public static EventFiringWebDriver driver=null;
	public static boolean isLoggedIn=false;
	//Workbook wb=new XSSFWorkbook();
	
	public void initialize() throws IOException {
		if(driver == null) {
		
		CONFIG= new Properties();
		FileInputStream fn = new FileInputStream(Config.objectRepo);
		CONFIG.load(fn);
	
		
		//Initialize the webdriver and EventFiringWebDriver
		if(Config.browserType.equals("firefox")){
			 dr = new FirefoxDriver();
			
		}else if (Config.browserType.equals("chrome")) {
			 System.setProperty("webdriver.chrome.driver", Config.chromeDriverPath);
			 dr = new ChromeDriver();
		}
		
		driver = new EventFiringWebDriver(dr);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}
}
	
	public static WebElement getObject(String elementName){
		try{
			String elementType=elementName.split(":")[0];
			String elementPath=elementName.split(":")[1];
			if(elementType.equalsIgnoreCase("id")){
				return driver.findElement(By.id(OR.getProperty(elementPath)));
			}
			else if(elementType.equalsIgnoreCase("name")){
				return driver.findElement(By.name(OR.getProperty(elementPath)));
			}
		}catch(Throwable t){
			//report error
			return null;
		}
		return null;
	}
}