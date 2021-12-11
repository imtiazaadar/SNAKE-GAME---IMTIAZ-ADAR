package snakegame;
import javax.swing.ImageIcon;
/*
 * Imtiaz Adar
 * Email : imtiaz-adar@hotmail.com
 * Project : SNAKE GAME
 * Language Used : Java
 */
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class SnakeGamePanel extends JPanel implements ActionListener{
	private final int width = 1300, height = 750;
	private final int unit = 25;
	private final int[] x = new int[500];
	private final int[] y = new int[500];
	private int headXCor = width / 2, headYCor = height / 2;
	private int score = 0, highscore = 0, delay = 160, body_parts = 5, appleX, appleY;
	private boolean running = false;
	private char direction = 'R';
	private Random random;
	private Timer timer;
	private Color headColor = Color.green;
	private Color bodyColor = new Color(26, 155, 78);
	private JButton resetGame;
	public SnakeGamePanel() {
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_UP:
					if(direction != 'D')
						direction = 'U';
					break;
				case KeyEvent.VK_DOWN:
					if(direction != 'U')
						direction = 'D';
					break;
				case KeyEvent.VK_LEFT:
					if(direction != 'R')
						direction = 'L';
					break;
				case KeyEvent.VK_RIGHT:
					if(direction != 'L')
						direction = 'R';
					break;
				}
			}
		});
		GameStart();
	}
	public void GameStart() {
		x[0] = headXCor;
		y[0] = headYCor;
		x[1] = headXCor;
		y[1] = headYCor;
		x[2] = headXCor;
		y[2] = headYCor;
		x[3] = headXCor;
		y[3] = headYCor;
		x[4] = headXCor;
		y[4] = headYCor;
		SetNewApple();
		running = true;
		Tick();
	}
	public void Tick() {
		timer = new Timer(delay, this);
		timer.start();
	}
	public void Move() {
		for(int i = body_parts; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}
		switch(direction) {
		case 'U':
			y[0] -= unit;
			break;
		case 'D':
			y[0] += unit;
			break;
		case 'L':
			x[0] -= unit;
			break;
		case'R':
			x[0] += unit;
			break;
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		if(running) {
			g.setColor(Color.CYAN);
			g.fillRect(0, 95, width, 4);
			g.setColor(Color.RED);
			g.fillOval(appleX, appleY, unit, unit);
			g.setColor(headColor);
			g.fillRect(x[0], y[0], unit, unit);
			for(int i = 1; i < body_parts; i++) {
				g.setColor(bodyColor);
				g.fillRect(x[i], y[i], unit, unit);
			}
			ScoreSet(g);
		}
		else {
			GameOver(g);
		}
	}
	public void CheckAppleEaten() {
		if(appleX == x[0] && appleY == y[0]) {
			score += 10;
			body_parts++;
			SetNewApple();
			if(score > 50) delay -= 0.005;
			else if(score > 100) delay -= 0.007;
			else if(score > 200) {
				delay -= 0.009;
			}
			if(highscore < score) highscore = score;
		}
	}
	public void CheckCollision() {
		for(int i = body_parts; i > 0; i--) {
			if(x[0] == x[i] && y[0] == y[i]) {
				running = false;
			}
		}
		if(x[0] < 0)
			running = false;
		if(x[0] > width - 10)
			running = false;
		if(y[0] < 78)
			running = false;
		if(y[0] > height - 10)
			running = false;
		if(!running)
			timer.stop();
	}
	public void SetNewApple() {
		random = new Random();
		appleX = random.nextInt((int)(width / unit)) * (unit);
		int yaxis = (int)(((Math.random() * (740 - 100)) + 100) / unit) * unit;
		appleY = yaxis;
	}
	public void ScoreSet(Graphics g) {
		g.setColor(Color.CYAN);
		g.setFont(new Font("Comic Sans Ms", Font.BOLD, 45));
		FontMetrics font = getFontMetrics(g.getFont());
		g.drawString("HIGH SCORE : " + highscore, 35, 64);
		g.drawString("SCORE : " + score, (width - font.stringWidth("SCORE : " + score)) / 2 + 30, 64);
		g.setFont(new Font("DS-DIGITAL", Font.BOLD, 20));
		FontMetrics font1 = getFontMetrics(g.getFont());
		g.drawString("SNAKE GAME BY IMTIAZ ADAR", 1030, 56);
	}
	public void GameOver(Graphics g) {
		ScoreSet(g);
		g.setColor(Color.CYAN);
		g.setFont(new Font("Comic Sans Ms", Font.BOLD, 70));
		FontMetrics font = getFontMetrics(g.getFont());
		g.drawString("GAME OVER", (width - font.stringWidth("GAME OVER")) / 2, height / 2);
		if(!running) {

			resetGame = new JButton("RESET GAME");
			resetGame.setBounds(472, 600, 350, 70);
			resetGame.setFont(new Font("Comic Sans Ms", Font.BOLD, 40));
			resetGame.setBackground(Color.CYAN);
			resetGame.setFocusPainted(false);
			resetGame.setCursor(new Cursor(Cursor.HAND_CURSOR));
			resetGame.setForeground(Color.BLACK);
			this.add(resetGame);
			resetGame.addActionListener(this);
			resetGame.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					resetGame.setBackground(Color.BLACK);
					resetGame.setForeground(Color.CYAN);
				}

				@Override
				public void mousePressed(MouseEvent e) {}

				@Override
				public void mouseReleased(MouseEvent e) {}

				@Override
				public void mouseEntered(MouseEvent e) {
					resetGame.setBackground(Color.BLACK);
					resetGame.setForeground(Color.CYAN);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					resetGame.setBackground(Color.CYAN);
					resetGame.setForeground(Color.BLACK);
				}
			});
		}
	}
	public void GameReset() {
		GameStart();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			Move();
			CheckAppleEaten();
			CheckCollision();
		}
		repaint();
		if(e.getSource() == resetGame) {
			this.remove(resetGame);
			headXCor = width / 2; headYCor = height / 2;
			score = 0; delay = 160; body_parts = 5;
			running = false;
			direction = 'R';
			GameStart();
		}
	}
}
