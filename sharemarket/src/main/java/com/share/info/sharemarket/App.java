package com.share.info.sharemarket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class App 
{
	private WebDriver driver = null;
	private WebDriverWait driverwait = null;
	By Table = By.xpath("//table[contains(@class,'topic-list')]/tbody/tr[not(contains(@class,'category-forum-guidelines')) and  not(contains(@class,'category-uncategorized'))]");
	By TableLink = By.xpath("//table[contains(@class,'topic-list')]/tbody/tr[not(contains(@class,'category-forum-guidelines')) and  not(contains(@class,'category-uncategorized'))]/td[contains(@class,'main-link')]/*[contains(@class,'link-top')]/a");
	By AuthorName = By.xpath("//div[contains(@class,'topic-body')]/div[@role='heading']/div[contains(@class,'user-card')]//a");
	By AuthorContent = By.xpath("//div[contains(@class,'topic-owner')]//div[@class='regular contents']/div[@class='cooked']");
	By loadspinner = By.xpath("//div[contains(@class,'spinner')]");
	
	
	@BeforeTest
	public void setuplaunchbrowser() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driverwait = new WebDriverWait(driver,30);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://forum.valuepickr.com");
		
		
		
	}
	@Test
	public void getInfo() {
		List<String> infolist = new ArrayList<>();
		int count = 20;
		List<WebElement> elementlist = getElements(TableLink);
		if(elementlist.size() > 0 ) {
			for (int i=0;i<20;i++) {
				List<WebElement> currentlist = getElements(TableLink);
				WebElement link = currentlist.get(i);
				if(link != null) {
				boolean isfound = 	(infolist.size() > 0 ? infolist.contains(link.getText().trim())  : false);
					if(!isfound) {
						infolist.add(link.getText().trim());
						link.click();
						waitforelementstale();
						System.out.println("Author Name : "+getElement(AuthorName).getText());
						System.out.println("Author Content : "+getElement(AuthorContent).getText());
						driver.navigate().back();
						break;
					}
				}
				
				
			}
		}
		
		
		
		
	}
	
	public boolean waitforelementstale() {
	  
		
		if(getElement(loadspinner) != null) {
			try {
				return driverwait.until(ExpectedConditions.stalenessOf(getElement(loadspinner)));
			}catch (Exception e) {
				// TODO: handle exception
				return false;
			}
			
		}
		return true;
		
		
	}
	 
	public  WebElement getElement(By path) {
		try {
		      WebElement ele=  driver.findElement(path);
		      return ele;
		}catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	public  List<WebElement> getElements(By path) {
		try {
		      List<WebElement> elist =  driver.findElements(path);
		      return elist;
		}catch (Exception e) {
			// TODO: handle exception
		}
		return Collections.emptyList();
	}
	@AfterTest
	public void quit() {
		if(driver != null) {
			driver.quit();
		}
	}
}
