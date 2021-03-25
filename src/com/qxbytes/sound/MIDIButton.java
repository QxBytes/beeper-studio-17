package com.qxbytes.sound;

import javax.swing.JButton;
/**
 * 
 * @author QxBytes
 *
 */
public class MIDIButton extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int x;
	int y;
	public MIDIButton(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public int getXX() {
		return x;
	}
	public void setXX(int x) {
		this.x = x;
	}
	public int getYY() {
		return y;
	}
	public void setYY(int y) {
		this.y = y;
	}
	
}
