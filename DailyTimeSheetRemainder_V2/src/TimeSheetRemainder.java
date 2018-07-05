import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
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


public class TimeSheetRemainder  {
	
	public static String key,Value,email,TimesheetPassword,timesheet,Emailpassword;
	public static ArrayList<Integer> Arrangedpositions = new ArrayList<>();			       
	public static ArrayList<Integer> positions = new ArrayList<>();
	public static ArrayList<Integer> TimeIntervals = new ArrayList<>();
	
	public void readcredentials() throws IOException{
		
		File prop =new File("C:/Timesheet/Input.properties");
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
	
	
	public static void runTask(int hour, int min, int second, ArrayList<String> selectedDays) throws ParseException{
		
		String daycheck;
		Calendar calendar = Calendar.getInstance();

		 String dayNames[] = new DateFormatSymbols().getWeekdays();
	     Calendar date2 = Calendar.getInstance();
	      

	     boolean confirm = true;
		int day = calendar.get(Calendar.DAY_OF_WEEK);
		int hourofday  = calendar.get(Calendar.HOUR_OF_DAY);
		int minofhour  = calendar.get(Calendar.MINUTE);
		
		Timer time = new Timer();
		
		  
				for(int a=0;a<selectedDays.size();a++){
									
				daycheck = selectedDays.get(a); 
				System.out.println("Today date check: "+daycheck+" ,"+dayNames[date2.get(Calendar.DAY_OF_WEEK)]);
				
		if(daycheck.equalsIgnoreCase(dayNames[date2.get(Calendar.DAY_OF_WEEK)])){						
					
			if((hourofday>hour))
		
			{			
			calendar.set(Calendar.HOUR_OF_DAY, hour);
		    calendar.set(Calendar.MINUTE, min);
		    calendar.set(Calendar.SECOND, second);
			calendar.add(calendar.DATE, 7);
			Date tomoroww = calendar.getTime();
//			System.out.println(tomoroww);
			
			/*TimeSheetRemainder .infoBox("Sorry! We have crossed the given time :( \n"
					+ "The reminder will work from "+tomoroww+" onwards. \n"
							+ " \n"
							+ "If you want to stop me then do this. Goto 'Start task manager -> Processes -> javaw.exe' and kill it", "Information!");*/
			confirm = true;
			}
			
			if((hourofday==hour)&(minofhour>min))
				
			{
				
			calendar.set(Calendar.HOUR_OF_DAY, hour);
		    calendar.set(Calendar.MINUTE, min);
		    calendar.set(Calendar.SECOND, second);
			calendar.add(calendar.DATE, 7);
			Date tomoroww = calendar.getTime();
			System.out.println(tomoroww);
			
			/*TimeSheetRemainder .infoBox("Sorry! We have crossed the given time :( \n"
					+ "The reminder will work from "+tomoroww+" onwards. \n"
							+ " \n"
							+ "Note: \n"
							+ "If you want to stop me then do this. Goto 'Start task manager -> Processes -> javaw.exe' and kill it", "Information!");*/
			
			confirm = true;
			}
			
			calendar.set(Calendar.HOUR_OF_DAY, hour);
	        calendar.set(Calendar.MINUTE, min);
	        calendar.set(Calendar.SECOND, second);
	        calendar.set(Calendar.MILLISECOND, 0);
	        System.out.println(calendar.getTime());	        
//			TimeSheetRemainder .infoBox("Please do continue with your stuff :) I will remind you.","Information");
			confirm = true;
			if(selectedDays.size()==1 && daycheck.equalsIgnoreCase(dayNames[date2.get(Calendar.DAY_OF_WEEK)])){
				time.schedule(new Customtask(), calendar.getTime(), TimeUnit.DAYS.toMillis(7));
				break;
			}else{
				time.schedule(new Customtask(), calendar.getTime(), TimeUnit.DAYS.toMillis(7));
				break;
			}
			
//			break;
		}
	}
			
			String[] DaysForTimeInterval = selectedDays.toArray(new String[selectedDays.size()]);
			
			if(confirm==true){
			
				boolean TomOrYes = false;
				
			for(int n=0;n<DaysForTimeInterval.length;n++){
				
//				System.out.println("Selected days: "+DaysForTimeInterval[n]);
													
					
					if(DaysForTimeInterval[n].equalsIgnoreCase("sunday")){
						if(day<1){
						LocalDate ld = LocalDate.of(date2.get(Calendar.YEAR),date2.get(Calendar.MONTH)+1,date2.get(Calendar.DATE));
						ld = ld.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
						calendar.set(ld.getYear(),ld.getMonthValue()-1,ld.getDayOfMonth());
						//System.out.println(ld.getYear()+","+ld.getMonthValue()-1+","+ld.getDayOfMonth());	
						
						calendar.set(Calendar.HOUR_OF_DAY, hour);
				        calendar.set(Calendar.MINUTE, min);
				        calendar.set(Calendar.SECOND, second);
				        calendar.set(Calendar.MILLISECOND, 0);

				        System.out.println(calendar.getTime());	
				        
				        time.schedule(new Customtask(), calendar.getTime(), TimeUnit.DAYS.toMillis(7));
						
						System.out.println(calendar.getTime()); TomOrYes = true;	
//						message("sunday");
//						break;
						}
					}
					if(DaysForTimeInterval[n].equalsIgnoreCase("monday")){
						if(day<2){
							LocalDate ld = LocalDate.of(date2.get(Calendar.YEAR),date2.get(Calendar.MONTH)+1,date2.get(Calendar.DATE));
							ld = ld.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
							calendar.set(ld.getYear(),ld.getMonthValue()-1,ld.getDayOfMonth());
							//System.out.println(ld.getYear()+","+ld.getMonthValue()-1+","+ld.getDayOfMonth());	
							
							calendar.set(Calendar.HOUR_OF_DAY, hour);
					        calendar.set(Calendar.MINUTE, min);
					        calendar.set(Calendar.SECOND, second);
					        calendar.set(Calendar.MILLISECOND, 0);
					        time.schedule(new Customtask(), calendar.getTime(), TimeUnit.DAYS.toMillis(7));
							
							System.out.println(calendar.getTime()); TomOrYes = true;	
//							message("monday");
//							break;
							}
						
					}
					if(DaysForTimeInterval[n].equalsIgnoreCase("tuesday")){
						if(day<3){
						LocalDate ld = LocalDate.of(date2.get(Calendar.YEAR),date2.get(Calendar.MONTH)+1,date2.get(Calendar.DATE));
						ld = ld.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
						calendar.set(ld.getYear(),ld.getMonthValue()-1,ld.getDayOfMonth());
						//System.out.println(ld.getYear()+","+ld.getMonthValue()-1+","+ld.getDayOfMonth());	

						calendar.set(Calendar.HOUR_OF_DAY, hour);
				        calendar.set(Calendar.MINUTE, min);
				        calendar.set(Calendar.SECOND, second);
				        calendar.set(Calendar.MILLISECOND, 0);
				        time.schedule(new Customtask(), calendar.getTime(), TimeUnit.DAYS.toMillis(7));
				        
						System.out.println(calendar.getTime()); TomOrYes = true;	
//						message("tuesday");
//						break;
						}
					}
					if(DaysForTimeInterval[n].equalsIgnoreCase("wednesday")){
						if(day<4){
						LocalDate ld = LocalDate.of(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DATE));
						ld = ld.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
						calendar.set(ld.getYear(),ld.getMonthValue()-1,ld.getDayOfMonth());
						//System.out.println(ld.getYear()+","+ld.getMonthValue()-1+","+ld.getDayOfMonth());	
						
						calendar.set(Calendar.HOUR_OF_DAY, hour);
				        calendar.set(Calendar.MINUTE, min);
				        calendar.set(Calendar.SECOND, second);
				        calendar.set(Calendar.MILLISECOND, 0);
				        time.schedule(new Customtask(), calendar.getTime(), TimeUnit.DAYS.toMillis(7));
//				        
						System.out.println(calendar.getTime()); TomOrYes = true;	
//						message("wednesday"); 
//						break;
						}
					}
					
					if(DaysForTimeInterval[n].equalsIgnoreCase("thursday")){
						if(day<5){
						LocalDate ld = LocalDate.of(date2.get(Calendar.YEAR),date2.get(Calendar.MONTH)+1,date2.get(Calendar.DATE));
						ld = ld.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
						calendar.set(ld.getYear(),ld.getMonthValue()-1,ld.getDayOfMonth());
						//System.out.println(ld.getYear()+","+ld.getMonthValue()-1+","+ld.getDayOfMonth());	
						
						calendar.set(Calendar.HOUR_OF_DAY, hour);
				        calendar.set(Calendar.MINUTE, min);
				        calendar.set(Calendar.SECOND, second);
				        calendar.set(Calendar.MILLISECOND, 0);
				        time.schedule(new Customtask(), calendar.getTime(), TimeUnit.DAYS.toMillis(7));
						
						System.out.println(calendar.getTime()); TomOrYes = true;	
//						message("thursday");
//						break;
						}
					}
					if(DaysForTimeInterval[n].equalsIgnoreCase("friday")){
						if(day<6){
						LocalDate ld = LocalDate.of(date2.get(Calendar.YEAR),date2.get(Calendar.MONTH)+1,date2.get(Calendar.DATE));
						ld = ld.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
						calendar.set(ld.getYear(),ld.getMonthValue()-1,ld.getDayOfMonth());
						//System.out.println(ld.getYear()+","+ld.getMonthValue()-1+","+ld.getDayOfMonth());	
						
						calendar.set(Calendar.HOUR_OF_DAY, hour);
				        calendar.set(Calendar.MINUTE, min);
				        calendar.set(Calendar.SECOND, second);
				        calendar.set(Calendar.MILLISECOND, 0);
				        time.schedule(new Customtask(), calendar.getTime(), TimeUnit.DAYS.toMillis(7));
						
						System.out.println(calendar.getTime()); TomOrYes = true;	
//						message("friday");
//						break;
						}
					}
					if(DaysForTimeInterval[n].equalsIgnoreCase("saturday")){
						if(day<7){
						LocalDate ld = LocalDate.of(date2.get(Calendar.YEAR),date2.get(Calendar.MONTH)+1,date2.get(Calendar.DATE));
						ld = ld.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
						calendar.set(ld.getYear(),ld.getMonthValue()-1,ld.getDayOfMonth());
						//System.out.println(ld.getYear()+","+ld.getMonthValue()-1+","+ld.getDayOfMonth());	
						
						calendar.set(Calendar.HOUR_OF_DAY, hour);
				        calendar.set(Calendar.MINUTE, min);
				        calendar.set(Calendar.SECOND, second);
				        calendar.set(Calendar.MILLISECOND, 0);
				        time.schedule(new Customtask(), calendar.getTime(), TimeUnit.DAYS.toMillis(7));
						
						System.out.println(calendar.getTime()); TomOrYes = true;	
//						message("saturday");
//						break;
						}
					}
				}
			
		
				
				for(int n=0;n<DaysForTimeInterval.length;n++){
					
//					System.out.println("Selected days: "+DaysForTimeInterval[n]);
					
						
						if(DaysForTimeInterval[n].equalsIgnoreCase("sunday")){
							if(day>1){
							LocalDate ld = LocalDate.of(date2.get(Calendar.YEAR),date2.get(Calendar.MONTH)+1,date2.get(Calendar.DATE));
							ld = ld.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
							calendar.set(ld.getYear(),ld.getMonthValue()-1,ld.getDayOfMonth());
							//System.out.println(ld.getYear()+","+ld.getMonthValue()-1+","+ld.getDayOfMonth());	
							
							calendar.set(Calendar.HOUR_OF_DAY, hour);
					        calendar.set(Calendar.MINUTE, min);
					        calendar.set(Calendar.SECOND, second);
					        calendar.set(Calendar.MILLISECOND, 0);
					        time.schedule(new Customtask(), calendar.getTime(), TimeUnit.DAYS.toMillis(7));
							
							System.out.println(calendar.getTime()); TomOrYes = true;	
//							message("sunday");
//							break;
							}
						}
						if(DaysForTimeInterval[n].equalsIgnoreCase("monday")){
							if(day>2){
								LocalDate ld = LocalDate.of(date2.get(Calendar.YEAR),date2.get(Calendar.MONTH)+1,date2.get(Calendar.DATE));
								ld = ld.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
								calendar.set(ld.getYear(),ld.getMonthValue()-1,ld.getDayOfMonth());
								//System.out.println(ld.getYear()+","+ld.getMonthValue()-1+","+ld.getDayOfMonth());	
								
								calendar.set(Calendar.HOUR_OF_DAY, hour);
						        calendar.set(Calendar.MINUTE, min);
						        calendar.set(Calendar.SECOND, second);
						        calendar.set(Calendar.MILLISECOND, 0);
						        time.schedule(new Customtask(), calendar.getTime(), TimeUnit.DAYS.toMillis(7));
						        
								System.out.println(calendar.getTime()); TomOrYes = true;	
//								message("monday");
//								break;
								}
							
						}
						if(DaysForTimeInterval[n].equalsIgnoreCase("tuesday")){
							if(day>3){
							LocalDate ld = LocalDate.of(date2.get(Calendar.YEAR),date2.get(Calendar.MONTH)+1,date2.get(Calendar.DATE));
							ld = ld.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
							calendar.set(ld.getYear(),ld.getMonthValue()-1,ld.getDayOfMonth());
							//System.out.println(ld.getYear()+","+ld.getMonthValue()-1+","+ld.getDayOfMonth());	
							
							calendar.set(Calendar.HOUR_OF_DAY, hour);
					        calendar.set(Calendar.MINUTE, min);
					        calendar.set(Calendar.SECOND, second);
					        calendar.set(Calendar.MILLISECOND, 0);
					        time.schedule(new Customtask(), calendar.getTime(), TimeUnit.DAYS.toMillis(7));
							
							System.out.println(calendar.getTime()); TomOrYes = true;	
//							message("tuesday");
//							break;
							}
						}
						if(DaysForTimeInterval[n].equalsIgnoreCase("wednesday")){
							if(day>4){
							LocalDate ld = LocalDate.of(date2.get(Calendar.YEAR),date2.get(Calendar.MONTH)+1,date2.get(Calendar.DATE));
							ld = ld.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
							calendar.set(ld.getYear(),ld.getMonthValue()-1,ld.getDayOfMonth());
							//System.out.println(ld.getYear()+","+ld.getMonthValue()-1+","+ld.getDayOfMonth());	
							
							calendar.set(Calendar.HOUR_OF_DAY, hour);
					        calendar.set(Calendar.MINUTE, min);
					        calendar.set(Calendar.SECOND, second);
					        calendar.set(Calendar.MILLISECOND, 0);
					        time.schedule(new Customtask(), calendar.getTime(), TimeUnit.DAYS.toMillis(7));
							
							System.out.println(calendar.getTime()); TomOrYes = true;	
//							message("wednesday"); 
//							break;
							}
						}
						
						if(DaysForTimeInterval[n].equalsIgnoreCase("thursday")){
							if(day>5){
							LocalDate ld = LocalDate.of(date2.get(Calendar.YEAR),date2.get(Calendar.MONTH)+1,date2.get(Calendar.DATE));
							ld = ld.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
							calendar.set(ld.getYear(),ld.getMonthValue()-1,ld.getDayOfMonth());
							//System.out.println(ld.getYear()+","+ld.getMonthValue()-1+","+ld.getDayOfMonth());	
							
							calendar.set(Calendar.HOUR_OF_DAY, hour);
					        calendar.set(Calendar.MINUTE, min);
					        calendar.set(Calendar.SECOND, second);
					        calendar.set(Calendar.MILLISECOND, 0);
					        time.schedule(new Customtask(), calendar.getTime(), TimeUnit.DAYS.toMillis(7));
							
							System.out.println(calendar.getTime()); TomOrYes = true;	
//							message("thursday");
//							break;
							}
						}
						if(DaysForTimeInterval[n].equalsIgnoreCase("friday")){
							if(day>6){
							LocalDate ld = LocalDate.of(date2.get(Calendar.YEAR),date2.get(Calendar.MONTH)+1,date2.get(Calendar.DATE));
							ld = ld.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
							calendar.set(ld.getYear(),ld.getMonthValue()-1,ld.getDayOfMonth());
							//System.out.println(ld.getYear()+","+ld.getMonthValue()-1+","+ld.getDayOfMonth());	
							
							calendar.set(Calendar.HOUR_OF_DAY, hour);
					        calendar.set(Calendar.MINUTE, min);
					        calendar.set(Calendar.SECOND, second);
					        calendar.set(Calendar.MILLISECOND, 0);
					        time.schedule(new Customtask(), calendar.getTime(), TimeUnit.DAYS.toMillis(7));
							
							System.out.println(calendar.getTime()); TomOrYes = true;	
//							message("friday");
//							break;
							}
						}
						if(DaysForTimeInterval[n].equalsIgnoreCase("saturday")){
							if(day>7){
							LocalDate ld = LocalDate.of(date2.get(Calendar.YEAR),date2.get(Calendar.MONTH)+1,date2.get(Calendar.DATE));
							ld = ld.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
							calendar.set(ld.getYear(),ld.getMonthValue()-1,ld.getDayOfMonth());
							//System.out.println(ld.getYear()+","+ld.getMonthValue()-1+","+ld.getDayOfMonth());	
							
							calendar.set(Calendar.HOUR_OF_DAY, hour);
					        calendar.set(Calendar.MINUTE, min);
					        calendar.set(Calendar.SECOND, second);
					        calendar.set(Calendar.MILLISECOND, 0);
					        time.schedule(new Customtask(), calendar.getTime(), TimeUnit.DAYS.toMillis(7));
							
							System.out.println(calendar.getTime()); TomOrYes = true;	
//							message("saturday");
//							break;
							}
						}
				
			}
					
			        
					TimeSheetRemainder .infoBox("Please do continue with your stuff :) I will remind you.","Information");
					
//					System.out.println(calendar.getTime());
			}
		
			for(int n=0;n<DaysForTimeInterval.length;n++){
				
				if(DaysForTimeInterval[n].equalsIgnoreCase("sunday")){
					positions.add(1);
				}
				if(DaysForTimeInterval[n].equalsIgnoreCase("monday")){
					positions.add(2);
				}
				if(DaysForTimeInterval[n].equalsIgnoreCase("tuesday")){
					positions.add(3);
				}
				if(DaysForTimeInterval[n].equalsIgnoreCase("wednesday")){
					positions.add(4);
				}
				if(DaysForTimeInterval[n].equalsIgnoreCase("thursday")){
					positions.add(5);
				}
				if(DaysForTimeInterval[n].equalsIgnoreCase("friday")){
					positions.add(6);
				}
				if(DaysForTimeInterval[n].equalsIgnoreCase("saturday")){
					positions.add(7);
				}
				
				}
				for (Integer number : positions) {
			         if(day<number){
			        	 Arrangedpositions.add(number);			        	 
			         }
			         
			      } 	
				
				for (Integer number : positions) {
					
			         if(number==1){
			        	 Arrangedpositions.add(number);			        	 
			         }
			         
			      }
				
				for (Integer number : positions) {
					
			         if(day>=number){
			        	 if(number==1){
			        		 
			        	 }else{
			        	 Arrangedpositions.add(number);		
			        	 }
			         }
			         
			      }
				
				
			/*	int intervel = 0;
				if(positions.size()==1){
					time.schedule(new Customtask(), calendar.getTime(), TimeUnit.DAYS.toMillis(7));
				}else{
					int r =1;
					boolean Letsbreak = false;
					
				for(Integer pos: Arrangedpositions){
					
					System.out.println("position: "+pos);
					
					if(r==Arrangedpositions.size()){
						Arrangedpositions.addAll(Arrangedpositions);
						Letsbreak = true;
					}
					
					if(Arrangedpositions.get(r)>Arrangedpositions.get(r-1)){
				intervel = 24*(Arrangedpositions.get(r)-(Arrangedpositions.get(r-1)));
				r++;
				System.out.println(intervel);
				TimeIntervals.add(intervel);
				if(Letsbreak){
					break;
				}
				
				
			}else if(Arrangedpositions.get(r)<Arrangedpositions.get(r-1)){
				intervel = 24*((Arrangedpositions.get(r)+7)-(Arrangedpositions.get(r-1)));
				r++;
				System.out.println(intervel);
				TimeIntervals.add(intervel);
				if(Letsbreak){
					break;
				}
			}
					
			
			}
				
				
		  }	*/
		}
	
      
	public static void message(String tomoroww){
		TimeSheetRemainder .infoBox("Sorry! We have crossed the given time :( \n"
				+ "The reminder will work from "+tomoroww+" onwards. \n"
						+ " \n"
						+ "Note: \n"
						+ "If you want to stop me then do this. Goto 'Start task manager -> Processes -> javaw.exe' and kill it", "Information!");
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
		 ProcessBuilder pb = new ProcessBuilder("Notepad.exe", "C://Timesheet//Input.properties");
		 pb.start();
		 
			 }
			 catch(IOException e){
				 
			 }
		 
		 }
		 
		 
		 String user = new com.sun.security.auth.module.NTSystem().getName();
		 
		 File srcDir2 = new File(dir+"/Reminder.jar");
		 File destDir2 = new File("C:/Users/"+user+"/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup");
		 
		 File iffolder2exists = new File("C:/Users/"+user+"/AppData/Roaming/Microsoft/Windows/Start Menu/Programs/Startup/Reminder.jar");
		
		 
		/* TimeSheetRemainder .infoBox(srcDir2 +"\n"
		 		+ destDir2 +"\n"
		 		+ iffolder2exists +"\n","Try");*/
		 
//		 if(!iffolder2exists.exists()){
			 try{
//			
		 FileUtils.copyFileToDirectory(srcDir2, destDir2);
		 
		 
			 }
			 catch(IOException e){
				 System.out.println(e);
//				 TimeSheetRemainder .infoBox("exception "+e,"Try");				 
			 }		 
//		 }
		 
		new Timeinput();
	}

}
