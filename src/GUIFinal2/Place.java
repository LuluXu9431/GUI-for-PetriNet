package GUIFinal2;
//Class Place
import java.awt.*;
import java.util.*;

import javax.swing.JTextField;

//import Applet.Place.LableListener;

public class Place {
	public Vector<Arc> arcs;
	private Arc removeArc, arc;
	public boolean valid;
	private String label;
	public int currentX, currentY, prevX, prevY, dragX, dragY;
	final static int NORMAL = 0, MOVING = 1, ACTIVE = 2, HIGHLIGHT = 3;
	
	public int tokenCounter, currentState, prevState;
	public int placeId;
	
	final static int SIZE = 30, LSIZE = 60, TOKEN_SIZE = 10;
	public Color colorNormal, colorSelected, colorActive, colorHighlight, c;
	JTextField labelField;
	public Place(int i, int x, int y) {
		placeId = i;
		currentX = prevX = x;
		currentY = prevY = y;

		arcs = new Vector<>(5, 1);
		removeArc = (Arc) null;

		colorNormal = new Color(0, 0, 0);//balck set some colors (red,green,blue)
		colorSelected = new Color(0, 125, 255);
		colorActive = new Color(255, 0, 0);
		colorHighlight = new Color(255, 155, 0);

		valid = true;
		tokenCounter = 0;
		currentState = NORMAL;	
		
		label = "p" + new Integer(placeId).toString();//p1,p2,p3.......
		labelField = new JTextField(label);
		labelField.setBounds(currentX+20, currentY+20,LSIZE, SIZE);	
	}
	
	public JTextField setlabelField(String aString) {
		labelField.setText(aString);
		labelField.setBounds(currentX+20, currentY+20,LSIZE, SIZE);
		return labelField;	
	}
	
	public JTextField updateTextField() {
		labelField.setBounds(currentX+20, currentY+20,LSIZE, SIZE);
		return labelField;
	}
	
	
	public JTextField getTextField() {
		return labelField;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getTokens() {
		return String.valueOf(tokenCounter);
	}
	
	public String getCoordinate() {
		String aString  ='('+String.valueOf(this.currentX)+','+String.valueOf(this.currentY)+')';
		return aString;
	}
	
	
	public void dragStart(int x, int y) {
		dragX = x;
		dragY = y;
		prevState = currentState;
		currentState = MOVING;
	}
	
	public void dragStop(int x, int y) {
		System.out.println("MOUSE_UP----drag ture--进入deselectdrag---情况1:placedrag--place的dragstop");
		move(currentX + (x - dragX), currentY + (y - dragY));
		dragX = x;
		dragY = y;
		currentState = prevState;
		System.out.println("MOUSE_UP----drag ture--进入deselectdrag---情况1:placedrag--place的dragstop-----end");
		System.out.println(this.currentX+" "+this.currentY);
	}

	public void drag(int x, int y) {
		move(currentX + (x - dragX), currentY + (y - dragY));//currentX=currentX+(X-dragX);currentY=currentY+(Y-dragX)
		dragX = x;
		dragY = y;
	}


	public void paint(Graphics g) {
		removePlace(g);
		if (!valid)
			return;
		switch (currentState) {
		case MOVING:
			c = colorSelected;
			break;
		case ACTIVE:
			c = colorActive;
			break;
		case HIGHLIGHT:
			c = colorHighlight;
			break;
		case NORMAL:
		default:
			c = colorNormal;
			break;
		}
		g.setColor(c);
		g.drawOval(currentX, currentY, SIZE, SIZE);
	
		if (tokenCounter > 0) {
			g.drawOval(currentX + SIZE / 2 - TOKEN_SIZE / 2, currentY + SIZE - TOKEN_SIZE * 2, TOKEN_SIZE, TOKEN_SIZE);// 画token的圆
			g.fillOval(currentX + SIZE / 2 - TOKEN_SIZE / 2, currentY + SIZE - TOKEN_SIZE * 2, TOKEN_SIZE, TOKEN_SIZE);
			if (tokenCounter > 1)
				g.drawString((new Integer(tokenCounter)).toString(), currentX + SIZE / 2 - 3, currentY + SIZE / 2 - 5);
		} 

		for (int i = 0; i < arcs.size(); i++) {
			arc = (Arc) (arcs.elementAt(i));

			switch (currentState) {
			case MOVING:
				c = colorSelected;
				break;
			case HIGHLIGHT:
				c = colorHighlight;
				break;
			case ACTIVE:
			case NORMAL:
			default:
				c = colorNormal;
				break;
			}

			g.setColor(c);// 
			arc.paint(g);
		}
		
		//g.drawString(label, currentX + SIZE + 5, currentY + SIZE - 35);
		
		
	}//end paint()

	public void removePlace(Graphics g) {
		g.setColor(Color.white);
		g.fillOval(prevX, prevY, SIZE, SIZE);
		//g.drawString(label, currentX + SIZE + 5, currentY + SIZE - 35);

		for (int i = 0; i < arcs.size(); i++) {
			arc = (Arc) (arcs.elementAt(i));
			arc.removeArc(g);
			if (arc == removeArc || !arc.transValid()) {
				arcs.removeElementAt(i);
				i--;
				removeArc = (Arc) null;
			}
		}

		prevX = currentX;
		prevY = currentY;
	}//end remove

	public Rectangle bounds() {
		return new Rectangle(currentX, currentY, SIZE, SIZE);
	}

	// check if the place inside the
	public boolean inside(int x, int y) {
		return (x >= currentX && x <= currentX + SIZE && y >= currentY && y <= currentY + SIZE);
	}

	public void move(int x, int y) {
		currentX = x;
		currentY = y;
	}

	public int placeId() {
		return placeId;
	}

	public void delete() {
		valid = false;
	}

	public boolean valid() {
		return valid;
	}

	public void highlightOn() {
		currentState = HIGHLIGHT;
	}

	public void highlightOff() {
		currentState = NORMAL;
	}


	
	public void addArc(Transition t) {
		
		for (int i = 0; i < arcs.size(); i++) {
			arc = (Arc) (arcs.elementAt(i));
			if (arc.trans() == t)//return toTrans
				return;
		}
		
		Arc arc = new Arc(this, t);//arcToPlace=false
		arcs.addElement( arc);
		
		t.addArcIn(arc);
	}

	
	public void removeArc(Transition t) {// place->transition
		
		for (int i = 0; i < arcs.size(); i++) {
			arc = (Arc) (arcs.elementAt(i));
			if (arc.trans() == t) {
				removeArc = arc;
				
				removeArc.trans().removeArcIn(removeArc);
				//return;
			}
		}
	}

	public void addToken() {
		tokenCounter++;
	}

	public boolean removeToken() {
		if (tokenCounter > 0) {
			tokenCounter--;
			return true;
		} else
			return false;
	}

	public int getTokenCount() {
		return tokenCounter;
	}

	public void IdSync(IdSync r) {//
		for (int i = 0; i < arcs.size(); i++) {
			arc = (Arc) (arcs.elementAt(i));
			arc.IdSync(r);
			arc.trans().addArcIn(arc);
		}
	}
	

	
	
	
}
