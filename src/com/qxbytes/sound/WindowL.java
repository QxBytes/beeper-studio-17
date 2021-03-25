package com.qxbytes.sound;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;
/**
 * 
 * @author QxBytes
 *
 */
public class WindowL implements WindowListener {

	@Override
	public void windowActivated(WindowEvent arg0) {
		 
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		 
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		int x =JOptionPane.showConfirmDialog(null, "Do you really want to quit?");
		if (x == JOptionPane.OK_OPTION) {
			System.exit(0);
		}
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		 
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		 
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		 
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		 
		
	}

}
