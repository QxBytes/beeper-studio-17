package com.qxbytes.sound;

import javax.sound.sampled.LineUnavailableException;
/**
 * 
 * @author QxBytes
 *
 */
public class NoteThread extends Thread {
	Note n;
	public NoteThread(Note n) {
		this.n = n;
	}
	public void run() {
		try {
			n.generateTone();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
}
