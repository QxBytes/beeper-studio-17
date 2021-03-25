package com.qxbytes.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JButton;
/**
 * 
 * @author QxBytes
 *
 */
public class LoadedPlay implements Runnable {
	JButton play;
	JButton stop;
	JButton loadplay;
	Track notes;
	Thread t;
	SourceDataLine sdl;
	public LoadedPlay(Track notes, JButton loadplay, JButton play, JButton stop) {
		if (Queue.isPlaying()) {
			return;
		}
		Queue.setPlaying(true);
		this.play = play;
		this.loadplay = loadplay;
		this.stop = stop;
		this.notes = notes;
		
		t = new Thread(this);
		
		AudioFormat af = new AudioFormat(44100,8,1,true,false);
		try {
			sdl = AudioSystem.getSourceDataLine(af);
			sdl = AudioSystem.getSourceDataLine(af);
			sdl.open(af);
			sdl.start();
			//doesn't really work... If something breaks, remove the previousNoteOffset (SEE NOTE Class too!)
			double previousNoteOffset = 0;
			
			for (int i = 0 ; i < notes.getNotes().size() ; i++) {
				if (i == 0) {
					previousNoteOffset = notes.getNotes().get(i).loadPlay(sdl,0);
				}
				notes.getNotes().get(i).loadPlay(sdl,previousNoteOffset);
			}
			
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		t.start();
	}
	@Override
	public void run() {
		play.setEnabled(false);
		stop.setEnabled(false);
		loadplay.setEnabled(false);
		
		sdl.drain();
		sdl.start();
		sdl.close();
		
		// TODO Auto-generated method stub
		play.setEnabled(true);
		stop.setEnabled(true);
		loadplay.setEnabled(true);
		Queue.setPlaying(false);
	}

}
