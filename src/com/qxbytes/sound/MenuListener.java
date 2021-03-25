package com.qxbytes.sound;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
/**
 * 
 * @author QxBytes
 *
 */
public class MenuListener implements ActionListener {
	String display;
	JPanel panel_9;
	JButton bpm;
	JMenuItem refreshButton;
	JCheckBox loop;
	JCheckBox metronome;
	JButton time;
	Track t;
	int mode = 0;
	public MenuListener(String text) {
		display = text;
	}
	public MenuListener(String text, int mode, JPanel panel_9 , Track t, JButton bpm, JCheckBox loop, JCheckBox metronome, JMenuItem refreshButton,JButton time) {
		display = text;
		this.mode = mode;
		this.panel_9 = panel_9;
		this.t = t;
		this.bpm = bpm;
		this.loop = loop;
		this.metronome = metronome;
		this.refreshButton = refreshButton;
		this.time = time;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (mode == 0) {
			if (Desktop.isDesktopSupported()) {
				if (display.equals("Submit a Bug")) {
					try {
						Desktop.getDesktop().browse(new URI("https://goo.gl/2YHpXE"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				} else if (display.equals("View the Tutorial")) {
					try {
						Desktop.getDesktop().browse(new URI("https://goo.gl/gSPjcy"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				} else if (display.equals("Updates")) {
					try {
						Desktop.getDesktop().browse(new URI("https://goo.gl/zsytq2"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				} else if (display.equals("News")) {
					try {
						Desktop.getDesktop().browse(new URI("https://goo.gl/hYPk1j"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return;
				}
			}
			JOptionPane.showMessageDialog(null, display);
			return;
		}
		JFileChooser jfc = new JFileChooser();

		if (mode == 2) {//Open
			int answer = jfc.showOpenDialog(null);
			if (answer != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File filename = jfc.getSelectedFile();

			t = new Track (filename, bpm, loop, metronome,time);


			Queue.addRefreshTrack(t);
			refreshButton.doClick();
		}
		if (mode == 1) {//Save
			int answer = jfc.showSaveDialog(null);
			if (answer != JFileChooser.APPROVE_OPTION) {
				return;
			}
			File filename = jfc.getSelectedFile();

			FileWriter fw;
			try {
				fw = new FileWriter(filename);
				BufferedWriter bw = new BufferedWriter(fw);

				bw.write(t.toString()+ "\n");

				for (int i = 0 ; i < t.getNotes().size() ; i++) {
					bw.write(t.getNotes().get(i).toString());
					if (i+1 < t.getNotes().size()) {
						bw.write("\n");
					}
				}

				bw.flush();
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		GUI.updatePanel(panel_9, t);
	}

}
