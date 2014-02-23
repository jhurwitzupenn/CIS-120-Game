import java.awt.Rectangle;


public class TractorBeam extends Sprite {

	public TractorBeam(Animation a) {
		super(a);
	}
	
	public String getName(){
		return "TractorBeam";
	}
	
	public Rectangle getBoundingBox(){
		return new Rectangle((int)this.getX()+25, (int)this.getY(),
							this.getWidth()-30, this.getHeight());

	}
	
	public void reactToCollision(Sprite other){
		
	}
}
