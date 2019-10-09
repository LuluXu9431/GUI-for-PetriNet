package GUIFinal2;

import java.io.*;
import java.util.Iterator;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
//import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

public class OpenFile extends JPanel implements ActionListener{
	JFileChooser fileChooser;
	static JFrame frame;
    	JButton openButton;
    	JButton exitButton;
    	JTextArea log;
    	static private final String newline = "\n";
	
    	public static void openFile() {
  
		//Create and set up the window.
		frame = new JFrame("Open An Existing Petri Net From File");
		frame.setSize(new Dimension(800, 600));
		frame.setLocationRelativeTo(null);

		//Add content to the window.
		frame.add(new OpenFile());

		//Display the window.
		frame.pack();
		frame.setVisible(true);
    	}
	
	
	public OpenFile() {
		
		super(new BorderLayout());
		//Create the log first, because the action listeners
        	//need to refer to it.
		log = new JTextArea(5,20);
		log.setMargin(new Insets(5,5,5,5));
		log.setEditable(false);
		JScrollPane logScrollPane = new JScrollPane(log);

		//Create a file chooser
		fileChooser = new JFileChooser();

		openButton = new JButton("Choose a folder");
		openButton.addActionListener(this);

		exitButton =new JButton("Exit");
		exitButton.addActionListener(this);

		//For layout purposes, put the buttons in a separate panel
		JPanel buttonPanel = new JPanel(); //use FlowLayout
		buttonPanel.add(openButton);

		JPanel buttonPanel2 = new JPanel(); //use FlowLayout
		buttonPanel2.add(exitButton);

		//Add the buttons and the log to this panel.
		add(buttonPanel, BorderLayout.NORTH);
		add(logScrollPane, BorderLayout.CENTER);
		add(buttonPanel2, BorderLayout.SOUTH);		
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == openButton) {
	            int returnVal = fileChooser.showOpenDialog(OpenFile.this);
	            if (returnVal == JFileChooser.APPROVE_OPTION) {
	                File file = fileChooser.getSelectedFile();
	                try {
				readJSONFile(file);
				} catch (org.json.simple.parser.ParseException e1) {
					e1.printStackTrace();
				}
	                log.append("Open File " + file.getName() + " successfully." + newline);
	                log.append("Click Exit to quit.");
	            } else {
	                log.append("Save command cancelled by user." + newline);
	            }
	            log.setCaretPosition(log.getDocument().getLength());
	        }
	        else if(e.getSource() == exitButton){
	        	frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	        }
		
	}
	
	 public void readJSONFile(File file) throws org.json.simple.parser.ParseException{
		
		 JSONParser parser = new JSONParser();
		  
		 try {
			 
			 Object obj = parser.parse(new FileReader("/Users/luluxu/Documents/eclipse-workspace/GUI-PetriNet-31/JSON/"+file.getName()));	 
			 JSONObject jsonObj = (JSONObject) obj;
			 JSONArray placesArray = (JSONArray)jsonObj.get("Places");
			 JSONArray transArray = (JSONArray)jsonObj.get("Transitions");
			 
			 Iterator<JSONObject> placeItr1 = placesArray.iterator();
			 Iterator<JSONObject> transItr2 = transArray.iterator();
			
			 while(placeItr1.hasNext()) {
				 JSONObject singlePlace =(JSONObject) placeItr1.next();
				 
				 int x = new Long((long)singlePlace.get("Coordinate-X")).intValue();
				 int y = new Long((long)singlePlace.get("Coordinate-Y")).intValue();
				 String aString  =String.valueOf(singlePlace.get("Place")) ;
				 
				 int token = new Long((long)singlePlace.get("Tokens#")).intValue();

				 if(Simulation.net.addPlace(x, y)) {
					 Simulation.panel2.repaint();
					 Simulation.panel2.add(Simulation.net.setLabelField(aString,x, y));
				 }
				 if( token > 0) {
					 for(int i = 0; i < token;i++) {
						 if(Simulation.net.addToken(x,y)) {
							 Simulation.panel2.repaint();			
					 	} 
					 }
				 }	
				
		 	}
			
			 while(transItr2.hasNext()) {

				 JSONObject singleTrans =(JSONObject) transItr2.next();

				 int x =new Long((long)singleTrans.get("Coordinate-X")).intValue();
				 int y = new Long((long)singleTrans.get("Coordinate-Y")).intValue();

				 String aString  =String.valueOf(singleTrans.get("Transition")) ;
				 String bString  =String.valueOf(singleTrans.get("Simulation Time")) ;
				 if(Simulation.net.addTrans(x, y)) {
					 Simulation.panel2.add(Simulation.net.setTransField(aString, x,y));
					 Simulation.panel2.add(Simulation.net.setTimeField(bString,x,y));
					 Simulation.panel2.repaint();	 
				 }
			 }	


			 JSONObject jsonObj2 = (JSONObject) obj;
			 JSONArray placesArray2 = (JSONArray)jsonObj.get("Places");
			 JSONArray transArray2 = (JSONArray)jsonObj.get("Transitions");

			 Iterator<JSONObject> placeItr12 = placesArray.iterator();
			 Iterator<JSONObject> transItr22 = transArray.iterator();

			 while(placeItr12.hasNext()) {
				
				 JSONObject singlePlace =(JSONObject) placeItr12.next();
				 int id = new Long((long)singlePlace.get("PlaceId")).intValue();
				 JSONArray arcsArray = (JSONArray)singlePlace.get("Arcs");
				 Iterator<JSONObject> arcItr12 = arcsArray.iterator();

				 while(arcItr12.hasNext()) {
					 JSONObject singleArc =(JSONObject) arcItr12.next(); 
					 int transId = new Long ((long)singleArc.get("Arc")).intValue();
					 System.out.println(transId);

					 Simulation.net.findPlace(id).addArc(Simulation.net.findTransId(transId));
					 Simulation.panel2.repaint();	
				 }


		 	}	 
			 
			 while(transItr22.hasNext()) {
				 JSONObject singleTrans =(JSONObject) transItr22.next();
				 int id = new Long((long)singleTrans.get("TransId")).intValue();
				
				 JSONArray arcsArray2 = (JSONArray)singleTrans.get("Arcs");
				 Iterator<JSONObject> arcItr2 = arcsArray2.iterator();

				 while(arcItr2.hasNext()) {
					 JSONObject singleArc =(JSONObject) arcItr2.next();
					 System.out.println(singleArc.get("Arc")); 
					 int placeId = new Long ((long)singleArc.get("Arc")).intValue();
					 Simulation.net.findTransId(id).addArcOut(Simulation.net.findPlace(placeId));
					 Simulation.panel2.repaint();
				 }	 
			 }	

		 }
		 
		 catch (FileNotFoundException e){
			 e.printStackTrace(); 
		 } catch (IOException e) {
	            e.printStackTrace();
	     } 	 
	 }
}
