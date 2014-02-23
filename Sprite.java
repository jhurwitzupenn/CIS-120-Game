import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

//This class was constructed from a tutorial from thenewboston on youtube.
//However, it was drastically modified
public class Sprite {
	
	private Animation a;
	private float x;
	private float y;
	private float vx;
	private float vy;
	private boolean addSprite;
	private String newSprite;
	private boolean dead;
	
	public Sprite(Animation a){
		this.a = a;
		dead = false;
		addSprite = false;
		newSprite = "";
	}
	
	//change position
	public void update(long timePassed, ArrayList<Sprite> collisions){
		if(collisions != null){
			if(collisions.size() > 0){
				while(collisions.size() > 0){
					for(int i = 0; i < collisions.size(); i++){
					reactToCollision(collisions.get(i));
					collisions.remove(i);
					}
				}
			}
			else{
				reactToCollision(null);
			}
			x += vx * timePassed;
			y += vy * timePassed;
			a.update(timePassed);
		}
	}
	
	//get x pos
	public float getX(){
		return x;
	}
	
	//get y pos
	public float getY(){
		return y;
	}
	
	//set sprite x position
	public void setX(Float x){
		this.x = x;
	}
	
	//set sprite x position
	public void setY(Float y){
		this.y = y;
	}
	
	//get sprite width
	public int getWidth(){
		return a.getImage().getWidth(null);
	}
	
	//get sprite height
	public int getHeight(){
			return a.getImage().getHeight(null);
	}
	
	//get horizontal velocity
	public float getVelocityX(){
		return vx;
	}
	
	//get vertical velocity
	public float getVelocityY(){
			return vy;
	}
	
	//get horizontal velocity
	public int getDirectionX(){
			if(this.getVelocityX() < 0){
				return -1;
			}
			else{
				return 1;
			}
	}
	
	//get vertical velocity
	public int getDirectionY(){
		if(this.getVelocityY() < 0){
			return -1;
		}
		else{
			return 1;
		}	
	}
	
	
	//set horizontal velocity
	public void setVelocityX(float vx){
			this.vx = vx;
		}
		
	//set vertical velocity
	public void setVelocityY(float vy){
		this.vy = vy;
	}
	
	//get sprite or image
	public Image getImage(){
		return a.getImage();
	}
	
	public Rectangle getBoundingBox(){
		return new Rectangle((int)this.getX(), (int)this.getY(), this.getWidth(), this.getHeight());

	}
	
	public boolean intersects(Sprite other){
		return this.getBoundingBox().intersects(other.getBoundingBox());
	}
	
	public String getName(){
		return "Sprite";
	}
	
	public String getNewSprite(){
		return newSprite;
	}
	
	public void setNewSprite(String spriteName){
		newSprite = spriteName;
	}
	
	public void makeDead(boolean isDead){
		dead = isDead;
	}
	
	public boolean isDead(){
		return dead;
	}
	
	public void addSprite(boolean addSprite){
		this.addSprite = addSprite;
	}
	
	public boolean isAddSprite(){
		return addSprite;
	}
	
	//override
	public void reactToCollision(Sprite other){
		
	}
	
	public void changeAnimation(Animation a){
		this.a = a;
	}
	
	public Animation getAnimation(){
		return a;
	}
}
