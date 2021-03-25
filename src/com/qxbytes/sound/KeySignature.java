package com.qxbytes.sound;
/**
 * 
 * @author QxBytes
 *
 */
public class KeySignature {
	static int key = 0;// + is sharp - is flat
	static final int[] flats = {1,4,0,3,6,2,5};//B E A D G C F (0 Index)
	final static int[] sharp = {5,2,6,3,0,4,1};//F C G D A E B (0 Index, so in reverse)
	
	static int getModifier(int selecteditem) {
		int loops = Math.abs(key);
		if (key > 0) {//sharp
			for (int i = 0 ; i < loops ; i++) {//is this note a sharp based on the key signature?
				if (selecteditem == sharp[i]) {
					return 2;
				}
			}
			return 0;
		}
		if (key < 0) {//flat
			for (int i = 0 ; i < loops ; i++) {//is this note a flat based on the key signature?
				if (selecteditem == flats[i]) {
					return 1;
				}
			}
			return 0;
		}
		return 0;
	}

	public static int getKey() {
		return key;
	}

	public static void setKey(int key) {
		KeySignature.key = key;
	}
	
}
