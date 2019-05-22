package jastify;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class MessageUtil {
    private static final ResourceBundle source =
        ResourceBundle.getBundle("const",
                ResourceBundle.Control
                        .getControl(ResourceBundle.Control.FORMAT_DEFAULT));

    public static String get(String key) {
        return source.getString(key);
    }

    public static String get(String key, String replacement) {
        return MessageFormat.format(source.getString(key), replacement);
    }
}
