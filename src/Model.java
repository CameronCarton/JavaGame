/*
 * Project by Cameron Carton
 * Student ID: 19720959
 */


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import util.GameObject;
import util.Point3f;
import util.Vector3f; 
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
public class Model {

	private Sound sound =new Sound();
	private Sound music =new Sound();
	private boolean soundOn=false;
	private  CopyOnWriteArrayList<PlayerObject> PlayerList  = new CopyOnWriteArrayList<PlayerObject>();
	private Controller controller = new Controller();
	
	private  CopyOnWriteArrayList<BlockObject> BlockList  = new CopyOnWriteArrayList<BlockObject>();
	private  CopyOnWriteArrayList<BulletObject> BulletsList  = new CopyOnWriteArrayList<BulletObject>();
	private  CopyOnWriteArrayList<EnemyObject> EnemyList  = new CopyOnWriteArrayList<EnemyObject>();
	private  CopyOnWriteArrayList<ButtonObject> ButtonList  = new CopyOnWriteArrayList<ButtonObject>();
	
	//this holds all the data for each room in the map
	private  ArrayList<String[]> mapData  = new ArrayList<String[]>();
	private String[][] mapArray = new String[11][11];
	private String currentRoom = "Start";
	private boolean startGame= false; 
	private boolean gameOver = false;
	
	private boolean coop = false;
	private int selectedButton = 0;
	
	private int Score=0; 
	private int level=1;
	
	public Model() {
		//setup game world 
		setUpGame();
		
	}
	
	public void setUpGame() {
		//clear console
		System.out.print("\033[H\033[2J");  
	    System.out.flush();
	    
		selectedButton=0;
		Score=0;
		currentRoom = "Start";
		//Player 
		PlayerList.add(new PlayerObject("res/chimp_idle.png","res/chimp_walk.png",
										"res/chimp_idle2.png","res/chimp_walk2.png",
										"res/chimp_idle3.png","res/chimp_walk3.png",
										"res/chimp_idle4.png","res/chimp_idle4.png",
										"res/chimp_death.png","res/chimp_hit.png",96,96,new Point3f(410,450,0),1));
		
		//generate map
		mapArray = createMap();
		mapData = addRoomsToList(mapArray);
		
		//generate room
		String roomToLoad = "res/room1_data.txt";
		ArrayList<String[]> roomArray = readRoomData(roomToLoad);
		createRoom(roomArray,"0");
		
		//print room for debugging
		for(int i=0;i<11;i++) {
			for(int j=0;j<11;j++) {
				if(mapArray[i][j]==null) {
					System.out.print(" 0 ");
				}else 
				if(mapArray[i][j]=="Start") {
					System.out.print(" S ");
				}else 
				if(mapArray[i][j]=="max") {
					System.out.print(" M ");
				}else 
				if(mapArray[i][j]=="chest") {
					System.out.print(" C ");
				}else
				if(Integer.parseInt(mapArray[i][j])>9) {
					System.out.print(" "+mapArray[i][j]);
				}else
				{
					System.out.print(" "+mapArray[i][j]+" ");
				}
			}
			System.out.println("");
		}
		System.out.println("");
		
		generateRoom();
		
		ButtonList.add(new ButtonObject("res/buttons.png",300,75,new Point3f(550,600,0),100,25,0,"start",1));
		ButtonList.add(new ButtonObject("res/buttons.png",300,75,new Point3f(550,680,0),100,25,1,"coop",0));
		ButtonList.add(new ButtonObject("res/buttons.png",300,75,new Point3f(550,760,0),100,25,2,"sound",0));
		
		gameOver=false;

	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////// Room Read /////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private ArrayList<String[]> readRoomData(String data){
		ArrayList<String[]> roomArray = new ArrayList<String[]>();
		
		BufferedReader reader;

		try {
			reader = new BufferedReader(new FileReader(data));
			String line = reader.readLine();
			int i=0;
			while (line != null) {
				//converting txt data into a string array
				line = line.replaceAll("\\s","");
				String[] row = line.split("-");
				roomArray.add(row);
				
				// read next line
				line = reader.readLine();
				
				i++;
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return roomArray;
	}

	private void createRoom(ArrayList<String[]> roomArray,String encounter) {
		for(int i=0;i<20;i++) {
			for(int j=0;j<20;j++) {
				
				//System.out.println(roomArray.get(i)[j]);
				
				if(roomArray.get(i)[j].equals("1")) {
					BlockList.add(new BlockObject("res/block_wood.png",48,48,new Point3f(j*48,i*48,0),16,16,true,0,"reg",""));
				}
				if(roomArray.get(i)[j].equals("2")) {
					BlockList.add(new BlockObject("res/block.png",48,48,new Point3f(j*48,i*48,0),16,16,false,0,"reg",""));
				}
				
				if(encounter=="0") {
				if(roomArray.get(i)[j].equals("3")) {
					EnemyList.add(new EnemyObject("res/chimpE_idle.png","res/chimpE_walk.png","res/chimpE_idle2.png","res/chimpE_walk2.png","res/chimp_hit.png",96,96,new Point3f(j*48,i*48,0),"goon") ) ;
				}
				
				if(roomArray.get(i)[j].equals("4")) {
					EnemyList.add(new EnemyObject("res/skull_move.png","res/skull_move.png","res/skull_move2.png","res/skull_move2.png","res/skull_hit.png",96,96,new Point3f(j*48,i*48,0),"boss") ) ;
				}
				
				if(roomArray.get(i)[j].equals("11")) {
					//BlockList.add(new BlockObject("res/chest.png",96,96,new Point3f((j*48),(i*48),0),32,32,false,0,"chest",""));
					spawnItem((j*48)+20,(i*48)+20);
				}
				}
				if(roomArray.get(i)[j].equals("10")) {
					BlockList.add(new BlockObject("res/statue.png",96,126,new Point3f((j*48)-0,(i*48)-100,0),32,42,false,0,"reg",""));
				}
				
				
			}
		}
	}
	
	public void generateRoom() {
		int path_top = 0;
		int path_bottom = 0;
		int path_left = 0;
		int path_right = 0;
		
		//wall blocks
		BlockList.add(new BlockObject("res/block_top.png",960,96,new Point3f(0,0,0),320,32,false,path_top,"top",""));
		BlockList.add(new BlockObject("res/block_bottom.png",960,96,new Point3f(0,960-96,0),320,32,false,path_bottom,"bottom",""));
		BlockList.add(new BlockObject("res/block_left.png",96,960,new Point3f(0,0,0),32,320,false,path_left,"left",""));
		BlockList.add(new BlockObject("res/block_right.png",96,960,new Point3f(960-96,0,0),32,320,false,path_right,"right",""));
		
		//warps
		BlockList.add(new BlockObject("res/door.png",96,96,new Point3f(480-48,0,0),32,32,false,2,"warp_top","1"));//top
		BlockList.add(new BlockObject("res/door.png",96,96,new Point3f(480-48,960-96,0),32,32,false,1,"warp_bottom","1"));//bottom
		BlockList.add(new BlockObject("res/door.png",96,96,new Point3f(0,480-48,0),32,32,false,4,"warp_left","1"));//left
		BlockList.add(new BlockObject("res/door.png",96,96,new Point3f(960-96,480-48,0),32,32,false,3,"warp_right","1"));//right
		
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////// Game Logic /////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	// This is the heart of the game , where the model takes in all the inputs ,decides the outcomes and then changes the model accordingly. 
	public void gamelogic() 
	{
		// Player Logic first 
		playerLogic(); 
		
		// Block Logic
		blockLogic();
		
		// Bullet Logic
		bulletLogic();
		
		// Enemy Logic
		enemyLogic();
				
		// interactions between objects 
		gameLogic(); 

	}

	private boolean canPress=true;
	private void gameLogic() { 
		
		if(!controller.isKeyWPressed() && !controller.isKeyIPressed() && !controller.isKeySPressed() && !controller.isKeyKPressed() && !controller.isKeySpacePressed() && !controller.isKeyDotPressed()) {
			canPress=true;
		}
		if(!startGame || gameOver) {
			//buttons in menu
			for (ButtonObject temp : ButtonList) 
			{
				//key press changes button selected
				if(controller.isKeyWPressed() || controller.isKeyIPressed()) {
					
					if(temp.getSelected()==1 && canPress) {
						if(temp.getImageIndex()==0) {
							selectedButton=2;
						}
						if(temp.getImageIndex()==1) {
							selectedButton=0;
						}
						if(temp.getImageIndex()==2) {
							selectedButton=1;
						}
						canPress=false;
					}
					
				}else
				if(controller.isKeySPressed() || controller.isKeyKPressed()) {
					
					if(temp.getSelected()==1 && canPress) {
						if(temp.getImageIndex()==0) {
							selectedButton=1;
						}
						if(temp.getImageIndex()==1) {
							selectedButton=2;
						}
						if(temp.getImageIndex()==2) {
							selectedButton=0;
						}
						canPress=false;
					}
				}else
				
				//select
				if(controller.isKeySpacePressed()) {
					if(temp.getSelected()==1 && canPress) {
						if(temp.getImageIndex()==0) {
							coop=false;
							startGame=true;
							for (ButtonObject butt : ButtonList) 
							{
								ButtonList.remove(butt);
							}
						}
						if(temp.getImageIndex()==1) {
							coop=true;
							PlayerList.add(new PlayerObject("res/chimp2_idle.png","res/chimp2_walk.png",
															"res/chimp2_idle2.png","res/chimp2_walk2.png",
															"res/chimp2_idle3.png","res/chimp2_walk3.png",
															"res/chimp2_idle4.png","res/chimp2_idle4.png",
															"res/chimp2_death.png","res/chimp_hit.png",96,96,new Point3f(450,450,0),2));
							startGame=true;
							for (ButtonObject butt : ButtonList) 
							{
								ButtonList.remove(butt);
							}
						}
						if(temp.getImageIndex()==2) {
							soundToggle();
							canPress=false;
						}
						if(temp.getImageIndex()==3) {
							canPress=false;
							resetGame();
							
						}
					}
				}
				
				//update images
				if(selectedButton==temp.getImageIndex() || temp.getType()=="main") {
					temp.setSelected(1);
				}else {
					temp.setSelected(0);
				}
			}
		}
		
		//see if they hit anything 
		for (BlockObject temp : BlockList) 
		{
			if(temp.getType()=="stat") {
				temp.setCentre(new Point3f(temp.getCentre().getX(),temp.getCentre().getY()-1,0));
				temp.setCount(temp.getCount()+1);
				if(temp.getCount()>=200) {
					BlockList.remove(temp);
				}
			}
		for (BulletObject Bullet : BulletsList) 
		{
			//collision detection
			if (((Bullet.getCentre().getX()+(Bullet.getWidth()/2) > temp.getCentre().getX() && Bullet.getCentre().getX()+(Bullet.getWidth()/2) < temp.getCentre().getX()+temp.getWidth()) && (Bullet.getCentre().getY()+(Bullet.getHeight()/2) > temp.getCentre().getY() && Bullet.getCentre().getY()+(Bullet.getHeight()/2) < temp.getCentre().getY()+temp.getHeight()))
			|| ((Bullet.getCentre().getX()+Bullet.getWidth()-(Bullet.getWidth()/2) > temp.getCentre().getX() && Bullet.getCentre().getX()+Bullet.getWidth()-(Bullet.getWidth()/2) < temp.getCentre().getX()+temp.getWidth()) && (Bullet.getCentre().getY()+(Bullet.getHeight()/2) > temp.getCentre().getY() && Bullet.getCentre().getY()+(Bullet.getHeight()/2) < temp.getCentre().getY()+temp.getHeight()))
			|| ((Bullet.getCentre().getX()+(Bullet.getWidth()/2) > temp.getCentre().getX() && Bullet.getCentre().getX()+(Bullet.getWidth()/2) < temp.getCentre().getX()+temp.getWidth()) && (Bullet.getCentre().getY()+Bullet.getHeight()-(Bullet.getHeight()/2) > temp.getCentre().getY() && Bullet.getCentre().getY()+Bullet.getHeight()-(Bullet.getHeight()/2) < temp.getCentre().getY()+temp.getHeight()))	
			|| ((Bullet.getCentre().getX()+Bullet.getWidth()-(Bullet.getWidth()/2) > temp.getCentre().getX() && Bullet.getCentre().getX()+Bullet.getWidth()-(Bullet.getWidth()/2) < temp.getCentre().getX()+temp.getWidth()) && (Bullet.getCentre().getY()+Bullet.getHeight()-(Bullet.getHeight()/2) > temp.getCentre().getY() && Bullet.getCentre().getY()+Bullet.getHeight()-(Bullet.getHeight()/2) < temp.getCentre().getY()+temp.getHeight())))
			{
				temp.setHp(temp.getHp()-Bullet.getDamage());
				if(temp.getBreakable()==true && temp.getHp()<=0) {
					BlockList.remove(temp);
				}
				BulletsList.remove(Bullet);
			}  
		}
		}
		
		//hit enemies
		for (EnemyObject temp : EnemyList) 
		{
		for (BulletObject Bullet : BulletsList) 
		{
			if(Bullet.getBulletType()=="player") {
				//collision detection
				if (((Bullet.getCentre().getX()+(Bullet.getWidth()/2) > temp.getCentre().getX() && Bullet.getCentre().getX()+(Bullet.getWidth()/2) < temp.getCentre().getX()+temp.getWidth()) && (Bullet.getCentre().getY()+(Bullet.getHeight()/2) > temp.getCentre().getY() && Bullet.getCentre().getY()+(Bullet.getHeight()/2) < temp.getCentre().getY()+temp.getHeight()))
				|| ((Bullet.getCentre().getX()+Bullet.getWidth()-(Bullet.getWidth()/2) > temp.getCentre().getX() && Bullet.getCentre().getX()+Bullet.getWidth()-(Bullet.getWidth()/2) < temp.getCentre().getX()+temp.getWidth()) && (Bullet.getCentre().getY()+(Bullet.getHeight()/2) > temp.getCentre().getY() && Bullet.getCentre().getY()+(Bullet.getHeight()/2) < temp.getCentre().getY()+temp.getHeight()))
				|| ((Bullet.getCentre().getX()+(Bullet.getWidth()/2) > temp.getCentre().getX() && Bullet.getCentre().getX()+(Bullet.getWidth()/2) < temp.getCentre().getX()+temp.getWidth()) && (Bullet.getCentre().getY()+Bullet.getHeight()-(Bullet.getHeight()/2) > temp.getCentre().getY() && Bullet.getCentre().getY()+Bullet.getHeight()-(Bullet.getHeight()/2) < temp.getCentre().getY()+temp.getHeight()))	
				|| ((Bullet.getCentre().getX()+Bullet.getWidth()-(Bullet.getWidth()/2) > temp.getCentre().getX() && Bullet.getCentre().getX()+Bullet.getWidth()-(Bullet.getWidth()/2) < temp.getCentre().getX()+temp.getWidth()) && (Bullet.getCentre().getY()+Bullet.getHeight()-(Bullet.getHeight()/2) > temp.getCentre().getY() && Bullet.getCentre().getY()+Bullet.getHeight()-(Bullet.getHeight()/2) < temp.getCentre().getY()+temp.getHeight())))
				{
					temp.setHp(temp.getHp()-Bullet.getDamage());
					temp.setGotHit(5);
					if(soundOn)playSoundEffect(4);
					if(temp.getHp()<=0) {
						EnemyList.remove(temp);
						if(temp.getType()=="boss")spawnExit();
						Score +=100;
					}
					BulletsList.remove(Bullet);
					Score +=10;
				}  
			}
		}
		}
		
		//hit player
		for (PlayerObject Player : PlayerList) 
		{
			for (BulletObject Bullet : BulletsList) 
			{
				if(Bullet.getBulletType()=="enemy") {
					//collision detection
					if (((Bullet.getCentre().getX()+(Bullet.getWidth()/2) > Player.getCentre().getX() && Bullet.getCentre().getX()+(Bullet.getWidth()/2) < Player.getCentre().getX()+Player.getWidth()) && (Bullet.getCentre().getY()+(Bullet.getHeight()/2) > Player.getCentre().getY() && Bullet.getCentre().getY()+(Bullet.getHeight()/2) < Player.getCentre().getY()+Player.getHeight()))
					|| ((Bullet.getCentre().getX()+Bullet.getWidth()-(Bullet.getWidth()/2) > Player.getCentre().getX() && Bullet.getCentre().getX()+Bullet.getWidth()-(Bullet.getWidth()/2) < Player.getCentre().getX()+Player.getWidth()) && (Bullet.getCentre().getY()+(Bullet.getHeight()/2) > Player.getCentre().getY() && Bullet.getCentre().getY()+(Bullet.getHeight()/2) < Player.getCentre().getY()+Player.getHeight()))
					|| ((Bullet.getCentre().getX()+(Bullet.getWidth()/2) > Player.getCentre().getX() && Bullet.getCentre().getX()+(Bullet.getWidth()/2) < Player.getCentre().getX()+Player.getWidth()) && (Bullet.getCentre().getY()+Bullet.getHeight()-(Bullet.getHeight()/2) > Player.getCentre().getY() && Bullet.getCentre().getY()+Bullet.getHeight()-(Bullet.getHeight()/2) < Player.getCentre().getY()+Player.getHeight()))	
					|| ((Bullet.getCentre().getX()+Bullet.getWidth()-(Bullet.getWidth()/2) > Player.getCentre().getX() && Bullet.getCentre().getX()+Bullet.getWidth()-(Bullet.getWidth()/2) < Player.getCentre().getX()+Player.getWidth()) && (Bullet.getCentre().getY()+Bullet.getHeight()-(Bullet.getHeight()/2) > Player.getCentre().getY() && Bullet.getCentre().getY()+Bullet.getHeight()-(Bullet.getHeight()/2) < Player.getCentre().getY()+Player.getHeight())))
					{
						Player.setHp(Player.getHp()-Bullet.getDamage());
						if(Player.getHp()<=0) {
							playerDeath();
						}else {
							Player.setGotHit(5);
							if(soundOn)playSoundEffect(3);
						}
						BulletsList.remove(Bullet);
					}  
				}
			}
		}
				
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////// Map generation ///////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private String[][] createMap(){
		String[][] mapArray = new String[11][11];
		
		mapArray[5][5] = "Start";
		
		//number of rooms in the map
		int maxRooms = 15;
		int minRooms = 10;
		int numberOfRooms = (int) ((Math.random() * (maxRooms - minRooms)) + minRooms);
		
		int roomNumber=1;
		
		int mapPosX=5;
		int mapPosY=5;
		
		//places new rooms in the array
		while(roomNumber<=numberOfRooms) {
			
			//random direction for next room
			int nextDirection = (int) ((Math.random() * (4 - 1)) + 1);
			
			int nextX=mapPosX;
			int nextY=mapPosY;
			
			if(nextDirection==1)nextX=mapPosX+1;
			if(nextDirection==2)nextX=mapPosX-1;
			if(nextDirection==3)nextY=mapPosY+1;
			if(nextDirection==4)nextY=mapPosY-1;
			
			//check if next room is in the array bounds
			if(nextX > 10) {nextX=10;}
			if(nextX < 0) {nextX=0;}
			if(nextY > 10) {nextY=10;}
			if(nextY < 0) {nextY=0;}
			
			if(mapArray[nextY][nextX] != null) {
				roomNumber--;
			}else {
				mapArray[nextY][nextX] = String.valueOf(roomNumber);
				if(roomNumber==numberOfRooms) {
					mapArray[nextY][nextX] = "max";
				}
				if(roomNumber== (int) (numberOfRooms/2)) {
					mapArray[nextY][nextX] = "chest";
				}
			}
			
			mapPosX=nextX;
			mapPosY=nextY;
			
			roomNumber++;
		}
		
		return mapArray;
	}
	
	private ArrayList<String[]> addRoomsToList(String[][] mapArray){
		ArrayList<String[]> mapData = new ArrayList<String[]>();
		
		
		//adding data for each room to a list
		//data is roomID and its 4 possible connecting paths at each side to other rooms
		for(int i=0;i<11;i++) {
			for(int j=0;j<11;j++) {
				
				if(mapArray[j][i] != null) {
					
					String roomID = mapArray[j][i];
					String pathUp = null;
					String pathDown = null;
					String pathLeft = null;
					String pathRight = null;
					String special = null;
					String roomLayout = "res/room2_data.txt";
					
					int maxRooms = 9;
					int minRooms = 2;
					int randomLayout = (int) ((Math.random() * (maxRooms - minRooms)) + minRooms);
					if(randomLayout==2) {roomLayout = "res/room2_data.txt";}
					if(randomLayout==3) {roomLayout = "res/room3_data.txt";}
					if(randomLayout==4) {roomLayout = "res/room4_data.txt";}
					if(randomLayout==5) {roomLayout = "res/room5_data.txt";}
					if(randomLayout==6) {roomLayout = "res/room6_data.txt";}
					if(randomLayout==7) {roomLayout = "res/room7_data.txt";}
					if(randomLayout==8) {roomLayout = "res/room8_data.txt";}
					
					//special room ids
					if(roomID=="Start") {roomLayout = "res/room1_data.txt";}
					if(roomID=="chest") {roomLayout = "res/roomChest_data.txt";}
					if(roomID=="max") {roomLayout = "res/roomBoss_data.txt";}
					
					if(j>0)pathUp = mapArray[j-1][i];
					if(j<10)pathDown = mapArray[j+1][i];
					if(i>0)pathLeft = mapArray[j][i-1];
					if(i<10)pathRight = mapArray[j][i+1];
					
					String[] room = {roomID,pathUp,pathDown,pathLeft,pathRight,roomLayout,special,"0"};
					
					mapData.add(room);
				}
				
			}
		}
		
		return mapData;
	}
	
	public String getCurrentRoom() {
		return currentRoom;
	}
	public void setCurrentRoom(String is) {
		currentRoom = is;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////// Room transitions /////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void moveToRoom(String nextRoom,String enterFrom) {
		
		if(EnemyList.size()==0 && nextRoom != null && itemExist==false) {
			for (PlayerObject Player : PlayerList) 
			{
				if(enterFrom=="top")Player.setCentre(new Point3f(430,720+50,0));
				if(enterFrom=="left")Player.setCentre(new Point3f(720+60,430,0));
				if(enterFrom=="right")Player.setCentre(new Point3f(80,430,0));
				if(enterFrom=="bottom")Player.setCentre(new Point3f(430,80,0));
			}
			for (BlockObject temp : BlockList) 
			{
				if(temp.getType()=="reg" || temp.getType()=="chest" || temp.getType()=="exit" || temp.getType()=="stat" || temp.getType()=="item") {
					BlockList.remove(temp);
				}
			}
			for (BulletObject temp : BulletsList) 
			{
				BulletsList.remove(temp);
			}
			
			
			String roomToLoad = "res/room1_data.txt";
			String encounter = "1";
			
			for(int i=0;i<mapData.size();i++) {
				if(mapData.get(i)[0].equals(nextRoom)) {
					roomToLoad = mapData.get(i)[5];
					if(mapData.get(i)[7]=="0") {
						encounter="0";
						mapData.get(i)[7] = "1";
					}
					break;
				}
			}
			
			ArrayList<String[]> roomArray = readRoomData(roomToLoad);
			createRoom(roomArray,encounter);
			
			setCurrentRoom(nextRoom);
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////// Player Logic ////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void playerLogic() {
		for (PlayerObject Player : PlayerList) 
		{
		Player.setGotHit(Player.getGotHit()-1);
		Player.changeCurrentTexture("idle");
		
		if(Player.getHp()<=0) {
			Player.changeCurrentTexture("death");
		}
		
		//movement 
		Player.setHspd(0);
		Player.setVspd(0);
		float hspd = Player.getHspd();
		float vspd = Player.getVspd();
		float moveSpd=3;
		
		int cont = Player.getController();
		
		//check for movement
		if(Player.getHp()>0 && startGame) {
			if((controller.isKeyAPressed()&&cont==1)||(controller.isKeyJPressed()&&cont==2)){
				hspd=-moveSpd;
				
				Player.setDirection(1);
				Player.changeCurrentTexture("walk");
			}
			if((controller.isKeyDPressed()&&cont==1)||(controller.isKeyLPressed()&&cont==2)){
				hspd=moveSpd;
				
				Player.setDirection(0);
				Player.changeCurrentTexture("walk");
			}
			if((controller.isKeyWPressed()&&cont==1)||(controller.isKeyIPressed()&&cont==2))
			{
				vspd=moveSpd;
				
				Player.setDirection(2);
				Player.changeCurrentTexture("walk");
			}
			if((controller.isKeySPressed()&&cont==1)||(controller.isKeyKPressed()&&cont==2))
			{
				vspd=-moveSpd;
				
				Player.setDirection(3);
				Player.changeCurrentTexture("walk");
			}
			
			if((controller.isKeyAPressed() && controller.isKeyDPressed() &&cont==1)||(controller.isKeyJPressed() && controller.isKeyLPressed() &&cont==2)){
				//checking if both keys are pressed, then dont move
				hspd=0;
			}
			if((controller.isKeyWPressed() && controller.isKeySPressed() &&cont==1)||(controller.isKeyIPressed() && controller.isKeyKPressed() &&cont==2)){
				//checking if both keys are pressed, then dont move
				vspd=0;
			}
			
			//shoot
			if((controller.isKeyCPressed() &&cont==1)||(controller.isKeyDotPressed() &&cont==2))
			{
				if(Player.getCanShoot()) {
					float xb = Player.getCentre().getX();
					float yb = Player.getCentre().getY();
					float dir = Player.getDirection();
					
					String spr = "res/fireball.png";
					
					createBullet(xb, yb, dir, spr, "player",Player.getBulletSpeed(),Player.getDamage());
					
					//play sound
					if(soundOn)playSoundEffect(0);
					
					Player.setCanShoot(false);
				}
			}else {
				Player.setCanShoot(true);
			}
		}
		
		//collision 
		float x = Player.getCentre().getX()+27;
		float y = Player.getCentre().getY()+24;
		float Pw = 42;
		float Ph = 62;
		float next_x = x +hspd;
		float next_y = y -vspd;

		boolean topCollision = false;
		boolean leftCollision = false;
		boolean rightCollision = false;
		boolean bottomCollision = false;
		
		//collision
		for (BlockObject temp : BlockList) 
		{
			if(temp.getType()!="stat") {
			float tx = temp.getCentre().getX();
			float ty = temp.getCentre().getY();
			float th = temp.getHeight();
			float tw = temp.getWidth();
			
			
			if(vspd>0 
			&& (((x > tx && x < tx+tw) 
			&& (next_y > ty && next_y < ty+th))
			|| ((x+Pw > tx && x+Pw < tx+tw) 
			&& (next_y > ty && next_y < ty+th)))) {
				topCollision=true;
				if(temp.getType()=="warp_top") {moveToRoom(temp.getWarpRoom(),"top");}
				if(temp.getType()=="exit") {proceedToNextLevel(temp);}
				if(temp.getType()=="item") {itemGet(temp, Player);}
			}
			
			if(hspd<0 
			&& (((next_x > tx && next_x < tx+tw) 
			&& (y > ty && y < ty+th)) 
			|| ((next_x > tx && next_x < tx+tw) 
			&& (y+(Ph/2) > ty && y+(Ph/2) < ty+th)) 
			|| ((next_x > tx && next_x < tx+tw) 
			&& (y+Ph > ty && y+Ph < ty+th)))){
				leftCollision=true;
				if(temp.getType()=="warp_left") {moveToRoom(temp.getWarpRoom(),"left");}
				if(temp.getType()=="exit") {proceedToNextLevel(temp);}
				if(temp.getType()=="item") {itemGet(temp, Player);}
			}
			
			if(hspd>0 
			&& (((next_x+Pw > tx && next_x+Pw < tx+tw) 
			&& (y > ty && y < ty+th)) 
			|| ((next_x+Pw > tx && next_x+Pw < tx+tw) 
			&& (y+(Ph/2) > ty && y+(Ph/2) < ty+th))
			|| ((next_x+Pw > tx && next_x+Pw < tx+tw) 
			&& (y+Ph > ty && y+Ph < ty+th)))){
				rightCollision=true;
				if(temp.getType()=="warp_right") {moveToRoom(temp.getWarpRoom(),"right");}
				if(temp.getType()=="exit") {proceedToNextLevel(temp);}
				if(temp.getType()=="item") {itemGet(temp, Player);}
			}
			
			if(vspd<0 
			&& (((x > tx && x < tx+tw) 
			&& (next_y+Ph > ty && next_y+Ph < ty+th))
			|| ((x+Pw > tx && x+Pw < tx+tw) 
			&& (next_y+Ph > ty && next_y+Ph < ty+th)))) {
				bottomCollision=true;
				if(temp.getType()=="warp_bottom") {moveToRoom(temp.getWarpRoom(),"bottom");}
				if(temp.getType()=="exit") {proceedToNextLevel(temp);}
				if(temp.getType()=="item") {itemGet(temp, Player);}
			}
			}
		}
		
		if(leftCollision && hspd<0){hspd=0;}  
		if(rightCollision && hspd>0){hspd=0;}  
		if(topCollision && vspd>0){vspd=0;}  
		if(bottomCollision && vspd<0){vspd=0;}  
		
		//move
		if(hspd!=0)Player.getCentre().ApplyVector( new Vector3f(hspd,0,0));
		if(vspd!=0)Player.getCentre().ApplyVector( new Vector3f(0,vspd,0));
		}
	}
	
	public CopyOnWriteArrayList<PlayerObject> getPlayers() {
		return PlayerList;
	}
	
	public void playerDeath() {
		boolean fullDead=true;
		for (PlayerObject Player : PlayerList) 
		{
			if(Player.getHp()>0) {
				fullDead=false;
			}
		}
		if(fullDead) {
			for (BulletObject bull : BulletsList) 
			{
				BulletsList.remove(bull);
			}
			ButtonList.add(new ButtonObject("res/gameOver.png",960,960,new Point3f(0,0,0),320,320,0,"gameOver",1));
			ButtonList.add(new ButtonObject("res/buttons.png",300,75,new Point3f(330,680,0),100,25,3,"main",1));
			selectedButton=3;
			gameOver=true;
		}
	}
	public void resetGame() {
		for (BlockObject temp : BlockList) 
		{
			BlockList.remove(temp);
		}
		for (EnemyObject temp : EnemyList) 
		{
			EnemyList.remove(temp);
		}
		for (PlayerObject temp : PlayerList) 
		{
			PlayerList.remove(temp);
		}
		for (ButtonObject butt : ButtonList) 
		{
			ButtonList.remove(butt);
		}
		for (BulletObject bull : BulletsList) 
		{
			BulletsList.remove(bull);
		}
		level=1;
		startGame=false;
		setUpGame();
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////// Bullet Logic ////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void bulletLogic() {
		// TODO Auto-generated method stub
		for (BulletObject temp : BulletsList) 
		{ 
			if(temp.getDirection()<=1) {temp.setHspd(( (temp.getDirection()-0.5f) /0.5f) * -temp.getSpeed());}
			if(temp.getDirection()>=2) {temp.setVspd(( (temp.getDirection()-2.5f) /0.5f) * -temp.getSpeed());}
			
			float hspd = temp.getHspd();
			float vspd = temp.getVspd();
			
			//move
			if(hspd!=0)temp.getCentre().ApplyVector( new Vector3f(hspd,0,0));
			if(vspd!=0)temp.getCentre().ApplyVector( new Vector3f(0,vspd,0));
		}
	}
	
	private void createBullet(float xb,float yb,float dir,String spr,String type,float speed,int damage) {
		BulletsList.add(new BulletObject(spr,96,96,new Point3f(xb,yb,0),dir,type,speed,damage)); 
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////// Block Logic ////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void blockLogic() {
		for (BlockObject temp : BlockList) 
		{ 
			if(temp.getType()!="reg") {
				
				for(int i=0;i<mapData.size();i++) {
					if(mapData.get(i)[0].equals(currentRoom)) {
						
						if(temp.getType()=="top") {
							if(mapData.get(i)[1]!=null) {
								temp.setImageIndex(1);
							}else {
								temp.setImageIndex(0);
							}
						}
						if(temp.getType()=="bottom") {
							if(mapData.get(i)[2]!=null) {
								temp.setImageIndex(1);
							}else {
								temp.setImageIndex(0);
							}	
						}
						if(temp.getType()=="left") {
							if(mapData.get(i)[3]!=null) {
								temp.setImageIndex(1);
							}else {
								temp.setImageIndex(0);
							}
						}
						if(temp.getType()=="right") {
							if(mapData.get(i)[4]!=null) {
								temp.setImageIndex(1);
							}else {
								temp.setImageIndex(0);
							}
						}
						
						//warps
						if(temp.getType()=="warp_top") {
							if(mapData.get(i)[1]!=null) {
								temp.setWarpRoom(mapData.get(i)[1]);
							}else {
								temp.setWarpRoom(null);
							}
						}
						if(temp.getType()=="warp_bottom") {
							if(mapData.get(i)[2]!=null) {
								temp.setWarpRoom(mapData.get(i)[2]);
							}else {
								temp.setWarpRoom(null);
							}
						}
						if(temp.getType()=="warp_left") {
							if(mapData.get(i)[3]!=null) {
								temp.setWarpRoom(mapData.get(i)[3]);
							}else {
								temp.setWarpRoom(null);
							}
						}
						if(temp.getType()=="warp_right") {
							if(mapData.get(i)[4]!=null) {
								temp.setWarpRoom(mapData.get(i)[4]);
							}else {
								temp.setWarpRoom(null);
							}
						}
						
						
						
					}
				}
			}
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////// Enemy Logic ////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void enemyLogic() {
		for (EnemyObject temp : EnemyList) 
		{ 
			temp.setGotHit(temp.getGotHit()-1);
			temp.changeCurrentTexture("idle");
			
			//movement 
			temp.setHspd(0);
			temp.setVspd(0);
			float hspd = temp.getHspd();
			float vspd = temp.getVspd();
			float moveSpd= 1f;
			
			//movement directions
			if(temp.getMovementDirection()==0) {
				hspd=moveSpd;
				temp.setDirection(0);
				temp.changeCurrentTexture("walk");
			}
			if(temp.getMovementDirection()==1) {
				hspd= (float) Math.sqrt((moveSpd*moveSpd)/2);
				vspd= (float) Math.sqrt((moveSpd*moveSpd)/2);
				temp.setDirection(0);
				temp.changeCurrentTexture("walk");
			}
			if(temp.getMovementDirection()==2) {
				vspd=moveSpd;
				temp.setDirection(0);
				temp.changeCurrentTexture("walk");
			}
			if(temp.getMovementDirection()==3) {
				hspd=-(float) Math.sqrt((moveSpd*moveSpd)/2);
				vspd=(float) Math.sqrt((moveSpd*moveSpd)/2);
				temp.setDirection(1);
				temp.changeCurrentTexture("walk");
			}
			if(temp.getMovementDirection()==4) {
				hspd=-moveSpd;
				temp.setDirection(1);
				temp.changeCurrentTexture("walk");
			}
			if(temp.getMovementDirection()==5) {
				hspd=-(float) Math.sqrt((moveSpd*moveSpd)/2);
				vspd=-(float) Math.sqrt((moveSpd*moveSpd)/2);
				temp.setDirection(1);
				temp.changeCurrentTexture("walk");
			}
			if(temp.getMovementDirection()==6) {
				vspd=-moveSpd;
				temp.setDirection(1);
				temp.changeCurrentTexture("walk");
			}
			if(temp.getMovementDirection()==7) {
				hspd=(float) Math.sqrt((moveSpd*moveSpd)/2);
				vspd=-(float) Math.sqrt((moveSpd*moveSpd)/2);
				temp.setDirection(0);
				temp.changeCurrentTexture("walk");
			}
			if(temp.getMovementDirection()==8 || temp.getMovementDirection()==9 || temp.getMovementDirection()==10 || temp.getMovementDirection()==11 || temp.getMovementDirection()==12) {
				//stand still
				hspd=0;
				vspd=0;
			}
			
			//collision 
			float x = temp.getCentre().getX()+27;
			float y = temp.getCentre().getY()+24;
			float Pw = 42;
			float Ph = 62;
			float next_x = x +hspd;
			float next_y = y -vspd;

			boolean topCollision = false;
			boolean leftCollision = false;
			boolean rightCollision = false;
			boolean bottomCollision = false;
			
			//collision
			for (BlockObject tempB : BlockList) 
			{
				float tx = tempB.getCentre().getX();
				float ty = tempB.getCentre().getY();
				float th = tempB.getHeight();
				float tw = tempB.getWidth();
				
				if(vspd>0 
				&& (((x > tx && x < tx+tw) 
				&& (next_y > ty && next_y < ty+th))
				|| ((x+Pw > tx && x+Pw < tx+tw) 
				&& (next_y > ty && next_y < ty+th)))) {
					topCollision=true;
				}
				
				if(hspd<0 
				&& (((next_x > tx && next_x < tx+tw) 
				&& (y > ty && y < ty+th)) 
				|| ((next_x > tx && next_x < tx+tw) 
				&& (y+(Ph/2) > ty && y+(Ph/2) < ty+th)) 
				|| ((next_x > tx && next_x < tx+tw) 
				&& (y+Ph > ty && y+Ph < ty+th)))){
					leftCollision=true;
				}
				
				if(hspd>0 
				&& (((next_x+Pw > tx && next_x+Pw < tx+tw) 
				&& (y > ty && y < ty+th)) 
				|| ((next_x+Pw > tx && next_x+Pw < tx+tw) 
				&& (y+(Ph/2) > ty && y+(Ph/2) < ty+th))
				|| ((next_x+Pw > tx && next_x+Pw < tx+tw) 
				&& (y+Ph > ty && y+Ph < ty+th)))){
					rightCollision=true;
				}
				
				if(vspd<0 
				&& (((x > tx && x < tx+tw) 
				&& (next_y+Ph > ty && next_y+Ph < ty+th))
				|| ((x+Pw > tx && x+Pw < tx+tw) 
				&& (next_y+Ph > ty && next_y+Ph < ty+th)))) {
					bottomCollision=true;
				}
				
			}
			
			if(leftCollision && hspd<0){hspd=0;}  
			if(rightCollision && hspd>0){hspd=0;}  
			if(topCollision && vspd>0){vspd=0;}  
			if(bottomCollision && vspd<0){vspd=0;}  
			
			
			//move
			if(hspd!=0)temp.getCentre().ApplyVector( new Vector3f(hspd,0,0));
			if(vspd!=0)temp.getCentre().ApplyVector( new Vector3f(0,vspd,0));
			
			
			//movement change direction
			temp.setMoveTimer(temp.getMoveTimer()+1);
			if(temp.getMoveTimer() > temp.getMoveTimerMax()) {
				
				//shoot
				if(startGame && !gameOver) {
					float xb = temp.getCentre().getX();
					float yb = temp.getCentre().getY();
					float dir = (int) ((Math.random() * (3.99 - 0)) + 0);
					float dir2 = (int) ((Math.random() * (3.99 - 0)) + 0);
					
					String spr = "res/magicball.png";
					createBullet(xb, yb, dir, spr, "enemy",4+level-1,level);
					if(soundOn)playSoundEffect(5);
					if(temp.getType()=="boss") {
						String spr2 = "res/magicball2.png";
						createBullet(xb, yb, dir2, spr2, "enemy",2+level-1,level);
					}
				}
				//move
				temp.setMovementDirection((int) ((Math.random() * (8 - 1)) + 1));
				temp.setMoveTimerMax((int) ((Math.random() * (80 - 30)) + 30));
				temp.setMoveTimer(0);
			}
		}
	}
	
	public CopyOnWriteArrayList<BlockObject> getBlocks() {
		return BlockList;
	}

	public CopyOnWriteArrayList<BulletObject> getBullets() {
		return BulletsList;
	}
	
	public CopyOnWriteArrayList<ButtonObject> getButtons() {
		return ButtonList;
	}
	
	public CopyOnWriteArrayList<EnemyObject> getEnemies() {
		return EnemyList;
	}
	
	public int getScore() { 
		return Score;
	}
	
	public void setCoop(boolean is) {
		coop = is;
	}
	
	public boolean getCoop() {
		return coop;
	}
	
	public void spawnExit() {
		BlockList.add(new BlockObject("res/exit.png",96,96,new Point3f(480-48,480-48,0),32,32,false,0,"exit",null));
		spawnItem(480-50,300);
	}
	
	//items
	private boolean itemExist=false;
	public void spawnItem(int x,int y) {
		itemExist=true;
		int max = 3;
		int min = -1;
		int items = (int) ((Math.random() * (max - min)) + min);
		if(items>2)items=2;
		if(items<0)items=0;
		BlockList.add(new BlockObject("res/items.png",45,45,new Point3f(x,y,0),15,15,false,items,"item",null));
	}
	
	
	private void itemGet(BlockObject item, PlayerObject player) {
		if(soundOn)playSoundEffect(2);
		itemExist=false;
		player.addItem(item.getImageIndex());
		BlockList.add(new BlockObject("res/statUps.png",300,36,new Point3f(player.getCentre().getX()-20,player.getCentre().getY(),0),100,12,false,item.getImageIndex(),"stat",""));
		BlockList.remove(item);
	}
	
	public boolean getItemExist() {
		return itemExist;
	}
	
	public void setItemExist(boolean is) {
		itemExist=is;
	}
	
	public void proceedToNextLevel(BlockObject bl) {
		if(soundOn)playSoundEffect(2);
		Score+=1000;
		level+=1;
		for (PlayerObject Player : PlayerList) 
		{
			Player.setCentre(new Point3f(430,450,0));
		}
		
		//revive player
		for (PlayerObject Player : PlayerList) 
		{
			if(Player.getHp()<=0) {
				Player.setHp(1);
			}
		}
		
		//this holds all the data for each room in the map
		mapData  = new ArrayList<String[]>();
		mapArray = new String[11][11];
		currentRoom = "Start";
		
		//generate map
		mapArray = createMap();
		mapData = addRoomsToList(mapArray);
		
		//generate room
		String roomToLoad = "res/room1_data.txt";
		ArrayList<String[]> roomArray = readRoomData(roomToLoad);
		createRoom(roomArray,"0");
		
		//print room for debugging
		for(int i=0;i<11;i++) {
			for(int j=0;j<11;j++) {
				if(mapArray[i][j]==null) {
					System.out.print(" 0 ");
				}else 
				if(mapArray[i][j]=="Start") {
					System.out.print(" S ");
				}else 
				if(mapArray[i][j]=="max") {
					System.out.print(" M ");
				}else 
				if(mapArray[i][j]=="chest") {
					System.out.print(" C ");
				}else
				if(Integer.parseInt(mapArray[i][j])>9) {
					System.out.print(" "+mapArray[i][j]);
				}else
				{
					System.out.print(" "+mapArray[i][j]+" ");
				}
			}
			System.out.println("");
		}
		System.out.println("");
		
		BlockList.remove(bl);
	}
	
	public boolean getStartGame() {
		return startGame;
	}
	public void setStartGame(boolean is) {
		startGame=is;
	}
	
	public boolean getGameOver() {
		return gameOver;
	}
	public void setGameOver(boolean is) {
		gameOver=is;
	}
	
	public int getLevel() {
		return level;
	}
	
	//sound
	public void playMusic(int i) {
		music.setFile(i);
		music.play();
		music.loop();
	}
	public void stopMusic() {
		music.stop();
	}
	public void playSoundEffect(int i) {
		sound.setFile(i);
		sound.play();
	}
	public void soundToggle() {
		if(soundOn==true) {
			stopMusic();
			soundOn=false;
		}else if(soundOn==false){
			
			playMusic(1);
			soundOn=true;
		}
	}
	public boolean getSoundOn() {
		return soundOn;
	}
}

