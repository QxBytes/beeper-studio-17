package com.qxbytes.sound;
/**
 * 
 * @author QxBytes
 *
 */
public class NoteMath {
	static double calculateNote(String note, String octave, String modifier) {
		double fixednote = 16.35;//C 0
		double value = 0;
		double a = 1.059463094359;
		if (note.equals("C")) {
			value = 0;
		}
		if (note.equals("D")) {
			value = 2;
		}
		if (note.equals("E")) {
			value = 4;
		}
		if (note.equals("F")) {
			value = 5;
		}
		if (note.equals("G")) {
			value = 7;
		}
		if (note.equals("A")) {
			value = 9;
		}
		if (note.equals("B")) {
			value = 11;
		}
		value += (12.0 * Integer.parseInt(octave));

		if (modifier.equals("FLAT")) {
			value--;
		}
		if (modifier.equals("SHARP")) {
			value++;
		}
		//f0 * a^n

		double hertz = fixednote * (Math.pow(a,value));
		if (modifier.equals("REST")) {
			return 0;
		}
		return hertz;
	}
	static int meta(String note, String octave, String modifier) {
		int value = 0;
		if (note.equals("C")) {
			value = 0;
		}
		if (note.equals("D")) {
			value = 2;
		}
		if (note.equals("E")) {
			value = 4;
		}
		if (note.equals("F")) {
			value = 5;
		}
		if (note.equals("G")) {
			value = 7;
		}
		if (note.equals("A")) {
			value = 9;
		}
		if (note.equals("B")) {
			value = 11;
		}
		value += (12.0 * Integer.parseInt(octave));

		if (modifier.equals("FLAT")) {
			value--;
		}
		if (modifier.equals("SHARP")) {
			value++;
		}
		return value;
	}
}
