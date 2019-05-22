package jastify;

import java.util.ResourceBundle;

public class MessageUtil {
    private static final ResourceBundle source =
        ResourceBundle.getBundle("const",
                ResourceBundle.Control
                        .getControl(ResourceBundle.Control.FORMAT_DEFAULT));

    public static String get(String key) {
        return source.getString(key);
    }

}
