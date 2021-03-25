package com.qxbytes.sound;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
/**
 * 
 * @author QxBytes
 *
 */
public class Track {
	List<Note> notes = new ArrayList<Note>();
	int loop = 1;
	boolean stop = false;
	boolean metronome = false;
	public Track (File f, JButton bpm, JCheckBox loop, JCheckBox metronome,JButton time) {
		try {
			List<String> rawdata = Files.readAllLines(f.toPath());

			String header = rawdata.get(0);

			for (int i = 1; i < rawdata.size() ; i++) {
				notes.add(new Note(rawdata.get(i)));
			}

			this.loop = Integer.parseInt(Utils.parseValue(header, ':', 0));
			this.metronome = Boolean.parseBoolean(Utils.parseValue(header, ':', 1));
			try { 
				Tempo.setBpm(Integer.parseInt(Utils.parseValue(header, ':', 2)));
				Tempo.setMeasure(Double.parseDouble(Utils.parseValue(header, ':', 3)));
				Tempo.setBeat(Double.parseDouble(Utils.parseValue(header, ':', 4)));

				bpm.setText("Change Tempo: " + Tempo.getBpm());
				time.setText("Change Time sig:" + Tempo.getMeasure() + "/" + Tempo.getBeat());
			} catch (Exception e) {
				System.out.println("Hmmm... Old file version. Let's update that shall we?");
			}
			if (this.loop > 1) {
				loop.setSelected(true);
			} else {
				loop.setSelected(false);
			}
			metronome.setSelected(this.metronome);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Track (List<Note> notes, boolean loop) {
		this.notes = notes;
	}
	public Track (int loop) {
		this.loop = loop;
	}
	public Track() {

	}
	@Deprecated
	public void play() {
		stop = false;
		for (int q = 0 ; q < loop ; q++) {
			for (int i = 0 ; i < notes.size() ; i++) {
				if (Queue.isStop() == true) {
					stop = true;
					return;
				}
				try {
					notes.get(i).generateTone();
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} 
		stop = true;
	}
	//NOT SUPPORTED ANYMORE
	//	public void loadedPlay() {
	//		AudioFormat af = new AudioFormat( (float )44100, 8, 1, true, false );
	//		try {
	//			SourceDataLine sdl = AudioSystem.getSourceDataLine( af );
	//			sdl.open();
	//			sdl.start();
	//			//Load it up!
	//			for (int q = 0 ; q < loop ; q++) {
	//				for (int i = 0 ; i < notes.size() ; i++) {
	//					sdl = notes.get(i).loadToLine(sdl);
	//				}
	//			} 
	//			try {
	//				Thread.sleep(2);
	//			} catch (InterruptedException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
	//			sdl.drain();
	//			sdl.stop();
	//		} catch (LineUnavailableException e) {
	//			// TODO Auto-generated catch block
	//			e.printStackTrace();
	//		}
	//	}
	public void addNote(Note note) {
		notes.add(note);
		notes.get(notes.size()-1).setMetronome(metronome);
	}
	public void removeNote() {
		if (notes.size() == 0) {
			JOptionPane.showMessageDialog(null, "Error: No notes to remove!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		notes.remove(notes.size()-1);
		JOptionPane.showMessageDialog(null, "Last Note Successfully Removed From Track");
	}
	public Note getLastNote() {
		return notes.get(notes.size()-1);
	}
	public List<Note> getNotes() {
		return notes;
	}
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	public void setLoop(int loop) {
		this.loop = loop;
	}
	public boolean isStop() {
		return stop;
	}
	public void setStop(boolean stop) {
		this.stop = stop;
	}
	public void setMetronome(boolean metronome) {
		this.metronome = metronome;
		for (Note x : notes) {
			x.setMetronome(metronome);
		}
	}
	public int getTotalBeats() {
		int beats = 0;

		for (Note x : notes) {
			beats+=x.getIn();//value 32 (whole) > 1(thirtysecondth)
		}

		return beats;
	}
	public String toString() {
		return loop + ":" + metronome + ":" + Tempo.getBpm() + ":" + Tempo.measure + ":" + Tempo.beat;
	}
}
