package pieces;

import java.awt.Color;

public class JPiece extends Piece {

	public JPiece() {
		

		Color c = Color.magenta;

		Square square = new Square(120, -20, c);
		addSquareToList(square);

		square = new Square(120, 0, c);
		addSquareToList(square);

		square = new Square(140, 0, c);
		addSquareToList(square);

		square = new Square(160, 0, c);
		addSquareToList(square);

	}

	public void rotate() {
		Orientation orientation = getOrientation();

		Square square;

		if (orientation == Orientation.POINT_UP) {
			square = getSquare(0);
			square.newPosition(square.getX() + 40, square.getY());

			square = getSquare(1);
			square.newPosition(square.getX() + 20, square.getY() - 20);

			square = getSquare(3);
			square.newPosition(square.getX() - 20, square.getY() + 20);

			setOrientation(Orientation.POINT_RIGHT);
		}

		else if (orientation == Orientation.POINT_RIGHT) {

			square = getSquare(0);
			square.newPosition(square.getX(), square.getY() + 40);

			square = getSquare(1);
			square.newPosition(square.getX() + 20, square.getY() + 20);

			square = getSquare(3);
			square.newPosition(square.getX() - 20, square.getY() - 20);

			setOrientation(Orientation.POINT_DOWN);
		}

		else if (orientation == Orientation.POINT_DOWN) {
			square = getSquare(0);
			square.newPosition(square.getX() - 40, square.getY());

			square = getSquare(1);
			square.newPosition(square.getX() - 20, square.getY() + 20);

			square = getSquare(3);
			square.newPosition(square.getX() + 20, square.getY() - 20);

			setOrientation(Orientation.POINT_LEFT);
		}

		else if (orientation == Orientation.POINT_LEFT) {
			square = getSquare(0);
			square.newPosition(square.getX(), square.getY() - 40);

			square = getSquare(1);
			square.newPosition(square.getX() - 20, square.getY() - 20);

			square = getSquare(3);
			square.newPosition(square.getX() + 20, square.getY() + 20);

			setOrientation(Orientation.POINT_UP);
		}

	

	}

}
