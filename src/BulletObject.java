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
public class BulletObject {
	
	private Point3f centre= new Point3f(0,0,0);			// Centre of object, using 3D as objects may be scaled  
	private int width=10;
	private int height=10;
	private boolean hasTextured=false;
	private String textureLocation; 
	private String blanktexture="res/blankSprite.png";
	
	private float direction;
	private float hspd=0;
	private float vspd=0;
	private String bulletType="player";
	private float speed = 4;
	private int damage = 1;
	public BulletObject() {  
		
	}
	
    public BulletObject(String textureLocation,int width,int height,Point3f centre,float dir, String bulletType,float speed,int damage) { 
    	 hasTextured=true;
    	 this.textureLocation=textureLocation;
    	 this.width=width;
		 this.height=height;
		 this.centre =centre;
		 this.bulletType = bulletType;
		 this.speed = speed;
		 this.damage = damage;
		 if(dir==0) {
			 this.direction = 0;
		 }
		 if(dir==1) {
			 this.direction = 1;
		 }
		 if(dir==2) {
			 this.direction = 2;
		 }
		 if(dir==3) {
			 this.direction = 3;
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

	public int getHeight() {
		return height;
	}

	public String getTexture() {
		if(hasTextured) 
			{
			return textureLocation;
			}
		 
		return blanktexture; 
	}
	
	public float getDirection() {
		return direction;
	}
	
	public void setDirection(float is) {
		direction=is;
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
	
	public void setBulletType(String is) {
		bulletType = is;
	}
	
	public String getBulletType() {
		return bulletType;
	}
	
	public void setSpeed(float is) {
		speed = is;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public void setDamage(int is) {
		damage = is;
	}
	public int getDamage() {
		return damage;
	}
  
}
