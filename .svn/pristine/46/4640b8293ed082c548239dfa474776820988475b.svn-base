package com.mhdt.toolkit;

import java.awt.Point;
import java.lang.reflect.Field;
import java.math.BigDecimal;

import com.mhdt.Print;

/**
 * @author 懒得出风头
 *         <p>
 *         Date: 2015/11/13<br>
 *         Time: 8:27<br>
 *         Email: 282854237@qq.com
 */
public class MathUtility {

	/**
	 * 计算除法
	 * 
	 * @param molecule
	 *            - 分子
	 * @param denominator
	 *            - 分母
	 * @param scale
	 *            - 保留小数点位数
	 * @return result
	 * @throws NumberFormatException
	 */
	public static double division(Object molecule, Object denominator, int scale) throws NumberFormatException {

		try {
			double param1 = Double.parseDouble(molecule.toString());
			double param2 = Double.parseDouble(denominator.toString());
			if (param2 == 0)
				return 0;
			double result = Double.parseDouble(String.format("%." + scale + "f", param1 / param2));
			return result;

		} catch (NumberFormatException e) {
			Print.error("Input the correct number");
			throw e;
		}

	}

	/**
	 * 保留小数一定位数的小数点且默认对后一位四舍五入.
	 * 
	 * @param f
	 *            - 要操作的小数
	 * @param digit
	 *            - 保留位数
	 * @return
	 */
	public static double keepDecimalPoint(Object f, int digit) {
		double num = 0;

		if (f instanceof BigDecimal) {
			BigDecimal bigDecimal = (BigDecimal) f;
			num = bigDecimal.doubleValue();
		} else {
			num = Double.parseDouble(f.toString());
		}
		return new BigDecimal(num).setScale(digit, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 平方
	 */
	public static int square(int x) {
		return x * x;
	}

	/**
	 * 返回2个数的绝对值较大的那个
	 */
	public static double getBiger(double a, double b) {
		a = Math.abs(a);
		b = Math.abs(b);
		if (a > b) {
			return a;
		} else {
			return b;
		}
	}

	/**
	 * 返回自己除以自己的绝对值
	 */
	public static int getDivisionValue(int a) {
		return Math.abs(a / a);
	}

	/**
	 * 求两点之间的角度
	 */
	public static double getAngle(Point p1, Point p2) {
		// 两点的x、y值
		int x = p2.x - p1.x;
		int y = p2.y - p1.y;
		double hypotenuse = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		// 斜边长度
		double cos = x / hypotenuse;
		double radian = Math.acos(cos);
		// 求出弧度
		double angle = 180 / (Math.PI / radian);
		// 用弧度算出角度
		if (y < 0) {
			angle = -angle;
		} else if ((y == 0) && (x < 0)) {
			angle = 180;
		}
		return angle;
	}

	/**
	 * 交换2个Integer 数值
	 * 
	 * @param a
	 * @param b
	 */
	public static void swap(Integer a, Integer b) {
		Field f;
		try {
			f = Integer.class.getDeclaredField("value");
			f.setAccessible(true);
			Integer temp = new Integer(a.intValue());
			try {
				f.set(a, b.intValue());
				f.set(b, temp);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 求2点之间的距离
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public static double getDistance(double x1, double y1, double x2, double y2) {
		
		double _x = Math.abs(x2 - x1);
		double _y = Math.abs(y2 - y1);
		return Math.sqrt((_x * _x) + (_y * _y));
	}
	

}
