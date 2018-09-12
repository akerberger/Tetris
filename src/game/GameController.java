package game;

import javax.swing.*;
import java.awt.event.*;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.TreeMap;
import java.io.*;
import pieces.*;
import panels.*;
import listeners.*;

public class GameController {

	private boolean gameRunning = false;
	private boolean firstGameOfSession = true;

	private Piece pieceInPlay;
	private Piece nextPiece;

	private GamePanel gamePanel = new GamePanel();
	private SidePanel sidePanel = new SidePanel(this);

	private Timer timer = new Timer(1000, new TimerListener(this));

	private JTextField nameField;

	private int score = 0;
	private int level = 1;

	private HighScore[] top3Array = new HighScore[3];

	private Map<Integer, Integer> rowCount = new TreeMap<>(); // y-coordinate, counter with 15 as full row
	private Map<Integer, List<Square>> squaresOnSameY = new HashMap<>(); // y-coordinate, list of squares on the same
																			// row

	private Set<Integer> yOfFullRows = new TreeSet<>(); // temporarily holds y-coordinates for full row/rows:
														// checkIfFullRows()

	public GameController() {
		new GUI(gamePanel, sidePanel);

		loadHighScoreFromStart();

	}

	private void loadHighScoreFromStart() {

		try {

			FileReader inFile = new FileReader("HighScore");
			BufferedReader reader = new BufferedReader(inFile);

			String line;

			int placementInList = 0;

			while ((line = reader.readLine()) != null) {

				String[] tokens = line.split(",");

				int scoreFromList = Integer.parseInt(tokens[0]);

				String playerName = tokens[1];

				HighScore hs = new HighScore(scoreFromList, playerName);

				top3Array[placementInList] = hs;

				sidePanel.updateHighScore(placementInList, hs);

				placementInList++;

			}

			reader.close();

		} catch (IOException e) {
			System.out.println("Error! Text document named \"Highscore\" must exist! " + e);
		} catch (NumberFormatException e) {
			System.out.println("Fel vid konvertering: " + e);
		}

	}

	public void startNewGame() {

		gameRunning = true;

		if (!firstGameOfSession) {
			resetGame();
		}
		// ha med nedräkning??!
		setUpPiecesFromStart();

		setControls();

		timer.start();

	}

	private void resetGame() {
		loadHighScoreFromStart();

		clearGamePanel();

		resetCollections();

		resetLevelAndScore();

		pieceInPlay = null;

		timer.setInitialDelay(0);
		timer.setDelay(1000);

	}

	private void clearGamePanel() {

		Set<Map.Entry<Integer, List<Square>>> entrySet = squaresOnSameY.entrySet();

		for (Map.Entry<Integer, List<Square>> entry : entrySet) {
			List<Square> list = entry.getValue();

			for (Square s : list) {
				gamePanel.remove(s);
			}

		}

	}
	
	private void resetLevelAndScore() {
		score = 0;
		level = 1;

		sidePanel.updateScoreLabel(0);

		sidePanel.updateLevelLabel(1);
	}

	private void resetCollections() {
		rowCount.clear();
		squaresOnSameY.clear();
		yOfFullRows.clear();

	}

	private void setUpPiecesFromStart() {

		nextPiece = generatePiece();

		newPieces();
	}

	private void newPieces() { // too much happening in this method?

		pieceInPlay = nextPiece;
		addNewPieceInPlayToGamePanel();

		nextPiece = generatePiece();
		sidePanel.displayNextPiece(nextPiece);

		checkIfGameOver(); // checks if the new pieceInPlay reaches another piece directly
	}

	private Piece generatePiece() {

		Piece p = null;

		switch ((new Random()).nextInt(7)) {

		case (0):
			p = new IPiece();
			break;
		case (1):
			p = new SPiece();
			break;
		case (2):
			p = new OPiece();
			break;
		case (3):
			p = new TPiece();
			break;
		case (4):
			p = new ZPiece();
			break;
		case (5):
			p = new JPiece();
			break;
		case (6):
			p = new LPiece();
		}

		return p;

	}

	private void addNewPieceInPlayToGamePanel() {

		for (int i = 0; i < 4; i++) {
			gamePanel.add(pieceInPlay.getSquare(i));
		}
		gamePanel.repaint();

	}

	public void setControls() {

		gamePanel.requestFocus();

		InputMap inputMap = gamePanel.getInputMap();
		ActionMap actionMap = gamePanel.getActionMap();

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeft");
		actionMap.put("moveLeft", new AbstractAction() {
			public void actionPerformed(ActionEvent a) {

				if (pieceInPlay.reachedLeftBorder() || reachedPieceToTheLeft()) {
					return;
				}

				pieceInPlay.moveLeft();
				gamePanel.repaint();
			}
		});

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRight");
		actionMap.put("moveRight", new AbstractAction() {
			public void actionPerformed(ActionEvent a) {

				if (pieceInPlay.reachedRightBorder() || reachedPieceToTheRight()) {
					return;
				}

				pieceInPlay.moveRight();
				gamePanel.repaint();

			}
		});

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDown");
		actionMap.put("moveDown", new AbstractAction() {
			public void actionPerformed(ActionEvent a) {

				movePieceDown();
				timer.restart();

			}

		});

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "turn");
		actionMap.put("turn", new AbstractAction() {
			public void actionPerformed(ActionEvent a) {

				validateRotation();

				pieceInPlay.rotate();

				gamePanel.repaint();

			}
		});

		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "sinkToBottom");
		actionMap.put("sinkToBottom", new AbstractAction() {
			public void actionPerformed(ActionEvent a) {
				sinkPieceToBottom();
			}
		});

		// maby call gamePanel.repaint() from here instead of from the pieces?

	}// setControls()

	public void movePieceDown() {

		pieceInPlay.moveDown();
		gamePanel.repaint();

		if (checkIfGameOver()) {
			return;
		}

		if (pieceInPlay.reachedBottom() || reachedAnotherPiece()) {

			actionsForStoppedPiece();
		}

		// add a delay to the timer so that the player can move the piece sideways after
		// it has
		// landed

	}

	private void sinkPieceToBottom() {

		while (!pieceInPlay.reachedBottom() && !reachedAnotherPiece()) {
			pieceInPlay.moveDown();
			gamePanel.repaint();
		}

		if (!checkIfGameOver()) {
			actionsForStoppedPiece();
		}

	}

	private boolean checkIfGameOver() {

		if (reachedAnotherPiece() && pieceInPlay.isAtStartLevel()) {

			updateSquaresOnSameYMap(); // needed to clear the gamePanel on reset after game over
			gameOver();
			return true;
		}
		return false;
	}
	
	private boolean reachedAnotherPiece() {

		for (int i = 0; i < 4; i++) {

			Square squareInPlay = pieceInPlay.getSquare(i);

			List<Square> squaresOneStepDown = squaresOnSameY.get(squareInPlay.getY() + 20);

			if (squaresOneStepDown != null) {
				for (Square s : squaresOneStepDown) {
					if (s.getX() == squareInPlay.getX()) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private void actionsForStoppedPiece() {

		updateSquaresOnSameYMap();

		updateRowCount();

		newPieces();

	}

	private void updateRowCount() {

		for (int i = 0; i < 4; i++) {
			Square square = pieceInPlay.getSquare(i);

			int yCoordinate = square.getY();

			Integer counterForRow = rowCount.get(yCoordinate);

			if (counterForRow == null) {
				rowCount.put(yCoordinate, 1);
			} else {
				rowCount.put(yCoordinate, counterForRow + 1);
			}

		}

		checkIfFullRows();
	}

	public void updateSquaresOnSameYMap() {

		for (int i = 0; i < 4; i++) {

			Square square = pieceInPlay.getSquare(i);
			int yCoordinate = square.getY();

			List<Square> squares = squaresOnSameY.get(yCoordinate);

			if (squares == null) {
				squares = new ArrayList<Square>();
				squaresOnSameY.put(yCoordinate, squares);
				squares.add(square);

			} else {
				squares.add(square);
			}

		}

	}

	private void checkIfFullRows() {

		for (int i = 0; i < 4; i++) {

			int yCoordinate = pieceInPlay.getSquare(i).getY(); // gets the Y-coordinate for each square of the stoped
																// pieceInPlay

			if (rowCount.get(yCoordinate) == 15) {

				yOfFullRows.add(yCoordinate);

			}

		}

		if (!yOfFullRows.isEmpty()) {

			for (int i = 0; i < yOfFullRows.size(); i++) {
				updateScore(); // updates Score one time for each full row
			}

			actionsForFullRows();

			yOfFullRows.clear();
		}

	}

	private void actionsForFullRows() {

		moveRowCountDown();
		moveSquaresInMapDown();

	}

	private void moveRowCountDown() {

		Map<Integer, Integer> temp = new HashMap<>();

		for (Integer yOfFullRow : yOfFullRows) {
			rowCount.remove(yOfFullRow);

			Iterator<Map.Entry<Integer, Integer>> iter = rowCount.entrySet().iterator();

			while (iter.hasNext()) {
				Map.Entry<Integer, Integer> entry = iter.next();

				Integer yFromMap = entry.getKey();
				Integer counter = entry.getValue();

				if (yFromMap < yOfFullRow) {
					temp.put(yFromMap + 20, counter);
					iter.remove();
				}
			}

			rowCount.putAll(temp);

			temp.clear();
		}

	}

	private void moveSquaresInMapDown() {

		Map<Integer, List<Square>> temp = new HashMap<>();

		for (Integer yOfFullRow : yOfFullRows) {
			removeRowFromGamePanel(yOfFullRow);

			squaresOnSameY.remove(yOfFullRow);

			Iterator<Map.Entry<Integer, List<Square>>> iter = squaresOnSameY.entrySet().iterator();

			while (iter.hasNext()) {
				Map.Entry<Integer, List<Square>> entry = iter.next();

				Integer yInMap = entry.getKey();
				List<Square> squares = entry.getValue();

				if (yInMap < yOfFullRow) {
					temp.put(yInMap + 20, squares);
					iter.remove();

					moveSquaresDown(squares);
				}
			}

			squaresOnSameY.putAll(temp);
			temp.clear();
		}

	}

	private void moveSquaresDown(List<Square> squares) {
		for (Square s : squares) {
			s.setY(s.getY() + 20);
		}

		gamePanel.repaint();
	}

	private void removeRowFromGamePanel(int yCoordinate) {
		List<Square> squares = squaresOnSameY.get(yCoordinate);
		for (Square s : squares) {
			gamePanel.remove(s);
		}
	}

	private void updateScore() {
		score = score + 100;

		sidePanel.updateScoreLabel(score);

		int delay = 1000;

		if (score >= 300 && score < 600) {
			updateLevel(2);
			delay = 800;

		} else if (score >= 600 && score < 900) {
			updateLevel(3);
			delay = 600;
		} else if (score >= 900 && score < 1200) {
			updateLevel(4);
			delay = 400;
		}

		else if (score >= 1200 && score < 1500) {
			updateLevel(5);
			delay = 200;
		}

		else if (score >= 1500) {
			updateLevel(6);
			delay = 100;

		}

		timer.setInitialDelay(0);
		timer.setDelay(delay);

	}

	// snygga till updateScore()!

	private void updateLevel(int newLevel) {
		level = newLevel;

		sidePanel.updateLevelLabel(newLevel);
	}

	private boolean reachedPieceToTheLeft() {

		for (int i = 0; i < 4; i++) {
			Square squareInPlay = pieceInPlay.getSquare(i);

			List<Square> squaresOnSameRow = squaresOnSameY.get(squareInPlay.getY());

			if (squaresOnSameRow != null) {
				for (Square s : squaresOnSameRow) {
					if (s.getX() == (squareInPlay.getX() - 20)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private boolean reachedPieceToTheRight() {

		for (int i = 0; i < 4; i++) {

			Square squareInPlay = pieceInPlay.getSquare(i);

			List<Square> squaresOnSameRow = squaresOnSameY.get(squareInPlay.getY());

			if (squaresOnSameRow != null) {
				for (Square s : squaresOnSameRow) {
					if (s.getX() == squareInPlay.getX() + 20) {
						return true;
					}
				}
			}

		}

		return false;
	}

	private void validateRotationAtLeftBorder() {

		Orientation orientation = pieceInPlay.getOrientation();

		if (pieceInPlay instanceof OPiece) {
			return;
		}

		else if (pieceInPlay instanceof IPiece) {

			if (orientation == Orientation.POINT_RIGHT) {
				pieceInPlay.moveRight();
				pieceInPlay.moveRight();
			}
		}

		else if (pieceInPlay instanceof TPiece) {
			if (orientation == Orientation.POINT_RIGHT) {
				pieceInPlay.moveRight();
			}
		}

		else if (pieceInPlay instanceof SPiece) {
			if (orientation == Orientation.POINT_UP) {
				pieceInPlay.moveRight();
			}

		}

		else if (pieceInPlay instanceof JPiece) {
			if (orientation == Orientation.POINT_RIGHT) {
				pieceInPlay.moveRight();
			}
		}

		else if (pieceInPlay instanceof ZPiece) {
			if (orientation == Orientation.POINT_UP) {
				pieceInPlay.moveRight();
			}
		}

		else if (pieceInPlay instanceof LPiece) {
			if (orientation == Orientation.POINT_RIGHT) {
				pieceInPlay.moveRight();
			}
		}

	}

	private void validateRotationAtRightBorder() {

		Orientation orientation = pieceInPlay.getOrientation();

		System.out.println("Position innan rotation: " + orientation);

		if (pieceInPlay instanceof OPiece) {
			return;
		}

		else if (pieceInPlay instanceof IPiece) {
			if (orientation == Orientation.POINT_RIGHT) {
				pieceInPlay.moveLeft();
			}
		}

		else if (pieceInPlay instanceof JPiece) {
			if (orientation == Orientation.POINT_LEFT) {
				pieceInPlay.moveLeft();
			}
		}

		else if (pieceInPlay instanceof LPiece) {
			if (orientation == Orientation.POINT_LEFT) {
				pieceInPlay.moveLeft();
			}
		}

		else if (pieceInPlay instanceof SPiece) {
			return;
		}

		else if (pieceInPlay instanceof TPiece) {
			if (orientation == Orientation.POINT_LEFT) {
				pieceInPlay.moveLeft();
			}
		}

		else if (pieceInPlay instanceof ZPiece) {
			return;
		}

	}

	private void validateRotation() {

		if (pieceInPlay.reachedLeftBorder()) {

			validateRotationAtLeftBorder();
		}

		else if (pieceInPlay.reachedRightBorder()) {

			validateRotationAtRightBorder();
		}
	}

	private void gameOver() {
		timer.stop();

		gameRunning = false;
		firstGameOfSession = false;

		int placement = checkIfHighScore();

		if (placement < 0) {
			JOptionPane.showMessageDialog(null, "Game Over! No high score...");

		} else {
			actionsForNewHighScore(placement);
		}

	}

	private int checkIfHighScore() {

		for (int i = 0; i < 3; i++) {

			HighScore hs = top3Array[i];

			if (hs == null || score > hs.getScore()) {
				return i;
			}

		}

		return -1;

	}

	private void actionsForNewHighScore(int listPositionForNewHighScore) {

		showNewHighScoreWindow();

		if (nameField.getText().isEmpty()) {
			nameField.setText("Anonym");
		}

		HighScore hs = new HighScore(score, nameField.getText());

		updateTop3Array(listPositionForNewHighScore, hs);

		for (HighScore h : top3Array) {
			System.out.println(h);
		}

		updateHighScoreDocument();
	}

	public void showNewHighScoreWindow() {

		JPanel highScorePanel = createHSPanel();

		JOptionPane.showConfirmDialog(null, highScorePanel, "New High Score!", JOptionPane.OK_CANCEL_OPTION);

	}

	private JPanel createHSPanel() {

		JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel row1 = new JPanel();
		row1.add(new JLabel("New High Score: "));
		row1.add(new JLabel(score + ""));
		panel.add(row1);

		JPanel row2 = new JPanel();
		row2.add(new JLabel("Enter name: "));
		nameField = new JTextField(8);
		row2.add(nameField);
		panel.add(row2);

		return panel;
	}

	private void updateTop3Array(int listPositionForNewHighScore, HighScore hs) {

		HighScore[] newHighScoreArray = new HighScore[3];

		switch (listPositionForNewHighScore) {

		case (0):

			newHighScoreArray[0] = hs;
			newHighScoreArray[1] = top3Array[0];
			newHighScoreArray[2] = top3Array[1];

			break;

		case (1):

			newHighScoreArray[0] = top3Array[0];
			newHighScoreArray[1] = hs;
			newHighScoreArray[2] = top3Array[1];

			break;

		case (2):
			newHighScoreArray[0] = top3Array[0];
			newHighScoreArray[1] = top3Array[1];
			newHighScoreArray[2] = hs;
		}

		top3Array = newHighScoreArray;

	}

	private void updateHighScoreDocument() {

		try {

			FileWriter outputFile = new FileWriter("HighScore");

			PrintWriter out = new PrintWriter(outputFile);

			for (int i = 0; i < 3; i++) {

				HighScore hs = top3Array[i];

				if (hs != null) {
					out.println(hs.getScore() + "," + hs.getName()); // + datum!
				}

			}

			outputFile.close();

		} catch (IOException e) {
			System.err.println("Fel: " + e.getMessage());
		}
	}

	public boolean gameRunning() {
		return gameRunning;
	}

	public void pauseGame(boolean paused) {
		if (paused == false) {

			timer.stop();

		} else
			timer.start();
	}

}// class
