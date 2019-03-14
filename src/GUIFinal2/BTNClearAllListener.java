package GUIFinal2;

import java.awt.Color;
import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

public class BTNClearAllListener extends MouseOperaListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		
		int dialogButton = JOptionPane.YES_NO_OPTION;
		int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you want to wipe out all Petri net components?","Warning!",dialogButton);
		if(dialogResult == JOptionPane.YES_OPTION){
			Simulation.BTN_ClearAll.setVisible(true);
			Simulation.BTN_ClearAll.setForeground(Color.BLUE);
			Simulation.panel2.removeAll();
			Simulation.panel2.repaint();
			Simulation.net= new PetriNet();
			Simulation.BTN_ClearAll.setForeground(null);
		}
		Simulation.bt = Button.PANEL;
	}
}

