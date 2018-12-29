package com.mhdt.toolkit;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 
 *
 * @author LazyToShow <br>
 *         Date: 2018年10月14日 <br>
 *         Time: 下午1:59:15
 */
public class NumberUtilly {

	private static final BigInteger LONG_MIN = BigInteger.valueOf(Long.MIN_VALUE);

	private static final BigInteger LONG_MAX = BigInteger.valueOf(Long.MAX_VALUE);

	/**
	 * Convert the given number into an instance of the given target class.
	 * 
	 * @param number      the number to convert
	 * @param targetClass the target class to convert to
	 * @return the converted number
	 * @throws IllegalArgumentException if the target class is not supported (i.e.
	 *                                  not a standard Number subclass as included
	 *                                  in the JDK)
	 * @see java.lang.Byte
	 * @see java.lang.Short
	 * @see java.lang.Integer
	 * @see java.lang.Long
	 * @see java.math.BigInteger
	 * @see java.lang.Float
	 * @see java.lang.Double
	 * @see java.math.BigDecimal
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Number> T convertNumberToTargetClass(Number number, Class<T> targetClass)
			throws IllegalArgumentException {

		Assert.notNull(number, "Number must not be null");
		Assert.notNull(targetClass, "Target class must not be null");

		if (targetClass.isInstance(number)) {
			return (T) number;

		} else if (Byte.class == targetClass) {

			long value = checkedLongValue(number, targetClass);

			if (value < Byte.MIN_VALUE || value > Byte.MAX_VALUE) {
				raiseOverflowException(number, targetClass);
			}

			return (T) Byte.valueOf(number.byteValue());

		} else if (Short.class == targetClass) {

			long value = checkedLongValue(number, targetClass);

			if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
				raiseOverflowException(number, targetClass);
			}

			return (T) Short.valueOf(number.shortValue());

		} else if (Integer.class == targetClass) {

			long value = checkedLongValue(number, targetClass);

			if (value < Integer.MIN_VALUE || value > Integer.MAX_VALUE) {
				raiseOverflowException(number, targetClass);
			}

			return (T) Integer.valueOf(number.intValue());

		} else if (Long.class == targetClass) {

			long value = checkedLongValue(number, targetClass);
			return (T) Long.valueOf(value);

		} else if (BigInteger.class == targetClass) {

			if (number instanceof BigDecimal) {
				// do not lose precision - use BigDecimal's own conversion
				return (T) ((BigDecimal) number).toBigInteger();
			} else {
				// original value is not a Big* number - use standard long conversion
				return (T) BigInteger.valueOf(number.longValue());
			}

		} else if (Float.class == targetClass) {

			return (T) Float.valueOf(number.floatValue());

		} else if (Double.class == targetClass) {

			return (T) Double.valueOf(number.doubleValue());

		} else if (BigDecimal.class == targetClass) {
			// always use BigDecimal(String) here to avoid unpredictability of
			// BigDecimal(double)
			// (see BigDecimal javadoc for details)
			return (T) new BigDecimal(number.toString());

		} else {
			throw new IllegalArgumentException("Could not convert number [" + number + "] of type ["
					+ number.getClass().getName() + "] to unsupported target class [" + targetClass.getName() + "]");
		}
	}

	/**
	 * Check for a {@code BigInteger}/{@code BigDecimal} long overflow before
	 * returning the given number as a long value.
	 * 
	 * @param number      the number to convert
	 * @param targetClass the target class to convert to
	 * @return the long value, if convertible without overflow
	 * @throws IllegalArgumentException if there is an overflow
	 * @see #raiseOverflowException
	 */
	private static long checkedLongValue(Number number, Class<? extends Number> targetClass) {

		BigInteger bigInt = null;

		if (number instanceof BigInteger) {
			bigInt = (BigInteger) number;
		} else if (number instanceof BigDecimal) {
			bigInt = ((BigDecimal) number).toBigInteger();
		}

		// Effectively analogous to JDK 8's BigInteger.longValueExact()
		if (bigInt != null && (bigInt.compareTo(LONG_MIN) < 0 || bigInt.compareTo(LONG_MAX) > 0)) {
			raiseOverflowException(number, targetClass);
		}
		return number.longValue();
	}

	/**
	 * Raise an <em>overflow</em> exception for the given number and target class.
	 * 
	 * @param number      the number we tried to convert
	 * @param targetClass the target class we tried to convert to
	 * @throws IllegalArgumentException if there is an overflow
	 */
	private static void raiseOverflowException(Number number, Class<?> targetClass) {
		throw new IllegalArgumentException("Could not convert number [" + number + "] of type ["
				+ number.getClass().getName() + "] to target class [" + targetClass.getName() + "]: overflow");
	}
	
	private static boolean isHexNumber(String value) {
		int index = (value.startsWith("-") ? 1 : 0);
		return (value.startsWith("0x", index) || value.startsWith("0X", index) || value.startsWith("#", index));
	}

	private static BigInteger decodeBigInteger(String value) {
		int radix = 10;
		int index = 0;
		boolean negative = false;

		// Handle minus sign, if present.
		if (value.startsWith("-")) {
			negative = true;
			index++;
		}

		// Handle radix specifier, if present.
		if (value.startsWith("0x", index) || value.startsWith("0X", index)) {
			index += 2;
			radix = 16;
		}
		else if (value.startsWith("#", index)) {
			index++;
			radix = 16;
		}
		else if (value.startsWith("0", index) && value.length() > 1 + index) {
			index++;
			radix = 8;
		}

		BigInteger result = new BigInteger(value.substring(index), radix);
		return (negative ? result.negate() : result);
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Number> T parseNumber(String text, Class<T> targetClass) {
		Assert.notNull(text, "Text must not be null");
		Assert.notNull(targetClass, "Target class must not be null");
		String trimmed = StringUtility.trimAllWhitespace(text);

		if (Byte.class == targetClass) {
			return (T) (isHexNumber(trimmed) ? Byte.decode(trimmed) : Byte.valueOf(trimmed));
		}
		else if (Short.class == targetClass) {
			return (T) (isHexNumber(trimmed) ? Short.decode(trimmed) : Short.valueOf(trimmed));
		}
		else if (Integer.class == targetClass) {
			return (T) (isHexNumber(trimmed) ? Integer.decode(trimmed) : Integer.valueOf(trimmed));
		}
		else if (Long.class == targetClass) {
			return (T) (isHexNumber(trimmed) ? Long.decode(trimmed) : Long.valueOf(trimmed));
		}
		else if (BigInteger.class == targetClass) {
			return (T) (isHexNumber(trimmed) ? decodeBigInteger(trimmed) : new BigInteger(trimmed));
		}
		else if (Float.class == targetClass) {
			return (T) Float.valueOf(trimmed);
		}
		else if (Double.class == targetClass) {
			return (T) Double.valueOf(trimmed);
		}
		else if (BigDecimal.class == targetClass || Number.class == targetClass) {
			return (T) new BigDecimal(trimmed);
		}
		else {
			throw new IllegalArgumentException(
					"Cannot convert String [" + text + "] to target class [" + targetClass.getName() + "]");
		}
	}

}
