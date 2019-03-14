package GUIFinal2;

import java.awt.Color;
import java.awt.event.MouseEvent;

public class BTNDeleteTokenListener extends MouseOperaListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		Simulation.BTN_DeleteToken.setVisible(true);
		Simulation.BTN_DeleteToken.setForeground(Color.BLUE);
		Simulation.bt = Button.DELETE_TOKEN;
	}
}
