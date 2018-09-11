package panels;

import javax.swing.*;
import java.awt.*;


public class GameOverPanel extends JComponent{

	public GameOverPanel() {
		setBounds(100, 200, 100, 100);
		setOpaque(false);
		
	}
	
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.WHITE);
		
		g.fillRect(0,0,90,90);
	}
}
