package GUIFinal2;

import java.awt.Color;
import java.awt.event.MouseEvent;

public class BTNAddPlaceListener extends MouseOperaListener{

	public void mouseClicked(MouseEvent e) {
		Simulation.BTN_AddPlace.setVisible(true);
		Simulation.BTN_AddPlace.setForeground(Color.BLUE); 
		Simulation.bt = Button.ADD_PLACE;
	}	
}
