import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Random;

import javax.swing.*;


public class Game implements KeyListener, MouseListener, MouseMotionListener,
	MouseWheelListener {
	
	public static void main(String args[]){
		Game g = new Game();
		g.run();
	}
	
	private SpriteVector sv;
	private Ship ship;
	private Animation a;
	private Animation pR;
	private Animation pL;
	private Animation tur;
	private ScreenManager s;
	private Image bg;
	private boolean inGame;
	private boolean running;
	private boolean started;
	private boolean end;
	private boolean instructions;
	private String strInstructions;
	private int slides = 0;


	private int lastKey;
	private final int MOVEMENT = 1;
	private final int NONMOVEMENT = 0;
	private int timer = 0;
	
	private static final DisplayMode[] modes1 = {
			new DisplayMode(800, 600, 32, 0),
			new DisplayMode(800, 600, 24, 0),
			new DisplayMode(800, 600, 16, 0),
			new DisplayMode(640, 480, 32, 0),
			new DisplayMode(640, 480, 24, 0),
			new DisplayMode(640, 480, 16, 0)
	};
	
	//load images and add scenes
	public void loadImages(){
		
		Window w = s.getFullScreenWindow();
		w.addKeyListener(this);
		w.addMouseListener(this);
		w.addMouseMotionListener(this);
		w.addMouseWheelListener(this);
		lastKey = NONMOVEMENT;

		bg = new ImageIcon("bg.jpg").getImage().getScaledInstance(w.getWidth(),
							w.getHeight(), 0);
		Image h1 = new ImageIcon("Ship1.png").getImage();
    	Image h2 = new ImageIcon("Ship2.png").getImage();
    	Image h3 = new ImageIcon("Ship3.png").getImage();
    	Image h4 = new ImageIcon("Ship4.png").getImage();
    	Image h5 = new ImageIcon("Ship5.png").getImage();
    	Image h6 = new ImageIcon("Ship6.png").getImage();
    	Image h7 = new ImageIcon("Ship7.png").getImage();
    	Image h8 = new ImageIcon("Ship8.png").getImage();
    	Image h9 = new ImageIcon("Ship9.png").getImage();
    	Image h10 = new ImageIcon("Ship10.png").getImage();
    	Image h11 = new ImageIcon("Ship11.png").getImage();
    	Image h12 = new ImageIcon("Ship12.png").getImage();

    	
    	a = new Animation();
    	a.addScene(h1, 50);
    	a.addScene(h2, 50);
    	a.addScene(h3, 50);
    	a.addScene(h4, 50);
    	a.addScene(h5, 50);
    	a.addScene(h6, 50);
    	a.addScene(h7, 50);
    	a.addScene(h8, 50);
    	a.addScene(h9, 50);
    	a.addScene(h10, 50);
    	a.addScene(h11, 50);
    	a.addScene(h12, 50);

    	
    	ship = new Ship(a);    	
    	
    	Image i1 = new ImageIcon("person1.png").getImage();
		Image i2 = new ImageIcon("person2.png").getImage();
		Image i3 = new ImageIcon("person3.png").getImage();
		Image i4 = new ImageIcon("person4.png").getImage();
		Image i5 = new ImageIcon("person5.png").getImage();
		Image i6 = new ImageIcon("person6.png").getImage();
		
		pR = new Animation();	
		pR.addScene(i1, 50);
		pR.addScene(i2, 50);
		pR.addScene(i3, 50);
		pR.addScene(i4, 50);
		pR.addScene(i5, 50);
		pR.addScene(i6, 50);
		
		Image i1L = new ImageIcon("person1L.png").getImage();
		Image i2L = new ImageIcon("person2L.png").getImage();
		Image i3L = new ImageIcon("person3L.png").getImage();
		Image i4L = new ImageIcon("person4L.png").getImage();
		Image i5L = new ImageIcon("person5L.png").getImage();
		Image i6L = new ImageIcon("person6L.png").getImage();
		
		pL = new Animation();	
		pL.addScene(i1L, 50);
		pL.addScene(i2L, 50);
		pL.addScene(i3L, 50);
		pL.addScene(i4L, 50);
		pL.addScene(i5L, 50);
		pL.addScene(i6L, 50);
		
		Image t = new ImageIcon("turret.png").getImage();
		tur = new Animation();
		tur.addScene(t, 10);
    	
    	inGame = true;
		running = false;
		started = false;
		instructions = false;
		end = false;
	}
	
	//main method called from main
	public void run(){
		s = new ScreenManager();
		try{
			DisplayMode dm = s.findFirstCompatibleMode(modes1);
			s.setFullScreen(dm);
			loadImages();
			mainLoop();
			
		}finally{
			s.restoreScreen();
		}
	}
	
		public void openingLoop(){
			
			while(!started){
				//draw and update the screen
				Graphics2D g = s.getGraphics();
				drawOpening(g);
				g.dispose();
				s.update();
				
				try{
					Thread.sleep(20);
				}catch(Exception ex){}
			}
		}
		
	public void instructionLoop(){
			
			while(instructions){
				Graphics2D g = s.getGraphics();
				drawInstructions(g);
				g.dispose();
				s.update();
				
				try{
					Thread.sleep(20);
				}catch(Exception ex){}
			}
		}
	
	//play movie
	public void movieLoop(){
		long startingTime = System.currentTimeMillis();
		long cumTime = startingTime;
		
		while(running){
			long timePassed = System.currentTimeMillis() - cumTime;
			cumTime += timePassed;
			update(timePassed);
			
			//draw and update the screen
			Graphics2D g = s.getGraphics();
			draw(g);
			g.dispose();
			s.update();
			if(ship.getLives() <= 0){
				stop();
				end = true;
			}
			
			try{
				Thread.sleep(20);
			}catch(Exception ex){}
		}
	}
	
	public void mainLoop(){ 	
		while(inGame){
			sv = new SpriteVector();
	    	sv.addSprite(ship);
	    	slides = 0;
			openingLoop();
			instructionLoop();
			movieLoop();
			endLoop();
		}
		
	}
	
	public void endLoop(){
		while(end){
			Graphics2D g = s.getGraphics();
			drawEndScreen(g);
			g.dispose();
			s.update();
			
			try{
				Thread.sleep(20);
			}catch(Exception ex){}
		}
	}
	
	//draw graphics
	public synchronized void draw(Graphics2D g){
		
		String strLives = "Lives: " + ship.getLives();
		String strScore = "Score :" + ship.getScore();
		addPeople();
		addTurrets();
		
		g.drawImage(bg, 0, 0, null);
		g.setColor(Color.cyan);
		g.setFont(new Font("Arial", Font.PLAIN, 40));
		g.drawString(strLives, 650, 60);
		g.drawString(strScore, 400, 60);
		if(ship.isDead()){
			ship.reset();
		}
		for(int i = 0; i < sv.getSprites().size(); i++){
			Sprite s = sv.getSprites().get(i);
			g.drawImage(s.getImage(), Math.round(s.getX()),
						Math.round(s.getY()), null);
		}
	}
	
	//draw graphics
		public synchronized void drawOpening(Graphics2D g){
			
			String title = "UFO ABDUCTION";
			String play = "PLAY";
			String instructions = "INSTRUCTIONS";
			String exit = "EXIT";
			
			g.setColor(Color.gray);
			g.fillRect(0, 0, s.getWidth(), s.getHeight());
			g.setColor(Color.cyan);
			g.setFont(new Font("Arial", Font.PLAIN, 90));
			g.drawString(title, s.getWidth()/2 - 400, s.getHeight()/2 - 200);
			g.setFont(new Font("Arial", Font.PLAIN, 40));
			g.drawString(play, s.getWidth()/2-200, s.getHeight()/2);
			g.drawString(instructions, s.getWidth()/2-200,
						 s.getHeight()/2 + 100);
			g.drawString(exit, s.getWidth()/2-200, s.getHeight()/2 + 200 );
			
		}
		
		public synchronized void drawInstructions(Graphics2D g){
			if(slides == 0){
				strInstructions = "Hello and welcome to UFO Abduction";
			}
			if(slides == 1){
				strInstructions = "Let's talk about the instructions";
			}
			if(slides == 2){
				strInstructions = "The goal of the game is to abduct people"
								   + " and stay alivE";
			}
			if(slides == 3){
				strInstructions = "Use the arrow keys to move around the"
								   + " screen";
			}
			if(slides == 4){
				strInstructions = "Space bar activates the tractor"
								+ "beam which you will use to abduct people";
			}
			if(slides == 5){
				strInstructions = "Watch out for bullets from the turrets"
								+ "which destroy your ship";
			}
			if(slides == 6){
				strInstructions = "As far as cool features go, I implemented:"
						+ " enhanced animations, complex programmed motion...";
			}
			
			if(slides == 7){
				strInstructions = "A variety of game objects, and some paddle"
						+ " physics. And a great start screen and story...";
			}
			String helper = "Press Enter to move onto the next instruction";
			
			g.setColor(Color.gray);
			g.fillRect(0, 0, s.getWidth(), s.getHeight());
			g.setColor(Color.cyan);
			g.setFont(new Font("Arial", Font.PLAIN, 15));
			g.drawString(strInstructions, 50, s.getHeight()/2);
			g.drawString(helper, s.getWidth()/2 -200, s.getHeight()-100);
		}
		
		public synchronized void drawEndScreen(Graphics2D g){
			String gameOver = "GAME OVER";
			String score = "Your Score Was: " + ship.getScore();
			String playAgain = "Play Again? (y/n)";
			
			g.setColor(Color.green);
			g.fillRect(0, 0, s.getWidth(), s.getHeight());
			g.setColor(Color.black);
			g.setFont(new Font("Arial", Font.PLAIN, 50));
			g.drawString(gameOver, 50, s.getHeight()/2);
			g.setFont(new Font("Arial", Font.PLAIN, 25));
			g.drawString(score, 50, s.getHeight()-150);
			g.drawString(playAgain, s.getWidth()/2 -100, s.getHeight()-100);
		}
	
	
	public void stop(){
		running = false;
	}
	
	public void exitGame(){
		inGame = false;
	}
	
	//update sprite
	public void update(long timePassed){
		for(int i = 0; i < sv.getSprites().size(); i++){
			Sprite sprite = sv.getSprites().get(i);
			if(sprite.getX() < 0){
				if(sprite.getName().equals("Person")){
					sprite.changeAnimation(pR);
				}
				if(!sprite.getName().equals("Bullet")){
					sprite.setVelocityX(Math.abs(sprite.getVelocityX()));
				}
			}
			else if(sprite.getX() + sprite.getWidth() >= s.getWidth()){
				if(sprite.getName().equals("Person")){
					sprite.changeAnimation(pL);
				}
				if(!sprite.getName().equals("Bullet")){
					sprite.setVelocityX(-Math.abs(sprite.getVelocityX()));
				}
			}
			else if(sprite.getY() < 0){
				if(!sprite.getName().equals("Bullet")){
					sprite.setVelocityY(Math.abs(sprite.getVelocityY()));
				}
			}
			else if(sprite.getY() + sprite.getHeight() >= s.getHeight()-150){
				if(sprite.getName().equals("Ship")){
					sprite.setVelocityY(0);
					sprite.setY(sprite.getY() -10);
				}
				if(sprite.getName().equals("Person")){
					sprite.setVelocityY(.1f);
					 if(sprite.getY() +
						sprite.getHeight() >= s.getHeight()-s.getHeight()/8-10){
						 if(((Person) sprite).isFalling()){
							 ((Person) sprite).setFalling(false);
							 
							 if(sprite.getAnimation() == pL){
								 sprite.setVelocityX(-.1f);
							 }
							 if(sprite.getAnimation() == pR){
								 sprite.setVelocityX(.1f);
							 }
						 }
						 sprite.setVelocityY(0f);
					 }
				}
			}
		}
		sv.update(timePassed);
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(!started){
			if(e.getX() >= s.getWidth()/2 - 200 &&
			 e.getX() <= s.getWidth()/2 - 100 && e.getY() >= s.getHeight()/2
			 - 100 && e.getY() <= s.getHeight()/2){
				started = true;
				running = true;
			}		
			else if(e.getX() >= s.getWidth()/2 - 200 && e.getX()
					<= s.getWidth()/2 + 100 && e.getY() >= s.getHeight()/2 + 
					70 && e.getY() <= s.getHeight()/2 +100){
				started = true;
				instructions = true;
			}
			else if(e.getX() >= s.getWidth()/2 - 200 && e.getX()
					<= s.getWidth()/2 - 110 && e.getY() >= s.getHeight()/2 + 
					170 && e.getY() <= s.getHeight()/2 +200){
				started = true;
				instructions = false;
				stop();
				exitGame();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_ESCAPE){
			if(instructions){
				instructions = false;
				started = false;
			}
			else if(running){
				stop();
				exitGame();
			}
			else{	
			}
		}
		if(keyCode == KeyEvent.VK_RIGHT){
			lastKey = MOVEMENT;
			ship.setVelocityX(.3f);
			if(ship.isAddSprite()){
				ship.addSprite(false);
			}

		}
		if(keyCode == KeyEvent.VK_LEFT){
			lastKey = MOVEMENT;
			ship.setVelocityX(-.3f);
			if(ship.isAddSprite()){
				ship.addSprite(false);
			}
		}
		
		if(keyCode == KeyEvent.VK_UP){
			lastKey = MOVEMENT;
			ship.setVelocityY(-.3f);
			if(ship.isAddSprite()){
				ship.addSprite(false);
			}
		}
		if(keyCode == KeyEvent.VK_DOWN){
			lastKey = MOVEMENT;
			ship.setVelocityY(.3f);
			if(ship.isAddSprite()){
				ship.addSprite(false);
			}
		}
		if(lastKey == NONMOVEMENT){
			if(keyCode == KeyEvent.VK_SPACE){
					ship.addSprite(true);
					ship.setNewSprite("TractorBeam");
			}
		}
		if(keyCode == KeyEvent.VK_ENTER){
			slides++;
			if(instructions && slides < 8){}
			else{
				instructions = false;
				started = false;
			}
		}
		else{
			e.consume();
		}		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_RIGHT){
			lastKey = NONMOVEMENT;
			ship.setVelocityX(0);
		}
		if(keyCode == KeyEvent.VK_LEFT){
			lastKey = NONMOVEMENT;
			ship.setVelocityX(0);
		}
		if(keyCode == KeyEvent.VK_UP){
			lastKey = NONMOVEMENT;
			ship.setVelocityY(0f);
		}
		if(keyCode == KeyEvent.VK_DOWN){
			lastKey = NONMOVEMENT;
			ship.setVelocityY(0f);
		}
		if(keyCode == KeyEvent.VK_SPACE){
			ship.addSprite(false);
		}
		if(keyCode == KeyEvent.VK_Y){
			if(end){
				running = true;
				timer = 0;
				end = false;
				reset();
			}
		}
		if(keyCode == KeyEvent.VK_N){
			if(end){
				started = false;
				timer = 0;
				end = false;
				reset();
			}
		}
		
		else{
			e.consume();
		}		
	}		

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void addPeople(){
		timer ++;
		if(timer % 75 == 0){
			Person p = new Person(pR);
			Random rand = new Random();
			p.setX(((float) rand.nextFloat() * (s.getWidth()-200)) + 25f);
			p.setY((float) s.getHeight() - s.getHeight()/8 - p.getHeight()-10);
			p.setVelocityX(.1f);
			sv.addSprite(p);
		}	
	}
	

	public void addTurrets(){
		timer ++;
		if(timer % 750 == 0){
			Turret t = new Turret(tur);
			Random rand = new Random();
			t.setX(((float) rand.nextFloat() * (s.getWidth()-200)) + 25f);
			t.setY((float) s.getHeight() - s.getHeight()/8 - t.getHeight() -10);
			t.addSprite(true);
			t.setNewSprite("Bullet");
			sv.addSprite(t);
		}	
	}
	
	public void reset(){
		sv.reset();
		ship.setLives(3);
		ship.setScore(-ship.getScore());
		ship.reset();
	}
	
}
