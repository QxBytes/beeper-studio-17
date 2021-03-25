package com.qxbytes.sound;
/**
 * 
 * @author QxBytes
 *
 */
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageManager {
	static final int HTILES = 16;
	static final int VTILES = 16;
	static final int TILESIZE = 16;
	static BufferedImage[][] images = new BufferedImage[16][16];
	static public void loadImages() {
		try {
			BufferedImage bi = ImageIO.read(ImageManager.class.getResourceAsStream("sprites.png"));
			for (int y = 0 ; y < VTILES ; y++) {
				for (int x = 0 ; x < HTILES ; x++) {
					images[x][y] = bi.getSubimage(x*TILESIZE, y*TILESIZE, TILESIZE, TILESIZE);
				}
			}
			System.out.println("Finished Loading");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static public BufferedImage getImage(int x, int y) {
		return images[x][y];
	}
}
