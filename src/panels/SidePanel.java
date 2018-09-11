package panels;


import javax.swing.*;
import java.awt.*;
import game.*;
import listeners.*;
import pieces.Piece;


public class SidePanel extends JPanel {

	private NextPanel nextPanel = new NextPanel();
	private GameController gameController;
	private JLabel scoreLabel;
	private JLabel levelLabel;

	private JPanel highScorePanel;

	private JLabel hs1, hs2, hs3;

	public SidePanel(GameController game) {
		this.gameController = game;
		setApperance();
		
		addComponents();

	}
	
	private void setApperance() {
		setPreferredSize(new Dimension(150, 600));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	private void addComponents() {
		add(new JLabel("Next:"));
		add(nextPanel);

		add(createHighScorePanel());
		
		add(Box.createRigidArea(new Dimension(0, 50)));

		add(new JLabel("Score: "));
		scoreLabel = new JLabel("0");
		add(scoreLabel);
		
		add(Box.createRigidArea(new Dimension(0, 20)));
		
		add(new JLabel("Level: "));
		levelLabel = new JLabel("1");
		add(levelLabel);
		
		add(Box.createRigidArea(new Dimension(0, 20)));

		JButton newGameButton = new JButton("New Game");
		add(newGameButton);
		newGameButton.addActionListener(new NewGameListener(gameController));

		JButton pauseButton = new JButton("Pause");
		add(pauseButton);
		pauseButton.addActionListener(new PauseListener(gameController));
	}

	private JPanel createHighScorePanel() {

		highScorePanel = new JPanel();

		highScorePanel.setLayout(new BoxLayout(highScorePanel, BoxLayout.Y_AXIS));
		highScorePanel.add(new JLabel("HighScore"));

		hs1 = new JLabel("1: ");
		hs2 = new JLabel("2: ");
		hs3 = new JLabel("3: ");

		highScorePanel.add(hs1);
		highScorePanel.add(hs2);
		highScorePanel.add(hs3);

		return highScorePanel;
	}

	public void updateScoreLabel(int newScore) {
		scoreLabel.setText("" + newScore);
	}

	public void updateLevelLabel(int newLevel) {
		levelLabel.setText("" + newLevel);
	}

	public void updateHighScore(int position, HighScore highScore) { 		// send the top3Array to this method?

		String name = highScore.getName();
		int score = highScore.getScore();

		switch (position) {
		case (0):
			hs1.setText("1: " + name + " " + score + " pts");
			break;
		case (1):
			hs2.setText("2: " + name + " " + score + " pts");
			break;
		case (2):
			hs3.setText("3: " + name + " " + score + " pts");

		}

	}

	public void displayNextPiece(Piece piece) {

		nextPanel.updateNextPiece(piece);

	}

}
