import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;


public class SpriteVector {
	
	private ArrayList<Sprite> sprites;
	
	public SpriteVector(){
		sprites = new ArrayList<Sprite>();
	}
	
	public void addSprite(Sprite s){
		sprites.add(s);
	}
	
	public Sprite getSpriteFromIndex(int i){
		return sprites.get(i);
	}

	public ArrayList<Sprite> findCollisions(Sprite s){
		ArrayList<Sprite> collisions = new ArrayList<Sprite>();
		for(int i = 0; i < sprites.size(); i++){
				if (s != sprites.get(i) && s.intersects(sprites.get(i))){
					if (!collisions.contains(sprites.get(i))){
						collisions.add(sprites.get(i));
					}
				}
			}
		return collisions;
	}
	
	public void update(long timePassed){
		for(int i = 0; i < sprites.size(); i++){
			if(!sprites.get(i).isDead()){
				
				sprites.get(i).update(timePassed, findCollisions(sprites.get(i)));
				
				if(sprites.get(i).getName().equals("Bullet")){
					((Bullet) sprites.get(i)).decTimer();
					if(((Bullet) sprites.get(i)).getTimer() < 0){
						sprites.get(i).makeDead(true);
					}
				}
				if (sprites.get(i).isAddSprite()){
					if(sprites.get(i).getName().equals("Ship")){
						if(sprites.get(i).getNewSprite().equals("TractorBeam")){
							if(getIndexFromName("TractorBeam") == -1){
								TractorBeam t = makeTractorBeam();
								t.setX(sprites.get(i).getX() +((float) 
									   sprites.get(i).getWidth()/2) - 32);
								t.setY(sprites.get(i).getY() + ((float)
									   sprites.get(i).getHeight()) - 10);
								sprites.add(t);
							}
						}
					}
					if(sprites.get(i).getName().equals("Turret")){
						if(sprites.get(i).getNewSprite().equals("Bullet")){
							((Turret) sprites.get(i)).incTimer();
							Bullet b = makeBullet
									   (((Turret) sprites.get(i)).getTimer());
							if(b != null){
								b.setX(sprites.get(i).getX() + ((float) 
									   sprites.get(i).getWidth()/2));
								b.setY(sprites.get(i).getY());
								
								float dx = sprites.get(getIndexFromName("Ship"))
												  .getX()-sprites.get(i).getX();
								float dy = sprites.get(getIndexFromName("Ship"))
												  .getY()-sprites.get(i).getY();
								double h = Math.sqrt(dx * dx + dy * dy);
								float dn = (float)(h / Math.sqrt(2));
								
								b.setVelocityX(dx/dn*.1f);
								b.setVelocityY(dy/dn*.1f);
								
								sprites.add(b);
							}
						}
					}
			}
			else if (!sprites.get(i).isAddSprite()){
				if(sprites.get(i).getName().equals("Ship")){
					if(getIndexFromName("TractorBeam") != -1){
						sprites.remove(getIndexFromName("TractorBeam"));
					}
				}
			}
		}
			else{	
				sprites.remove(i);
			}
		}
	}
	
	public ArrayList<Sprite> getSprites(){
		ArrayList<Sprite> returnable = new ArrayList<Sprite>();
		for(int i = 0; i < sprites.size(); i++){
			returnable.add(sprites.get(i));
		}
		
		return returnable;
	}
	
	public int getIndexFromName(String name){
		for(int i = 0; i < sprites.size(); i++){
			if(sprites.get(i).getName().equals(name)){
				return  i;
			}
		}	
		return -1;
	}
	
	public TractorBeam makeTractorBeam(){
		
		Image h1 = new ImageIcon("tractorbeam.png").getImage();
		Image h2 = new ImageIcon("tractorbeam2.png").getImage();
		Image h3 = new ImageIcon("tractorbeam3.png").getImage();
    	
    	Animation a = new Animation();
    	a.addScene(h1, 50);
    	a.addScene(h2, 50);
    	a.addScene(h3, 50);
    	
    	TractorBeam t = new TractorBeam(a);
    	
    	return t;
	}
	
	public Bullet makeBullet(int timer){
		if(timer % 75 == 0){
			Image h1 = new ImageIcon("LB1.png").getImage();
			Image h2 = new ImageIcon("LB2.png").getImage();
			Image h3 = new ImageIcon("LB3.png").getImage();
    	
			Animation a = new Animation();
			a.addScene(h1, 50);
			a.addScene(h2, 50);
			a.addScene(h3, 50);
    	
			Bullet b = new Bullet(a);
   			return b;
		}
		return null;
	}
	
	public void reset(){
		sprites.clear();
	}
}
