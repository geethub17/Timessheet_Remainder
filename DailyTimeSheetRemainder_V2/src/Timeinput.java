
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.UIManager;



public class Timeinput extends javax.swing.JFrame
{
	static JFrame frame2;
	
	static TimeSheetRemainder  tm = new TimeSheetRemainder ();
		  public Timeinput() {
	        EventQueue.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	                } catch (Exception ex) {
	                }
	                
	                frame2 = new JFrame("Select days and time");
	                frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	                frame2.setLayout(new BorderLayout());
	                frame2.add(new TestPane());;
	                frame2.pack();
	                frame2.setResizable(false);
	                frame2.setLocationRelativeTo(null);
	                frame2.setVisible(true);
	                frame2.setAlwaysOnTop (true);
	                
	            }
	        });
	    }

	    public static class TestPane extends JPanel implements ActionListener{
	    	
	    
	    	 JButton Set;
	    	 static JCheckBox selectall, monday, tuesday, thursday, wednesday,  friday, saturday, sunday;
	    	 static ArrayList<String> Selectedfields = new ArrayList<>();
	    	 
	    	 int hr=LocalDateTime.now().getHour();
	    	 int min=LocalDateTime.now().getMinute();
	    	 int finalmin;
	    	 	    	 	    	
	    	public Dimension getPreferredSize() {
	            return new Dimension(300, 230);
	        }
	    	
	    	
	    	private BufferedImage image;

	        public TestPane() {
	        	
	            setLayout(new GridBagLayout());
	            GridBagConstraints gbc = new GridBagConstraints();
	            	 
	            try { 
	            	
	                image = ImageIO.read(this.getClass().getResource("/alarm5.jpg"));
	             } catch (IOException ex) {
	                  // handle exception...
	             }         
	            
	            Font myFont = new Font("Calibri", Font.PLAIN | Font.BOLD, 14);
	            Font myFont2 = new Font("Calibri", Font.PLAIN | Font.BOLD, 12);
	            
	            gbc.gridx=0;
	            gbc.gridy=0;
	            gbc.anchor = GridBagConstraints.WEST;
	            selectall =new JCheckBox("Select all");
	            selectall.setForeground(Color.WHITE);
	            selectall.setOpaque(false);
	            selectall.setFont(myFont);
	            selectall.addActionListener(this);
	            add(selectall,gbc);
	            //gbc.insets = new Insets(10,5,5,5);
	            
	            gbc.gridx=0;
	            gbc.gridy=1;
	            gbc.anchor = GridBagConstraints.WEST;
	            monday =new JCheckBox("Monday");
	            monday.setForeground(Color.WHITE);
	            monday.setOpaque(false);
	            monday.setFont(myFont2);
	            monday.addActionListener(this);
	            add(monday,gbc);
	            //gbc.insets = new Insets(10,5,5,5);
	            
	            gbc.gridx=0;
	            gbc.gridy=2;
	            tuesday =new JCheckBox("Tuesday");
	            tuesday.setForeground(Color.WHITE);
	            tuesday.setOpaque(false);
	            tuesday.addActionListener(this);
	            tuesday.setFont(myFont2);
	            add(tuesday,gbc);
	            //gbc.insets = new Insets(10,5,5,5);
	            
	            gbc.gridx=0;
	            gbc.gridy=3;
	            gbc.anchor = GridBagConstraints.WEST;
	            wednesday =new JCheckBox("Wednesday");
	            wednesday.setForeground(Color.WHITE);
	            wednesday.setOpaque(false);
	            wednesday.addActionListener(this);
	            wednesday.setFont(myFont2);
	            add(wednesday,gbc);
	            //gbc.insets = new Insets(10,5,5,5);
	            
	            gbc.gridx=0;
	            gbc.gridy=4;
	            gbc.anchor = GridBagConstraints.WEST;
	            thursday =new JCheckBox("Thursday");
	            thursday.setForeground(Color.WHITE);
	            thursday.setOpaque(false);
	            thursday.addActionListener(this);
	            thursday.setFont(myFont2);
	            add(thursday,gbc);
	            //gbc.insets = new Insets(10,5,5,5);
	            
	            gbc.gridx=0;
	            gbc.gridy=5;
	            friday =new JCheckBox("Friday");
	            friday.setForeground(Color.WHITE);
	            friday.setOpaque(false);
	            friday.addActionListener(this);
	            friday.setFont(myFont2);
	            add(friday,gbc);
	            //gbc.insets = new Insets(10,5,5,5);
	            
	            
	            gbc.gridx=1;
	            gbc.gridy=1;
	            gbc.anchor = GridBagConstraints.WEST;
	            saturday =new JCheckBox("Saturday");
	            saturday.setForeground(Color.WHITE);
	            saturday.setOpaque(false);
	            saturday.addActionListener(this);
	            saturday.setFont(myFont2);
	            add(saturday,gbc);
	            //gbc.insets = new Insets(10,5,5,5);
	            
	            gbc.gridx=1;
	            gbc.gridy=2;
	            gbc.anchor = GridBagConstraints.WEST;
	            sunday =new JCheckBox("Sunday");
	            sunday.setForeground(Color.WHITE);
	            sunday.setOpaque(false);
	            sunday.addActionListener(this);
	            sunday.setFont(myFont2);
	            add(sunday,gbc);
	            //gbc.insets = new Insets(10,5,5,5);	            
	            
	            gbc.gridx = 0;
	            gbc.gridy = 7;
	            gbc.fill = GridBagConstraints.HORIZONTAL;  
	            gbc.gridwidth = 2;  
	            gbc.insets = new Insets(10,0,0,0);
	            JLabel env = new JLabel("At what time, should i remind you?  ");
	            env.setForeground(Color.WHITE);
	            env.setFont(myFont);
	            add(env, gbc);
//	            add(new JLabel("* Choose enviroment          "), gbc);	            

	            gbc.gridx=3;
	            gbc.gridy=7;	            
	            final JTextField  tm = new JTextField("21:00:00");  
	            add(tm, gbc);
//	            gbc.insets = new Insets(5,2,2,2);            
	           
	            gbc.gridx =1;
	            gbc.gridy=8;
	            gbc.fill = GridBagConstraints.NONE;
	            gbc.gridwidth = 2;
	            Set = new JButton(" Set ");
	            Set.setFocusable(false);
	            add(Set, gbc);
	           
	            
	          
		            
	            Set.addActionListener(new ActionListener() {
		    		public void actionPerformed(ActionEvent ae) {
		    			try {
		    				
		    				Selectedfields.clear();
			    			
			    		 	if(monday.isSelected()){
			                 	Selectedfields.add("Monday");
			                 }
			    		 	if(tuesday.isSelected()){
			                 	Selectedfields.add("Tuesday");
			                 }
			    		 	if(wednesday.isSelected()){
			                 	Selectedfields.add("Wednesday");
			                 }
			    		 	if(thursday.isSelected()){
			                 	Selectedfields.add("Thursday");
			                 }
			    		 	if(friday.isSelected()){
			                 	Selectedfields.add("Friday");
			                 }
			    		 	if(saturday.isSelected()){
			                 	Selectedfields.add("Saturday");
			                 }
			    		 	if(sunday.isSelected()){
			                 	Selectedfields.add("Sunday");
			                 }
			    		 	
			    		 	if(!(monday.isSelected()||tuesday.isSelected()||wednesday.isSelected()||thursday.isSelected()||friday.isSelected()||
			    		 			saturday.isSelected()||sunday.isSelected())){
			    		 		frame2.setState(Frame.ICONIFIED);
			    		 		Timeinput.infoBox("Please select day/days. \n"
		    					 		+ " ", "Error!");
			    		 		 frame2.setState(Frame.NORMAL);
			    		 	}
			    		 	else{
		    				 String giventime =tm.getText();
		    				 String[] indtimes = giventime.split(":");
		    				 		    				 
		    				 int time = Integer.parseInt(indtimes[0]);
		    				 int min = Integer.parseInt(indtimes[1]);
		    				 int sec = Integer.parseInt(indtimes[2]);
		    				 
		    				 if(time>24 || min >60 ||sec>60 ){
		    					 frame2.setState(Frame.ICONIFIED);
		    					 Timeinput.infoBox("Entered the incorrect value for hour/minit/second. \n"
		    					 		+ "Please enter the valid time.", "Error!");
		    					 frame2.setState(Frame.NORMAL);
		    				 }else{
		    				 
		    				frame2.dispose();		    				
							TimeSheetRemainder .runTask(time,min,sec,Selectedfields);
		    				 }
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
						
							if (arg0.getKeyCode()==KeyEvent.VK_ENTER){	
								try {
				    				
				    				Selectedfields.clear();
					    			
					    		 	if(monday.isSelected()){
					                 	Selectedfields.add("Monday");
					                 }
					    		 	if(tuesday.isSelected()){
					                 	Selectedfields.add("Tuesday");
					                 }
					    		 	if(wednesday.isSelected()){
					                 	Selectedfields.add("Wednesday");
					                 }
					    		 	if(thursday.isSelected()){
					                 	Selectedfields.add("Thursday");
					                 }
					    		 	if(friday.isSelected()){
					                 	Selectedfields.add("Friday");
					                 }
					    		 	if(saturday.isSelected()){
					                 	Selectedfields.add("Saturday");
					                 }
					    		 	if(sunday.isSelected()){
					                 	Selectedfields.add("Sunday");
					                 }
					    		 	
					    		 	if(!(monday.isSelected()||tuesday.isSelected()||wednesday.isSelected()||thursday.isSelected()||friday.isSelected()||
					    		 			saturday.isSelected()||sunday.isSelected())){
					    		 		frame2.setState(Frame.ICONIFIED);
					    		 		Timeinput.infoBox("Please select day/days. \n"
				    					 		+ " ", "Error!");
					    		 		 frame2.setState(Frame.NORMAL);
					    		 	}
					    		 	else{
				    				 String giventime =tm.getText();
				    				 String[] indtimes = giventime.split(":");
				    				 		    				 
				    				 int time = Integer.parseInt(indtimes[0]);
				    				 int min = Integer.parseInt(indtimes[1]);
				    				 int sec = Integer.parseInt(indtimes[2]);
				    				 
				    				 if(time>24 || min >60 ||sec>60 ){
				    					 frame2.setState(Frame.ICONIFIED);
				    					 Timeinput.infoBox("Entered the incorrect value for hour/minit/second. \n"
				    					 		+ "Please enter the valid time.", "Error!");
				    					 frame2.setState(Frame.NORMAL);
				    				 }else{
				    				 
				    				frame2.dispose();		    				
									TimeSheetRemainder .runTask(time,min,sec,Selectedfields);
				    				 }
				    			}
								} catch (ParseException e) {
									
									e.printStackTrace();
								}
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
	        
	        public void actionPerformed(ActionEvent e){

	        	if(e.getSource()==selectall){
	        		
	        		if(sunday.isSelected()==false){
	        		
	        			 monday.setSelected(true);
	        			 tuesday.setSelected(true);
	        			 wednesday.setSelected(true);
	        			 thursday.setSelected(true);
	        			 friday.setSelected(true);
	        			 saturday.setSelected(true);
	        			 sunday.setSelected(true);
	        			 	        		
	             		
	        		   }
	        		 else if(sunday.isSelected()==true){
	        			 monday.setSelected(false);
	        			 tuesday.setSelected(false);
	        			 wednesday.setSelected(false);
	        			 thursday.setSelected(false);
	        			 friday.setSelected(false);
	        			 saturday.setSelected(false);
	        			 sunday.setSelected(false);
	        			 
	        			 
	        	   } 
	        		 
	            }
	        	if(e.getSource()==monday||e.getSource()==tuesday||e.getSource()==wednesday||e.getSource()==thursday||e.getSource()==friday||e.getSource()==saturday||e.getSource()==sunday){
 	        		if(monday.isSelected()==false||tuesday.isSelected()==false||wednesday.isSelected()==false||thursday.isSelected()==false||friday.isSelected()==false||saturday.isSelected()==false||sunday.isSelected()==false){
 	        			selectall.setSelected(false);
 	        		}
 	        	}
	        }
	        
	      	       
	        
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            g.drawImage(image, 0, 0, getWidth(), getHeight(), this); 
	            
	        }
	        
	       
	      	    }
	    
	    
	     public static void infoBox(String infoMessage, String titleBar)
	        {
	      		JOptionPane.showMessageDialog(null, infoMessage, "" + titleBar,JOptionPane.ERROR_MESSAGE);
	      		
	        }  
	   	      
}

