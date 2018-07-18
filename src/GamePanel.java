import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
	Timer timer;
	Font titleFont;
	Font normalFont;
	Rocketship ship;
	ObjectManager manager;
	final int MENU_STATE = 0;
	final int GAME_STATE = 1;
	final int END_STATE = 2;
	int currentState;

	public GamePanel() {
		
		
		timer = new Timer(1000 / 60, this);
		currentState = MENU_STATE;
		titleFont = new Font("Arial",Font.PLAIN,48);
		normalFont = new Font("Arial",Font.PLAIN, 26);
		ship = new Rocketship(250,700,50,50);
		manager = new ObjectManager(ship);
	}

	public void startGame() {
		timer.start();
	}

	@Override
	public void paintComponent(Graphics g) {
		// g.fillRect(10,10,100,100);
		if (currentState == MENU_STATE) {
			drawMenuState(g);

		} else if (currentState == GAME_STATE) {
			drawGameState(g);

		} else if (currentState == END_STATE) {
			drawEndState(g);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (currentState == MENU_STATE) {
			updateMenuState();

		} else if (currentState == GAME_STATE) {
			updateGameState();

		} else if (currentState == END_STATE) {
			updateEndState();
		}
		repaint();

	}

	public void updateMenuState() {

	}

	public void updateGameState() {
		manager.update();
		manager.manageEnemies();
		manager.purgeObjects();
		manager.checkCollision();
		if(ship.isAlive == false) {
			currentState = END_STATE;
		}
	}

	public void updateEndState() {

	}

	public void drawMenuState(Graphics g) {
		g.setColor(Color.BLUE);
		g.fillRect(0, 0, LeagueInvaders.width, LeagueInvaders.height);
		g.setColor(Color.YELLOW);
		g.setFont(titleFont);
		g.drawString("LEAGUE INVADERS", 40, 200);
		g.setFont(normalFont);
		g.drawString("Press ENTER to start", 130, 350);
		g.drawString("Press SPACE for instructions", 100,500);
		
	}

	public void drawGameState(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, LeagueInvaders.width, LeagueInvaders.height);
		manager.draw(g);
	}

	public void drawEndState(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(0, 0, LeagueInvaders.width, LeagueInvaders.height);
		g.setColor(Color.BLACK);
		g.setFont(titleFont);
		g.drawString("GAME OVER", 60, 200);
		g.drawString("" + manager.getScore(), 60, 400);
	}
	


	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			
			System.out.println("swapping");
			if(currentState == END_STATE) {
				currentState = MENU_STATE;
				ship = new Rocketship(250,700,50,50);
				manager = new ObjectManager(ship);
			} else {
				currentState++;
			}
			
		} else if(e.getKeyCode() == KeyEvent.VK_UP) {
			ship.y -= ship.speed;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			ship.y += ship.speed;
		} 
		else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			ship.x -= ship.speed;
		} 
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			ship.x += ship.speed;
		} 
		else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			manager.addProjectile(new Projectile(ship.x + (ship.width)/2,ship.y,10,10));
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		

	}
}
