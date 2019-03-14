package GUIFinal2;

import java.awt.Color;
import java.awt.event.MouseEvent;

public class BTNOpenFileListener extends MouseOperaListener{
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Simulation.BTN_Open.setVisible(true);
		Simulation.BTN_Open.setForeground(Color.BLUE);
		
		OpenFile.openFile();
		Simulation.BTN_Open.setForeground(null);		
	}
}
