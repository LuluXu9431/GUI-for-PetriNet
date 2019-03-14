package GUIFinal2;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class SaveFile extends JPanel
					implements ActionListener {

	static Place[] places; 
	static Transition[] transitions;
    JFileChooser fc;
    static private final String newline = "\n";

    static JFrame frame;
    JButton saveButton;
    JButton exitButton;
    JTextArea log;

    //static method, entry point of the class
    public static void saveFile(Place[] l1, Transition[] l2) {
    		places = l1;
    		transitions = l2;
   
        //Create and set up the window.
        frame = new JFrame("Save Petri Net to a JSON File");
        frame.setSize(new Dimension(600, 400));
        frame.setLocationRelativeTo(null);
        
        //Add content to the window.
        frame.add(new SaveFile());
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
  
    //constructor
    public SaveFile() {
    	super(new BorderLayout());
 
        //Create the log first, because the action listeners
        //need to refer to it.
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
 
        //Create a file chooser
        fc = new JFileChooser();
 
        saveButton = new JButton("Choose a folder");
        saveButton.addActionListener(this);
        
        exitButton =new JButton("Exit");
        exitButton.addActionListener(this);
 
        //For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(saveButton);
        
        JPanel buttonPanel2 = new JPanel(); //use FlowLayout
        buttonPanel2.add(exitButton);
 
        //Add the buttons and the log to this panel.
        add(buttonPanel, BorderLayout.NORTH);
        add(logScrollPane, BorderLayout.CENTER);
        add(buttonPanel2, BorderLayout.SOUTH);
    }
 
    //act upon click of the two buttons
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveButton) {
            int returnVal = fc.showSaveDialog(SaveFile.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                write(file);
                log.append("File saved to " + file.getName() + " successfully." + newline);
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

    //write places and transitions to a json file
    //filename is chosen by JFileChooser
	public void write(File file) {	
		
		JSONObject jsonObj = new JSONObject();
		
		// store the places into placesArray
		JSONArray placesArray = new JSONArray();
		JSONArray transArray = new JSONArray();	

		//create 一个 placeObj
		for(int i = 0;i < places.length;i++) {	
			JSONObject placeObj=new JSONObject();
			
			if(places[i]!=null) {
				placeObj.put("PlaceId",places[i].placeId);
				placeObj.put("Coordinate-X", places[i].currentX);//坐标	
				placeObj.put("Coordinate-Y", places[i].currentY);
				placeObj.put("Tokens#",places[i].getTokenCount());//token
				placeObj.put("Place",places[i].getTextField().getText());//name
				
				JSONArray  placeArcsArray = new JSONArray();	
				// create 一个 arcObj
				for(int j = 0; j < places[i].arcs.size(); j++) {
					JSONObject placeArcObj = new JSONObject();
					Arc arc = (Arc) (places[i].arcs.elementAt(j));
					// arc 不为空
					if (arc!=null) {
						placeArcObj.put("Arc",arc.toTrans.transId);
						placeArcsArray.add(placeArcObj);
					}// 
				}	
				placeObj.put("Arcs", placeArcsArray);	
			}
			// 若place[i]为null,继续 for loop
			else
				continue;
			//结束 create  一个 placeObj--------//并且 将其 加入数组 placesArray
			placesArray.add(placeObj);	
		}	
		// 将 placesArray 作为 key“places” 的 value
		jsonObj.put("Places", placesArray);
		// store the transitions into transitionsArray
		
		for(int p=0;p<transitions.length;p++) {
			JSONObject transObj=new JSONObject();
			if(transitions[p]!=null) {
				transObj.put("TransId", transitions[p].transId);
				transObj.put("Coordinate-X",transitions[p].currentX);
				transObj.put("Coordinate-Y",transitions[p].currentY);
				transObj.put("Transition",transitions[p].getLabelField().getText());
				transObj.put("Simulation Time",transitions[p].getTimeField().getText());

				JSONArray  tranArcsArray = new JSONArray();
				
				for(int k = 0; k < transitions[p].arcsOut.size(); k++) {
					JSONObject tranArcObj = new JSONObject();
					Arc arc = (Arc) (transitions[p].arcsOut.elementAt(k));
					if (arc!=null) {
						tranArcObj.put("Arc",arc.toPlace.placeId);
						tranArcsArray.add(tranArcObj);
					}
				}	
				transObj.put("Arcs", tranArcsArray);		
			}
			else 
				continue;
			transArray.add(transObj);
		}	
		jsonObj.put("Transitions", transArray);	
		
		//write to json file
		try(FileWriter f = new FileWriter(file)){//create a json file
			 f.write(jsonObj.toJSONString());// write all the places to the file
			// f.write(jsonTransition.toJSONString());//write all the transitions to the file	
			 System.out.println("Successfully Copied JSON Object to File...");
		}
		catch(IOException e) {
			 e.printStackTrace();
		}
	}
}