package listeners;

import java.awt.event.*;
import game.*;


public class TimerListener implements ActionListener {
	
	private GameController gameController;
	
	public TimerListener(GameController gameController) {
		this.gameController = gameController;
	}
	
	public void actionPerformed(ActionEvent e) {
		gameController.movePieceDown();
	}
}
