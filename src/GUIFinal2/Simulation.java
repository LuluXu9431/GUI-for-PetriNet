package GUIFinal2;
import java.awt.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

interface IdSync {
	Place syncPlaceId(int id);
	Transition syncTransId(int id);
}

public class Simulation extends JPanel{
	
	public static PetriNet net;
	public static JScrollPane scrollpane;
	public static JPanel panel1;
	public static JPanel panel2;
	public static JPanel panel3;
	public static JPanel panel4;
	public static JFrame frame;
	public static JButton BTN_ClearAll, BTN_Run, BTN_Stop, 
						 BTN_AddPlace,BTN_AddToken, BTN_AddTrans, BTN_AddArc,
						 BTN_DeletePlace, BTN_DeleteToken, BTN_DeleteTrans, BTN_DeleteArc, 
						 BTN_EventTiming, BTN_ResConfig, BTN_AddComment,BTN_Preferences;
	public static JButton BTN_New,BTN_Open,BTN_Save,BTN_SaveAs,BTN_Close,BTN_Exit,BTN_Simulation,BTN_Analysis,BTN_Help;

	public static Button bt = Button.PANEL;
	public static boolean drag;
	
	public static void Simulation() {

		frame = new JFrame();
		//frame.addMyWindowListener();
		panel1 = new JPanel();
		panel1.setBackground(Color.WHITE);
		panel1.setLayout(new GridLayout(0,7));
		frame.getContentPane().add(BorderLayout.NORTH, panel1);

		
		scrollpane = new JScrollPane();
		panel2 = new Simulation();
		panel2.setBackground(Color.WHITE);
		panel2.setLayout(new FlowLayout());
		frame.getContentPane().add(BorderLayout.CENTER, panel2);
		
		panel3 = new Simulation();
		panel3.setBackground(Color.LIGHT_GRAY);
		panel3.setLayout(new BoxLayout(panel3,BoxLayout.Y_AXIS));
		TitledBorder titledBorder  = BorderFactory.createTitledBorder("Petri Net");
		panel3.setBorder(titledBorder);
		
	
		frame.getContentPane().add(BorderLayout.WEST, panel3);
		
		panel4 = new Simulation();
		panel4.setBackground(Color.LIGHT_GRAY);
		panel4.setLayout(new FlowLayout());
		JLabel panel4Name = new JLabel("Monmouth University, West Long Branch, New Jersey, 2018");
		panel4.add(panel4Name);
	
		frame.getContentPane().add(BorderLayout.SOUTH,panel4);

		BTN_ClearAll = new JButton("Clear All"); BTN_ClearAll.addMouseListener(new BTNClearAllListener());
		BTN_Run = new JButton("Run");  BTN_Run.addMouseListener(new BTNRunListener());
		BTN_Stop = new JButton("Stop");  BTN_Stop.addMouseListener(new BTNStopListener());
		BTN_AddPlace = new JButton("Add Place"); BTN_AddPlace.addMouseListener(new BTNAddPlaceListener());
		BTN_AddToken = new JButton("Add Token"); BTN_AddToken.addMouseListener(new BTNAddTokenListener());
		BTN_AddTrans = new JButton("Add Trans"); BTN_AddTrans.addMouseListener(new BTNAddTransListener());
		BTN_AddArc = new JButton("Add Arc"); BTN_AddArc.addMouseListener(new BTNAddArcListener());
		BTN_DeletePlace = new JButton("Delete Place"); BTN_DeletePlace.addMouseListener(new BTNDeletePlaceListener());
		BTN_DeleteToken = new JButton("Delete Token"); BTN_DeleteToken.addMouseListener(new BTNDeleteTokenListener());
		BTN_DeleteTrans = new JButton("Delete Trans"); BTN_DeleteTrans.addMouseListener(new BTNDeleteTransListener());
		BTN_DeleteArc = new JButton("Delete Arc"); BTN_DeleteArc.addMouseListener(new BTNDeleteArcListener());
		BTN_EventTiming = new JButton("Event Timing");
		BTN_ResConfig = new JButton("ResConfig");
		BTN_AddComment = new JButton("Add Comment");

		BTN_New= new JButton("New");
		BTN_New.addMouseListener(new BTNClearAllListener());
		BTN_Open = new JButton("Open");
		
		BTN_Open.addMouseListener(new BTNOpenFileListener());
		
		BTN_Save = new JButton("Save");
		BTN_Save.addMouseListener(new BTNSaveFileListener());
		BTN_SaveAs = new JButton("Save As");
		BTN_SaveAs.addMouseListener(new BTNSaveFileListener());
		BTN_Close = new JButton("Close");
		
		BTN_Exit = new JButton("Exit");
		BTN_Exit.addMouseListener(new BTNExitListener());
		
//		BTN_Simulation = new JButton("Simulation");
//		BTN_Analysis = new JButton("Analysis");
//		BTN_Preferences = new JButton("Preferences");;
		BTN_Help = new JButton("Help");
		
		panel3.add(BTN_New);
		panel3.add(BTN_Open);
		panel3.add(BTN_Save);
		panel3.add(BTN_SaveAs);
		panel3.add(BTN_Close);
		panel3.add(BTN_Exit);
//		panel3.add(BTN_Simulation);
//		panel3.add(BTN_Analysis);
//		panel3.add(BTN_Preferences);
		panel3.add(BTN_Help);
		
		panel1.add(BTN_ClearAll); panel1.add(BTN_Run); panel1.add(BTN_Stop); panel1.add(BTN_AddPlace);
		panel1.add(BTN_AddToken); panel1.add(BTN_AddTrans); panel1.add(BTN_AddArc); panel1.add(BTN_DeletePlace);
		panel1.add(BTN_DeleteToken); panel1.add(BTN_DeleteTrans); panel1.add(BTN_DeleteArc); panel1.add(BTN_EventTiming);
		panel1.add(BTN_ResConfig);panel1.add(BTN_AddComment);

		panel2.addMouseListener(new PanelListener());
			
		frame.setTitle("PetriNet Application");
		frame.setSize(new Dimension(1200, 800));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

		frame.setVisible(true);
	
		net= new PetriNet();
		drag = false;
	}
	
	public void paint(Graphics g) {
		if(bt == Button.CLEAR_ALL){
			g.setColor(Color.WHITE);
			bt = Button.PANEL;
		}
		//System.out.println("进入到simulation的paint");
		super.paint(g);
		g.setColor(Color.WHITE);
		net.paint(g);// method defined in PetriNet class
		//System.out.println("_______退出simulation的paint,退出 net.paint()____________");
	}
	
	public static void main(String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable(){
			public void run(){
				Simulation();
			}
		});
	}
}	


