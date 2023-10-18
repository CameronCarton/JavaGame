/*
 * Project by Cameron Carton
 * Student ID: 19720959
 */


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import util.UnitTests;

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



public class MainWindow {
	private static  JFrame frame = new JFrame("Game");   // Change to the name of your game 
	private static   Model gameworld= new Model();
	private static   Viewer canvas = new  Viewer( gameworld);
	private KeyListener Controller1 =new Controller(); 
	private static   int TargetFPS = 100;
	
	
	public MainWindow() {
		frame.setSize(975, 975);  // you can customise this later and adapt it to change on size.  
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //If exit // you can modify with your way of quitting , just is a template.
		frame.setLayout(null);
		frame.add(canvas);  
		canvas.setBounds(0, 0, 1000, 1000); 
		canvas.setBackground(new Color(255,255,255)); //white background  replaced by Space background but if you remove the background method this will draw a white screen 
		frame.setVisible(true); 
		frame.setResizable(false);
		canvas.addKeyListener(Controller1);    //adding the controller to the Canvas 
		canvas.requestFocusInWindow();   // making sure that the Canvas is in focus so keyboard input will be taking in .

	}

	public static void main(String[] args) {
		MainWindow hello = new MainWindow();  //sets up environment 

		//fps
		double TimeBetweenFrames =  1000000000 / TargetFPS;
		double FrameCheck = System.nanoTime() + TimeBetweenFrames; 
		
		
		while(true)   //not nice but remember we do just want to keep looping till the end.  // this could be replaced by a thread but again we want to keep things simple 
		{ 
			gameloop();
			
			//wait till next time step 
			//while (FrameCheck > System.currentTimeMillis()){} 

			try {
				//pause time for frames
				double remainingTime = (FrameCheck - System.nanoTime())/1000000;
				if(remainingTime < 0) {
					remainingTime=0;
				}
				Thread.sleep((long) remainingTime);
				FrameCheck+=TimeBetweenFrames;

			}catch(InterruptedException e) {

			}
			//UNIT test to see if framerate matches 
			//UnitTests.CheckFrameRate(System.currentTimeMillis(),FrameCheck, TargetFPS); 
		}


	} 
	//Basic Model-View-Controller pattern 
	private static void gameloop() { 
		// GAMELOOP  

		// controller input  will happen on its own thread 
		// So no need to call it explicitly 

		// model update   
		gameworld.gamelogic();
		// view update 

		canvas.updateview(); 

		// Both these calls could be setup as  a thread but we want to simplify the game logic for you.  
		//score update  
		frame.setTitle("MonkeyMages"); 


	}

}
