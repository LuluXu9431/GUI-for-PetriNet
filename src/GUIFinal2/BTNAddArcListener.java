package GUIFinal2;

import java.awt.Color;
import java.awt.event.MouseEvent;

public class BTNAddArcListener extends MouseOperaListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		Simulation.BTN_AddArc.setVisible(true);
		Simulation.BTN_AddArc.setForeground(Color.BLUE);
		Simulation.bt = Button.ADD_ARC;
	}
}

