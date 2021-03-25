package com.qxbytes.sound;

import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author QxBytes
 *
 */
public class Utils {
	static String parseValue(String x, char separator, int num) {
		List<String> values = new ArrayList<String>();
		String addto = "";
		for (int i = 0 ; i < x.length() ; i++) {
			if (x.charAt(i) == separator) {
				values.add(addto);
				addto = "";
			} else {
				addto += x.charAt(i);
			}
		}
		values.add(addto);
		return values.get(num);
	}
}
