package game;

import javax.swing.*;
import java.awt.*;
import panels.*;

public class GUI extends JFrame {

	private GamePanel gamePanel;
	private SidePanel sidePanel;
	
	
	public GUI(GamePanel gamePanel, SidePanel sidePanel) {
		super("Tetris");
		
		this.gamePanel = gamePanel;
		this.sidePanel = sidePanel;
		
		setApperance();
		
	}
	
	private void setApperance() {
		setLayout(new BorderLayout());
		setSize(new Dimension(450, 600));

		add(gamePanel, BorderLayout.CENTER);
		add(sidePanel, BorderLayout.EAST);

		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
