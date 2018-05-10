
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;



public  class Remainder_popup 
{
	static JFrame frame;
	static String SelectedEnv;
	static TimeSheetRemainder tm = new TimeSheetRemainder();
		  public Remainder_popup() {
	        EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	                } catch (Exception ex) {
	                }

	                frame = new JFrame("Time sheet remainder ");
	                frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	                frame.setLayout(new BorderLayout());
	                frame.add(new TestPane());
	                frame.pack();
	                frame.setResizable(false);
	                frame.setLocationRelativeTo(null);
	                frame.setVisible(true);
	                frame.setAlwaysOnTop (true);
	            }
	        });
	    }

	    public static class TestPane extends JPanel{
	    	
	    	 JButton update, later;
	    	 
	    	 
	    	 int hr=LocalDateTime.now().getHour();
	    	 int min=LocalDateTime.now().getMinute();
	    	 int finalmin;
	    	 	    	 	    	
	    	public Dimension getPreferredSize() {
	            return new Dimension(350, 80);
	        }

	        public TestPane() {
	        		        	
	            setLayout(new GridBagLayout());
	            GridBagConstraints gbc = new GridBagConstraints();
	            	    	            
	            /*Label sizeLabel = new Label("Font Size:");
	            sizeLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
	            add(sizeLabel);*/
	            
	            gbc.gridx = 0;
	            gbc.gridy = 1;
	            JLabel env = new JLabel("Update the time sheet before you leave!    ");
	            add(env, gbc);
//	            add(new JLabel("* Choose enviroment          "), gbc);
	            gbc.insets = new Insets(5,2,2,2);
	            
	                       
	            gbc.gridx =1;
	            gbc.gridy=0;
	            gbc.fill = GridBagConstraints.NONE;
	            gbc.gridwidth = 5;
	            update = new JButton(" Update now ");
	            update.setFocusable(false);
	            add(update, gbc);
	            gbc.insets = new Insets(5,2,2,2);
	            
	            gbc.gridx =1;
	            gbc.gridy=2;
	            gbc.fill = GridBagConstraints.NONE;
	            gbc.gridwidth = 8;
	            later = new JButton(" 5 mins later ");
	            later.setFocusable(false);
	            add(later, gbc);
	            gbc.insets = new Insets(5,2,2,2);
	            
//	            add(new JButton(" Update "), gbc);
	            
		            
	            update.addActionListener(new ActionListener() {
		    		public void actionPerformed(ActionEvent ae) {
		    			
		    			try {
		    				frame.dispose();
		    				
							tm.readcredentials();
							
							finalmin=0;
						} catch (IOException e) {
							
							e.printStackTrace();
						}
		    			
		    		}
		    	
		       });
	            
	            Calendar startDate = Calendar.getInstance();
	    		startDate.set(2018, Calendar.MARCH, 02);
	    		
	    		 
	    		
	            later.addActionListener(new ActionListener() {
		    		public void actionPerformed(ActionEvent ae) {
		    			frame.dispose();
		    			 finalmin=min+5;
//		    			System.out.println(finalmin);
		    			Calendar calendar = Calendar.getInstance();		    	        
		    	        calendar.set(Calendar.HOUR_OF_DAY, hr);
		    	        calendar.set(Calendar.MINUTE, finalmin);
		    	        calendar.set(Calendar.SECOND, 0);
		    	        calendar.set(Calendar.MILLISECOND, 0);
		    	        Timer time = new Timer(); // Instantiate Timer Object
		    	        time.schedule(new Customtask(), calendar.getTime());
		    	        
		    			
		    		}
		    	
		       });
	           
	            
	            
	            
	        }
	        
	        public static void infoBox(String infoMessage, String titleBar)
	        {
	      		JOptionPane.showMessageDialog(null, infoMessage, "" + titleBar,JOptionPane.INFORMATION_MESSAGE);
	      		
	        }     
 

	    }

	   	      
}

