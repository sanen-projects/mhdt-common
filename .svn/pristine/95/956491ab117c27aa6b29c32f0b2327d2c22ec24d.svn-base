package com.mhdt.test;

import java.io.File;

import javax.annotation.processing.FilerException;

import com.mhdt.Print;
import com.mhdt.media.Mp3Player;

public class Mp3Test {

	public static void main(String[] args) {
		
		Print.start();
		
		Mp3Player m;
		
		try {
			m = new Mp3Player(new File("music.mp3"));
			m.loop();
		} catch (FilerException e) {
			e.printStackTrace();
			System.out.println("music.mp3");
		}
		
		Print.end();
		
	}
}
