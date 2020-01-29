package jastify;

import okhttp3.MediaType;

public class Const {
    public static final String JOIN = "%20";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String TOKEN_KEY = "Authorization";

    public static final MediaType JSON =
        MediaType.parse("application/json; charset=utf-8");
}
