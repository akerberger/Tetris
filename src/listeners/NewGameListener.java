package listeners;


import java.awt.event.*;
import game.*;


public class NewGameListener implements ActionListener {

	private GameController gameController;

	public NewGameListener(GameController gameController) {
		this.gameController = gameController;
	}

	public void actionPerformed(ActionEvent a) {

		if (gameController.gameRunning()) {
			System.out.println("körs redan");
			return;
		}

		else {
			gameController.startNewGame();
			System.out.println("nytt spel startas");
		}
	}

}
