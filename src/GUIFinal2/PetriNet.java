package GUIFinal2;
/* IMPORTS */
import java.awt.*;
import java.util.*;
import javax.swing.*;

public class PetriNet implements IdSync {

	public Place places[];
	public Transition trans[];

	public Place placeDelete, placeAddArc, placeDrag;
	public Transition transDelete, transAddArc, transDrag;

	final static int LIMIT = 500, NORMAL = 1, NO_TRANS = 2;
	public int numOfPlaces, numOfTrans, transI;
	public boolean arcToPlace, arcDirection;

	private Vector<Transition> activableTrans;
	private Random r;

	
	public JTextField textField ;
	public PetriNet() {
		places = new Place[LIMIT];
		trans = new Transition[LIMIT];
		placeDelete = placeAddArc = placeDrag = (Place) null;
		transDelete = transAddArc = transDrag = (Transition) null;
		numOfPlaces = numOfTrans = 0;
		arcToPlace = arcDirection = false;
		r = new Random();

		for (int i = 0; i < LIMIT; i++)
			places[i] = (Place) null;

		for (int i = 0; i < LIMIT; i++)
			trans[i] = (Transition) null;

		activableTrans = new Vector<Transition>(LIMIT);
	}

	public Place syncPlaceId(int id) {
		if (id < 0 || id >= LIMIT)
			return (Place) null;
		return places[id];
	}

	public Transition syncTransId(int id) {
		if (id < 0 || id >= LIMIT)
			return (Transition) null;
		return trans[id];

	}

	public void paint(Graphics g) {
		if (placeDelete != (Place) null) {
			placeDelete.removePlace(g);
			placeDelete = (Place) null;
		}

		if (transDelete != (Transition) null) {
			transDelete.remove(g);
			transDelete = (Transition) null;
		}

		for (int i = LIMIT - 1; i >= 0; i--)
			if (places[i] != (Place) null){	
				places[i].paint(g);//画出所有的place
			}
		for (int i = LIMIT - 1; i >= 0; i--)
			if (trans[i] != (Transition) null)
				trans[i].paint(g);//画出所有的transition
	}

	public Place locatePlace(int x, int y) {
		for (int i = 0; i < LIMIT; i++)
			if (places[i] != (Place) null && places[i].inside(x, y))
				return places[i];

		return (Place) null;
	}

	public Transition locateTrans(int x, int y) {
		for (int i = 0; i < LIMIT; i++)
			if (trans[i] != (Transition) null && trans[i].inside(x, y))
				return trans[i];

		return (Transition) null;
	}

	public boolean addPlace(int x, int y) {// add a place in the net
		if (numOfPlaces == LIMIT){
			return false;
		}
		else {
			numOfPlaces++;
			int i;
			for (i = 0; i < LIMIT; i++) {//找到空位？？？？
				if (places[i] == (Place) null)
					break;
			}
			places[i] = new Place(i, x, y);//constructor 
			
		}
		return true;	
	}
	
	
	
	
	
	public JTextField getPlaceTextField(int x, int y) {
		Place p = locatePlace(x,y);
		//System.out.println(p.currentX+" "+p.currentY);
		textField = p.getTextField();
		//System.out.println(textField.getX()+" "+textField.getY());
		return textField;
	}
	public JTextField updatePlaceTextField(int x, int y) {
		Place p = locatePlace(x,y);
		//System.out.println(p.currentX+" "+p.currentY);
		textField = p.updateTextField();
		//System.out.println(textField.getX()+" "+textField.getY());
		return textField;
	}
	public JTextField setLabelField(String aString,int x, int y) {
		Place p = locatePlace(x,y);
		textField = p.setlabelField(aString);
		return textField;
	}
	
	public JTextField getTransTextField(int x, int y) {
		Transition trans = locateTrans(x,y);
		textField = trans.getLabelField();
		//textField.setText(trans.getLabel());
		return textField;
	}
	
	public JTextField getTimeTextField(int x, int y) {
		Transition trans = locateTrans(x,y);
		textField = trans.getTimeField();
		textField.setText(String.valueOf(trans.getTime()));
		return textField;
		
	}
	public JTextField updateTransTextField(int x, int y) {
		Transition trans = locateTrans(x,y);
		textField = trans.updateLabelField();
		//textField.setText(trans.getLabel());
		return textField;
	}
	public JTextField updateTimeTextField(int x, int y) {
		Transition trans = locateTrans(x,y);
		textField = trans.updateTimelField();
		//textField.setText(trans.getLabel());
		return textField;
	}
	public JTextField setTransField(String aString,int x, int y) {
		Transition t = locateTrans(x,y);
		textField = t.setlabelField(aString);
		return textField;
	}
	
	public JTextField setTimeField(String aString,int x, int y) {
		Transition t = locateTrans(x,y);
		textField = t.setTimeField(aString);
		return textField;
	}
	
	
	public boolean deletePlace(int x, int y) {
		
		if (numOfPlaces == 0) {
			return false;
		}
		else{
			numOfPlaces--;
			int i;
			for (i = 0; i < LIMIT; i++) {// 找到要删除的place
				System.out.println("进入循环");
				if (places[i] != (Place) null && places[i].inside(x, y)){
					System.out.println(places[i].getLabel());
					break;
				}		
			}
		
			if (i == LIMIT) {	
				return false;
			}
			else {
				placeDelete = places[i];
				placeDelete.delete();// valid==false			
				places[i] = (Place) null;// 位置变空	
			}
		}
		return true;	
	}

	public boolean addTrans(int x, int y) {
		if (numOfTrans == LIMIT) {
			return false;}
		else {
			numOfTrans++;
			int i;
			for (i = 0; i < LIMIT; i++)// 找到空位子
				if (trans[i] == (Transition) null)
					break;
			trans[i] = new Transition(i, x, y);
		}
		return true;
	}

	public boolean deleteTrans(int x, int y) {
		if (numOfTrans == 0) {		
			return false;}
		else{
			numOfTrans--;
			int i;
			for (i = 0; i < LIMIT; i++)
				if (trans[i] != (Transition) null && trans[i].inside(x, y))
					break;
				if (i == LIMIT)
					return false;
				transDelete = trans[i];
				transDelete.delete();
				trans[i] = (Transition) null;
			}
		return true;
	}
	
	public Transition findTransId(int id) {
		Transition t = (Transition)null;
		for(int i = 0; i < trans.length; i++) {
			if(trans[i].transId == id) {
				t = trans[i];
				break;
			}
		}
		return t;
	}
	
	public Place findPlace(int id) {
		Place p = (Place) null;
		for(int i = 0; i < places.length;i++) {
			if(places[i].placeId == id) {
				p = places[i];
				break;
			}
		}
		return p;
	}
	

	public boolean placeAddArc(int x, int y) {
		placeAddArc = locatePlace(x, y);// placeAddArc:Place类
		//find the clicked place
		if (placeAddArc == (Place) null)// if the place's (valid )is false 
			return false;
		placeAddArc.highlightOn();//改变clicked的place的颜色
		
		if (!arcDirection) {//
			arcToPlace = false; 
			arcDirection = true;
		}
		return true;
	}

	public boolean transAddArc(int x, int y) {
		transAddArc = locateTrans(x, y);//transAddArc:Transition类

		if (transAddArc == (Transition) null)
			return false;

		transAddArc.highlightOn();//改变clicked的transition的颜色

		if (!arcDirection) {
			arcToPlace = true;
			arcDirection = true;
		}

		return true;
	}

	public void addArc() {// add arc between place and transition
		System.out.println("进入add arc了");
		if (placeAddArc != (Place) null && transAddArc != (Transition) null) {
			if (arcToPlace) {
				transAddArc.addArcOut(placeAddArc);// transition-place
			}
			else {
				System.out.println("情况1是指向tran的arc");
				placeAddArc.addArc(transAddArc);// place-transition
				
			}
			placeAddArc = (Place) null;
			transAddArc = (Transition) null;
		}

		arcDirection = false;
	}

	public void removeArc() {//remove arc between place and transition
		if (placeAddArc != (Place) null && transAddArc != (Transition) null) {
			if (arcToPlace) {
				System.out.println("情况2-2");
				transAddArc.removeArcOut(placeAddArc);
			} 
			else {//arc不是指向place
				System.out.println("情况1-2");
				placeAddArc.removeArc(transAddArc);
			} // place->transition
			placeAddArc = (Place) null;
			transAddArc = (Transition) null;
		}

		arcDirection = false;
	}

	public boolean addToken(int x, int y) {
		int i;
		for (i = 0; i < LIMIT; i++)// 找寻定位的place
			if (places[i] != (Place) null && places[i].inside(x, y))
				break;
		if (i == LIMIT)
			return false;
		places[i].addToken();//tokens++
		return true;
	}

	public boolean removeToken(int x, int y) {
		int i;
		for (i = 0; i < LIMIT; i++)//找寻定位的place
			if (places[i] != (Place) null && places[i].inside(x, y))
				break;
		if (i == LIMIT)
			return false;
		return places[i].removeToken();// tokens--
	}

	public int drag(int x, int y) {
		if (placeDrag != (Place) null) {
			placeDrag.drag(x, y);
			return 1;
		}
			
		else if (transDrag != (Transition) null) {
			transDrag.drag(x, y);
			
		}
		return 2;	
	}

	public boolean selectDrag(int x, int y) {
		System.out.println("进入select drag");
		placeDrag = locatePlace(x, y);
		//System.out.println(placeDrag.currentX+" "+placeDrag.currentY);
		
		
		if (placeDrag == (Place) null) {
			transDrag = locateTrans(x, y);
			if (transDrag == (Transition) null) {
				System.out.println("进入select drag---return false");
				return false;
			} else {
				System.out.println("选择要drag 的是 trans");
				transDrag.dragStart(x, y);
				return true;
			}
		} else {
			System.out.println("选择要drag 的是 place");
			placeDrag.dragStart(x, y);
			return true;
		}
	}

	public int deselectDrag(int x, int y) {//
		System.out.println("MOUSE_UP----drag ture--进入deselectdrag");
		if (placeDrag != (Place) null) {
			placeDrag.dragStop(x, y);
			System.out.println("MOUSE_UP----drag ture--进入deselectdrag---情况1:placedrag");
			return 1;
			
		}
		else if (transDrag != (Transition) null) {
			System.out.println("MOUSE_UP----drag ture--进入deselectdrag---情况2:transdrag");
			transDrag.dragStop(x, y);
			
		}
		placeDrag = (Place) null;
		transDrag = (Transition) null;
		return 2;
	}

	public int start() {//
		stop();
		return NORMAL;
	}

	public int run() {
		activableTrans.setSize(0);// vector

		for (int i = 0; i < LIMIT && i < numOfTrans; i++) {
			if (trans[i] == (Transition) null)
				continue;
			if (trans[i].isActive())
				activableTrans.addElement((trans[i]));//将obj插入向量的尾部。obj可以是任何类型的对象。
			//对同一个向量对象，亦可以在其中插入不同类的对象。但插入的应是对象而不是数值，所以插入数值时要注意将数组转换成相应的对象。
		}

		if (activableTrans.size() == 0)
			return NO_TRANS;
		
	
//		for( int i = 0; i < LIMIT && i < activableTrans.size(); i++ ) {
//			if (activableTrans.elementAt(i).getCurrentState() == 2) {
//				Transition move = (Transition) activableTrans.elementAt(transI);
//				move.moveToken();//*****************
//			}
//			else 
//				continue;
//		}
//		return NORMAL;//run完之后，回归normal

		transI = (int) (r.nextFloat() * activableTrans.size());// 

		Transition move = (Transition) activableTrans.elementAt(transI);
		move.moveToken();//*****************

		return NORMAL;//run完之后，回归normal
	}

	public void stop() {
	}
	
	public boolean saveNet() {
		if(numOfPlaces==0||numOfTrans==0) {
			return false;
		}
		else {
			System.out.println("进入petri net的 save net");
			SaveFile.saveFile(places, trans);
			
			return true;		
		}
	}	
}

