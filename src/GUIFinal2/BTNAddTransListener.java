package GUIFinal2;

import java.awt.Color;
import java.awt.event.MouseEvent;

public class BTNAddTransListener extends MouseOperaListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		Simulation.BTN_AddTrans.setVisible(true);
		Simulation.BTN_AddTrans.setForeground(Color.BLUE); 
		Simulation.bt = Button.ADD_TRANSITION;
	}	
}
