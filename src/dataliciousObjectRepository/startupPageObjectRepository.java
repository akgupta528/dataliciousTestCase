package dataliciousObjectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


// It has all the elements which you are using to click the links
public class startupPageObjectRepository {
private static WebElement element=null;
	
	public static WebElement searchbox(WebDriver driver){
		element=driver.findElement(By.id("lst-ib"));
		return element;
	}
	
	public static WebElement search_button(WebDriver driver){
		element=driver.findElement(By.id("_fZl"));
		return element;
	}
	
	public static WebElement firstLink(WebDriver driver){
		element=driver.findElement(By.xpath("//div[@class='_NId']//a[1]"));
		return element;
	}

}
