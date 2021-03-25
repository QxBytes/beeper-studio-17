package com.qxbytes.sound;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
/**
 * 
 * @author QxBytes
 *
 */
public class GUI {
	//private File selectedwav;
	private JPanel panel_9;
	//private JFileChooser jfc = new JFileChooser();
	private JFrame frame;
	private String[] notes = {"Whole Note","Half Note","Quarter Note","8th Note","16nth Note","32nth Note","Whole Note w/ Dot","Half Note w/ Dot","Quarter Note w/ Dot","8th Note w/ Dot","16nth Note w/ Dot"};
	private String[] keys = {"A","B","C","D","E","F","G"};
	private String[] octaves = {"1","2","3","4","5","6","7","8"};
	private String[] modifiers = {"NATURAL","FLAT","SHARP","REST"};
	private ArrayList<Track> tracks = new ArrayList<Track>();
	private ArrayList<Note> clipboard = new ArrayList<Note>();
	private char[] auth = {'q','x','b','y','t','e','s'};
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImageManager.loadImages();
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Beeper Studio Has Crashed!\n\nDon't do that again please.\nReport this bug: " + e.getMessage(), "Fatal Error", JOptionPane.INFORMATION_MESSAGE);
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
		auth[0] = 'Q';
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		tracks.add(new Track());

		frame = new JFrame("Beeper Studio 17");
		frame.setIconImage((ImageManager.getImage(1, 1)));
		frame.setBounds(100, 100, 600, 720);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowL());
		frame.getContentPane().setLayout(new BorderLayout());
		

		JPanel left = new JPanel();
		left.setLayout(new GridLayout(2,1,0,0));
		frame.getContentPane().add(left,BorderLayout.CENTER);

		JPanel panel = new JPanel();
		left.add(panel);
		panel.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(3, 0, 0, 0));

		JPanel panel_11 = new JPanel();
		panel_2.add(panel_11);
		panel_11.setLayout(new GridLayout(1, 4, 0, 0));

		JLabel lblAddBeep = new JLabel("Add Beep (C-4 is middle c)");
		panel_11.add(lblAddBeep);

		JComboBox<String> comboBox_2 = new JComboBox<>(keys);
		panel_11.add(comboBox_2);

		JComboBox<String> comboBox_3 = new JComboBox<>(octaves);
		comboBox_3.setSelectedIndex(3);
		panel_11.add(comboBox_3);

		JComboBox<String> comboBox_4 = new JComboBox<>(modifiers);
		panel_11.add(comboBox_4);

		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new CompoundBorder());
		panel_2.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 4, 0, 0));

		JComboBox<String> comboBox = new JComboBox<>(notes);
		comboBox.setSelectedIndex(2);
		panel_3.add(comboBox);

		JSpinner spinner = new JSpinner();
		JSpinner spinner_1 = new JSpinner();
		JLabel lblStatusBar = new JLabel("Status Bar");

		JPanel addContainer = new JPanel();
		addContainer.setLayout(new GridLayout(2,1));

		SpinnerNumberModel snm = new SpinnerNumberModel(1,1,32,1);
		spinner.setModel(snm);
		JButton btnAdd = new JButton("");
		btnAdd.setIcon(new ImageIcon(ImageManager.getImage(2, 0)));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int track = 0;
					if (track < 0) {
						return;
					}
					for (int i = 0 ; i < snm.getNumber().intValue() ; i++) {
						tracks.get(track).addNote(new Note(comboBox.getSelectedIndex(),NoteMath.calculateNote(comboBox_2.getSelectedItem()+"", comboBox_3.getSelectedItem()+"", comboBox_4.getSelectedItem()+""),NoteMath.meta(comboBox_2.getSelectedItem()+"", comboBox_3.getSelectedItem()+"", comboBox_4.getSelectedItem()+""),Integer.parseInt(spinner_1.getValue()+"")));
						lblStatusBar.setText("Successfully Added Note: " + tracks.get(track).getLastNote().displayString());
					}
				} catch (Exception x) {
					JOptionPane.showMessageDialog(null, "Error: Not a number/Not a Pitch", "Error", JOptionPane.INFORMATION_MESSAGE);
				} finally {
					updatePanel();
				}
				//System.out.println("Update!");
				updatePanel();
			}
		});
		
		/*
		 * Next two buttons are quick press!
		 */
		
		JButton btnAdd1 = new JButton("");
		btnAdd1.setIcon(new ImageIcon(ImageManager.getImage(3, 1)));
		btnAdd1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int track = 0;
					if (track < 0) {
						return;
					}
					for (int i = 0 ; i < snm.getNumber().intValue() ; i++) {
						tracks.get(track).addNote(new Note(2,NoteMath.calculateNote(comboBox_2.getSelectedItem()+"", comboBox_3.getSelectedItem()+"", comboBox_4.getSelectedItem()+""),NoteMath.meta(comboBox_2.getSelectedItem()+"", comboBox_3.getSelectedItem()+"", comboBox_4.getSelectedItem()+""),Integer.parseInt(spinner_1.getValue()+"")));
						lblStatusBar.setText("Successfully Added Note: " + tracks.get(track).getLastNote().displayString());
					}
				} catch (Exception x) {
					JOptionPane.showMessageDialog(null, "Error: Not a number/Not a Pitch", "Error", JOptionPane.INFORMATION_MESSAGE);
				} finally {
					updatePanel();
				}
				//System.out.println("Update!");
				updatePanel();
			}
		});
		JButton btnAdd2 = new JButton("");
		btnAdd2.setIcon(new ImageIcon(ImageManager.getImage(4, 1)));
		btnAdd2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int track = 0;
					if (track < 0) {
						return;
					}
					for (int i = 0 ; i < snm.getNumber().intValue() ; i++) {
						tracks.get(track).addNote(new Note(3,NoteMath.calculateNote(comboBox_2.getSelectedItem()+"", comboBox_3.getSelectedItem()+"", comboBox_4.getSelectedItem()+""),NoteMath.meta(comboBox_2.getSelectedItem()+"", comboBox_3.getSelectedItem()+"", comboBox_4.getSelectedItem()+""),Integer.parseInt(spinner_1.getValue()+"")));
						lblStatusBar.setText("Successfully Added Note: " + tracks.get(track).getLastNote().displayString());
					}
				} catch (Exception x) {
					JOptionPane.showMessageDialog(null, "Error: Not a number/Not a Pitch", "Error", JOptionPane.INFORMATION_MESSAGE);
				} finally {
					updatePanel();
				}
				//System.out.println("Update!");
				updatePanel();
			}
		});
		
		/*
		 * Next button is shown at top (not in quick press)
		 */
		
		JButton btnAddTop = new JButton("Add");
		btnAddTop.setIcon(new ImageIcon(ImageManager.getImage(2, 0)));
		btnAddTop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int track = 0;
					if (track < 0) {
						return;
					}
					for (int i = 0 ; i < snm.getNumber().intValue() ; i++) {
						tracks.get(track).addNote(new Note(comboBox.getSelectedIndex(),NoteMath.calculateNote(comboBox_2.getSelectedItem()+"", comboBox_3.getSelectedItem()+"", comboBox_4.getSelectedItem()+""),NoteMath.meta(comboBox_2.getSelectedItem()+"", comboBox_3.getSelectedItem()+"", comboBox_4.getSelectedItem()+""),Integer.parseInt(spinner_1.getValue()+"")));
						lblStatusBar.setText("Successfully Added Note: " + tracks.get(track).getLastNote().displayString());
					}
				} catch (Exception x) {
					JOptionPane.showMessageDialog(null, "Error: Not a number/Not a Pitch", "Error", JOptionPane.INFORMATION_MESSAGE);
				} finally {
					updatePanel();
				}
				//System.out.println("Update!");
				updatePanel();
			}
		});
		addContainer.add(btnAddTop);
		JButton btnAddInsert = new JButton("Insert...");
		btnAddInsert.setIcon(new ImageIcon(ImageManager.getImage(2, 0)));
		btnAddInsert.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String x = JOptionPane.showInputDialog("At What Index?");
				try {
					for (int i = 0 ; i < snm.getNumber().intValue() ; i++) {
						Note n = (new Note(comboBox.getSelectedIndex(),NoteMath.calculateNote(comboBox_2.getSelectedItem()+"", comboBox_3.getSelectedItem()+"", comboBox_4.getSelectedItem()+""),NoteMath.meta(comboBox_2.getSelectedItem()+"", comboBox_3.getSelectedItem()+"", comboBox_4.getSelectedItem()+""),Integer.parseInt(spinner_1.getValue()+"")));
						tracks.get(0).getNotes().add(Integer.parseInt(x), n);
					}
					lblStatusBar.setText("Successfully Added Note: " + tracks.get(0).getLastNote().displayString());
				} catch (Exception q) {
					JOptionPane.showMessageDialog(null, "Error: Not a number/Not an Index (start at 0)", "Error", JOptionPane.INFORMATION_MESSAGE);
				} finally {
					updatePanel();
				}

			}

		});

		spinner_1.setModel(new SpinnerNumberModel(50, 0, 100, 1));
		spinner_1.setToolTipText("Volume (0-100)");
		panel_3.add(spinner_1);



		spinner.setToolTipText("#of notes added");
		panel_3.add(spinner);

		addContainer.add(btnAdd);
		addContainer.add(btnAddInsert);
		panel_3.add(addContainer);

		JLabel lblOr = new JLabel("OR (Hover over spinners/components for info)");
		lblOr.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblOr);

//		JPanel panel_4 = new JPanel();
//		panel_2.add(panel_4);
//		panel_4.setLayout(new GridLayout(0, 3, 0, 0));

//		JLabel lblAddSound = new JLabel("Add Background Sound");
//		panel_4.add(lblAddSound);
//
//		JButton btnChooseClip = new JButton("Choose Clip (.wav)");
//		btnChooseClip.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				FileNameExtensionFilter filter = new FileNameExtensionFilter(".wav", "wav");
//				jfc.setFileFilter(filter);
//				int x = jfc.showOpenDialog(frame);
//				if (x == JFileChooser.APPROVE_OPTION) {
//					btnChooseClip.setText(jfc.getSelectedFile().getName());
//					selectedwav = jfc.getSelectedFile();
//				}
//			}
//		});
//		panel_4.add(btnChooseClip);

//		JButton btnAdd_1 = new JButton("Add");
//		btnAdd_1.setIcon(new ImageIcon(ImageManager.getImage(2, 0)));
//		btnAdd_1.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				try {
//					tracks.get(0).addNote(new Note(selectedwav));
//				} catch (Exception x) {
//
//				} finally {
//					updatePanel();
//				}
//			}
//		});
//		panel_4.add(btnAdd_1);

		JPanel panel_5 = new JPanel();
		panel.add(panel_5);
		panel_5.setLayout(new GridLayout(0, 2, 0, 0));

		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6);
		panel_6.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel panel_1 = new JPanel();
		panel_6.add(panel_1);
		panel_1.setLayout(new GridLayout(4, 1, 0, 0));

		JButton btnRemove = new JButton("Remove Last Note");
		btnRemove.setIcon(new ImageIcon(ImageManager.getImage(3, 0)));
		panel_1.add(btnRemove);

		JButton btnRemoveNote = new JButton("Remove Note #...");
		btnRemoveNote.setIcon(new ImageIcon(ImageManager.getImage(3, 0)));
		btnRemoveNote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					int start;
					int end;
					start = Integer.parseInt(JOptionPane.showInputDialog("Enter Starting Index (Inclusive)"));
					end = Integer.parseInt(JOptionPane.showInputDialog("Enter Ending Index (Exclusive)"));
					for (int i = start ; i < end ; i++) {
						tracks.get(0).getNotes().remove(i);
					}

					JOptionPane.showMessageDialog(null, "Successfully Deleted Notes: " + start + "-" + end);
				} catch (Exception q) {
					JOptionPane.showMessageDialog(null, "Error: No such note/NaN", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				updatePanel();
			}
		});
		panel_1.add(btnRemoveNote);

		JButton btnRemoveAllNotes = new JButton("Remove All Notes...");
		btnRemoveAllNotes.setIcon(new ImageIcon(ImageManager.getImage(3, 0)));
		btnRemoveAllNotes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int x = JOptionPane.showConfirmDialog(null, "Delete All Notes?");
				if (x == JOptionPane.OK_OPTION) {
					tracks.get(0).getNotes().removeAll(tracks.get(0).getNotes());

				}
				updatePanel();
			}
		});
		panel_1.add(btnRemoveAllNotes);
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*
						int x = showTrackDialog();
						if (x < 0) {
							return;
						} else {*/
				tracks.get(0).removeNote();
				updatePanel();

				//}
			}
		});
		JPanel panel_edit = new JPanel(new GridLayout(1,3));
		
		panel_1.add(panel_edit);
		
		JButton btnCopy = new JButton("Copy");
		JButton btnPaste = new JButton("Paste");
		JButton btnPasteAt = new JButton("Paste...");
		
		btnCopy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int x = Integer.parseInt(JOptionPane.showInputDialog("Copy from note # (Inclusive)"));
					int y = Integer.parseInt(JOptionPane.showInputDialog("Copy to note # (Exclusive)\nWrite -1 for last note\nThese notes will be copied to the clipboard"));
					if (y == -1) {
						y = tracks.get(0).getNotes().size();
					}
					clipboard.removeAll(clipboard);
					int xx = 0;
					for (int i = x; i < y ; i++) {
						clipboard.add(tracks.get(0).getNotes().get(i));
						xx++;
					}
					JOptionPane.showMessageDialog(null, "Successfully copied " + xx + " notes!");
				} catch (Exception ee) {
					JOptionPane.showMessageDialog(null, "Error: NaN/Out of bounds", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				
			}
			
		});
		btnPaste.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				for (int i = 0 ; i < clipboard.size() ; i++) {
					tracks.get(0).getNotes().add(clipboard.get(i));
				}
				updatePanel();
			}
			
		});
		btnPasteAt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int start = Integer.parseInt(JOptionPane.showInputDialog("Insert at what number?"));
					for (int i = 0 ; i < clipboard.size() ; i++) {
						tracks.get(0).getNotes().add(start+i, clipboard.get(i));
					}
				} catch (Exception ee) {
					JOptionPane.showMessageDialog(null, "Error: Out of Bounds", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
				updatePanel();
			}
			
		});
		panel_edit.add(btnCopy);
		panel_edit.add(btnPaste);
		panel_edit.add(btnPasteAt);
		/*
		JButton btnDeleteTrack = new JButton("Delete Track...");
		btnDeleteTrack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int x = showTrackDialog();
				if (x < 0) {
					return;
				} else {
					tracks.remove(x);
				}
				JOptionPane.showMessageDialog(null, "Track #" + x + " Deleted\nWhen adding notes, be sure to note that any tracks after the removed track will be moved down by one\nEX: Removed: Track #3, so, Track #4 becomes Track #3!");
			}
		});
		panel_6.add(btnDeleteTrack);

		JButton btnAddTrack = new JButton("Add Track");
		btnAddTrack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tracks.add(new Track());
				JOptionPane.showMessageDialog(null, "Successfully Created Track #" + (tracks.size()-1));
			}
		});
		panel_6.add(btnAddTrack);
		 */
		JPanel TandTcont = new JPanel(new GridLayout(2,1));

		JButton btnChangeTempo = new JButton("Change Tempo: " + Tempo.getBpm());
		btnChangeTempo.setIcon(new ImageIcon(ImageManager.getImage(5,0)));
		btnChangeTempo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int x = Integer.parseInt(JOptionPane.showInputDialog("Enter BPM (1-180):"));
					if (x > 180 || x < 1) {
						throw new Exception();
					}
					Tempo.setBpm(x);
					btnChangeTempo.setText("Change Tempo: " + Tempo.getBpm());
				} catch (Exception q) {
					JOptionPane.showMessageDialog(null, "Error: Invalid BPM (1-180)", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		JButton btnChangeTime = new JButton("Change Time Sig: " + Tempo.measure+ "/"+Tempo.beat );
		btnChangeTime.setIcon(new ImageIcon(ImageManager.getImage(4, 0)));
		btnChangeTime.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					double x = Double.parseDouble(JOptionPane.showInputDialog("Enter Beats Per Measure"));
					double y = Double.parseDouble(JOptionPane.showInputDialog("Enter Note that gets the Beat\nThis will only change the length of the notes added in the future\nNotes on the track will not be changed."));
					Tempo.setMeasure(x);
					Tempo.setBeat(y);
					
					updatePanel();
					btnChangeTime.setText("Change Time Sig: "  + Tempo.measure+ "/"+Tempo.beat );
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error: Invalid Numbers", "Error", JOptionPane.INFORMATION_MESSAGE);
				}
			}

		});
		TandTcont.add(btnChangeTime);
		TandTcont.add(btnChangeTempo);

		panel_6.add(TandTcont);

		JPanel panel_7 = new JPanel();
		panel_5.add(panel_7);
		panel_7.setLayout(new GridLayout(4, 1, 0, 0));

		JButton btnPlay = new JButton("Play");
		btnPlay.setIcon(new ImageIcon(ImageManager.getImage(6, 0)));
		JButton btnPlayAt = new JButton("Play At...");
		btnPlayAt.setIcon(new ImageIcon(ImageManager.getImage(6, 0)));
		JButton btnStop = new JButton("Stop");
		btnStop.setIcon(new ImageIcon(ImageManager.getImage(7, 0)));
		JButton btnHqPlayno = new JButton("HQ Play (No Pausing)");
		btnHqPlayno.setIcon(new ImageIcon(ImageManager.getImage(6, 0)));
		btnPlayAt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int start = Integer.parseInt(JOptionPane.showInputDialog("Start at what note?"));
					if (start < 0 || start > tracks.get(0).getNotes().size()-1) {
						throw new Exception();
					}
					new NoteDispatchThread(tracks.get(0),btnPlay,lblStatusBar,btnHqPlayno,start);
				} catch (Exception ee) {
					JOptionPane.showMessageDialog(null, "Error: NaN", "Error", JOptionPane.INFORMATION_MESSAGE);
				} 
			}
			
		});
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//				for (int i = 0 ; i < tracks.size() ; i++) {
				//					new TrackThread(tracks.get(i)).start();
				//				}
				
				btnHqPlayno.setEnabled(false);
				new NoteDispatchThread(tracks.get(0),btnPlay,lblStatusBar,btnHqPlayno,0);

			}
		});

		btnHqPlayno.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int x = JOptionPane.showConfirmDialog(null, "WARNING: You will not be able to modify, close, or change the app window until the song has finished playing!","HQ play",JOptionPane.WARNING_MESSAGE,JOptionPane.WARNING_MESSAGE);
				if (x != JOptionPane.OK_OPTION) {
					return;
				}
				btnHqPlayno.setEnabled(false);
				btnPlay.setEnabled(false);
				btnStop.setEnabled(false);
				new LoadedPlay(tracks.get(0),btnHqPlayno,btnPlay,btnStop);
			}
		});
		panel_7.add(btnHqPlayno);
		panel_7.add(btnPlay);
		panel_7.add(btnPlayAt);

		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnPlay.isEnabled() || !Queue.isPlaying()) {
					return;
				}
				Queue.setStop(true);
				int needed = tracks.size();
				int received = 0;
				while (received == 0) {
					received = 0;
					for (int i = 0 ; i < needed ; i++) {
						if (!Queue.isStop()) {
							received++;
						}
					}
					System.out.println("Waiting!");
				}
				System.out.println("Finished!");
				Queue.setStop(false);
				Queue.setPlaying(false);
				btnPlay.setEnabled(true);
			}
		});
		panel_7.add(btnStop);

		panel_9 = new JPanel();
		left.add(panel_9);
		panel_9.setLayout(new BorderLayout(0, 0));

		panel_9.add(lblStatusBar, BorderLayout.NORTH);

		JPanel panel_8 = new JPanel();
		panel_9.add(panel_8, BorderLayout.SOUTH);
		panel_8.setLayout(new GridLayout(0, 4, 0, 0));

		JLabel lblTrackSettings = new JLabel("Track Settings");
		panel_8.add(lblTrackSettings);

		JCheckBox chckbxLoop = new JCheckBox("Loop");
		//chckbxLoop.setIcon(new ImageIcon(ImageManager.getImage(2, 1)));
		panel_8.add(chckbxLoop);

		JCheckBox chckbxMetronome = new JCheckBox("Metronome");
		//chckbxMetronome.setIcon(new ImageIcon(ImageManager.getImage(5,0)));
		panel_8.add(chckbxMetronome);

		JButton btnApply = new JButton("Apply");
		btnApply.setIcon(new ImageIcon(ImageManager.getImage(0, 1)));
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int x = 0;
				if (x < 0) {
					return;
				}
				int loops = tracks.get(x).loop;
				if (chckbxLoop.isSelected()) {
					try {
						loops = Integer.parseInt(JOptionPane.showInputDialog("Enter # of loops"));
					} catch (Exception xx) {
						JOptionPane.showMessageDialog(null, "Error: Not a Number","Error",JOptionPane.INFORMATION_MESSAGE);
					}
				}
				tracks.get(x).setLoop(loops);
				tracks.get(x).setMetronome(chckbxMetronome.isSelected());
				JOptionPane.showMessageDialog(null, "Successfully Updated Track Settings!");

				updatePanel();

			}
		});
		panel_8.add(btnApply);

		JScrollPane scrollPane = new JScrollPane();
		GUIPanel guipanel = new GUIPanel(tracks.get(0));
		scrollPane.setViewportView(guipanel);
		panel_9.add(scrollPane);

		MIDI midi = new MIDI(comboBox_2,comboBox_3,comboBox_4,btnAdd,btnAdd1,btnAdd2);
		frame.getContentPane().add(midi,BorderLayout.EAST);

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmRefresh = new JMenuItem("Do NOT display");
		mntmRefresh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				tracks.set(0, Queue.refreshTrack());
				System.out.println("REFRESHED!");
				System.out.println("CONTENTS: " + tracks.get(0).getNotes());
			}

		});
		//mnFile.add(mntmRefresh);
		JFileChooser jfc = new JFileChooser();

		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.setIcon(new ImageIcon(ImageManager.getImage(0, 0)));
		mntmSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int x = jfc.showSaveDialog(null);
				if (x == JFileChooser.APPROVE_OPTION) {
					File filename = jfc.getSelectedFile();
					Track t = tracks.get(0);
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
						e.printStackTrace();
					}
				}
			}

		});
		mnFile.add(mntmSave);

		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.setIcon(new ImageIcon(ImageManager.getImage(1, 0)));
		mntmOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int x = jfc.showOpenDialog(null);
				if (x == JFileChooser.APPROVE_OPTION) {
					File filename = jfc.getSelectedFile();

					Track t = new Track (filename, btnChangeTempo, chckbxLoop, chckbxMetronome,btnChangeTime);

					Queue.addRefreshTrack(t);
					mntmRefresh.doClick();
				}
				updatePanel();
			}

		});
		mnFile.add(mntmOpen);

		JMenu mnInfo = new JMenu("Info");
		menuBar.add(mnInfo);

		JMenuItem mntmCredits = new JMenuItem("Credits");
		mntmCredits.addActionListener(new MenuListener("Created by QxBytes // That is all..."));
		mnInfo.add(mntmCredits);

		JMenuItem mntmCopyright = new JMenuItem("Copyright");
		mntmCopyright.addActionListener(new MenuListener("License is GPL 3.0"));
		mnInfo.add(mntmCopyright);

		JMenuItem mntmOtherInfo = new JMenuItem("Other Info");
		mntmOtherInfo.addActionListener(new MenuListener("Beeper Studio v0.0.2"));
		mnInfo.add(mntmOtherInfo);

		JMenuItem mntmDisclaimer = new JMenuItem("Disclaimer");
		mntmDisclaimer.addActionListener(new MenuListener("See GPL 3.0"));
		mnInfo.add(mntmDisclaimer);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenu mnIKeepHearing = new JMenu("I Keep Hearing Clicking/Static");
		mnHelp.add(mnIKeepHearing);

		JMenu mnOkWhatDo = new JMenu("So what should I do?");
		mnIKeepHearing.add(mnOkWhatDo);

		JMenu mnNothing = new JMenu("Nothing.");
		mnOkWhatDo.add(mnNothing);

		JMenuItem mntmThatWouldBe = new JMenuItem("That would be correct.");
		mnNothing.add(mntmThatWouldBe);

		JMenu mnFixIt = new JMenu("Fix it.");
		mnOkWhatDo.add(mnFixIt);

		JMenuItem mntmThatIsNot = new JMenuItem("Done!");
		mnFixIt.add(mntmThatIsNot);

		JMenuItem mntmYouWontDo = new JMenuItem("You won't do anything.");
		mnOkWhatDo.add(mntmYouWontDo);

		JMenu mnWhatDoThe = new JMenu("What do the buttons mean?");
		mnHelp.add(mnWhatDoThe);

		JMenuItem mntmHoverOverThe = new JMenuItem("Hover over the components for info");
		mnWhatDoThe.add(mntmHoverOverThe);

		JMenu mnWhatDoThe_1 = new JMenu("What do the colors of the notes mean?");
		mnHelp.add(mnWhatDoThe_1);

		JMenuItem mntmTheHigherThe = new JMenuItem("The higher the note, the more red it is.");
		mnWhatDoThe_1.add(mntmTheHigherThe);
		
//		JMenuItem mntmBug = new JMenuItem("Submit a Bug");
//		mntmBug.addActionListener(new MenuListener("Submit a Bug"));
//		mnHelp.add(mntmBug);
		
		JMenuItem mntmTutorial = new JMenuItem("Tutorial");
		mntmTutorial.addActionListener(new MenuListener("View the Tutorial"));
		mnHelp.add(mntmTutorial);
		
		JMenuItem mntmUpdates = new JMenuItem("Updates/Changelog");
		mntmUpdates.addActionListener(new MenuListener("Updates"));
		mnHelp.add(mntmUpdates);
		
		JMenuItem mntmNews = new JMenuItem("News");
		mntmNews.addActionListener(new MenuListener("News"));
		mnHelp.add(mntmNews);
	}
//	private int showTrackDialog() {
//		try {
//			if (tracks.size() == 0) {
//				JOptionPane.showMessageDialog(null, "Error: There are no tracks! Add a track!", "Error", JOptionPane.INFORMATION_MESSAGE);
//				return -1;
//			}
//			int x = Integer.parseInt(JOptionPane.showInputDialog("Choose a track...(0-" + (tracks.size()-1) + ")"));
//			tracks.get(x);
//			return x;
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(null, "Error: Not a Number/No track", "Error", JOptionPane.INFORMATION_MESSAGE);
//			return -1;
//		}
//	}
	private void updatePanel() {
		panel_9.remove(2);
		//System.out.println("ENTERED");

		JScrollPane scrollPane = new JScrollPane();
		GUIPanel guipanel = new GUIPanel(tracks.get(0));

		scrollPane.setViewportView(guipanel);

		guipanel.revalidate();
		scrollPane.revalidate();
		panel_9.revalidate();

		scrollPane.getHorizontalScrollBar().setMaximum(999999999);
		scrollPane.getHorizontalScrollBar().setValue ( 999999999);
		panel_9.revalidate();
		panel_9.add(scrollPane);

	}
	static public void updatePanel(JPanel panel_9, Track t) {
		panel_9.remove(2);
		//System.out.println("ENTERED");

		JScrollPane scrollPane = new JScrollPane();
		GUIPanel guipanel = new GUIPanel(t);

		scrollPane.setViewportView(guipanel);

		guipanel.revalidate();
		scrollPane.revalidate();
		panel_9.revalidate();

		scrollPane.getHorizontalScrollBar().setMaximum(999999999);
		scrollPane.getHorizontalScrollBar().setValue ( 999999999);
		panel_9.revalidate();
		panel_9.add(scrollPane);
	}
}
