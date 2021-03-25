package com.qxbytes.sound;
/**
 * 
 * @author QxBytes
 *
 */
public class TrackThread extends Thread {
	Track t;
	public TrackThread(Track t) {
		this.t = t;
	}
	public void run() {
		//t.play();
	}
}
