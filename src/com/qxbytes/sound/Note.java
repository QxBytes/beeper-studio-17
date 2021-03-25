package com.qxbytes.sound;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
/**
 * 
 * @author QxBytes
 *
 */
public class Note {
	public static final int FADE_TIME = 2000;
	private int volume;//sets the current volume
	private int meta = 0;//amount of half steps from C(0) - derived from 'note' variable
	private int in = 0;//value of note in 32ndth notes
	private int note = -1;//selected index of combo box (whole note, etc.)
	private int length = -1;//amount of time note will actually play before being replaced with blank sound
	private double pitch = -1;//hertz of note
	private boolean metronome = false;//whether a 'click' will be heard like a metronome before pausing
	private boolean beep = true;//if the note is a beep or clip
	private File clip = null;//clip, if any, of the sound
	//Note TO FILE: NOTE(y/n) , NOTE VALUE , NOTE HERTZ VALUE , HALF STEPS FROM C(0) , METRONOME , VOLUME, CLIP
	//EX: true:0:440.95:32:false:null
	//	public Note(int milliseconds, double pitch, boolean metronome, int meta) {
	//		note = milliseconds;
	//		length = 100;
	//		this.pitch = pitch;
	//		this.metronome = metronome;
	//		this.meta = meta;
	//		switch (note) {
	//		case 0:
	//			in = 32;
	//			break;
	//		case 1:
	//			in = 16;
	//			break;
	//		case 2:
	//			in = 8;
	//			break;
	//		case 3:
	//			in = 4;
	//			break;
	//		case 4:
	//			in = 2;
	//			break;
	//		case 5:
	//			in = 1;
	//			break;
	//		}
	//	}
	//Note TO FILE: NOTE(y/n) , NOTE VALUE , NOTE HERTZ VALUE , HALF STEPS FROM C(0) , METRONOME , VOLUME, CLIP
	public Note(String raw) {
		this.beep = Boolean.parseBoolean(Utils.parseValue(raw, ':', 0));
		this.note = Integer.parseInt(Utils.parseValue(raw, ':', 1));
		this.pitch = Double.parseDouble(Utils.parseValue(raw, ':', 2));
		this.meta = Integer.parseInt(Utils.parseValue(raw, ':', 3));
		this.metronome = Boolean.parseBoolean(Utils.parseValue(raw, ':', 4));
		this.volume = Integer.parseInt(Utils.parseValue(raw, ':', 5));
		this.clip = new File(Utils.parseValue(raw, ':', 6));
		switch (note) {
		case 0:
			in = 32;
			break;
		case 1:
			in = 16;
			break;
		case 2:
			in = 8;
			break;
		case 3:
			in = 4;
			break;
		case 4:
			in = 2;
			break;
		case 5:
			in = 1;
			break;
		case 6:
			in = 48;
			break;
		case 7:
			in = 24;
			break;
		case 8:
			in = 12;
			break;
		case 9:
			in = 6;
			break;
		case 10:
			in = 3;
			break;
		}
	}
	public Note(int milliseconds, double pitch, int meta, int volume) {
		note = milliseconds;
		this.volume = volume;
		this.pitch = pitch;
		this.meta = meta;
		switch (note) {
		case 0:
			in = 32;
			break;
		case 1:
			in = 16;
			break;
		case 2:
			in = 8;
			break;
		case 3:
			in = 4;
			break;
		case 4:
			in = 2;
			break;
		case 5:
			in = 1;
			break;
		case 6:
			in = 48;
			break;
		case 7:
			in = 24;
			break;
		case 8:
			in = 12;
			break;
		case 9:
			in = 6;
			break;
		case 10:
			in = 3;
			break;
		}
	}
	public Note(File clip) {
		this.clip = clip;
	}
	//	public SourceDataLine loadToLine(SourceDataLine sdlx) throws LineUnavailableException {
	//		SourceDataLine sdl = sdlx;
	//		int mills = 0;
	//		switch (note) {
	//		case 0:
	//			mills = Tempo.WHOLENOTE();
	//			in = 32;
	//			break;
	//		case 1:
	//			mills = Tempo.HALFNOTE();
	//			in = 16;
	//			break;
	//		case 2:
	//			mills = Tempo.QUARTERNOTE();
	//			in = 8;
	//			break;
	//		case 3:
	//			mills = Tempo.EIGTHNOTE();
	//			in = 4;
	//			break;
	//		case 4:
	//			mills = Tempo.SIXTEENTHNOTE();
	//			in = 2;
	//			break;
	//		case 5:
	//			mills = Tempo.THIRTYSECONDTHNOTE();
	//			in = 1;
	//			break;
	//		default:
	//			mills = Tempo.calculate(in);
	//			break;
	//		}
	//		if (metronome == false) {
	//			length = mills;
	//		}
	//		byte[] buf = new byte[ 1 ];
	//		for( int i = 0; i < mills * (float )44100 / 1000; i++ ) {
	//
	//			double angle = i / ( (float )44100 / 440 ) * 2.0 * pitch;
	//			buf[ 0 ] = (byte )( Math.sin( angle ) * 100 );
	//
	//			if (metronome == true && i > length * (float) 44100 / 1000) {
	//				double angle1 = i / ( (float )44100 / 440 ) * 2.0 * 0;
	//				buf[ 0 ] = (byte )( Math.sin( angle1 ) * 100 );
	//				sdl.write(buf, 0, 1);
	//				continue;
	//			}
	//
	//			if (i > mills * (float) 44100/ 1000 ) {
	//				double angle1 = i / ( (float )44100 / 440 ) * 2.0 * 0;
	//				buf[ 0 ] = (byte )( Math.sin( angle1 ) * 100 );
	//				sdl.write(buf, 0, 1);
	//				sdl.write(buf, 0, 1);
	//				break;
	//			}
	//			sdl.write( buf, 0, 1 );
	//
	//		}
	//		return sdl;
	//	}
	//	@Deprecated
	//	public void play() throws LineUnavailableException {
	//		int mills = 0;
	//		switch (note) {
	//		case 0:
	//			mills = Tempo.WHOLENOTE();
	//			break;
	//		case 1:
	//			mills = Tempo.HALFNOTE();
	//			break;
	//		case 2:
	//			mills = Tempo.QUARTERNOTE();
	//			break;
	//		case 3:
	//			mills = Tempo.EIGTHNOTE();
	//			break;
	//		case 4:
	//			mills = Tempo.SIXTEENTHNOTE();
	//			break;
	//		case 5:
	//			mills = Tempo.THIRTYSECONDTHNOTE();
	//			break;
	//		default:
	//			mills = note;
	//			break;
	//		}
	//		if (metronome == false) {
	//			length = mills;
	//		}
	//		byte[] buf = new byte[ 1 ];
	//		AudioFormat af = new AudioFormat( (float )44100, 8, 1, true, false );
	//		SourceDataLine sdl = AudioSystem.getSourceDataLine( af );
	//		sdl.open();
	//		sdl.start();
	//		for( int i = 0; i < mills * (float )44100 / 1000; i++ ) {
	//			
	//
	//			double angle = i / ( (float )44100 / 440 ) * 2.0 * pitch;
	//			buf[ 0 ] = (byte )( Math.sin( angle ) * 100 );
	//
	//			if (metronome == true && i > length * (float) 44100 / 1000) {
	//				double angle1 = i / ( (float )44100 / 440 ) * 2.0 * 0;
	//				buf[ 0 ] = (byte )( Math.sin( angle1 ) * 100 );
	//				sdl.write(buf, 0, 1);
	//				continue;
	//			}
	//
	//			if (i > mills * (float) 44100/ 1000 ) {
	//				double angle1 = i / ( (float )44100 / 440 ) * 2.0 * 0;
	//				buf[ 0 ] = (byte )( Math.sin( angle1 ) * 100 );
	//				sdl.write(buf, 0, 1);
	//				sdl.write(buf, 0, 1);
	//				break;
	//			}
	//			sdl.write( buf, 0, 1 );
	//
	//		}
	//		sdl.drain();
	//		sdl.stop();
	//		sdl.close();
	//	}
	/** Generates a tone.
	  @param hz Base frequency (neglecting harmonic) of the tone in cycles per second
	  @param msecs The number of milliseconds to play the tone.
	  @param volume Volume, form 0 (mute) to 100 (max).
	  @param addHarmonic Whether to add an harmonic, one octave up. */
	public void generateTone()
			throws LineUnavailableException {
		if (clip != null && !clip.getName().equals("null") && !clip.getName().equals("")) {
			try {
				AudioInputStream ais = AudioSystem.getAudioInputStream(clip);
				Clip c = AudioSystem.getClip();
				c.open(ais);
				c.setFramePosition(0);
				c.start();
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return;
		}
		double hz = pitch;
		int mills = 0;

		switch (note) {
		case 0:
			mills = Tempo.WHOLENOTE();
			break;
		case 1:
			mills = Tempo.HALFNOTE();
			break;
		case 2:
			mills = Tempo.QUARTERNOTE();
			break;
		case 3:
			mills = Tempo.EIGTHNOTE();
			break;
		case 4:
			mills = Tempo.SIXTEENTHNOTE();
			break;
		case 5:
			mills = Tempo.THIRTYSECONDTHNOTE();
			break;
		default:
			mills = Tempo.calculate(in);
			break;
		}
		//buffer offset
		length = mills-20;
		int msecs = length;
		float frequency = 44100;//Standard Frequency 44khz

		byte[] buf;
		AudioFormat af;
		buf = new byte[1];
		af = new AudioFormat(frequency,8,1,true,false);
		SourceDataLine sdl = AudioSystem.getSourceDataLine(af);
		sdl = AudioSystem.getSourceDataLine(af);
		sdl.open(af);
		sdl.start();
		float totalTime = msecs*frequency/1000;
		float fadeTime = FADE_TIME;
		for(int i=0; i<msecs*frequency/1000; i++){//"Draw" the sound wave here
			//System.out.println("Thru");

			double angle = i/(frequency/hz)*2.0*Math.PI;//Simplified: i / k (where k depends on the note)
			float adj = 1.0f;
			if (i > totalTime - fadeTime) {
				adj = ((totalTime-i)/fadeTime);
			}
			if (i < fadeTime) {
				adj = (i/fadeTime);
			}
			if (metronome == true && i > 8000) {
				buf[0]=(byte)(Math.sin(angle)*0);
			} else {
				buf[0]=(byte)(Math.sin(angle)*volume*adj);//"Draw" one "slice" of the sound wave here
			}

			sdl.write(buf,0,1);

		}
		sdl.drain();
		sdl.stop();
		sdl.close();
	}
	/**
	 * 
	 * @param sdl
	 * @return previous note's angle (where you left off)-doesn't really work...
	 */
	public double loadPlay(SourceDataLine sdl, double start) {
		if (clip != null && !clip.getName().equals("null") && !clip.getName().equals("")) {
			return 0;
		}
		double hz = pitch;
		int mills = 0;

		switch (note) {
		case 0:
			mills = Tempo.WHOLENOTE();
			break;
		case 1:
			mills = Tempo.HALFNOTE();
			break;
		case 2:
			mills = Tempo.QUARTERNOTE();
			break;
		case 3:
			mills = Tempo.EIGTHNOTE();
			break;
		case 4:
			mills = Tempo.SIXTEENTHNOTE();
			break;
		case 5:
			mills = Tempo.THIRTYSECONDTHNOTE();
			break;
		default:
			mills = Tempo.calculate(in);
			break;
		}
		//DO NOT INCLUDE OFFSET
		length = mills;
		int msecs = length;
		float frequency = 44100;

		byte[] buf;
		//AudioFormat af;
		buf = new byte[1];
		//af = new AudioFormat(frequency,8,1,true,false);
		double angle = 0;
		float totalTime = msecs*frequency/1000;
		float fadeTime = FADE_TIME;
		for(int i=0; i < totalTime; i++){
			//start is offset
			angle = i/(frequency/hz)*2.0*Math.PI+start;
			float adj = 1.0f;
			if (i > totalTime - fadeTime) {
				adj = ((totalTime-i)/fadeTime);
			}
			if (i < fadeTime) {
				adj = (i/fadeTime);
			}
			if (metronome == true && i > 8000) {
				buf[0]=(byte)(Math.sin(angle)*0);
			} else {
				buf[0]=(byte)(Math.sin(angle)*volume*adj);
			}

			sdl.write(buf,0,1);

		}

		return angle;
	}
	public int calculateApproximateDuration() {
		int mills = 0;

		switch (note) {
		case 0:
			mills = Tempo.WHOLENOTE();
			break;
		case 1:
			mills = Tempo.HALFNOTE();
			break;
		case 2:
			mills = Tempo.QUARTERNOTE();
			break;
		case 3:
			mills = Tempo.EIGTHNOTE();
			break;
		case 4:
			mills = Tempo.SIXTEENTHNOTE();
			break;
		case 5:
			mills = Tempo.THIRTYSECONDTHNOTE();
			break;
		default:
			mills = Tempo.calculate(in);
			break;
		}
		System.out.println(mills);
		return mills;
	}
	public int getMeta() {
		return meta;
	}
	public void setMeta(int meta) {
		this.meta = meta;
	}
	public int getNote() {
		return note;
	}
	public void setNote(int note) {
		this.note = note;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public double getPitch() {
		return pitch;
	}
	public void setPitch(double pitch) {
		this.pitch = pitch;
	}
	public boolean isBeep() {
		return beep;
	}
	public void setBeep(boolean beep) {
		this.beep = beep;
	}
	public File getClip() {
		return clip;
	}
	public void setClip(File clip) {
		this.clip = clip;
	}
	public int getIn() {
		return in;
	}
	public void setIn(int in) {
		this.in = in;
	}
	public boolean isMetronome() {
		return metronome;
	}
	public void setMetronome(boolean metronome) {
		if (metronome) {
			length = 100;
		}
		this.metronome = metronome;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}
	//Note TO FILE: NOTE(y/n) , NOTE VALUE , NOTE HERTZ VALUE , HALF STEPS FROM C(0) , METRONOME , VOLUME, CLIP
	public String toString() {
		return beep + ":" + note + ":" + pitch + ":" + meta + ":" + metronome + ":" + volume + ":" + clip;
	}
	public String displayString() {
		return toString();
	}
}
