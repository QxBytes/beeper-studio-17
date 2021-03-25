package com.qxbytes.sound;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.List;

import javax.swing.JPanel;
/**
 * 
 * @author QxBytes
 *
 */
public class GUIPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Track t;
	int x;
	int cs = 4;
	public GUIPanel(Track t) {
		this.t = t;
		//Content
		x = t.getTotalBeats();
		setPreferredSize(new Dimension(x*cs,90));
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//one cell = 1/32 = 32 * 1/32 = 1 WHOLE NOTE (one measure)
		int pos = 0;
		int measure = 1;
		List<Note> notes = t.getNotes();

		//draw measures
		g.setColor(Color.BLACK);
		for (int i = 0 ; i < this.getWidth() ; i++) {
			if (i % ((8*Tempo.getMeasure())*cs) == 0) {
				g.drawString(""+measure, i+4, 20);
				g.drawRect(i, 0, 1, this.getHeight());
				measure++;
			}
		}
		//draw clefs
		g.setColor(Color.GRAY);
		
		g.drawLine(0, this.getHeight()-50*3-1, this.getWidth(), this.getHeight()-50*3-1);
		g.drawLine(0, this.getHeight()-53*3-1, this.getWidth(), this.getHeight()-53*3-1);
		g.drawLine(0, this.getHeight()-57*3-1, this.getWidth(), this.getHeight()-57*3-1);
		g.drawLine(0, this.getHeight()-60*3-1, this.getWidth(), this.getHeight()-60*3-1);
		g.drawLine(0, this.getHeight()-64*3-1, this.getWidth(), this.getHeight()-64*3-1);
		
		for (int i = 0 ; i < notes.size() ; i++) {
			//System.out.println("NOTES: " + notes.size());
			int meta = notes.get(i).getMeta();
			double hertz = notes.get(i).getPitch();
			g.setColor(new Color(meta*2,255-meta*2,0));
			int y = this.getHeight()-(meta*3);
			
			if (meta == 0) {
				g.setColor(Color.MAGENTA);
				g.drawRect(pos, this.getHeight()/2-60, 5, 40);
			}
			if (hertz == 0) {
				g.setColor(Color.DARK_GRAY);
				g.drawRect(pos, this.getHeight()/2-4, notes.get(i).getIn()*cs, 2);
			} else {
				g.drawRect(pos, y, (int)(notes.get(i).getIn()*cs), 2);
				
				switch (notes.get(i).getIn()) {
				case 48:
					g.drawImage(ImageManager.getImage(5,1),pos,y-12,null);
					break;
				case 32:
					g.drawImage(ImageManager.getImage(5,1),pos,y-12,null);
					break;
				case 24:
					g.drawImage(ImageManager.getImage(6,1),pos,y-12,null);
					break;
				case 16:
					g.drawImage(ImageManager.getImage(6,1),pos,y-12,null);
					break;
				case 12:
					g.drawImage(ImageManager.getImage(3,1),pos,y-12,null);
					break;
				case 8:
					g.drawImage(ImageManager.getImage(3,1),pos,y-12,null);
					break;
				case 6:
					g.drawImage(ImageManager.getImage(4,1),pos,y-12,null);
					break;
				case 4:
					g.drawImage(ImageManager.getImage(4,1),pos,y-12,null);
					break;
				case 2:
					g.drawImage(ImageManager.getImage(7,1),pos,y-12,null);
					break;
				case 1:
					g.drawImage(ImageManager.getImage(8,1),pos,y-12,null);
					break;
				}
			}
			if (i % 10 == 0) {
				g.setColor(Color.BLUE);
				g.drawString(""+i, pos, this.getHeight()/2+32);
			}
			pos+=notes.get(i).getIn()*cs;
		}

	}
}
