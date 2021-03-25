package com.qxbytes.sound;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
/**
 * 
 * @author QxBytes
 *
 */
public class NoteDispatchThread implements Runnable{
	JButton button;
	JButton hqbutton;
	boolean running = true;
	int mildelay = 300;
	long relativestarttime;
	//loading
	long currentposition;
	//playing
	int currentnote = 0;
	int start = 0;
	int caught = 0;
	//current prototype: obtain all note dispatch times relative to ONE point in the near future. Point = ~+300ms
	Thread t;
	Track track;
	JLabel l;
	List<Long> queue = new ArrayList<Long>();
	/**
	 * 
	 * -Obtains all note dispatch times relative to the current time 300 mills in the future
	 * -Funnels them into a queue (list of longs) for reference in the update loop
	 * 
	 * @param track
	 */
	public NoteDispatchThread(Track track, JButton button,JLabel label, JButton hqbutton, int start) {
		if (Queue.isPlaying()) {
			return;
		}
		this.start = start;
		Queue.setPlaying(true);
		this.button = button;
		this.hqbutton = hqbutton;
		this.track = track;
		l = label;
		button.setEnabled(false);
		t = new Thread(this);

		relativestarttime = System.currentTimeMillis()+mildelay;
		currentposition = relativestarttime;
		for (int q = 0 ; q < track.loop ; q++) {
			for (int i = start ; i < track.getNotes().size() ; i++) {
				queue.add(currentposition);
				currentposition += track.getNotes().get(i).calculateApproximateDuration();
			}
			System.out.println("Again!");
		}
		System.out.println(queue);

		t.start();
	}
	public synchronized void stop() {
		running = false;
	}
	public void update() {
		try {
			if (Queue.isStop()) {
				running = false;
				return;
			}
			if (System.currentTimeMillis()-1 >= queue.get(currentnote)) {
				new NoteThread(track.getNotes().get((currentnote)%(track.getNotes().size()-start)+start)).start();
				
				currentnote++;
			}
		} catch (Exception e) {
			System.out.println("Error" + e.getMessage());
			caught++;
			if (caught >= track.loop ) {
				running = false;
			}
		}
	}
	@Override
	public void run() {
		long NS_PER_UPDATE = 1000000000/60L;
		long previous = System.nanoTime();
		int lag = 0;

		int updates = 0;
		int renders = 0;

		long lastSecond = System.currentTimeMillis();

		while (running) {
			long current = System.nanoTime();
			long elapsed = current - previous;
			previous = current;
			lag += elapsed;

			while (lag >= NS_PER_UPDATE) {
				updates++;
				//60 fps goodness
				l.setText("Status: Time: " + (System.currentTimeMillis()-relativestarttime) + "|Note: " + (currentnote+start));
				lag -= NS_PER_UPDATE;
			}
			update();
			if (System.currentTimeMillis() - lastSecond >= 1000) {
				//System.out.println("ups: " + updates + ", fps: " + renders);
				//System.out.println("ups: " + updates + ", fps: " + renders);
				updates = 0;
				renders = 0;
				lastSecond += 1000;
			}
		}
		Queue.setStop(false);
		Queue.setPlaying(false);
		button.setEnabled(true);
		hqbutton.setEnabled(true);
		System.out.println(updates + "|"+renders);
	}
}
