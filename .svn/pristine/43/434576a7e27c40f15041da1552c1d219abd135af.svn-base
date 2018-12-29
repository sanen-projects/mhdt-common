package com.mhdt.toolkit;

import java.text.DecimalFormat;

import com.mhdt.toolkit.MathUtility;

/**
 * @author 懒得出风头
 *         <p>
 *         Date: 2016/3/25<br>
 *         Time: 11:58<br>
 *         Email: 282854237@qq.com
 */
public class RamUsage {

	private static final double THRRSHOLD = MathUtility.square(1024);
	private DecimalFormat df = new DecimalFormat("0.0");
	private String maxRam;
	private float passTime;
	private String ram;

	public RamUsage() {
		maxRam = df.format(Runtime.getRuntime().maxMemory() / THRRSHOLD);
	}

	public final String getRamUsage(float delta) {
		
		if(ram == null)
			ram = getRam();
		
		passTime += delta;
		
		if (passTime >= 1000) {
			passTime %= 1000;
			ram = getRam();
		}

		return ram;

	}

	private String getRam() {
		return "RAM		" + df.format(getJavaHeap() / THRRSHOLD) + "	" + maxRam + " MB";
	}

	public static final long getJavaHeap() {
		return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	}

}
