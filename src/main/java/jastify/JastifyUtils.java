package jastify;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JastifyUtils {
    private static final ResourceBundle source =
        ResourceBundle.getBundle("const",
                ResourceBundle.Control
                        .getControl(ResourceBundle.Control.FORMAT_DEFAULT));

    protected static String get(String key) {
        return source.getString(key);
    }

    protected static String get(String key, String replacement) {
        return MessageFormat.format(source.getString(key), replacement);
    }

    protected static Map<String, Object> parseJsonNest(String json) {
        TypeReference<HashMap<String, Object>> reference =
            new TypeReference<HashMap<String, Object>>() {
            };
        HashMap<String, Object> jsonMap = null;
        try {
            jsonMap = new ObjectMapper().readValue(json, reference);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonMap;
    }

    @Deprecated
    protected static Optional<String> sendRequest(Request request) {
        try (Response response =
            new OkHttpClient().newCall(request).execute()) {
            System.out.println("responseCode: " + response.code());
            if (!response.isSuccessful()) {
                System.out.println("error!!");
                System.out.println("body: " + response.body().string());
            }
            if (response.body() != null) {
                System.out.println("success!!");
                String body = response.body().string();
                System.out.println("body: " + body);
                return Optional.of(body);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    protected static Map<String, String> sendRequestV2(Request request) {
        try (Response response =
            new OkHttpClient().newCall(request).execute()) {
            System.out.println("responseCode: " + response.code());

            if (!response.isSuccessful()) {
                System.out.println("API calling has failed...");
            }
            if (response.body() != null) {
                Map<String, String> map = new HashMap<>();
                map.put("code", String.valueOf(response.code()));
                map.put("body", response.body().string());

                System.out.println(map.get("body"));

                return map;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new HashMap<>();
    }
}
