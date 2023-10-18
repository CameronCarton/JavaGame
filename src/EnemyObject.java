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
public class EnemyObject {
	
	private Point3f centre= new Point3f(0,0,0);			// Centre of object, using 3D as objects may be scaled  
	private int width=10;
	private int height=10;
	private boolean hasTextured=false;
	private String currentTexture; 
	private String textureLocation_idle; 
	private String textureLocation_walk; 
	private String textureLocation_idle2; 
	private String textureLocation_walk2; 
	private String textureLocation_hit; 
	private String blanktexture="res/blankSprite.png";
	private String currentTex = "idle";
	private int direction=0;
	private int movementDirection=0;
	private int gotHit=0;
	private float hspd=0;
	private float vspd=0;
	private boolean canShoot=true;
	private int moveTimer=0;
	private int moveTimerMax=30;
	private int hp = 3;
	private String type="enemy";
	
	public EnemyObject() {  
		
	}
	
    public EnemyObject(String textureLocation_idle,String textureLocation_walk,String textureLocation_idle2,String textureLocation_walk2,String textureLocation_hit,int width,int height,Point3f centre,String type) { 
    	 hasTextured=true;
    	 this.textureLocation_idle=textureLocation_idle;
    	 this.textureLocation_walk=textureLocation_walk;
    	 this.textureLocation_idle2=textureLocation_idle2;
    	 this.textureLocation_walk2=textureLocation_walk2;
    	 this.textureLocation_hit=textureLocation_hit;
    	 currentTexture=textureLocation_idle;
    	 
    	 movementDirection = (int) ((Math.random() * (12 - 0)) + 0);
    	 moveTimerMax = (int) ((Math.random() * (70 - 30)) + 30);
    	 this.width=width;
		 this.height=height;
		 this.centre =centre;
		 this.type = type;
		 if(type=="boss") {
			 hp=50;
		 }
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
			currentTex="idle";
		}
		if(tex=="walk") {
			if(direction==0) {currentTexture = textureLocation_walk;}
			if(direction==1) {currentTexture = textureLocation_walk2;}
			currentTex="walk";
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
	
	public int getMovementDirection() {
		return movementDirection;
	}
	
	public void setMovementDirection(int num) {
		movementDirection = num;
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
	
	public int getMoveTimer() {
		return moveTimer;
	}
	
	public void setMoveTimer(int is) {
		moveTimer = is;
	}
	
	public int getMoveTimerMax() {
		return moveTimerMax;
	}
	
	public void setMoveTimerMax(int is) {
		moveTimerMax = is;
	}
	
	public void setHp(int is) {
		hp=is;
	}
	
	public int getHp() {
		return hp;
	}
	
	public void setType(String is) {
		type=is;
	}
	
	public String getType() {
		return type;
	}
}
