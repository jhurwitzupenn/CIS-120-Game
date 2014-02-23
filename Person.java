public class Person extends Sprite {
	
	private String newSprite;
	private boolean falling;

	public Person(Animation a) {
		super(a);
		a = new Animation();
		falling = true;

	}
	public void reactToCollision(Sprite other){
	if(other != null){
		if(other.getName().equals("TractorBeam")){
				this.setVelocityY(-.1f);
				this.setVelocityX(0);
				falling = true;
		}
		if(other.getName().equals("Ship")){
				this.makeDead(true);
		}
		}
		else{
			if(falling){
				this.setVelocityY(.1f);
			}
		}
	}

	@Override
	public String getName() {
		return "Person";
	}
	
	public void setNewSprite(String spriteName){
		newSprite = spriteName;
	}
	
	public String getNewSprite(){
		return newSprite;
	}
	
	public void setFalling(boolean falling){
		this.falling = falling;
	}
	
	public boolean isFalling(){
		return falling;
	}
}
