package jastify;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jastify.base.SpotifyResponseBase;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JastifyUtils {
	private static final ResourceBundle source = ResourceBundle.getBundle("const",
			ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_DEFAULT));

	protected static String get(String key) {
		return source.getString(key);
	}

	protected static String get(String key, String replacement) {
		return MessageFormat.format(source.getString(key), replacement);
	}

	protected static Map<String, Object> parseJsonNest(String json) {
		TypeReference<HashMap<String, Object>> reference = new TypeReference<HashMap<String, Object>>() {
		};
		HashMap<String, Object> jsonMap = null;
		try {
			jsonMap = new ObjectMapper().readValue(json, reference);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonMap;
	}

	protected static Map<String, String> sendRequest(Request request) {
		try (Response response = new OkHttpClient().newCall(request).execute()) {
			System.out.println("responseCode: " + response.code());

			if (!response.isSuccessful()) {
				System.out.println("API calling has failed...");
			}
			if (response.body() != null) {
				Map<String, String> map = new HashMap<>();
				map.put("code", String.valueOf(response.code()));
				map.put("body", response.body().string());

//                System.out.println(map.get("body"));

				return map;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new HashMap<>();
	}

	protected static <T extends SpotifyResponseBase> T setResult(Map<String, String> apiResult, Class<T> clazz) {

		T dto = null;
		try {
			dto = clazz.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		dto.setCode(Integer.valueOf(apiResult.get("code")));

		try {
			dto = new ObjectMapper().readValue(apiResult.get("body"), clazz);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return dto;
	}
}
