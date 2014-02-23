
public class Turret extends Sprite{

	private String newSprite;
	private int timer;

	public Turret(Animation a) {
		super(a);
		timer = 0;
	}
	
	public void reactToCollision(Sprite other){
	}

	@Override
	public String getName() {
		return "Turret";
	}
	
	public void setNewSprite(String spriteName){
		newSprite = spriteName;
	}
	
	public String getNewSprite(){
		return newSprite;
	}
	
	public void incTimer(){
		timer++;
	}
	
	public int getTimer(){
		return timer;
	}
	
}
