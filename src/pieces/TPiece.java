package pieces;

import java.awt.Color;

public class TPiece extends Piece {

	public TPiece() {
		

		Color c = Color.GREEN;

		Square s1 = new Square(120, 0, c);
		addSquareToList(s1);

		Square s2 = new Square(140, 0, c);
		addSquareToList(s2);

		Square s3 = new Square(160, 0, c);
		addSquareToList(s3);

		Square s4 = new Square(140, -20, c);
		addSquareToList(s4);

	}

	public void rotate() {
		Orientation orientation = getOrientation();

		if (orientation == Orientation.POINT_UP) {

			Square square = getSquare(0);
			square.newPosition(square.getX() + 20, square.getY() + 20);

			setOrientation(Orientation.POINT_RIGHT);

		} else if (orientation == Orientation.POINT_RIGHT) {
			Square square = getSquare(3);

			square.newPosition(square.getX() - 20, square.getY() + 20);

			setOrientation(Orientation.POINT_DOWN);

		}

		else if (orientation == Orientation.POINT_DOWN) {
			Square square = getSquare(2);

			square.newPosition(square.getX() - 20, square.getY() - 20);
			setOrientation(Orientation.POINT_LEFT);
		}

		else if (orientation == Orientation.POINT_LEFT) {
			Square square = getSquare(2);
			square.newPosition(square.getX() + 20, square.getY() + 20);

			square = getSquare(0);
			square.newPosition(square.getX() - 20, square.getY() - 20);

			square = getSquare(3);
			square.newPosition(square.getX() + 20, square.getY() - 20);

			setOrientation(Orientation.POINT_UP);
		}

		

	}

}
