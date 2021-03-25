package com.qxbytes.sound;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.LineUnavailableException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
/**
 * 
 * @author QxBytes
 *
 */
public class MIDI extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	MIDIButton[][] buttons = new MIDIButton[7][8];
	JScrollPane pane = new JScrollPane();
	JPanel root = new JPanel(new GridLayout(1,2));
	JPanel buttonContainer = new JPanel(new GridLayout(0,1));
	JPanel qbCont = new JPanel(new GridLayout(0,3));
	int note;
	int octave;
	JComboBox<String> one;
	JComboBox<String> two;
	JComboBox<String> three;
	JButton quickPress;
	JButton qP1;
	JButton qP2;
	public MIDI(JComboBox<String> one, JComboBox<String> two, JComboBox<String> three, JButton quickPress,JButton qP1, JButton qP2)	{
		this.one = one;
		this.two = two;
		this.three = three;
		this.quickPress = quickPress;
		this.qP1 = qP1;
		this.qP2 = qP2;
		int count = 0;
		setLayout(new GridLayout(1,2));
		for (int y = 7 ; y >= 0 ; y--) {
			for (int x = 6 ; x >= 0 ; x--) {
				buttons[x][y] = new MIDIButton(x,y);
				
				char q = 'C';
				if (x == 0) {
					q = 'C';
				}
				if (x == 1) {
					q = 'D';
				}
				if (x == 2) {
					q = 'E';
				}
				if (x == 3) {
					q = 'F';
				}
				if (x == 4) {
					q = 'G';
				}
				if (x == 5) {
					q = 'A';
				}
				if (x == 6) {
					q = 'B';
				}
				String print = q + "|" + y;
				if (count > 16 && count < 26 && count % 2 == 1) {
					print = "---" + print;
					print += "---";
				} else if (count % 2 == 1){
					print += "-";
				}
				buttons[x][y].setHorizontalAlignment(SwingConstants.LEFT);
				buttons[x][y].setText(print);
				buttons[x][y].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						for (int y = 7 ; y >= 0 ; y--) {
							for (int x = 6 ; x >= 0 ; x--) {
								if (arg0.getSource() == buttons[x][y]) {
									note = x;
									octave = y;
									
									one.setSelectedIndex((note+2)%7);
									two.setSelectedIndex(octave-1);
									System.out.println(note + ":" + octave + ":" + three.getSelectedItem());
									three.setSelectedIndex(KeySignature.getModifier(one.getSelectedIndex()));
									Note sample = new Note(3,NoteMath.calculateNote(one.getSelectedItem()+"", two.getSelectedItem()+"", three.getSelectedItem()+""),NoteMath.meta(note+"", octave+"", three.getSelectedItem()+""),50);
									System.out.println(sample.getPitch());
									try {
										sample.generateTone();
									} catch (LineUnavailableException e) {
										e.printStackTrace();
									}
								}
							}
						}

					}

				});
				if (count < 35) {
					buttonContainer.add(buttons[x][y]);
				}
				count++;
			}
		}
		JButton keysig = new JButton("Key: " + KeySignature.getKey());
		keysig.setHorizontalAlignment(SwingConstants.LEFT);
		keysig.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String x = JOptionPane.showInputDialog("Enter key -8 > 8 inclusive. Negative = Flat. Positive = Sharp\nZero is = C major\nThis will only change notes to be added in this panel\nYou can manually change notes in the panel on the left.");
				try {
					if (Math.abs(Integer.parseInt(x)) > 8) {
						throw new Exception();
					}
					KeySignature.setKey(Integer.parseInt(x));
					JOptionPane.showMessageDialog(null, "Success!");
					keysig.setText("Key Sig:" + x);
				} catch (Exception ee) {
					JOptionPane.showMessageDialog(null, "Error: NaN/No Key/Out of bounds", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
		buttonContainer.add(keysig);
		root.add(buttonContainer);
		qbCont.add(quickPress);
		qbCont.add(qP1);
		qbCont.add(qP2);
		root.add(qbCont);
		root.setPreferredSize(new Dimension(150,500));
		pane.setViewportView(root);
		add(pane);
	}
}
