
public class Bullet extends Sprite{
	private int timer;

	public Bullet(Animation a) {
		super(a);
		timer = 200;
	}
	
	public synchronized void reactToCollision(Sprite other){
		if(other != null){
			if(other.getName().equals("Ship")){
					this.makeDead(true);
			}
		}
		else{
		}
	}
	
	public String getName(){
		return "Bullet";
	}
	
	public void decTimer(){
		timer --;
	}
	
	public int getTimer(){
		return timer;
	}
	
	
}
