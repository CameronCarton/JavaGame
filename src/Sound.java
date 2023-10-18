/*
 * Project by Cameron Carton
 * Student ID: 19720959
 */

import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	
	//sound system and implementation from YOUTUBE VIDEO: Sound - How to Make a 2D Game in Java #9
	//https://www.youtube.com/watch?v=nUHh_J2Acy8&ab_channel=RyiSnow
	Clip clip;
	URL soundURL[] = new URL[30];
	
	public Sound() {
		soundURL[0] = getClass().getResource("/Sounds/fireballShoot.wav");
		soundURL[1] = getClass().getResource("/Sounds/templeMusic2.wav");
		soundURL[2] = getClass().getResource("/Sounds/powerUp.wav");
		soundURL[3] = getClass().getResource("/Sounds/hurt.wav");
		soundURL[4] = getClass().getResource("/Sounds/aah.wav");
		soundURL[5] = getClass().getResource("/Sounds/magicPlop.wav");
	}
	
	public void setFile(int i) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
			clip = AudioSystem.getClip();
			clip.open(ais);
		}catch(Exception e) {
			
		}
	}
	
	public void play() {
		clip.start();
	}
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void stop() {
		clip.stop();
	}
}
