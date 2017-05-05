package dataliciousTestCase;

import java.io.FileOutputStream;
import org.openqa.selenium.WebDriver;
import dataliciousObjectRepository.startupPageObjectRepository;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.CaptureType;

public class lib {
	
	
	public static void openBrowser(BrowserMobProxy proxy,WebDriver driver,String SearchItem) throws Exception{
		proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT);
		  // create a new HAR with the label "datalicious.com"
		  proxy.newHar("datalicious.com");
		  driver.get("https://www.google.co.in/");
		  startupPageObjectRepository.searchbox(driver).sendKeys(SearchItem);
		  Thread.sleep(3000);
		  startupPageObjectRepository.search_button(driver).click();
		  startupPageObjectRepository.firstLink(driver).click();
		  Thread.sleep(10000);
		
	}
	
	public static void captureNetworkLog(BrowserMobProxy proxy,String pathForHarLog,String URL_1,String URL_2,String QueryStringName_1,String QueryStringName_2) throws Exception{
	  Har har = proxy.getHar();
	  int totalNumberofEntries= har.getLog().getEntries().size();
	  for(int indexForEntries=0;indexForEntries<totalNumberofEntries;indexForEntries++){
		  if(har.getLog().getEntries().get(indexForEntries).getRequest().getUrl().indexOf(URL_1) !=-1 || har.getLog().getEntries().get(indexForEntries).getRequest().getUrl().indexOf(URL_2) !=-1 ){
			  System.out.println(har.getLog().getEntries().get(indexForEntries).getRequest().getUrl());
			  int queryStringSize=har.getLog().getEntries().get(indexForEntries).getRequest().getQueryString().size();
			  for(int indexForQueryString=0;indexForQueryString<queryStringSize;indexForQueryString++){
			   if(har.getLog().getEntries().get(indexForEntries).getRequest().getQueryString().get(indexForQueryString).getName().equalsIgnoreCase(QueryStringName_1) || har.getLog().getEntries().get(indexForEntries).getRequest().getQueryString().get(indexForQueryString).getName().equalsIgnoreCase(QueryStringName_2)){
				 String value=har.getLog().getEntries().get(indexForEntries).getRequest().getQueryString().get(indexForQueryString).getValue();
				 value=value+"\n";
				 lib.writeFile(pathForHarLog,value);
				 System.out.println(value);
			  }
		  }
		 }
	  }
	
	  
   }
	
	public static void writeFile(String pathForHarLog,String value) throws Exception{
//		if(value==null){
		FileOutputStream fw = new FileOutputStream(pathForHarLog,true);
		fw.write(value.getBytes());	
//		}
	}

}
