/*
 * Project by Cameron Carton
 * Student ID: 19720959
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;

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

//Singeton pattern
public class Controller implements KeyListener {
        
	   private static boolean KeyAPressed= false;
	   private static boolean KeySPressed= false;
	   private static boolean KeyDPressed= false;
	   private static boolean KeyWPressed= false;
	   private static boolean KeyCPressed= false;
	   private static boolean KeySpacePressed= false;
	   
	   private static boolean KeyJPressed= false;
	   private static boolean KeyKPressed= false;
	   private static boolean KeyLPressed= false;
	   private static boolean KeyIPressed= false;
	   private static boolean KeyDotPressed= false;
	   
	   
	   
	 public Controller() { 
	}
	 
	   
	@Override
	// Key pressed , will keep triggering 
	public void keyTyped(KeyEvent e) { 
		 
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{ 
			switch (e.getKeyChar()) 
			{
				case 'a':setKeyAPressed(true);break;  
				case 's':setKeySPressed(true);break;
				case 'w' :setKeyWPressed(true);break;
				case 'd':setKeyDPressed(true);break;
				case 'c':setKeyCPressed(true);break;   
			    case 'j':setKeyJPressed(true);break;  
				case 'k':setKeyKPressed(true);break;
				case 'i' :setKeyIPressed(true);break;
				case 'l':setKeyLPressed(true);break;
				case '.':setKeyDotPressed(true);break; 
				case ' ':setKeySpacePressed(true);break; 
			    default:
			    	//System.out.println("Controller test:  Unknown key pressed");
			        break;
			}  
	 // You can implement to keep moving while pressing the key here . 
		
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{ 
			switch (e.getKeyChar()) 
			{
				case 'a':setKeyAPressed(false);break;  
				case 's':setKeySPressed(false);break;
				case 'w':setKeyWPressed(false);break;
				case 'd':setKeyDPressed(false);break;
				case 'c':setKeyCPressed(false);break;   
			    case 'j':setKeyJPressed(false);break;  
				case 'k':setKeyKPressed(false);break;
				case 'i':setKeyIPressed(false);break;
				case 'l':setKeyLPressed(false);break;
				case '.':setKeyDotPressed(false);break;  
				case ' ':setKeySpacePressed(false);break;  
			    default:
			    	//System.out.println("Controller test:  Unknown key pressed");
			        break;
			}  
		 //upper case 
	
	}


	public boolean isKeyAPressed() {
		return KeyAPressed;
	}


	public void setKeyAPressed(boolean keyAPressed) {
		KeyAPressed = keyAPressed;
	}


	public boolean isKeySPressed() {
		return KeySPressed;
	}


	public void setKeySPressed(boolean keySPressed) {
		KeySPressed = keySPressed;
	}


	public boolean isKeyDPressed() {
		return KeyDPressed;
	}


	public void setKeyDPressed(boolean keyDPressed) {
		KeyDPressed = keyDPressed;
	}


	public boolean isKeyWPressed() {
		return KeyWPressed;
	}


	public void setKeyWPressed(boolean keyWPressed) {
		KeyWPressed = keyWPressed;
	}


	public boolean isKeyCPressed() {
		return KeyCPressed;
	}


	public void setKeyCPressed(boolean keyCPressed) {
		KeyCPressed = keyCPressed;
	} 
	
	public boolean isKeySpacePressed() {
		return KeySpacePressed;
	}


	public void setKeySpacePressed(boolean keySpacePressed) {
		KeySpacePressed = keySpacePressed;
	} 
	
	public boolean isKeyJPressed() {
		return KeyJPressed;
	}


	public void setKeyJPressed(boolean keyJPressed) {
		KeyJPressed = keyJPressed;
	}


	public boolean isKeyKPressed() {
		return KeyKPressed;
	}


	public void setKeyKPressed(boolean keyKPressed) {
		KeyKPressed = keyKPressed;
	}


	public boolean isKeyLPressed() {
		return KeyLPressed;
	}


	public void setKeyLPressed(boolean keyLPressed) {
		KeyLPressed = keyLPressed;
	}


	public boolean isKeyIPressed() {
		return KeyIPressed;
	}


	public void setKeyIPressed(boolean keyIPressed) {
		KeyIPressed = keyIPressed;
	}


	public boolean isKeyDotPressed() {
		return KeyDotPressed;
	}


	public void setKeyDotPressed(boolean keyDotPressed) {
		KeyDotPressed = keyDotPressed;
	} 
}

/*
 * 
 * KEYBOARD :-) . can you add a mouse or a gamepad 

 *@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ @@@@@@@@@@@@@@@

  @@@     @@@@    @@@@    @@@@    @@@@     @@@     @@@     @@@     @@@     @@@  

  @@@     @@@     @@@     @@@@     @@@     @@@     @@@     @@@     @@@     @@@  

  @@@     @@@     @@@     @@@@    @@@@     @@@     @@@     @@@     @@@     @@@  

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

@     @@@     @@@     @@@      @@      @@@     @@@     @@@     @@@     @@@     @

@     @@@   W   @@@     @@@      @@      @@@     @@@     @@@     @@@     @@@     @

@@    @@@@     @@@@    @@@@    @@@@    @@@@     @@@     @@@     @@@     @@@     @

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@N@@@@@@@@@@@@@@@@@@@@@@@@@@@

@@@     @@@      @@      @@      @@      @@@     @@@     @@@     @@@     @@@    

@@@   A   @@@  S     @@  D     @@      @@@     @@@     @@@     @@@     @@@     @@@    

@@@@ @  @@@@@@@@@@@@ @@@@@@@    @@@@@@@@@@@@    @@@@@@@@@@@@     @@@@   @@@@@   

    @@@     @@@@    @@@@    @@@@    $@@@     @@@     @@@     @@@     @@@     @@@

    @@@ $   @@@      @@      @@ /Q   @@ ]M   @@@     @@@     @@@     @@@     @@@

    @@@     @@@      @@      @@      @@      @@@     @@@     @@@     @@@     @@@

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

@       @@@                                                @@@       @@@       @

@       @@@              SPACE KEY       @@@        @@ PQ     

@       @@@                                                @@@        @@        

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
 * 
 * 
 * 
 * 
 * 
 */
