package GUIFinal2;


import java.awt.event.MouseEvent;

public class PanelListener extends MouseOperaListener{
	
	public void mousePressed(MouseEvent e) 
	{
		Simulation.drag = Simulation.net.selectDrag(e.getX(),e.getY());
		if(!Simulation.drag) {
			//Simulation.drag = Simulation.net.selectDrag(e.getX(),e.getY());
			return;
		}
           
		else {
        	   	//Simulation.drag = Simulation.net.selectDrag(e.getX(),e.getY());
        	   	Simulation.panel2.repaint();
        	   	if(Simulation.drag) {
 
        	   		switch(Simulation.net.drag(e.getX(),e.getY())) {
        	   		case 1:
        	   			Simulation.panel2.remove(Simulation.net.getPlaceTextField(e.getX(), e.getY()));
        	   		break;
        	   			
        	   		case 2:
        	   			Simulation.panel2.remove(Simulation.net.getTransTextField(e.getX(), e.getY()));
        	   			Simulation.panel2.remove(Simulation.net.getTimeTextField(e.getX(), e.getY()));
        	   		break;
        	   			
        	   		default:
        	   		break;	
        	   			}
        	   		}
        	   	Simulation.drag = true;
    	   		Simulation.panel2.repaint();
			}
	
	}

	
	public void mouseReleased(MouseEvent e)
	{	
		if(!Simulation.drag) {
			System.out.println("进入mouse-release1");	
			return;
		}
		
		else {
			System.out.println("MOUSE_UP----drag ture");
			System.out.println("进入mouse-released2");
			
			switch(Simulation.net.deselectDrag(e.getX(),e.getY())) {	
			case 1:
				Simulation.panel2.add(Simulation.net.updatePlaceTextField(e.getX(), e.getY()));
			break;
			
			case 2:
				Simulation.panel2.add(Simulation.net.updateTransTextField(e.getX(), e.getY()));
				Simulation.panel2.add(Simulation.net.updateTimeTextField(e.getX(), e.getY()));
			break;
			default:
			break;	
			}
			Simulation.panel2.repaint();
			Simulation.drag = false;
			System.out.println("一次点击完成");
		}
		
	}

		public void mouseClicked(MouseEvent e) {
			System.out.println("进入mouse-clicked");
			switch(Simulation.bt){

			case ADD_PLACE:	
				Simulation.BTN_AddPlace.setForeground(null);
				if(Simulation.net.addPlace(e.getX(), e.getY())) {
					
					Simulation.panel2.add(Simulation.net.getPlaceTextField(e.getX(), e.getY()));
					Simulation.panel2.repaint();		
				}
				Simulation.bt = Button.PANEL;
				break;
			case ADD_TOKEN:
				Simulation.BTN_AddToken.setForeground(null);
				if(Simulation.net.addToken(e.getX(),e.getY())) {
					Simulation.panel2.repaint();			
				}
				Simulation.bt = Button.PANEL;
				break;
				
			case ADD_TRANSITION:
				Simulation.BTN_AddTrans.setForeground(null);
				if(Simulation.net.addTrans(e.getX(),e.getY())) {
					Simulation.panel2.add(Simulation.net.getTransTextField(e.getX(), e.getY()));
					Simulation.panel2.add(Simulation.net.getTimeTextField(e.getX(), e.getY()));
					Simulation.panel2.repaint();	
				}
				Simulation.bt = Button.PANEL;	
				break;
				
			case ADD_ARC:
				if (Simulation.net.placeAddArc(e.getX(),e.getY())) {
					System.out.println("情况1place-add-arc开始的情况");
					Simulation.bt = Button.TRANS_ADD_ARC;// 
				}
				else {
					if (Simulation.net.transAddArc(e.getX(),e.getY())) {
						System.out.println("trane-add-arc开始的情况");
						Simulation.bt = Button.PLACE_ADD_ARC;// 
				}
					else {
						Simulation.BTN_AddArc.setForeground(null);
						break;
					}
				}
				Simulation.panel2.repaint();
				break;
			//arc 从 transition 出发	
			case TRANS_ADD_ARC:
				System.out.println("情况1 换了click1 place-->trans");
				if (Simulation.net.transAddArc(e.getX(),e.getY())) {
					System.out.println("情况1 换了click1 后要返回");
					Simulation.net.addArc();
					Simulation.BTN_AddArc.setForeground(null);
				}
				Simulation.panel2.repaint();
				//Simulation.bt = Button.PANEL;
				break;
			// arc 从place 出发
			case PLACE_ADD_ARC:
				if (Simulation.net.placeAddArc(e.getX(),e.getY())) {
					Simulation.net.addArc();
					Simulation.BTN_AddArc.setForeground(null);
				}
				Simulation.panel2.repaint();
				Simulation.bt = Button.PANEL;
				break;	
			case DELETE_PLACE:
				Simulation.BTN_DeletePlace.setForeground(null);
				Simulation.panel2.remove(Simulation.net.getPlaceTextField(e.getX(), e.getY()));
				if(Simulation.net.deletePlace(e.getX(),e.getY())) {
					Simulation.panel2.repaint();
				}
				Simulation.bt = Button.PANEL;
				break;
				
			case DELET_TRANSITION:
				Simulation.BTN_DeleteTrans.setForeground(null);
				Simulation.panel2.remove(Simulation.net.getTransTextField(e.getX(), e.getY()));
				Simulation.panel2.remove(Simulation.net.getTimeTextField(e.getX(), e.getY()));
				if(Simulation.net.deleteTrans(e.getX(),e.getY())) {
					Simulation.panel2.repaint();		
				}
				Simulation.bt = Button.PANEL;
				break;
				
			case DELETE_TOKEN:
				Simulation.BTN_DeleteToken.setForeground(null);
				if(Simulation.net.removeToken(e.getX(),e.getY())) {
					Simulation.panel2.repaint();
					Simulation.bt = Button.PANEL;
				}
				break;
	
			case DELETE_ARC:
				if (Simulation.net.placeAddArc(e.getX(),e.getY())) {
					System.out.println("情况1");
					Simulation.bt = Button.TRANS_DELETE_ARC;
				}
				else {
					if (Simulation.net.transAddArc(e.getX(),e.getY())) {
						System.out.println("情况2");
						Simulation.bt = Button.PLACE_DELETE_ARC;
					}
					else {
						Simulation.BTN_DeleteArc.setForeground(null);
						System.out.println("情况3！！！！！");
						break;
					}
				}
				Simulation.panel2.repaint();	
				break;
			
			case TRANS_DELETE_ARC:
				if (!Simulation.net.transAddArc(e.getX(),e.getY())) {
					System.out.println("情况1-1 false");
					break;
				}
				System.out.println("情况1-1true");
				Simulation.net.removeArc();
				Simulation.panel2.repaint();	
				Simulation.BTN_DeleteArc.setForeground(null);
				break;
			
			case PLACE_DELETE_ARC:
				if (!Simulation.net.placeAddArc(e.getX(),e.getY())) {
					System.out.println("情况2-1 false");
					break;
				}
				System.out.println("情况2-1 true");
				Simulation.net.removeArc();	
				Simulation.panel2.repaint();	
				Simulation.BTN_DeleteArc.setForeground(null);
				break;
				
			case RUN:
				Simulation.BTN_Run.setForeground(null);
				switch (Simulation.net.run()) {
					case PetriNet.NO_TRANS:
						break;
					case PetriNet.NORMAL:
					default:
						Simulation.panel2.repaint();
						break;
				}	
				Simulation.bt = Button.PANEL;
				break;
				
			default:		
				break;
			}
		}
}
	
