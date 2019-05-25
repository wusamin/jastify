package jastify;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jastify.base.SpotifyResponseBase;
import jastify.dto.Devices;
import jastify.dto.PlayingItem;
import jastify.dto.SearchResultAlbums;
import jastify.dto.SearchResultArtists;
import jastify.dto.SearchResultPlaylists;
import jastify.dto.SearchResultTracks;
import jastify.dto.SpotifyDevice;
import jastify.dto.SpotifyTrack;
import jastify.dto.UsersProfile;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Jastify {

    private String token;

    private String refreshToken;

    private String code;

    private String clientID;

    private String clientSecret;

    private String userID;

    private Jastify() {
    }

    private Jastify(Builder builder) {
        this.code = builder.code;
        this.token = builder.token;
        this.refreshToken = builder.refreshToken;
        this.clientID = builder.clientID;
        this.clientSecret = builder.clientSecret;
        this.userID = builder.userID;
    }

    private static final String JOIN = "%20";

    private static final String TOKEN_PREFIX = "Bearer ";

    private static final String TOKEN_KEY = "Authorization";

    private static final MediaType JSON =
        MediaType.parse("application/json; charset=utf-8");

    private Map<String, String> search(String[] searchWords, String market,
            int limit, String type) {
        final String url = JastifyUtils.get("spotify.url.api.search");

        return JastifyUtils
                .sendRequestV2(new Request.Builder()
                        .url(HttpUrl.parse(url)
                                .newBuilder()
                                .addEncodedQueryParameter("q",
                                        Arrays.stream(searchWords)
                                                .collect(Collectors
                                                        .joining(JOIN)))
                                .addEncodedQueryParameter("type", type)
                                .addEncodedQueryParameter("market", market)
                                .addEncodedQueryParameter("limit",
                                        String.valueOf(limit))
                                .build())
                        .addHeader(TOKEN_KEY, TOKEN_PREFIX + token)
                        .build());
    }

    private <T extends SpotifyResponseBase> T setResult(
            Map<String, String> apiResult, Class<T> clazz) {

        T dto = null;
        try {
            dto = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e1) {
            // TODO 自動生成された catch ブロック
            e1.printStackTrace();
            return dto;
        }

        dto.setCode(Integer.valueOf(apiResult.get("code")));

        try {
            dto = new ObjectMapper().readValue(apiResult.get("body"), clazz);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return dto;
    }

    public SearchResultTracks searchTracks(String[] searchWords, String market,
            int limit) {
        return setResult(search(searchWords, market, limit, "track"),
                SearchResultTracks.class);
    }

    public SearchResultAlbums searchAlbums(String[] searchWords, String market,
            int limit) {
        return setResult(search(searchWords, market, limit, "album"),
                SearchResultAlbums.class);
    }

    public SearchResultArtists searchArtists(String[] searchWords,
            String market, int limit) {
        return setResult(search(searchWords, market, limit, "artist"),
                SearchResultArtists.class);
    }

    public SearchResultPlaylists searchPlaylists(String[] searchWords,
            String market, int limit) {
        return setResult(search(searchWords, market, limit, "playlist"),
                SearchResultPlaylists.class);
    }

    public void startMusic(String deviceId) {
        String url = JastifyUtils.get("spotify.url.me.player.startPlayback");

        JastifyUtils
                .sendRequestV2(
                        new Request.Builder()
                                .url(HttpUrl.parse(url)
                                        .newBuilder()
                                        .addEncodedQueryParameter("device_id",
                                                deviceId)
                                        .build())
                                .put(new FormBody.Builder().build())
                                .addHeader(TOKEN_KEY, TOKEN_PREFIX + token)
                                .build());
    }

    /**
     * Set device's volume.
     *
     * @param volumePercent 0-100
     * @param device
     * @return
     */
    public void setVolume(int volumePercent, SpotifyDevice device) {

        final String url = JastifyUtils.get("spotify.url.me.player.setVolume");

        JastifyUtils.sendRequestV2(new Request.Builder()
                .url(HttpUrl.parse(url)
                        .newBuilder()
                        .addEncodedQueryParameter("volume_percent",
                                String.valueOf(volumePercent))
                        .addEncodedQueryParameter("device_id", device.getId())
                        .build())
                .put(RequestBody.create(null, new byte[] {}))
                .addHeader(TOKEN_KEY, TOKEN_PREFIX + token)
                .build());

    }

    public void playTracks(SpotifyDevice device, List<SpotifyTrack> tracks) {
        String url = JastifyUtils.get("spotify.url.me.player.startPlayback");

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

        JastifyUtils
                .sendRequestV2(new Request.Builder()
                        .url(HttpUrl.parse(url)
                                .newBuilder()
                                .addEncodedQueryParameter("device_id",
                                        device.getId())
                                .build())
                        .put(RequestBody.create(JSON, test))
                        .addHeader(TOKEN_KEY, TOKEN_PREFIX + token)
                        .build());
    }

    public PlayingItem getNowPlaying() {
        String url = JastifyUtils.get("spotify.url.me.player.currentlyPlaying");

        Request request =
            new Request.Builder()
                    .url(HttpUrl.parse(url)
                            .newBuilder()
                            .addEncodedQueryParameter("market", "JP")
                            .build())
                    .addHeader(TOKEN_KEY, TOKEN_PREFIX + token)
                    .build();

        return setResult(JastifyUtils.sendRequestV2(request),
                PlayingItem.class);
    }

    public void getUsersPlaylist() {
        String url = JastifyUtils.get("spotify.url.user.playlists");

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        Map<String, String> params = new HashMap<>();
        params.put("limit", "5");
        params.put("offset", "0");
        params.put("market", "JP");
        params.forEach(urlBuilder::addEncodedQueryParameter);

        JastifyUtils.sendRequest(new Request.Builder().url(urlBuilder.build())
                .addHeader("Authorization", TOKEN_PREFIX + token)
                .build()).ifPresent(p -> {
                    Map<String, Object> jsonMap = JastifyUtils.parseJsonNest(p);
                    System.out.println(p);
                });
    }

    /**
     * Refresh the access token.
     */
    public void refreshToken() {
        final String url = JastifyUtils.get("spotify.url.api.refreshToken");

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

        Map<String, Object> jsonMap =
            JastifyUtils.parseJsonNest(
                    JastifyUtils.sendRequestV2(new Request.Builder().url(url)
                            .post(new FormBody.Builder()
                                    .add("grant_type", "refresh_token")
                                    .add("refresh_token", refreshToken)
                                    .build())
                            .addHeader(TOKEN_KEY, result)
                            .build()).get("body"));

        //        System.out.println(a_token);

        token = jsonMap.get("access_token").toString();
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
        String url = JastifyUtils.get("spotify.url.me.player.devices");

        Request request =
            new Request.Builder().url(url)
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader(TOKEN_KEY, TOKEN_PREFIX + token)
                    .build();

        Map<String, String> map = JastifyUtils.sendRequestV2(request);

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

    /**
     * Get usersProfile of user that this instance.
     *
     * @return
     */
    public UsersProfile usersProfile() {
        return usersProfile(userID);
    }

    /**
     * Get usersProfile of user specified by user id.
     *
     * @param userID
     * @return
     */
    public UsersProfile usersProfile(String userID) {
        String url = JastifyUtils.get("spotify.url.user", userID);

        Map<String, String> map =
            JastifyUtils.sendRequestV2(new Request.Builder().url(url)
                    .addHeader("Accept", "application/json")
                    .addHeader("user_id", userID)
                    .addHeader(TOKEN_KEY, TOKEN_PREFIX + token)
                    .build());

        UsersProfile t = new UsersProfile();
        t.setCode(Integer.valueOf(map.get("code")));

        try {
            t =
                new ObjectMapper().readValue(map.get("body"),
                        UsersProfile.class);
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }

        return t;
    }

    //    public Category category() {
    //        String url = MessageUtil.get("spotify.url.me.player.devices");
    //
    //        Request request =
    //            new Request.Builder().url(url)
    //                    .addHeader("Accept", "application/json")
    //                    .addHeader("Content-Type", "application/json")
    //                    .addHeader(TOKEN_KEY, TOKEN_PREFIX + token)
    //                    .build();
    //
    //        Map<String, String> map = sendRequestV2(request);
    //
    //        ObjectMapper mapper = new ObjectMapper();
    //        Devices t = new Devices();
    //        t.setCode(Integer.valueOf(map.get("code")));
    //
    //        try {
    //            t = mapper.readValue(map.get("body"), Devices.class);
    //        } catch (IOException e) {
    //            // TODO 自動生成された catch ブロック
    //            e.printStackTrace();
    //        }
    //
    //        return t;
    //    }

    public static class Builder {
        private String token;

        private String refreshToken;

        private String code;

        private String clientID;

        private String clientSecret;

        private String userID;

        public Jastify build() {
            return new Jastify(this);
        }

        public Builder token(String token) {
            this.refreshToken = token;
            return this;
        }

        public Builder userID(String userID) {
            this.userID = userID;
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

        /**
         * load paramters from jastify.properties.
         *
         * @return
         */
        public Builder load() {
            return load("jastify");
        }

        /**
         * load parameters from .properties file specified.
         *
         * @param fileName
         * @return
         */
        public Builder load(String fileName) {
            ResourceBundle bundle = null;
            try {
                bundle =
                    ResourceBundle.getBundle(fileName,
                            ResourceBundle.Control.getControl(
                                    ResourceBundle.Control.FORMAT_DEFAULT));
            } catch (MissingResourceException e) {
                e.printStackTrace();
                throw new RuntimeErrorException(null, e.getMessage());
            }
            token(bundle.getString("spotify.token"))
                    .refreshToken(bundle.getString("spotify.refreshToken"))
                    .clientSecret(bundle.getString("spotify.clientSecret"))
                    .clientID(bundle.getString("spotify.clientID"))
                    .code(bundle.getString("spotify.code"))
                    .userID(bundle.getString("spotify.userID"));
            return this;
        }

    }

}
