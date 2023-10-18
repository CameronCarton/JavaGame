/*
 * Project by Cameron Carton
 * Student ID: 19720959
 */

import util.Point3f;

/*
 * Created by Abraham Campbell on 15/01/2020.
 *   Copyright (c) 2020  Abraham Campbell

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
   
   (MIT LICENSE ) e.g do what you want with this :-) 
 */ 
public class PlayerObject {
	
	private Point3f centre= new Point3f(0,0,0);			// Centre of object, using 3D as objects may be scaled  
	private int width=10;
	private int height=10;
	private boolean hasTextured=false;
	private String currentTexture; 
	private String textureLocation_idle; 
	private String textureLocation_walk; 
	private String textureLocation_idle2; 
	private String textureLocation_walk2; 
	private String textureLocation_idle3; 
	private String textureLocation_walk3; 
	private String textureLocation_idle4; 
	private String textureLocation_walk4; 
	private String textureLocation_death; 
	private String textureLocation_hit; 
	private String blanktexture="res/blankSprite.png";
	private String currentTex = "idle";
	private int gotHit=0;
	private int direction=0;
	private float hspd=0;
	private float vspd=0;
	private boolean canShoot=true;
	private float bulletSpeed=8;
	private int damage = 1;
	private int hp = 3;
	private int hpMax = 3;
	private int cont;
	
	public PlayerObject() {  
		
	}
	
    public PlayerObject(String textureLocation_idle,String textureLocation_walk,
    					String textureLocation_idle2,String textureLocation_walk2,
    					String textureLocation_idle3,String textureLocation_walk3,
    					String textureLocation_idle4,String textureLocation_walk4,
    					String textureLocation_death,String textureLocation_hit,int width,int height,Point3f centre,int cont) { 
    	 hasTextured=true;
    	 this.textureLocation_idle=textureLocation_idle;
    	 this.textureLocation_walk=textureLocation_walk;
    	 this.textureLocation_idle2=textureLocation_idle2;
    	 this.textureLocation_walk2=textureLocation_walk2;
    	 this.textureLocation_idle3=textureLocation_idle3;
    	 this.textureLocation_walk3=textureLocation_walk3;
    	 this.textureLocation_idle4=textureLocation_idle4;
    	 this.textureLocation_walk4=textureLocation_walk4;
    	 this.textureLocation_death=textureLocation_death;
    	 this.textureLocation_hit=textureLocation_hit;
    	 this.width=width;
		 this.height=height;
		 this.centre =centre;
		 this.cont = cont;
	}

	public Point3f getCentre() {
		return centre;
	}

	public void setCentre(Point3f centre) {
		this.centre = centre;
		
		//make sure to put boundaries on the gameObject 
	 
	}

	public int getWidth() {
		return width;
	}
	
	public void setWidth(int w) {
		width = w;
	}

	public int getHeight() {
		return height;
	}

	public String getTexture() {
		
		if(hasTextured) 
			{
			return currentTexture;
			}
		 
		return blanktexture; 
	}
	
	public void changeCurrentTexture(String tex) {
		if(tex=="idle") {
			if(direction==0) {currentTexture = textureLocation_idle;}
			if(direction==1) {currentTexture = textureLocation_idle2;}
			if(direction==2) {currentTexture = textureLocation_idle4;}
			if(direction==3) {currentTexture = textureLocation_idle3;}
			currentTex="idle";
		}
		if(tex=="walk") {
			if(direction==0) {currentTexture = textureLocation_walk;}
			if(direction==1) {currentTexture = textureLocation_walk2;}
			if(direction==2) {currentTexture = textureLocation_walk4;}
			if(direction==3) {currentTexture = textureLocation_walk3;}
			currentTex="walk";
		}
		if(tex=="death") {
			currentTexture = textureLocation_death;
			currentTex="death";
		}
		
		
	}
	
	public String getHit() {
		return textureLocation_hit;
	}
	
	public int getGotHit() {
		return gotHit;
	}
	public void setGotHit(int is) {
		gotHit=is;
		if(gotHit<0)gotHit=0;
	}
	
	public String getCurrentTex() {
		return currentTex;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void setDirection(int num) {
		direction = num;
	}
	
	public void setHspd(float spd) {
		hspd = spd;
	}
	
	public float getHspd() {
		return hspd;
	}
	
	public void setVspd(float spd) {
		vspd = spd;
	}
	
	public float getVspd() {
		return vspd;
	}
	
	public boolean getCanShoot() {
		return canShoot;
	}
	
	public void setCanShoot(boolean is) {
		canShoot=is;
	}
	
	public void setHp(int is) {
		hp=is;
		if(hp<0)hp=0;
		if(hp>6)hp=6;
	}
	
	public int getHp() {
		return hp;
	}
	
	public void setHpMax(int is) {
		hpMax=is;
		if(hpMax>6)hpMax=6;
	}
	
	public int getHpMax() {
		return hpMax;
	}
	
	public void setBulletSpeed(float is) {
		bulletSpeed=is;
	}
	
	public float getBulletSpeed() {
		return bulletSpeed;
	}
	
	public void setController(int is) {
		cont=is;
	}
	
	public int getController() {
		return cont;
	}
	
	public void addItem(int item) {
		//item 0 = health
		//item 1 = speed
		//item 2 = damage
		if(item==0) {
			hp+=1;
			hpMax+=1;
		}
		if(item==1) {
			bulletSpeed+=2;		
		}
		if(item==2) {
			damage+=1;
		}
	}
	
	public void setDamage(int is) {
		damage = is;
	}
	public int getDamage() {
		return damage;
	}
}
