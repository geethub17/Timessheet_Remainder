import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Customtask extends TimerTask  {

   public Customtask(){

     //Constructor

   }

   public void run() {
    try {

    	Calendar startDate = Calendar.getInstance(); 
    	Calendar calendar = Calendar.getInstance();
		
		Timer time = new Timer();

		
		
/*	if (startDate.get(Calendar.HOUR_OF_DAY) == 21){
    		
			calendar.set(Calendar.HOUR_OF_DAY, 21);
	        calendar.set(Calendar.MINUTE, 31);
	        calendar.set(Calendar.SECOND, 0);
	        calendar.set(Calendar.MILLISECOND, 0);
	        
			time.schedule(new TimerTask() {
			    public void run() {
			    	new Remainder_popup();
			    }
			}, calendar.getTime());       
	      }
	*/
//    	if (startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY||startDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
//		    System.out.println("It is weekend :)");
//		    
//		} else  {
			System.out.println(startDate.get(Calendar.DAY_OF_WEEK));
			new Remainder_popup();
//		}


            } catch (Exception ex) {

        System.out.println("error running thread " + ex.getMessage());
    }
}

}