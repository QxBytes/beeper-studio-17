package com.qxbytes.sound;
/**
 * 
 * @author QxBytes
 *
 */
public class Queue {
	static boolean stop = false;
	static Track t;
	static boolean playing = false;
	public static boolean isStop() {
		return stop;
	}

	public static void setStop(boolean stop) {
		Queue.stop = stop;
	}
	public static Track refreshTrack() {
		return t;
	}
	public static void addRefreshTrack(Track t) {
		Queue.t = t;
	}
	public static boolean isPlaying() {
		return playing;
	}
	public static void setPlaying(boolean playing) {
		Queue.playing = playing;
	}
}
