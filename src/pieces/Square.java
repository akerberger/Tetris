package pieces;

import javax.swing.*;
import java.awt.*;

public class Square extends JComponent {		

	private int x;
	private int y;
	private Color c;

	public Square(int x, int y, Color c) {

		this.x = x;
		this.y = y;
		this.c = c;

		setBounds(x, y, 19, 19);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(c);
		g.fillRect(0, 0, 19, 19);

	}

	
	public void newPosition(int newX, int newY) {
		x = newX;
		y = newY;
		setBounds(x, y, 19, 19);
	}

	public void setY(int newY) {

		y = newY;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public String toString() {			//ta bort denna metod sen för att kolla datan som kommer med dess naturliga toString();
		return y+"   "+x;
	}
	
	
	public void setX(int newX) {
		x = newX;
	}
	
	public Color getColor() {
		return c;
	}

}
