package snakegame;
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
	private final int WIDTH = 1300, HEIGHT = 750;
	private final int UNIT = 25;
	private final int[] X = new int[500];
	private final int[] Y = new int[500];
	private int headXCor = WIDTH / 2, headYCor = HEIGHT / 2;
	private int SCORE = 0, HIGHSCORE = 0, BODY_PARTS = 5, appleX, appleY;
	private int DELAY = 160;
	private boolean RUNNING = false;
	private boolean HIGHSCOREMORETHANFOURDIGIT = false;
	private char DIRECTION = 'R';
	private Random random;
	private Timer timer;
	private Color headColor = Color.green;
	private Color bodyColor = new Color(26, 155, 78);
	private JButton resetGame;
	public SnakeGamePanel() {
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBackground(Color.BLACK);
		this.setFocusable(true);
		this.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_UP:
					if(DIRECTION != 'D')
						DIRECTION = 'U';
					break;
				case KeyEvent.VK_DOWN:
					if(DIRECTION != 'U')
						DIRECTION = 'D';
					break;
				case KeyEvent.VK_LEFT:
					if(DIRECTION != 'R')
						DIRECTION = 'L';
					break;
				case KeyEvent.VK_RIGHT:
					if(DIRECTION != 'L')
						DIRECTION = 'R';
					break;
				}
			}
		});
		GameStart();
	}
	public void GameStart() {
		X[0] = headXCor;
		Y[0] = headYCor;
		X[1] = headXCor;
		Y[1] = headYCor;
		X[2] = headXCor;
		Y[2] = headYCor;
		X[3] = headXCor;
		Y[3] = headYCor;
		X[4] = headXCor;
		Y[4] = headYCor;
		SetNewApple();
		RUNNING = true;
		Tick();
	}
	public void Tick() {
		timer = new Timer(DELAY, this);
		timer.start();
	}
	public void Move() {
		for(int i = BODY_PARTS; i > 0; i--) {
			X[i] = X[i - 1];
			Y[i] = Y[i - 1];
		}
		switch(DIRECTION) {
		case 'U':
			Y[0] -= UNIT;
			break;
		case 'D':
			Y[0] += UNIT;
			break;
		case 'L':
			X[0] -= UNIT;
			break;
		case'R':
			X[0] += UNIT;
			break;
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	public void draw(Graphics g) {
		if(RUNNING) {
			g.setColor(Color.CYAN);
			g.fillRect(0, 95, WIDTH, 4);
			g.setColor(Color.RED);
			g.fillOval(appleX, appleY, UNIT, UNIT);
			g.setColor(headColor);
			g.fillRect(X[0], Y[0], UNIT, UNIT);
			for(int i = 1; i < BODY_PARTS; i++) {
				g.setColor(bodyColor);
				g.fillRect(X[i], Y[i], UNIT, UNIT);
			}
			ScoreSet(g);
		}
		else {
			GameOver(g);
		}
	}
	public void CheckAppleEaten() {
		if(appleX == X[0] && appleY == Y[0]) {
			SCORE += 10;
			BODY_PARTS++;
			SetNewApple();
			if(SCORE > 50) DELAY -= 0.006;
			else if(SCORE > 100) DELAY = 150;
			else if(SCORE > 200) DELAY = 130;
			else if(SCORE > 350) DELAY = 120;
			else if(SCORE > 500) DELAY = 105;
			else if(SCORE > 800) DELAY = 100;
			else if(SCORE > 1000) DELAY = 85;
			else if(SCORE > 1500) DELAY = 75;
			else if(SCORE > 2000) DELAY = 60;
			if(HIGHSCORE < SCORE) HIGHSCORE = SCORE;
			if(Integer.toString(HIGHSCORE).length() >= 4) HIGHSCOREMORETHANFOURDIGIT = true;
		}
	}
	public void CheckCollision() {
		for(int i = BODY_PARTS; i > 0; i--) {
			if(X[0] == X[i] && Y[0] == Y[i]) {
				RUNNING = false;
			}
		}
		if(X[0] < 0)
			RUNNING = false;
		if(X[0] > WIDTH - 10)
			RUNNING = false;
		if(Y[0] < 78)
			RUNNING = false;
		if(Y[0] > HEIGHT - 10)
			RUNNING = false;
		if(!RUNNING)
			timer.stop();
	}
	public void SetNewApple() {
		random = new Random();
		appleX = random.nextInt((int)(WIDTH / UNIT)) * (UNIT);
		int yaxis = (int)(((Math.random() * (740 - 100)) + 100) / UNIT) * UNIT;
		appleY = yaxis;
	}
	public void ScoreSet(Graphics g) {
		g.setColor(Color.CYAN);
		g.setFont(new Font("Comic Sans Ms", Font.BOLD, 45));
		FontMetrics font = getFontMetrics(g.getFont());
		g.drawString("HIGH SCORE : " + HIGHSCORE, 35, 64);
		if(HIGHSCOREMORETHANFOURDIGIT) g.drawString("SCORE : " + SCORE, ((WIDTH - font.stringWidth("SCORE : " + SCORE)) / 2) + 120, 64);
		else g.drawString("SCORE : " + SCORE, (WIDTH - font.stringWidth("SCORE : " + SCORE)) / 2 + 30, 64);
		g.setFont(new Font("DS-DIGITAL", Font.BOLD, 20));
		FontMetrics font1 = getFontMetrics(g.getFont());
		g.drawString("SNAKE GAME BY IMTIAZ ADAR", 1030, 56);
	}
	public void GameOver(Graphics g) {
		ScoreSet(g);
		g.setColor(Color.CYAN);
		g.setFont(new Font("Comic Sans Ms", Font.BOLD, 70));
		FontMetrics font = getFontMetrics(g.getFont());
		g.drawString("GAME OVER", (WIDTH - font.stringWidth("GAME OVER")) / 2, HEIGHT / 2);
		if(!RUNNING) {

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
		if(RUNNING) {
			Move();
			CheckAppleEaten();
			CheckCollision();
		}
		repaint();
		if(e.getSource() == resetGame) {
			this.remove(resetGame);
			headXCor = WIDTH / 2; headYCor = HEIGHT / 2;
			SCORE = 0; DELAY = 160; BODY_PARTS = 5;
			RUNNING = false;
			DIRECTION = 'R';
			GameStart();
		}
	}
}
