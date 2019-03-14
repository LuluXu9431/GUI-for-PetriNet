package GUIFinal2;

import java.awt.Color;
import java.awt.event.MouseEvent;

public class BTNDeleteArcListener extends MouseOperaListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		Simulation.BTN_DeleteArc.setVisible(true);
		Simulation.BTN_DeleteArc.setForeground(Color.BLUE); 
		Simulation.bt = Button.DELETE_ARC;
	}
}
