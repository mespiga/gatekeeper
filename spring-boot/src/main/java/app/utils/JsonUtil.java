package app.utils;

import com.google.gson.Gson;

public class JsonUtil {

	public static String toJsonString(Object obj) {
		return new Gson().toJson(obj);
	}

	public static <T> T fromString(String value, Class<T> clazz) {
		T jsonString = null;
		try {
			jsonString = new Gson().fromJson(value, clazz);
		} catch (Exception e) {
		}
		return jsonString;
	}
}
