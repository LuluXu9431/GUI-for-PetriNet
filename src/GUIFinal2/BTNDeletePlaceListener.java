package GUIFinal2;


import java.awt.Color;
import java.awt.event.MouseEvent;

public class BTNDeletePlaceListener extends MouseOperaListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		Simulation.BTN_DeletePlace.setVisible(true);
		Simulation.BTN_DeletePlace.setForeground(Color.BLUE);
		Simulation.bt = Button.DELETE_PLACE;
	}
}
