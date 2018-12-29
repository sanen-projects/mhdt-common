package com.mhdt.toolkit;

/**
 * 偏好设置
 * @author LazyToShow
 * Date：	2018年2月31日
 * Time:	上午11:51:02
 */
public class Preferences {

	java.util.prefs.Preferences prefs;

	public Preferences() {
		prefs = java.util.prefs.Preferences.userNodeForPackage(Preferences.class);
	}
	
	public void put(Object key, String value) {
		prefs.put(key.toString(), value);
	}

	public void putInt(String key, int value) {
		prefs.putInt(key, value);
	}

	public void putDouble(String key, double value) {
		prefs.putDouble(key, value);
	}

	public void putFloat(String key, float value) {
		prefs.putFloat(key, value);
	}

	public void putBoolean(String key, boolean value) {
		prefs.putBoolean(key, value);
	}

	public void putLong(String key, long value) {
		prefs.putLong(key, value);
	}

	public String get(Object key, String def) {
		return prefs.get(key.toString(), def);
	}

	public long getLong(String key, long def) {
		return prefs.getLong(key, def);
	}

	public boolean getBoolean(String key, boolean def) {
		return prefs.getBoolean(key, def);
	}

	public int getInt(String key, int def) {
		return prefs.getInt(key, def);
	}

	public byte[] getByteArray(String key, byte[] def) {
		return prefs.getByteArray(key, def);
	}

	public double getDouble(String key, double def) {
		return prefs.getDouble(key, def);
	}

	public float getFloat(String key, float def) {
		return prefs.getFloat(key, def);
	}

}
