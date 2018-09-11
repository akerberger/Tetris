package pieces;

import java.awt.Color;

public class ZPiece extends Piece {

	public ZPiece() {
		

		Color c = Color.CYAN;

		Square s1 = new Square(120, -20, c);
		addSquareToList(s1);

		Square s2 = new Square(140, -20, c);
		addSquareToList(s2);

		Square s3 = new Square(140, 0, c);
		addSquareToList(s3);

		Square s4 = new Square(160, 0, c);
		addSquareToList(s4);

		setOrientation(Orientation.POINT_LEFT);

	}

	public void rotate() {
		Orientation orientation = getOrientation();

		if (orientation == Orientation.POINT_LEFT) {
			Square square = getSquare(0);
			square.newPosition(square.getX() + 20, square.getY() + 40);

			square = getSquare(1);
			square.newPosition(square.getX() + 20, square.getY());

			setOrientation(Orientation.POINT_UP);
		}

		else if (orientation == Orientation.POINT_UP) {
			Square square = getSquare(0);
			square.newPosition(square.getX() - 20, square.getY() - 40);

			square = getSquare(1);
			square.newPosition(square.getX() - 20, square.getY());

			setOrientation(Orientation.POINT_LEFT);
		}

		
	}

}
