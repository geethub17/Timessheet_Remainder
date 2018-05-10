
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.UIManager;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;



public class Timeinput extends javax.swing.JFrame
{
	static JFrame frame2;
	
	static TimeSheetRemainder tm = new TimeSheetRemainder();
		  public Timeinput() {
	        EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	                } catch (Exception ex) {
	                }

	                frame2 = new JFrame("Time input ");
	                frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                frame2.setLayout(new BorderLayout());
	                frame2.add(new TestPane());
	                frame2.pack();
	                frame2.setResizable(false);
	                frame2.setLocationRelativeTo(null);
	                frame2.setVisible(true);
	                frame2.setAlwaysOnTop (true);
	            }
	        });
	    }

	    public static class TestPane extends JPanel{
	    	
	    	 JButton Set;
	    	 
	    	 
	    	 int hr=LocalDateTime.now().getHour();
	    	 int min=LocalDateTime.now().getMinute();
	    	 int finalmin;
	    	 	    	 	    	
	    	public Dimension getPreferredSize() {
	            return new Dimension(310, 50);
	        }

	        public TestPane() {
	        		        	
	            setLayout(new GridBagLayout());
	            GridBagConstraints gbc = new GridBagConstraints();
	            	    	            
	            /*Label sizeLabel = new Label("Font Size:");
	            sizeLabel.setFont(new Font("Helvetica", Font.BOLD, 14));
	            add(sizeLabel);*/
	            
	            gbc.gridx = 0;
	            gbc.gridy = 1;
	            JLabel env = new JLabel("At what time, should i remaind you?  ");
	            add(env, gbc);
//	            add(new JLabel("* Choose enviroment          "), gbc);
	            gbc.insets = new Insets(5,2,2,2);
	            
	           
	            
	            gbc.gridx = 1;
	            gbc.gridy = 1;
	            final JTextField  tm = new JTextField("22:30:00");  
	            add(tm, gbc);
//	            add(new JLabel("* Choose enviroment          "), gbc);
	            gbc.insets = new Insets(5,2,2,2);
	            
	           
	                       
	            gbc.gridx =2;
	            gbc.gridy=1;
	            gbc.fill = GridBagConstraints.NONE;
	            gbc.gridwidth = 5;
	            Set = new JButton(" Set ");
	            Set.setFocusable(false);
	            add(Set, gbc);
	            gbc.insets = new Insets(5,2,2,2);
	            
	                     
		            
	            Set.addActionListener(new ActionListener() {
		    		public void actionPerformed(ActionEvent ae) {
		    			try {
		    				
		    				 String giventime =tm.getText();
		    				 String[] indtimes = giventime.split("\\:");
		    				 		    				 
		    				 int time = Integer.parseInt(indtimes[0]);
		    				 int min = Integer.parseInt(indtimes[1]);
		    				 int sec = Integer.parseInt(indtimes[2]);
		    				 
		    				 if(time>24 || min >60 ||sec>60 ){
		    					 frame2.setState(Frame.ICONIFIED);
		    					 Timeinput.infoBox("Entered the incorrect time value for hour/minit/second. \n"
		    					 		+ "Please enter the valid time.", "Error!");
		    					 frame2.setState(Frame.NORMAL);
		    				 }else{
		    				 
		    				frame2.dispose();
							TimeSheetRemainder.runTask(time,min,sec);
		    				 }
							
						} catch (ParseException e) {
							
							e.printStackTrace();
						}
		    		}
		    	
		       });
	                
	            tm.addKeyListener(new KeyListener() {
		    		
					@Override
					public void keyPressed(KeyEvent arg0) {
						// TODO Auto-generated method stub
						try {
							if (arg0.getKeyCode()==KeyEvent.VK_ENTER){	
		    				 String giventime =tm.getText();
		    				 String[] indtimes = giventime.split("\\:");
		    				 		    				 
		    				 int time = Integer.parseInt(indtimes[0]);
		    				 int min = Integer.parseInt(indtimes[1]);
		    				 int sec = Integer.parseInt(indtimes[2]);
		    				 
		    				 if(time>24 || min >60 ||sec>60 ){
		    					 frame2.setState(Frame.ICONIFIED);
		    					 Timeinput.infoBox("Entered the incorrect time value for hour/minit/second. \n"
		    					 		+ "Please enter the valid time.", "Error!");
		    					 frame2.setState(Frame.NORMAL);
		    				 }else{
		    				 
		    				frame2.dispose();
							TimeSheetRemainder.runTask(time,min,sec);
		    				 }
							}
						} catch (ParseException e) {
							
							e.printStackTrace();
						}
					}

					@Override
					public void keyReleased(KeyEvent arg0) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void keyTyped(KeyEvent arg0) {
						// TODO Auto-generated method stub
						
					}
		    	
		       });
	        	            
	            
	        }
	        

	    }
	    
	     public static void infoBox(String infoMessage, String titleBar)
	        {
	      		JOptionPane.showMessageDialog(null, infoMessage, "" + titleBar,JOptionPane.ERROR_MESSAGE);
	      		
	        }  
	   	      
}

