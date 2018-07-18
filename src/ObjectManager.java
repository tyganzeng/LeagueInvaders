import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ObjectManager {
	Rocketship ship;
	List<Projectile> projectiles;
	List<Alien> aliens;
	long enemyTimer;
	int enemySpawnTime;
	int score;
	public ObjectManager(Rocketship r) {
		ship = r;
		projectiles = new ArrayList<Projectile>();
		aliens = new ArrayList<Alien>();
		enemyTimer = 0;
		enemySpawnTime = 1000;
		score = 0;
	}
	
	public void addProjectile(Projectile p) {
		projectiles.add(p);
	}
	
	public void addAlien(Alien a) {
		aliens.add(a);
	}
	
	public void manageEnemies(){
        if(System.currentTimeMillis() - enemyTimer >= enemySpawnTime){
                addAlien(new Alien(new Random().nextInt(LeagueInvaders.width), 0, 50, 50));
enemyTimer = System.currentTimeMillis();
        }
	}
	
	public void purgeObjects() {
		/*for(Alien a : aliens) {
			if(a.isAlive == false) {
				aliens.remove(a);
			}
		}
		
		for(Projectile p : projectiles) {
			if(p.isAlive == false) {
				projectiles.remove(p);
			}
		}*/
		
		for(int i = 0; i < aliens.size(); i++) {
			if(aliens.get(i).isAlive == false) {
				aliens.remove(i);
			}
		}
		
		for(int i = 0; i < projectiles.size(); i++) {
			if(projectiles.get(i).isAlive == false) {
				projectiles.remove(i);
			}
		}
	}
	
	public void checkCollision() {
		for(Alien a : aliens) {
			if(ship.collisionBox.intersects(a.collisionBox)) {
				ship.isAlive = false;
				System.out.println(ship.isAlive);
			}
		}
		for(Projectile p : projectiles) {
			for(Alien a : aliens) {
				if(p.collisionBox.intersects(a.collisionBox)) {
					a.isAlive = false;
					p.isAlive = false;
					System.out.println("p and a die");
					score++;
				}
			}
		}
	}
	
	public void update() {
		if(ship.isAlive == true) {
			ship.update();
		}
		for(Projectile p : projectiles) {
			p.update();
		}
		for(Alien a : aliens) {
			a.update();
		}
		
	}
	
	public void draw(Graphics g) {
		ship.draw(g);
		for(Projectile p : projectiles) {
			p.draw(g);
		}
		for(Alien a : aliens) {
			a.draw(g);
		}
	}
	
	public int getScore() {
		return score;
	}
}
