package com.mhdt.toolkit;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.mhdt.io.FileIO;

/**
 * The random class
 * 
 *
 * @author LazyToShow <br>
 * Date: 2017/03/04 <br>
 * Time: pm 8:23:00
 */
public class SimpleRandom extends RandomXS128 {

	private static final long serialVersionUID = 5319626971434295250L;
	List<Character> keys = new ArrayList<Character>();

	/**
	 * Random digit English letter
	 * 
	 * @param num
	 * @return
	 */
	public String nextLetter(int num) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < num; i++) {
			sb.append(getRanLetter());
		}

		return sb.toString();
	}

	public String nextChinese(int count) {

		Assert.state(count > 0, "Count must>0  : " + count);

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < count; i++)
			sb.append(nextChinese());

		return sb.toString();
	}

	static String baseChinese;

	public char nextChinese() {

		if (baseChinese == null)
			baseChinese = FileIO.getContent(SimpleRandom.class.getResourceAsStream("chinese.txt"));

		return (char) baseChinese.charAt(nextInt(baseChinese.length()));
	}

	private void initKeys() {
		String str = "EUzBbZcRdWeXfMghOiVjklJyamSLnCIpQIYqKrNsPtTuvSwAxDFGoH";
		for (int i = 0; i < str.length(); i++) {
			keys.add(str.charAt(i));
		}
	}

	private Character getRanLetter() {
		if (keys.isEmpty())
			initKeys();
		return keys.get(nextInt(keys.size()));
	}

	public Color nextColor() {
		int r = nextInt(255);
		int g = nextInt(255);
		int b = nextInt(255);
		return new Color(r, g, b);
	}

	/**
	 * A numeric string of random digits
	 * 
	 * @param count - digits
	 * @return
	 */
	public String nextNumber(int count) {
		
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < count; i++)
			sb.append(nextInt(10));
		
		return sb.toString();
	}

}
