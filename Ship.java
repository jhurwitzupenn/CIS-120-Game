public class Ship extends Sprite {
	
	private int lives;
	private int score;
	private boolean dead;
	private String newSprite;

	public Ship(Animation a) {
		super(a);
		this.setX(400f);
		this.setY(100f);
		lives = 3;
		score = 0;
		a = new Animation();

	}
	public void reactToCollision(Sprite other){
		if(other != null){
			if(other.getName().equals("Person")){
					setScore(10);
			}
			else if(other.getName().equals("Bullet")){
				this.makeDead(true);
				this.addSprite(false);
		}
		}
		else{
		}
	}

	@Override
	public String getName() {
		return "Ship";
	}
	
	public void setLives(int lives){
		this.lives = this.lives + lives;
	}
	
	public int getLives(){
		return lives;
	}
	
	public void setScore(int score){
		this.score = this.score + score;
	}
	
	public int getScore(){
		return score;
	}
	
	public void makeDead(boolean isDead){
		lives --;
		reset();
	}
	
	public boolean isDead(){
		return dead;
	}
	
	public void reset(){
		this.setX(300f);
		this.setY(100f);
	}
	
	public void setNewSprite(String spriteName){
		newSprite = spriteName;
	}
	
	public String getNewSprite(){
		return newSprite;
	}
}
