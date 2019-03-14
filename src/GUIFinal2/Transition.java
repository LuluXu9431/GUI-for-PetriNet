package GUIFinal2;

/* IMPORTS */
import java.awt.*;
import java.util.*;

import javax.swing.JTextField;
public class Transition {
	public Vector<Arc> arcsIn, arcsOut;
	private Arc removeArc, arc;

	final static int NORMAL = 0, MOVING = 1, ACTIVE = 2, HIGHLIGHT = 3;
	private Color colorNormal, colorSelected, colorActive, colorHighlight, c;
	final static int WIDTH = 15, HEIGHT = 40, LSIZE = 60, SIZE = 30;
	public int currentX, currentY, prevX, prevY, dragX, dragY;
	private int currentState, prevState;
	public int transId;
	private boolean valid;
	private String label;
	private JTextField labelField;
	private JTextField timeField;
	private int time;
	Random random = new Random();
	
	public Transition(int i, int x, int y) {
		transId = i;
		currentX = prevX = x;
		currentY = prevY = y;

		arcsIn = new Vector<Arc>(5, 1);// 5:设定向量对象的容量（即向量对象可存储数据的大小），
		// 当真正存放的数据个数超过容量时。系统会扩充向量对象存储容量。参数capacityincrement：1给定了每次扩充的扩充值。
		arcsOut = new Vector<Arc>(5, 1);//
		removeArc = (Arc) null;

		colorNormal = new Color(0, 0, 0);//
		colorSelected = new Color(0, 125, 255);//
		colorActive = new Color(255, 0, 0);//
		colorHighlight = new Color(255, 155, 0);//

		valid = true;
		currentState = NORMAL;
	
		label = "t-0" + new Integer(transId).toString();
		labelField = new JTextField(label);
		labelField.setBounds(currentX+20, currentY+20,LSIZE, SIZE);
		
		time=random.nextInt(20)+1;
		timeField = new JTextField(String.valueOf(time));
		timeField.setBounds(currentX+20,currentY-20,LSIZE, SIZE);	
		
	}
	public int getCurrentState() {
		return currentState;
	}
	public String getLabel() {
		return label;
	}
	
	public int getTime() {
		return time;
	}
	
	public JTextField getTimeField() {
		return timeField;
	}
	
	
	public JTextField getLabelField() {
		return labelField;
	}
	
	public JTextField updateLabelField() {
		labelField.setBounds(currentX+20, currentY+20,LSIZE, SIZE);
		return labelField;	
	}
	
	public JTextField setlabelField(String aString) {
		labelField.setText(aString);
		labelField.setBounds(currentX+20, currentY+20,LSIZE, SIZE);
		return labelField;	
	}

	public JTextField updateTimelField() {
		timeField.setBounds(currentX+20, currentY-20,LSIZE, SIZE);
		return timeField;	
	}
	public JTextField setTimeField(String bString) {
		timeField.setText(bString);
		timeField.setBounds(currentX+20, currentY-20,LSIZE, SIZE);
		return timeField;	
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
		move(currentX + (x - dragX), currentY + (y - dragY));
		dragX = x;
		dragY = y;
		currentState = prevState;
	}

	public void drag(int x, int y) {
		move(currentX + (x - dragX), currentY + (y - dragY));
		dragX = x;
		dragY = y;
	}

	public void paint(Graphics g) {//每一次paint之前先检查currentState
		//System.out.println("进入trans的paint");
		remove(g);

		if (!valid)
			return;

		switch (currentState) {
		case MOVING:
			c = colorSelected;
			break;
		case HIGHLIGHT:
			c = colorHighlight;
			break;
		case ACTIVE:
			c = colorActive;
			break;
		case NORMAL:
		default:
			c = colorNormal;
			break;
		}

		g.setColor(c);
		g.drawRect(currentX, currentY, WIDTH, HEIGHT);// 画一个长方形

		
		//arc 的颜色与 transition 一致
		for (int i = 0; i < arcsOut.size(); i++) {
			arc = (Arc) (arcsOut.elementAt(i));

			switch (currentState) {
			case MOVING:
				c = colorSelected;
				break;
			case HIGHLIGHT:
				c = colorHighlight;
				break;
			case ACTIVE:
				c = colorActive;
				//currentState = NORMAL;
			case NORMAL:
			default:
				c = colorNormal;
				break;
			}

			g.setColor(c);
			arc.paint(g);
		}//绘制arc结束

		//g.drawString(label, currentX + 5 + WIDTH, currentY + HEIGHT / 2 + 5);//显示transition的label
	}

	public void remove(Graphics g) {//remove的作用是个啥
		g.setColor(Color.white);
		g.fillRect(prevX, prevY, WIDTH, HEIGHT);
		//g.drawString(label, currentX + 5 + WIDTH, currentY + HEIGHT / 2 + 5);//画出transition

		for (int i = 0; i < arcsOut.size(); i++) {
			arc = (Arc) (arcsOut.elementAt(i));
			arc.removeArc(g);

			if (arc == removeArc || !arc.placeValid()) {
				arcsOut.removeElementAt(i);
				i--;
				removeArc = (Arc) null;
			}
		}

		prevX = currentX;
		prevY = currentY;

	}

	public Rectangle bounds() {
		return new Rectangle(currentX, currentY, WIDTH, HEIGHT);
	}

	public boolean inside(int x, int y) {
		return (x >= currentX && x <= currentX + WIDTH && y >= currentY && y <= currentY + HEIGHT);
	}

	public void move(int x, int y) {
		currentX = x;
		currentY = y;
	}

	public int transId() {
		return transId;
	}

	public void delete() {
		valid = false;
	}

	public boolean valid() {
		return valid;
	}

	public boolean isActive(){//****** check if there is at least one token in the place before it
		int i;
		for (i = 0; i < arcsIn.size(); i++) {
			arc = (Arc) (arcsIn.elementAt(i));
			if (arc.place().getTokenCount() == 0)//return toPlace
				return false;
		}
		return (i != 0);
	}

	public void highlightOn() {
		currentState = HIGHLIGHT;//
	}

	public void highlightOff() {
		currentState = NORMAL;
	}


	public void addArcIn(Arc x) {//arcIn里面的arc 都是Arc(Place p, Transition t)：arcToPlace=false
		
		arcsIn.addElement( x);
	}
	//用处是在place那边的addArc
	
	public void removeArcIn(Arc x) {
		System.out.println("情况1-----4--------------------------end");
		arcsIn.removeElement((Object) x);
	} 
	
	public void addArcOut(Place p) {// arc从transition出发	
		//先判断有没有arc指向place的
		System.out.println("情况2-----------------------end");
		for (int i = 0; i < arcsOut.size(); i++) {//遍历arcOut（存储arc） 这个向量的实际元素
			arc = (Arc) (arcsOut.elementAt(i));//返回第i处的组建，是一个Arc
			if (arc.place() == p)//找到有return toPlace的
				return;
		}

		Arc arc = new Arc(this, p);// 新定义一个arc  constructor:Arc(Transition t, Place p) 结果：arctoPlace=true
		arcsOut.addElement( arc);// 将arc插入向量arcOut的尾部
	}

	
	
	public void removeArcOut(Place p) {// 删除指向特定place的arc
		for (int i = 0; i < arcsOut.size(); i++) {
			arc = (Arc) (arcsOut.elementAt(i));
			if (arc.place() == p) {
				removeArc = arc;
				return;
			}
		}
		arcsOut.removeElement((Object) removeArc);
	}

	
	public void moveToken() {//**********************
		currentState = ACTIVE;
		//*****所有指向transition的place里面的token 都要 减1
		for (int i = 0; i < arcsIn.size(); i++) {
			arc = (Arc) (arcsIn.elementAt(i));
			arc.place().removeToken();//实际上是arc连接的place
		}
		//*****所有transition指向的place里面的token 都要 加1
		for (int i = 0; i < arcsOut.size(); i++) {
			arc = (Arc) (arcsOut.elementAt(i));
			arc.place().addToken();
		}
	}

	public void IdSync(IdSync r) {
		for (int i = 0; i < arcsOut.size(); i++) {
			arc= (Arc) (arcsOut.elementAt(i));
			arc.IdSync(r);
		}
	}
}
