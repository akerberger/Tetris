package pieces;

import java.awt.Color;

public class SPiece extends Piece {

	public SPiece() {
		

		Color c = Color.RED;

		Square s1 = new Square(120, 0, c);
		addSquareToList(s1);

		Square s2 = new Square(140, 0, c);
		addSquareToList(s2);

		Square s3 = new Square(140, -20, c);
		addSquareToList(s3);

		Square s4 = new Square(160, -20, c);
		addSquareToList(s4);

		setOrientation(Orientation.POINT_RIGHT);

	}

	public void rotate() {
		Orientation orientation = getOrientation();

		if (orientation == Orientation.POINT_RIGHT) {

			Square square = getSquare(3);
			square.newPosition(square.getX(), square.getY() + 20);

			square = getSquare(0);
			square.newPosition(square.getX() + 40, square.getY() + 20);

			setOrientation(Orientation.POINT_UP);
		}

		else if (orientation == Orientation.POINT_UP) {
			Square square = getSquare(3);
			square.newPosition(square.getX(), square.getY() - 20);

			square = getSquare(0);
			square.newPosition(square.getX() - 40, square.getY() - 20);

			setOrientation(Orientation.POINT_RIGHT);
		}

		
	}

}
