import java.awt.Color;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Window;


public abstract class Core {
	
	private static DisplayMode modes[] ={
		new DisplayMode(800,600,32,0),
		new DisplayMode(800,600,24,0),
		new DisplayMode(800,600,16,0),
		new DisplayMode(640,480,32,0),
		new DisplayMode(640,480,24,0),
		new DisplayMode(640,480,16,0)
	};
	
	private boolean running;
	protected ScreenManager s;
	
	//stop method
	public void stop(){
		running = false;
	}
	
	//call init and gameloop
	public void run(){
		try{
			init();
			gameLoop();
		}finally{
			s.restoreScreen();
		}
	}
	
	//set to full screen 
	public void init(){
		s = new ScreenManager();
		DisplayMode dm = s.findFirstCompatibleMode(modes);
		s.setFullScreen(dm);
		
		Window w = s.getFullScreenWindow();
		w.setFont(new Font("Arial", Font.PLAIN, 20));
		w.setBackground(Color.green);
		w.setForeground(Color.white);
		running = true;
	}
	
	//main gameloop
	public void gameLoop(){
		long startTime = System.currentTimeMillis();
		long cumTime = startTime;
		
		while(running){
			long timePassed = System.currentTimeMillis() - cumTime;
			cumTime += timePassed;
			
			update(timePassed);
			
			Graphics2D g = s.getGraphics();
			draw(g);
			g.dispose();
			s.update();
			
			try{
				Thread.sleep(20);
			}catch(Exception e){}
		}
	}
	
	//update animation
	public void update(long timePassed){
	}
	
	//draws to screen
	public abstract void draw(Graphics2D g);
}
