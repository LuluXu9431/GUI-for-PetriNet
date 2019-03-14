package GUIFinal2;

import java.awt.Color;
import java.awt.event.MouseEvent;

public class BTNDeleteTransListener extends MouseOperaListener{

	@Override
	public void mouseClicked(MouseEvent e) {
		Simulation.BTN_DeleteTrans.setVisible(true);
		Simulation.BTN_DeleteTrans.setForeground(Color.BLUE);
		Simulation.bt = Button.DELET_TRANSITION;
	}
}

