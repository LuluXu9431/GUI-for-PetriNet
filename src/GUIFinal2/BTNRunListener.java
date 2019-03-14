package GUIFinal2;

import java.awt.Color;
import java.awt.event.MouseEvent;

public class BTNRunListener extends MouseOperaListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		Simulation.BTN_Run.setVisible(true);
		Simulation.BTN_Run.setForeground(Color.BLUE);
		Simulation.bt = Button.RUN;
	}	
}

