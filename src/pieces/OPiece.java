package pieces;


import java.awt.Color;

public class OPiece extends Piece {

	public OPiece() {


		int startX = 120;

		for (int i = 0; i < 2; i++) {
			Square square = new Square(startX, -20, Color.YELLOW);
			addSquareToList(square);
			startX = startX + 20;
		}

		startX = 120;

		for (int i = 2; i < 4; i++) {
			Square square = new Square(startX, 0, Color.YELLOW);
			addSquareToList(square);
			startX = startX + 20;
		}

	}

	public void rotate() {
		return; // OPiece doesn´t rotate
	}

}// class
