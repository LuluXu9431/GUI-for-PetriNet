package GUIFinal2;


/* IMPORTS */
import java.awt.*;
public class Arc {

	public Place toPlace;
	public Transition toTrans;
	public boolean arcToPlace;
	public int objId;

	public int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
	public int labelX = 0, labelY = 0;
	public int xat = 0, xa1 = 0, xa2 = 0, yat = 0, ya1 = 0, ya2 = 0;

	public Arc(Place p, Transition t) {//   p->t
		toPlace = p;
		toTrans = t;
		arcToPlace = false;//************wc
		objId = t.transId();//返回指向的transition的ID
	}

	public Arc(Transition t, Place p) {//   t->p
		toTrans = t;
		toPlace = p;
		arcToPlace = true;//***************
		objId = p.placeId();//返回place的int型 id
	}

	public void paint(Graphics g) {
		Rectangle s, e;// start and end

		if (arcToPlace)//t->p为真，
			s = toTrans.bounds();//start：t
		else
			s = toPlace.bounds();//

		if (arcToPlace)//t->p为真
			e = toPlace.bounds();//end : p
		else
			e = toTrans.bounds();

		int dx = Math.abs(e.x - s.x);
		int dy = Math.abs(e.y - s.y);

		if (e.x >= s.x + s.width && dx >= dy) {// end 的坐标大于start的坐标
			x1 = s.x + s.width;
			y1 = s.y + s.height / 4 * 3;

			x2 = e.x;
			y2 = e.y + e.height / 4 * 3;

			xa1 = x2 - 5;
			ya1 = y2 - 5;

			xa2 = x2 - 5;
			ya2 = y2 + 5;

			labelX = x1 + (x2 - x1) / 4;
			labelY = y1 + (y2 - y1) / 4;
		} else if (s.y >= e.y + e.height && dy >= dx) {
			x1 = s.x + s.width / 4 * 3;
			y1 = s.y;

			x2 = e.x + e.width / 4 * 3;
			y2 = e.y + e.height;

			xa1 = x2 + 5;
			ya1 = y2 + 5;

			xa2 = x2 - 5;
			ya2 = y2 + 5;

			labelX = x1 + (x2 - x1) / 2;
			labelY = y1 + (y2 - y1) / 2;
		} else if (s.x > e.x + e.width && dx > dy) {
			x1 = s.x;
			y1 = s.y + s.height / 4;

			x2 = e.x + e.width;
			y2 = e.y + e.height / 4;

			xa1 = x2 + 5;
			ya1 = y2 + 5;

			xa2 = x2 + 5;
			ya2 = y2 - 5;

			labelX = x1 + (x2 - x1) / 4 * 3;
			labelY = y1 + (y2 - y1) / 4 * 3;
		} else if (e.y > s.y + s.height && dy > dx) {
			x1 = s.x + s.width / 4;
			y1 = s.y + s.height;

			x2 = e.x + e.width / 4;
			y2 = e.y;

			xa1 = x2 - 5;
			ya1 = y2 - 5;

			xa2 = x2 + 5;
			ya2 = y2 - 5;

			labelX = x1 + (x2 - x1) / 2;
			labelY = y1 + (y2 - y1) / 2;
		} else
			x1 = x2 = y1 = y2 = xa1 = xa2 = ya1 = ya2 = 0;

		xat = x2;
		yat = y2;

		draw(g);
	}

	public void draw(Graphics g) {//draw ->
		g.drawLine(x1, y1, x2, y2);
		g.drawLine(xat, yat, xa1, ya1);
		g.drawLine(xat, yat, xa2, ya2);
	}

	public void removeArc(Graphics g) {
		draw(g);
	}

	public boolean placeValid() {
		return (toPlace != (Place) null && toPlace.valid());
	}

	public boolean transValid() {
		return (toTrans != (Transition) null && toTrans.valid());
	}

	public boolean toPlace() {
		return arcToPlace;
	}

	public Place place() {
		return toPlace;
	}

	public Transition trans() {
		return toTrans;
	}

	public void IdSync(IdSync s) {
		if (arcToPlace) {
			if (toPlace == (Place) null)
				toPlace = (Place) s.syncPlaceId(objId);
		} else {
			if (toTrans == (Transition) null)
				toTrans = (Transition) s.syncTransId(objId);
		}
	}
}

