/*
 * Project by Cameron Carton
 * Student ID: 19720959
 */

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import util.GameObject;


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
 
 * Credits: Kelly Charles (2020)
 */ 
public class Viewer extends JPanel {
	private long CurrentAnimationTime= 0; 
	
	Model gameworld =new Model(); 
	private String background_image = "res/background.png";
	public Viewer(Model World) {
		this.gameworld=World;
		// TODO Auto-generated constructor stub
	}

	public Viewer(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}

	public Viewer(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public Viewer(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		// TODO Auto-generated constructor stub
	}

	public void updateview() {
		
		this.repaint();
		// TODO Auto-generated method stub
		
	}
	
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		CurrentAnimationTime++; // runs animation time step 
		
		//Draw background 
		if(gameworld.getCurrentRoom()=="Start") {
			if(gameworld.getStartGame()) {
				if(gameworld.getCoop()==false) {
				background_image="res/background_4.png";
				}else {
				background_image="res/background_3.png";
				}
			}else {
				background_image="res/background_5.png";
			}
		}else 
		if(gameworld.getCurrentRoom()=="chest") {
			background_image="res/background_2.png";
		}else {
			background_image="res/background.png";
		}
		drawBackground(g2,background_image);

		//Draw Blocks   
		gameworld.getBlocks().forEach((temp) -> 
		{
			drawBlocks((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g2, temp.getSpriteSizeX(), temp.getSpriteSizeY(), temp.getHp(),temp.getBreakable(),temp.getImageIndex(),temp.getType(),temp.getWarpRoom());	 

		}); 
		
		//Draw Bullets   
		gameworld.getBullets().forEach((temp) -> 
		{
			drawBullets((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g2, 32, temp.getDirection());	 

		}); 
		
		//Draw player
		gameworld.getPlayers().forEach((temp) -> 
		{
			drawPlayers((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),temp.getHit(),g2, 32, temp.getCurrentTex(),temp.getGotHit());	 

		}); 
		
		//Draw Enemy  
		gameworld.getEnemies().forEach((temp) -> 
		{
			drawEnemies((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),temp.getHit(),g2, 32, temp.getCurrentTex(),temp.getGotHit());	 

		}); 
		
		//Draw Buttons  
		gameworld.getButtons().forEach((temp) -> 
		{
			drawButtons((int) temp.getCentre().getX(), (int) temp.getCentre().getY(), (int) temp.getWidth(), (int) temp.getHeight(), temp.getTexture(),g2, temp.getSpriteSizeX(), temp.getSpriteSizeY(), temp.getImageIndex(),temp.getType(),temp.getSelected());	  

		}); 
				
		//Draw UI
		if(gameworld.getStartGame()) {
		drawUI(g2);
		}
		
				
		g2.dispose();
	}
	

	private void drawBackground(Graphics2D g,String image)
	{
		File TextureToLoad = new File(image);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		try {
			Image myImage = ImageIO.read(TextureToLoad); 
			 g.drawImage(myImage, 0,0, 960, 960, 0 , 0, 320, 320, null); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}

	private void drawUI(Graphics2D g) {
		int score = gameworld.getScore();
		int level = gameworld.getLevel();
		boolean gameOver = gameworld.getGameOver();
		for (PlayerObject player : gameworld.getPlayers()) 
		{ 
			File TextureToLoad = null;
			File TextureToLoad2 = null;
			File TextureToLoad3 = new File("res/bananaEmpty.png");
			File TextureToLoad4 = new File("res/score.png");
			File TextureToLoad5 = new File("res/level.png");
			File TextureToLoadNumbers = new File("res/numbers.png");
			int playerOffset=0;
			if(player.getController()==1) {
				TextureToLoad = new File("res/banana.png");
				TextureToLoad2 = new File("res/playerSign1.png");
			}else
			if(player.getController()==2) {
				TextureToLoad = new File("res/banana2.png");
				TextureToLoad2 = new File("res/playerSign2.png");
				playerOffset=720;
			}
			try {
				Image myImage = ImageIO.read(TextureToLoad);
				Image myImage2 = ImageIO.read(TextureToLoad2);
				Image myImage3 = ImageIO.read(TextureToLoad3);
				Image myImage4 = ImageIO.read(TextureToLoad4);
				Image myImage5 = ImageIO.read(TextureToLoad5);
				Image myImageNumbers = ImageIO.read(TextureToLoadNumbers);
				int x = 50;
				int y = 40;
				for(int i=0;i<player.getHpMax();i++) {
					g.drawImage(myImage3, x+(i*30)+playerOffset,y, x+(i*30)+45+playerOffset, y+45, 0, 0, 0+15, 15, null);
				}
				for(int i=0;i<player.getHp();i++) {
					g.drawImage(myImage, x+(i*30)+playerOffset,y, x+(i*30)+45+playerOffset, y+45, 0, 0, 0+15, 15, null);
				}
				g.drawImage(myImage2, 45+playerOffset, 4, 45+123+playerOffset, 4+30, 0, 0, 0+41, 10, null);
				
				//score
				int yOff=0;
				int xOff=0;
				if(gameOver) {yOff=480;xOff=120;}
				g.drawImage(myImage4, 250+xOff, 4+yOff, 250+93+xOff, 4+30+yOff, 0, 0, 0+31, 10, null);
				
				//numbers
				for(int i=0;i<7;i++) {
					int num =0;
					if(i==0) {
						num=score%10;
					}
					if(i==1) {
						num= (int) Math.floor((score%100)/10);					
					}
					if(i==2) {
						num= (int) Math.floor((score%1000)/100);
					}
					if(i==3) {
						num= (int) Math.floor((score%10000)/1000);
					}
					if(i==4) {
						num= (int) Math.floor((score%100000)/10000);
					}
					if(i==5) {
						num= (int) Math.floor((score%1000000)/100000);
					}
					if(i==6) {
						num= (int) Math.floor((score%10000000)/1000000);
					}
					num=num*4;
					g.drawImage(myImageNumbers, 290+60-(i*14)+90+xOff, 10+yOff, 290+60+12-(i*14)+90+xOff, 10+18+yOff, num, 0, num+4, 6, null);
				}
				
				//level
				yOff=0;
				xOff=0;
				if(gameOver) {yOff=400;xOff=-160;}
				g.drawImage(myImage5, 250+xOff+280, 4+yOff, 250+93+xOff+280, 4+30+yOff, 0, 0, 0+31, 10, null);
				
				//numbers
				for(int i=0;i<3;i++) {
					int num =0;
					if(i==0) {
						num=level%10;
					}
					if(i==1) {
						num= (int) Math.floor((level%100)/10);					
					}
					num=num*4;
					g.drawImage(myImageNumbers, 290-(i*14)+90+xOff+280, 10+yOff, 290+12-(i*14)+90+xOff+280, 10+18+yOff, num, 0, num+4, 6, null);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} 
		}
	}


	private void drawPlayers(int x, int y, int width, int height, String texture,String texture2,Graphics2D g, int spriteSize, String getCurrentTex, int gotHit) { 
		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		File TextureToLoad2 = new File(texture2);
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			//spriteSize for position of animation
			int currentPositionInAnimation= ((int) ((CurrentAnimationTime/3)%6))*spriteSize;
			if(getCurrentTex=="idle") {
				currentPositionInAnimation= ((int) ((CurrentAnimationTime/12)%6))*spriteSize;
			}else
			if(getCurrentTex=="walk") {
				currentPositionInAnimation= ((int) ((CurrentAnimationTime/2)%6))*spriteSize;
			}else
			if(getCurrentTex=="death") {
				currentPositionInAnimation= 0;
			}
			
			//getting hit
			if(gotHit>0) {
				myImage = ImageIO.read(TextureToLoad2);
				currentPositionInAnimation= 0;
			}
			
			g.drawImage(myImage, x,y, x+width, y+height, currentPositionInAnimation, 0, currentPositionInAnimation+spriteSize, spriteSize, null); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} 
		 
	}
	
	private void drawEnemies(int x, int y, int width, int height, String texture,String texture2,Graphics2D g, int spriteSize, String getCurrentTex, int gotHit) { 
		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		File TextureToLoad2 = new File(texture2);
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			
			//spriteSize for position of animation
			int currentPositionInAnimation= ((int) ((CurrentAnimationTime/3)%6))*spriteSize;
			if(getCurrentTex=="idle") {
				currentPositionInAnimation= ((int) ((CurrentAnimationTime/12)%6))*spriteSize;
			}else
			if(getCurrentTex=="walk") {
				currentPositionInAnimation= ((int) ((CurrentAnimationTime/2)%6))*spriteSize;
			}
			
			//getting hit
			if(gotHit>0) {
				myImage = ImageIO.read(TextureToLoad2);
				currentPositionInAnimation= 0;
			}
			
			g.drawImage(myImage, x,y, x+width, y+height, currentPositionInAnimation, 0, currentPositionInAnimation+spriteSize, spriteSize, null); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} 
		 
	}
	
	private void drawButtons(int x, int y, int width, int height, String texture, Graphics2D g, int spriteSizeX, int spriteSizeY,int image_index,String type, int selected) {
		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			
			//spriteSize for position of animation
			int currentPositionInAnimation= selected*spriteSizeX;
			g.drawImage(myImage, x,y, x+width, y+height, currentPositionInAnimation  , image_index*spriteSizeY, currentPositionInAnimation+spriteSizeX, (image_index*spriteSizeY)+spriteSizeY, null); 
			
			if(type == "sound" && gameworld.getSoundOn()==true) {
				g.drawImage(myImage, x,y, x+width, y+height, currentPositionInAnimation  , 4*spriteSizeY, currentPositionInAnimation+spriteSizeX, (4*spriteSizeY)+spriteSizeY, null); 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} 
		 
	}
	
	private void drawBlocks(int x, int y, int width, int height, String texture, Graphics2D g, int spriteSizeX, int spriteSizeY, int hp,boolean breakable,int image_index,String type,String warpRoom) {
		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			int currentPositionInAnimation= (3-hp)*spriteSizeX;
			if(breakable==false) {
				currentPositionInAnimation= 0;
			}
			if(type == "warp_right" || type == "warp_left" || type == "warp_top" || type == "warp_bottom") {
				if((gameworld.getEnemies().size()==0 && gameworld.getItemExist()==false)|| warpRoom==null) {
					image_index=0;
				}
			}
			g.drawImage(myImage, x,y, x+width, y+height, currentPositionInAnimation  , image_index*spriteSizeY, currentPositionInAnimation+spriteSizeX, (image_index*spriteSizeY)+spriteSizeY, null); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} 
		
	}
		 
	private void drawBullets(int x, int y, int width, int height, String texture, Graphics2D g, int spriteSize,float dir) {
		File TextureToLoad = new File(texture);  //should work okay on OSX and Linux but check if you have issues depending your eclipse install or if your running this without an IDE 
		try {
			Image myImage = ImageIO.read(TextureToLoad);
			int currentPositionInAnimation= ((int) ((CurrentAnimationTime/2)%4))*spriteSize;
			
			g.drawImage(myImage, x,y, x+width, y+height, currentPositionInAnimation  , (int) (dir*spriteSize), currentPositionInAnimation+spriteSize, (int) (dir*spriteSize)+spriteSize, null); 
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} 
		
	}
	 

}
