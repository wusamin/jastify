package jastify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jastify.dto.Devices;
import jastify.dto.PlayingItem;
import jastify.dto.SearchResultAlbums;
import jastify.dto.SearchResultArtists;
import jastify.dto.SearchResultPlaylists;
import jastify.dto.SearchResultTracks;
import jastify.dto.SpotifyDevice;
import jastify.dto.SpotifyTrack;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Jastify {

    private String token;

    private String refreshToken;

    private String code;

    private String clientID;

    private String clientSecret;

    private Jastify() {
    }

    private Jastify(Builder builder) {
        this.code = builder.code;
        this.token = builder.token;
        this.refreshToken = builder.refreshToken;
        this.clientID = builder.clientID;
        this.clientSecret = builder.clientSecret;
    }

    private static final String JOIN = "%20";

    private static final String TOKEN_PREFIX = "Bearer ";

    private static final String TOKEN_KEY = "Authorization";

    private static final MediaType JSON =
        MediaType.parse("application/json; charset=utf-8");

    private Map<String, Object> parseJsonNest(String json) {
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

    private Optional<String> sendRequest(Request request) {
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

    private Map<String, String> sendRequestV2(Request request) {
        try (Response response =
            new OkHttpClient().newCall(request).execute()) {
            System.out.println("responseCode: " + response.code());
            if (!response.isSuccessful()) {
                System.out.println("API calling has failed...");
                System.out.println("body : " + response.body().string());
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

    public SearchResultTracks searchTracks(String[] searchWords, String market,
            int limit) {
        final String url = MessageUtil.get("spotify.url.api.search");

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        Map<String, String> params = new HashMap<>();
        params.put("q",
                Arrays.stream(searchWords).collect(Collectors.joining(JOIN)));
        params.put("type", "track");
        params.put("market", market);
        params.put("limit", String.valueOf(limit));

        params.forEach(urlBuilder::addEncodedQueryParameter);

        Map<String, String> map =
            sendRequestV2(new Request.Builder().url(urlBuilder.build())
                    .addHeader(TOKEN_KEY, TOKEN_PREFIX + token)
                    .build());

        ObjectMapper mapper = new ObjectMapper();
        SearchResultTracks t = new SearchResultTracks();
        t.setCode(Integer.valueOf(map.get("code")));

        try {
            t = mapper.readValue(map.get("body"), SearchResultTracks.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return t;
    }

    public SearchResultAlbums searchAlbums(String[] searchWords, String market,
            int limit) {
        final String url = MessageUtil.get("spotify.url.api.search");

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        Map<String, String> params = new HashMap<>();
        params.put("q",
                Arrays.stream(searchWords).collect(Collectors.joining(JOIN)));
        params.put("type", "album");
        params.put("market", market);
        params.put("limit", String.valueOf(limit));

        params.forEach(urlBuilder::addEncodedQueryParameter);

        Map<String, String> map =
            sendRequestV2(new Request.Builder().url(urlBuilder.build())
                    .addHeader(TOKEN_KEY, TOKEN_PREFIX + token)
                    .build());

        ObjectMapper mapper = new ObjectMapper();
        SearchResultAlbums t = new SearchResultAlbums();
        t.setCode(Integer.valueOf(map.get("code")));

        try {
            t = mapper.readValue(map.get("body"), SearchResultAlbums.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return t;
    }

    public SearchResultArtists searchArtists(String[] searchWords,
            String market, int limit) {
        final String url = MessageUtil.get("spotify.url.api.search");

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        Map<String, String> params = new HashMap<>();
        params.put("q",
                Arrays.stream(searchWords).collect(Collectors.joining(JOIN)));
        params.put("type", "album");
        params.put("market", market);
        params.put("limit", String.valueOf(limit));

        params.forEach(urlBuilder::addEncodedQueryParameter);

        Map<String, String> map =
            sendRequestV2(new Request.Builder().url(urlBuilder.build())
                    .addHeader(TOKEN_KEY, TOKEN_PREFIX + token)
                    .build());

        ObjectMapper mapper = new ObjectMapper();
        SearchResultArtists t = new SearchResultArtists();
        t.setCode(Integer.valueOf(map.get("code")));

        try {
            t = mapper.readValue(map.get("body"), SearchResultArtists.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return t;
    }

    public SearchResultPlaylists searchPlaylists(String[] searchWords,
            String market, int limit) {
        final String url = MessageUtil.get("spotify.url.api.search");

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        Map<String, String> params = new HashMap<>();
        params.put("q",
                Arrays.stream(searchWords).collect(Collectors.joining(JOIN)));
        params.put("type", "playlist");
        params.put("market", market);
        params.put("limit", String.valueOf(limit));

        params.forEach(urlBuilder::addEncodedQueryParameter);

        Map<String, String> map =
            sendRequestV2(new Request.Builder().url(urlBuilder.build())
                    .addHeader(TOKEN_KEY, TOKEN_PREFIX + token)
                    .build());

        ObjectMapper mapper = new ObjectMapper();
        SearchResultPlaylists t = new SearchResultPlaylists();
        t.setCode(Integer.valueOf(map.get("code")));

        try {
            t = mapper.readValue(map.get("body"), SearchResultPlaylists.class);
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        return t;
    }

    public void startMusic(String deviceId) {
        String url = MessageUtil.get("spotify.url.me.player.startPlayback");

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        Map<String, String> params = new HashMap<>();
        params.put("device_id", deviceId);
        params.forEach(urlBuilder::addEncodedQueryParameter);

        sendRequestV2(new Request.Builder().url(urlBuilder.build())
                .put(new FormBody.Builder().build())
                .addHeader(TOKEN_KEY, TOKEN_PREFIX + token)
                .build());
    }

    public void playTracks(SpotifyDevice device, List<SpotifyTrack> tracks) {
        String url = MessageUtil.get("spotify.url.me.player.startPlayback");

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        Map<String, String> params = new HashMap<>();
        params.put("device_id", device.getId());
        params.forEach(urlBuilder::addEncodedQueryParameter);

        List<String> trackIdList = new ArrayList<>();

        tracks.forEach(p -> trackIdList.add("spotify:track:" + p.getId()));

        Map<String, String[]> map = new HashMap<String, String[]>();

        map.put("uris", trackIdList.toArray(new String[] {}));

        String test = null;

        try {
            test = new ObjectMapper().writeValueAsString(map);
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        System.out.println(test);

        sendRequestV2(new Request.Builder().url(urlBuilder.build())
                .put(RequestBody.create(JSON, test))
                .addHeader(TOKEN_KEY, TOKEN_PREFIX + token)
                .build());
    }

    public PlayingItem getNowPlaying() {
        String url = MessageUtil.get("spotify.url.me.player.currentlyPlaying");

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        Map<String, String> params = new HashMap<>();
        params.put("market", "JP");
        params.forEach(urlBuilder::addEncodedQueryParameter);

        Request request =
            new Request.Builder().url(urlBuilder.build())
                    .addHeader(TOKEN_KEY, TOKEN_PREFIX + token)
                    .build();

        Map<String, String> map = sendRequestV2(request);

        ObjectMapper mapper = new ObjectMapper();
        PlayingItem t = new PlayingItem();
        t.setCode(Integer.valueOf(map.get("code")));

        try {
            t = mapper.readValue(map.get("body"), PlayingItem.class);
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        return t;
    }

    public void getUsersPlaylist() {
        String url = MessageUtil.get("spotify.url.user.playlists");

        final Request.Builder builder = new Request.Builder();

        //        Map<String, String> httpHeaderMap = new HashMap<>();
        //        httpHeaderMap.put("Authorization", "Bearer " + token.getToken());
        //        httpHeaderMap.forEach(builder::addHeader);

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        Map<String, String> params = new HashMap<>();
        params.put("limit", "5");
        params.put("offset", "0");
        params.put("market", "JP");
        params.forEach(urlBuilder::addEncodedQueryParameter);

        sendRequest(builder.url(urlBuilder.build())
                .addHeader("Authorization", TOKEN_PREFIX + token)
                .build()).ifPresent(p -> {
                    Map<String, Object> jsonMap = parseJsonNest(p);
                    System.out.println(p);
                });
    }

    public void refreshToken() {
        final String url = MessageUtil.get("spotify.url.api.refreshToken");

        final String source =
            new StringBuilder().append(clientID)
                    .append(":")
                    .append(clientSecret)
                    .toString();

        final String result =
            "Basic "
                + Base64.getEncoder()
                        .encodeToString(
                                source.getBytes(StandardCharsets.UTF_8));

        final FormBody.Builder formBuilder = new FormBody.Builder();

        Map<String, String> requestBodyMap = new HashMap<>();
        requestBodyMap.put("grant_type", "refresh_token");
        requestBodyMap.put("refresh_token", refreshToken);

        requestBodyMap.forEach(formBuilder::add);

        Map<String, Object> jsonMap =
            parseJsonNest(sendRequestV2(new Request.Builder().url(url)
                    .post(formBuilder.build())
                    .addHeader(TOKEN_KEY, result)
                    .build()).get("body"));

        String a_token = jsonMap.get("access_token").toString();
        System.out.println(a_token);

        token = a_token;
    }

    /**
     * return s device available on spotify specified by name.
     *
     * @param deviceName
     * @return
     */
    public SpotifyDevice device(String deviceName) {
        for (SpotifyDevice device : devices().getDevices()) {
            if (device.getName().equals(deviceName)) {
                return device;
            }
        }
        return new SpotifyDevice();
    }

    /**
     * return all devices available on spotify.
     *
     * @return
     */
    public Devices devices() {
        String url = MessageUtil.get("spotify.url.me.player.devices");

        Request request =
            new Request.Builder().url(url)
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader(TOKEN_KEY, TOKEN_PREFIX + token)
                    .build();

        Map<String, String> map = sendRequestV2(request);

        ObjectMapper mapper = new ObjectMapper();
        Devices t = new Devices();
        t.setCode(Integer.valueOf(map.get("code")));

        try {
            t = mapper.readValue(map.get("body"), Devices.class);
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        return t;
    }

    public void spotifyDevice() {
        String url = MessageUtil.get("spotify.url.me.player.devices");
        final Request.Builder builder = new Request.Builder();

        Map<String, String> httpHeaderMap = new HashMap<>();
        httpHeaderMap.put("Accept", "application/json");
        httpHeaderMap.put("Content-Type", "application/json");
        httpHeaderMap.put("Authorization", "Bearer " + token);
        httpHeaderMap.forEach(builder::addHeader);

        Request request = builder.url(url).build();

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            int responseCode = response.code();
            System.out.println("responseCode: " + responseCode);

            if (!response.isSuccessful()) {
                System.out.println("error!!");
                System.out.println("body: " + response.body().string());
            }
            if (response.body() != null) {
                System.out.println("body: " + response.body().string());
            }
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }
    }

    public static class Builder {
        private String token;

        private String refreshToken;

        private String code;

        private String clientID;

        private String clientSecret;

        public Jastify build() {
            return new Jastify(this);
        }

        public Builder token(String token) {
            this.refreshToken = token;
            return this;
        }

        public Builder code(String code) {
            this.code = code;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }

        public Builder clientID(String clientID) {
            this.clientID = clientID;
            return this;
        }

        public Builder clientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
            return this;
        }

        public Builder load(String fileName) {
            Properties properties = loadFile(fileName);

            token(properties.getProperty("token"))
                    .refreshToken(properties.getProperty("refreshToken"))
                    .clientSecret(properties.getProperty("clientSecret"))
                    .clientID(properties.getProperty("clientID"))
                    .code(properties.getProperty("code"));

            return this;
        }

        public Builder load() {
            return load("jastify.properties");
        }

        private static Properties loadFile(String resourceFile) {
            try (InputStream is =
                ClassLoader.getSystemResourceAsStream(resourceFile);
                    InputStreamReader isr =
                        new InputStreamReader(is, StandardCharsets.UTF_8);
                    BufferedReader reader = new BufferedReader(isr)) {
                Properties props = new Properties();
                props.load(reader);
                return props;
            } catch (IOException e) {
                System.out.println(String.format("failed to load %s"));
                throw new RuntimeException(e);
            }
        }

    }

}
