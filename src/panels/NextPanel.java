package panels;


import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import pieces.*;

public class NextPanel extends JComponent {

	private Piece nextPiece = null;

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.setColor(Color.BLACK);
		g.fillRect(5, 0, 110, 110);

		if (nextPiece instanceof IPiece) {
			g.setColor(Color.BLUE);
			g.fillRect(30, 45, 14, 14);
			g.fillRect(45, 45, 14, 14);
			g.fillRect(60, 45, 14, 14);
			g.fillRect(75, 45, 14, 14);

		} else if (nextPiece instanceof TPiece) {
			g.setColor(Color.GREEN);
			g.fillRect(45, 60, 14, 14);
			g.fillRect(60, 60, 14, 14);
			g.fillRect(75, 60, 14, 14);
			g.fillRect(60, 45, 14, 14);

		} else if (nextPiece instanceof JPiece) {
			g.setColor(Color.MAGENTA);
			g.fillRect(45, 45, 14, 14);
			g.fillRect(60, 45, 14, 14);
			g.fillRect(75, 45, 14, 14);
			g.fillRect(45, 30, 14, 14);
		} else if (nextPiece instanceof OPiece) {
			g.setColor(Color.YELLOW);

			g.fillRect(45, 45, 14, 14);
			g.fillRect(60, 45, 14, 14);
			g.fillRect(45, 60, 14, 14);
			g.fillRect(60, 60, 14, 14);
		} else if (nextPiece instanceof SPiece) {
			g.setColor(Color.RED);

			g.fillRect(60, 45, 14, 14);
			g.fillRect(75, 45, 14, 14);
			g.fillRect(60, 60, 14, 14);
			g.fillRect(45, 60, 14, 14);
		} else if (nextPiece instanceof ZPiece) {
			g.setColor(Color.CYAN);

			g.fillRect(45, 45, 14, 14);
			g.fillRect(60, 45, 14, 14);
			g.fillRect(60, 60, 14, 14);
			g.fillRect(75, 60, 14, 14);
		} else if (nextPiece instanceof LPiece) {
			g.setColor(Color.WHITE);

			g.fillRect(45, 45, 14, 14);
			g.fillRect(60, 45, 14, 14);
			g.fillRect(75, 45, 14, 14);
			g.fillRect(45, 60, 14, 14);
		}

	}

	public void updateNextPiece(Piece piece) {

		nextPiece = piece;

		repaint();
	}

}
