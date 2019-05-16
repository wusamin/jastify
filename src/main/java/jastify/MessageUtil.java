package jastify;

import java.util.ResourceBundle;

public class MessageUtil {
    private static final ResourceBundle source =
        ResourceBundle.getBundle("parameters");

    public static String get(String key) {
        return source.getString(key);
    }

}
