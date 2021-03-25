package com.qxbytes.sound;
/**
 * 
 * @author QxBytes
 *
 */
public class Tempo {
	static int bpm = 60;
	static double measure = 4.0;
	static double beat = 4.0;
	//(int)((60.0/bpm*1000)*4.0)
	public static int WHOLENOTE() {
		return (int)((60.0/bpm*1000)*(beat/1));
	}
	public static int HALFNOTE() {
		return (int)((60.0/bpm*1000)*(beat/2));
	}
	public static int QUARTERNOTE() {
		return (int)((60.0/bpm*1000)*(beat/4));
	}
	public static int EIGTHNOTE() {
		return (int)((60.0/bpm*1000)*(beat/8));
	}
	public static int SIXTEENTHNOTE() {
		return (int)((60.0/bpm*1000)*(beat/16));
	}
	public static int THIRTYSECONDTHNOTE() {
		return (int)((60.0/bpm*1000)*(beat/32));
	}
	public static int getBpm() {
		return bpm;
	}
	public static int calculate(int note) {//note = # of 32ndth notes
		return (int) ((60.0/bpm*1000)*(note/8.0));
	}
	public static void setBpm(int bpm) {
		Tempo.bpm = bpm;
	}
	public static double getMeasure() {
		return measure;
	}
	public static void setMeasure(double measure) {
		Tempo.measure = measure;
	}
	public static double getBeat() {
		return beat;
	}
	public static void setBeat(double beat) {
		Tempo.beat = beat;
	}
	
}
