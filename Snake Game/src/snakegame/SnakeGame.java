package snakegame;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
/*
 * Author : Imtiaz Adar
 * Email : imtiaz-adar@hotmail.com
 * Project : SNAKE GAME
 * Language Used : Java
 */
public class SnakeGame extends JFrame {
	private ImageIcon icon;
	public SnakeGame() {
		icon = new ImageIcon(getClass().getResource("snakepic.png"));
		this.setIconImage(icon.getImage());
		this.add(new SnakeGamePanel());
		this.setBounds(100, 30, 1300, 750);
		this.setTitle("SNAKE GAME BY IMTIAZ ADAR");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLayout(null);
	}
	public static void main(String[] args) {
		SnakeGame snake = new SnakeGame();
	}
}
