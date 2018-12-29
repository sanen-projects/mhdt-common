package com.mhdt.toolkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class TestUtility {

	static final SimpleRandom ran = new SimpleRandom();

	public static Supplier<Map<String, Double>> mapDoubleSupplier(int count) {
		return mapSupplie(() -> ran.nextLetter(1)+ran.nextInt(999), (key) -> MathUtility.keepDecimalPoint(ran.nextDouble(), 2), count);
	}

	@SuppressWarnings("unchecked")
	public static <T> Supplier<Map<String, T>> mapSupplie(Supplier<String> keySupplier,
			Function<String, T> valueSupplier, int count) {

		return () -> {

			Map<String, T> map = new HashMap<>();

			for (int i = 0; i < count; i++) {
				String key = keySupplier.get();
				Object value = valueSupplier.apply(key);

				map.put(key, (T) value);
			}

			return map;
		};
	}

	public static <T> List<T> newList(Supplier<T> supplier, int count) {

		List<T> list = new ArrayList<T>(count);

		while (count-- > 0) {
			list.add(supplier.get());
		}

		return list;
	}

}
