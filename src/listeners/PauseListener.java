package listeners;


import java.awt.event.*;
import game.*;


public class PauseListener implements ActionListener{

	private boolean paused = false;
	
	private GameController gameController;
	
	public PauseListener(GameController gameController) {
		this.gameController = gameController;
	}
	
	public void actionPerformed(ActionEvent a) {
		
		
		
		System.out.println("Pausar spelet. (implementera noggrannare!)");
		
		gameController.pauseGame(paused);
		
		paused = !paused;
		
	}
	
}
