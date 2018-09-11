package pieces;

import game.*;
import panels.*;
import java.util.List;
import java.util.ArrayList;

public abstract class Piece { // nya

	private List<Square> squares = new ArrayList<>();
	
	private Orientation orientation = Orientation.POINT_UP;

	

	public void moveRight() {
		for (Square s : squares) {
			s.setX(s.getX() + 20);
		}
		
	}

	public void moveLeft() {
		for (Square s : squares) {
			s.setX(s.getX() - 20);
		}
	
	}

	public void moveDown() {

		for (Square s : squares) {

			s.setY(s.getY() + 20);

		}

	}

	public abstract void rotate();

	public boolean reachedLeftBorder() {
		for (Square s : squares) {
			if (s.getX() == 0) {
				return true;
			}
		}

		return false;
	}

	public boolean reachedRightBorder() {
		for (Square s : squares) {
			if (s.getX() == 280) {
				return true;
			}
		}
		return false;
	}

	public int getNoOfSquares() {
		return squares.size();
	}

	public boolean reachedBottom() {

		for (Square s : squares) {
			if (s.getY() == 560) {

				return true;
			}
		}
		return false;

	}

	protected void setOrientation(Orientation newOrientation) {
		orientation = newOrientation;
	}

	public Square getSquare(int orderInList) {
		return squares.get(orderInList);
	}


	public Orientation getOrientation() {
		return orientation;
	}

	protected void addSquareToList(Square square) {
		squares.add(square);
	}

	public boolean isAtStartLevel() {

		for (Square s : squares) {
			if (s.getY() == 0) {
				
				return true;
			}
		}

		return false;

	}


	public String toString() {

		Square s = getSquare(0);

		return s.toString();

	}

}
