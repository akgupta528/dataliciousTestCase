package dataliciousTestCase;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import dataliciousTestCase.lib;

@Parameters({"pathForHarLog"})
public class dataliciousTest {
	public WebDriver driver;
	public BrowserMobProxy proxy;


  @BeforeClass
  @Parameters({"browser","pathForFirefoxDriver","pathForChromeDriver"})
	public void setup(String browser,String pathForFirefoxDriver,String pathForChromeDriver) throws Exception {
	  	// start the Proxy
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
	  else if(browser.equalsIgnoreCase("firefox")){
		  	proxy = new BrowserMobProxyServer();
			proxy.start(0);
			// get the Selenium proxy object
			Proxy seleniumProxy = ClientUtil.createSeleniumProxy(proxy);
			// configure it as a desired capability
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(CapabilityType.PROXY, seleniumProxy);
			System.setProperty("webdriver.gecko.driver",pathForFirefoxDriver);
			driver = new FirefoxDriver(capabilities);
		  
	  }
  }
  
  @Test
  @Parameters({"SearchItem","pathForHarLog","filter_URL_1","filter_URL_2","QueryStringName_1","QueryStringName_2"})
	public void datalicious_network_log_test(String SearchItem,String pathForHarLog,String URL_1,String URL_2,String QueryStringName_1,String QueryStringName_2 ) throws InterruptedException, Exception {
	  lib.openBrowser(proxy,driver,SearchItem);
	  lib.captureNetworkLog(proxy,pathForHarLog,URL_1, URL_2, QueryStringName_1, QueryStringName_2);
	  //lib.writeFile(pathForHarLog);
	
	  
  }
		
}

  
