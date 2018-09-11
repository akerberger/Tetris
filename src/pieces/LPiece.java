package pieces;

import java.awt.Color;

public class LPiece extends Piece {

	public LPiece() {
		super();

		Color c = Color.WHITE;

		Square square = new Square(120, -20, c);
		addSquareToList(square);

		square = new Square(140, -20, c);
		addSquareToList(square);

		square = new Square(160, -20, c);
		addSquareToList(square);

		square = new Square(120, 0, c);
		addSquareToList(square);

		setOrientation(Orientation.POINT_DOWN);

	}

	public void rotate() {

		Orientation orientation = getOrientation();

		Square square;

		if (orientation == Orientation.POINT_DOWN) {

			square = getSquare(0);
			square.newPosition(square.getX() + 20, square.getY() - 20);

			square = getSquare(2);
			square.newPosition(square.getX() - 20, square.getY() + 20);

			square = getSquare(3);
			square.newPosition(square.getX(), square.getY() - 40);

			setOrientation(Orientation.POINT_LEFT);

		}

		else if (orientation == Orientation.POINT_LEFT) {

			square = getSquare(0);
			square.newPosition(square.getX() + 20, square.getY() + 20);

			square = getSquare(2);
			square.newPosition(square.getX() - 20, square.getY() - 20);

			square = getSquare(3);
			square.newPosition(square.getX() + 40, square.getY());

			setOrientation(Orientation.POINT_UP);
		}

		else if (orientation == Orientation.POINT_UP) {

			square = getSquare(0);
			square.newPosition(square.getX() - 20, square.getY() + 20);

			square = getSquare(2);
			square.newPosition(square.getX() + 20, square.getY() - 20);

			square = getSquare(3);
			square.newPosition(square.getX(), square.getY() + 40);

			setOrientation(Orientation.POINT_RIGHT);
		}

		else if (orientation == Orientation.POINT_RIGHT) {

			square = getSquare(0);
			square.newPosition(square.getX() - 20, square.getY() - 20);

			square = getSquare(2);
			square.newPosition(square.getX() + 20, square.getY() + 20);

			square = getSquare(3);
			square.newPosition(square.getX() - 40, square.getY());

			setOrientation(Orientation.POINT_DOWN);
		}



	}

}
