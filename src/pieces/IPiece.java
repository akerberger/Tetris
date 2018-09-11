package pieces;


import java.awt.Color;

public class IPiece extends Piece {

	public IPiece() {

		int startX = 120;
		for (int i = 0; i < 4; i++) {

			Square square = new Square(startX, 0, Color.BLUE); 
			addSquareToList(square);
			startX = startX + 20;
		}
		
//		setOrientation(Orientation.POINT_RIGHT);

	}// constructor

	public void rotate() {

		Orientation orientation = getOrientation();

		if (orientation == Orientation.POINT_UP) {

			int deltaX = 40;
			int deltaY = 40;

			for (int i = 0; i < 4; i++) {

				Square square = getSquare(i);

				square.newPosition(square.getX() + deltaX, square.getY() - deltaY);
				deltaX = deltaX - 20;
				deltaY = deltaY - 20;

			}
			setOrientation(Orientation.POINT_RIGHT);
		}

		else if (orientation == Orientation.POINT_RIGHT) {
			int deltaX = 40;
			int deltaY = 40;
			for (int i = 0; i < 4; i++) {
				Square square = getSquare(i);
				square.newPosition(square.getX() - deltaX, square.getY() + deltaY);
				deltaX = deltaX - 20;
				deltaY = deltaY - 20;
			}
			setOrientation(Orientation.POINT_UP);
		}

		

	}

}// class
