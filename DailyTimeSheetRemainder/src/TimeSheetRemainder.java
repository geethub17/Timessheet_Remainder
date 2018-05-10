import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class TimeSheetRemainder {
	
	public static String key,Value,email,TimesheetPassword,timesheet,Emailpassword;
	
	public void readcredentials() throws IOException{
		
		File prop =new File("C:/Timesheet/Ezeees.properties");
		FileInputStream input = new FileInputStream(prop);
		Properties readprop = new Properties();
		readprop.load(input);		
		Enumeration enumkey =  readprop.keys();
		
		while(enumkey.hasMoreElements()){
			key = (String) enumkey.nextElement();
			Value = readprop.getProperty(key);
			if(key.equalsIgnoreCase("Email")){
				email=Value;
			}			
			if(key.equalsIgnoreCase("TimesheetPassword")){
				TimesheetPassword=Value;
			}
			if(key.equalsIgnoreCase("Timesheet")){
				
				timesheet=Value;
			}
			if(key.equalsIgnoreCase("Emailpassword")){
				
				Emailpassword=Value;
			}
		}
		
		loginezeees();
			
	}
	
	WebDriver driver;
	 String windowHandle;

	public boolean loginezeees(){
		try{
			
			System.setProperty("webdriver.chrome.driver", "C://Timesheet//chromedriver.exe");
			
			ChromeOptions Options = new ChromeOptions();
			Options.addArguments("disable-infobars");
			driver = new ChromeDriver(Options);
			driver.manage().window().maximize();
					    
			driver.navigate().to("http://www.ezeees.com/");	
					
			driver.findElement(By.id("menu-item-2544")).click();
			
			driver.findElement(By.id("user_login")).clear();
			driver.findElement(By.id("user_login")).sendKeys(email);
			
			driver.findElement(By.id("user_pass")).clear();
			driver.findElement(By.id("user_pass")).sendKeys(TimesheetPassword);
			
			driver.findElement(By.id("wppb-submit")).click();
			
			navigatetotimereport();
			
			return true;
			
		}catch(Exception e){
			
			System.out.println("Exception----------> "+e);
			
			return false;
		}
	}
	
	public boolean navigatetotimereport(){
		try{
			
			WebElement time = driver.findElement(By.id("menu-item-2563"));
			time.click();
			
			List <WebElement> options= time.findElements(By.id("menu-item-2565"));
			options.get(0).click();	

			selecttodaysdate();
			
			return true;
			
		}catch(Exception e){
			
			System.out.println("Exception----------> "+e);
			
			return false;
		}
	}
	
	
	public boolean selecttodaysdate(){
		try{
			
			Date today = Calendar.getInstance().getTime();
			String newstring = new SimpleDateFormat("yyyy-MM-dd").format(today);
					
			
			driver.findElement(By.id("workitem_Date1")).clear();
			driver.findElement(By.id("workitem_Date1")).sendKeys(newstring);
			driver.findElement(By.id("workitem_Date1")).sendKeys(Keys.TAB);
			
			openexceltab();
			
			return true;
			
		}catch(Exception e){
			return false;
		}
	}
	
	 
	
	public boolean openexceltab(){
		try{
			WebDriverWait wait = new WebDriverWait(driver,30);
			
			 	Robot robot = new Robot();
			 	
			    robot.keyPress(KeyEvent.VK_CONTROL);
			    robot.keyPress(KeyEvent.VK_T);
			    
			    robot.keyRelease(KeyEvent.VK_T);
			    robot.keyRelease(KeyEvent.VK_CONTROL);
			    
			    Thread.sleep(3000);
			    
			    Set<String> handles = driver.getWindowHandles();
			    List<String> handlesList = new ArrayList<String>(handles);
//			    System.out.println(handles.size());
			    String newTab = handlesList.get(handlesList.size() - 1);
			    driver.switchTo().window(newTab); 
			    driver.get(timesheet);
			  if(driver.findElement(By.id("identifierId")).isDisplayed()||driver.findElement(By.id("identifierId")).isEnabled()){
				  driver.findElement(By.id("identifierId")).sendKeys(email);
				  driver.findElement(By.id("identifierId")).sendKeys(Keys.ENTER);
				  
				  wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@type='password']")));
				  
				  driver.findElement(By.xpath("//input[@type='password']")).sendKeys(Emailpassword);
				  driver.findElement(By.xpath("//input[@type='password']")).sendKeys(Keys.ENTER);
			  }
			  else if(driver.findElement(By.xpath("//input[@type='password']")).isDisplayed()||driver.findElement(By.xpath("//input[@type='password']")).isEnabled()){
				  				  
				  driver.findElement(By.xpath("//input[@type='password']")).sendKeys(Emailpassword);
				  driver.findElement(By.xpath("//input[@type='password']")).sendKeys(Keys.ENTER);
			  }
			    
			    
			return true;
		}catch(Exception e){
			
			System.out.println(e);
			return false;
		}
	}
	
	
	public static void runTask(int hour, int min, int second) throws ParseException{
		
		
		
		Calendar startDate = Calendar.getInstance();
		
		Calendar calendar = Calendar.getInstance();
		Date dt = new Date();
		
		int hourofday  = calendar.get(Calendar.HOUR_OF_DAY);
		int minofhour  = calendar.get(Calendar.MINUTE);
		
		Timer time = new Timer();
		
						
			if((hourofday>hour))
		
			{
				
			calendar.set(Calendar.HOUR_OF_DAY, hour);
		    calendar.set(Calendar.MINUTE, min);
		    calendar.set(Calendar.SECOND, second);
			calendar.add(calendar.DATE, 1);
			Date tomoroww = calendar.getTime();
//			System.out.println(tomoroww);
			
			TimeSheetRemainder.infoBox("Sorry! We have crossed the given time :( \n"
					+ "The remainder will work from "+tomoroww+" onwards. \n"
							+ " \n"
							+ "N	ote: \n"
							+ "1. I have not programmed to work on weekends. \n"
							+ "2. Please run me, whenever you re-start your system. \n"
							+ "3. If you want to stop me then do this. Goto 'Start task manager -> Processes -> javaw.exe' and kill it", "Information!");
			
			}
			
			if((hourofday==hour)&(minofhour>min))
				
			{
				
			calendar.set(Calendar.HOUR_OF_DAY, hour);
		    calendar.set(Calendar.MINUTE, min);
		    calendar.set(Calendar.SECOND, second);
			calendar.add(calendar.DATE, 1);
			Date tomoroww = calendar.getTime();
			System.out.println(tomoroww);
			
			TimeSheetRemainder.infoBox("Sorry! We have crossed the given time :( \n"
					+ "The remainder will work from "+tomoroww+" onwards. \n"
							+ " \n"
							+ "Note: \n"
							+ "1. I have not programmed to work on weekends. \n"
							+ "2. Please run me, whenever you re-start your system. \n"
							+ "3. If you want to stop me then do this. Goto 'Start task manager -> Processes -> javaw.exe' and kill it", "Information!");
			
			}
					
	        calendar.set(Calendar.HOUR_OF_DAY, hour);
	        calendar.set(Calendar.MINUTE, min);
	        calendar.set(Calendar.SECOND, second);
	        calendar.set(Calendar.MILLISECOND, 0);
	        
	        Date tomoroww2 = calendar.getTime();
//			System.out.println(tomoroww2);
			TimeSheetRemainder.infoBox("Please do continue with your stuff :) I will alert you day-to-day.","Information");
//			System.out.println(calendar.getTime());
	        time.schedule(new Customtask(), calendar.getTime(), TimeUnit.HOURS.toMillis(24));
		}
		
        
	public static void infoBox(String infoMessage, String titleBar)
    {
  		JOptionPane.showMessageDialog(null, infoMessage, "" + titleBar,JOptionPane.INFORMATION_MESSAGE);
  		
    } 
	
	
	public static void main(String[] args) throws ParseException, IOException{

		 final String dir = System.getProperty("user.dir");
	       
		 File srcDir = new File(dir+"/Timesheet");
		 File destDir = new File("C:/Timesheet");
		 
		 File iffolderexists = new File("C:/Timesheet");
		 
		 if(!iffolderexists.exists()){
			 try{
			
		 FileUtils.copyDirectory(srcDir, destDir);
		 ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "C://Timesheet//Ezeees.properties");
		 pb.start();
		 
			 }
			 catch(IOException e){
				 
			 }
		 
		 }
		 
		 
		 String user = new com.sun.security.auth.module.NTSystem().getName();
		 
		 File srcDir2 = new File(dir+"/Time sheet remainder.jar");
		 File destDir2 = new File("C:/Users/"+user+"/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup");
		 
		 File iffolder2exists = new File("C:/Users/"+user+"/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup/Time sheet remainder.jar");
		
		 
		/* TimeSheetRemainder.infoBox(srcDir2 +"\n"
		 		+ destDir2 +"\n"
		 		+ iffolder2exists +"\n","Try");*/
		 
		 if(!iffolder2exists.exists()){
			 try{
			
		 FileUtils.copyFileToDirectory(srcDir2, destDir2);
		 
		 
			 }
			 catch(IOException e){
				 System.out.println(e);
//				 TimeSheetRemainder.infoBox("exception "+e,"Try");
				 
			 }
		 
		 }
		 
		new Timeinput();
	}

}
