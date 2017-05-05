package dataliciousTestCase;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import dataliciousObjectRepository.startupPageObjectRepository;
import java.io.*;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;

@Parameters({"pathForHarLog"})
public class dataliciousTest {
	public static WebDriver driver;
	public static  BrowserMobProxy proxy;
	protected int indexForEntries;
	protected int indexForQueryString;
	protected String value="test";	

  @BeforeClass
  @Parameters({"browser","pathForFirefoxDriver","pathForChromeDriver"})
	public void setup(String browser,String pathForFirefoxDriver,String pathForChromeDriver) throws Exception {
	  	// start the proxy
	  if(browser.equalsIgnoreCase("chrome")){
	    proxy = new BrowserMobProxyServer();
	    proxy.start(0);
	    // get the Selenium proxy object
	    Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
	    // configure it as a desired capability
	    DesiredCapabilities capabilities = new DesiredCapabilities();
	    capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
	    System.setProperty("webdriver.chrome.driver",pathForChromeDriver);
	    driver = new ChromeDriver(capabilities);
	  }
  }
  
  @Test
  @Parameters({"SearchItem","pathForHarLog"})
	public void datalicious_network_log_test(String SearchItem,String pathForHarLog) throws InterruptedException, Exception {
	  proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT);
	  // create a new HAR with the label "datalicious.com"
	  proxy.newHar("datalicious.com");
	  driver.get("https://www.google.co.in/");
	  startupPageObjectRepository.searchbox(driver).sendKeys(SearchItem);
	  Thread.sleep(3000);
	  startupPageObjectRepository.search_button(driver).click();
	  startupPageObjectRepository.firstLink(driver).click();
	  Thread.sleep(10000);
	  // get the HAR data
	  Har har = proxy.getHar();
	  int totalNumberofEntries= har.getLog().getEntries().size();
	  for(int indexForEntries=0;indexForEntries<totalNumberofEntries;indexForEntries++){
		  if(har.getLog().getEntries().get(indexForEntries).getRequest().getUrl().indexOf("optimahub") !=-1 || har.getLog().getEntries().get(indexForEntries).getRequest().getUrl().indexOf("collect") !=-1 ){
			  System.out.println(har.getLog().getEntries().get(indexForEntries).getRequest().getUrl());
			  int queryStringSize=har.getLog().getEntries().get(indexForEntries).getRequest().getQueryString().size();
			  for(indexForQueryString=0;indexForQueryString<queryStringSize;indexForQueryString++){
			   if(har.getLog().getEntries().get(indexForEntries).getRequest().getQueryString().get(indexForQueryString).getName().equalsIgnoreCase("dt") || har.getLog().getEntries().get(indexForEntries).getRequest().getQueryString().get(indexForQueryString).getName().equalsIgnoreCase("dp")){
				 value=har.getLog().getEntries().get(indexForEntries).getRequest().getQueryString().get(indexForQueryString).getValue();
				 System.out.println(value);
			  }
		  }
		  }
	
	  
  }
		File file = new File(pathForHarLog);
		FileOutputStream fos = new FileOutputStream(pathForHarLog);
		fos.write(value.getBytes());
}
}
  
