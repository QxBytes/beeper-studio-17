package com.qxbytes.sound;

import javax.sound.sampled.LineUnavailableException;

public class Test {

	public static void main(String[] args) throws LineUnavailableException {
		System.out.println(NoteMath.calculateNote("C", "4", ""));
	}

}
