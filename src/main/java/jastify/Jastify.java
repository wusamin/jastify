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

import com.fasterxml.jackson.databind.ObjectMapper;

import jastify.common.Const;
import jastify.common.JastifyEnums.IncludeGroups;
import jastify.dto.Album;
import jastify.dto.AlbumList;
import jastify.dto.AlbumSimplified;
import jastify.dto.Artist;
import jastify.dto.ArtistSimplified;
import jastify.dto.Category;
import jastify.dto.Device;
import jastify.dto.Devices;
import jastify.dto.PlayingItem;
import jastify.dto.Playlist;
import jastify.dto.RecommendationsResponse;
import jastify.dto.RelatedArtsits;
import jastify.dto.SearchResultAlbums;
import jastify.dto.SearchResultArtists;
import jastify.dto.SearchResultPlaylists;
import jastify.dto.SearchResultTracks;
import jastify.dto.Snapshot;
import jastify.dto.SpotifySearchAlbums;
import jastify.dto.Track;
import jastify.dto.UsersPlaylists;
import jastify.dto.UsersProfile;
import jastify.parameter.TuneableTrack;
import lombok.Getter;
import lombok.Setter;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

@Getter
public class Jastify {

    @Setter
    private boolean debug = false;

    private String token;

    private String refreshToken;

    private String code;

    private String clientID;

    private String clientSecret;

    private String userID;

    private SearchService search;

    private PlayerService player;

    private ArtistsService artsits;

    private AlbumsService album;

    private BrowseService browse;

    private PlaylistService playlist;

    private Jastify() {
    }

    private Jastify(Builder builder) {
        this.code = builder.code;
        this.token = builder.token;
        this.refreshToken = builder.refreshToken;
        this.clientID = builder.clientID;
        this.clientSecret = builder.clientSecret;
        this.userID = builder.userID;

        search = new SearchService(token);
        player = new PlayerService(token);
        artsits = new ArtistsService(token);
        album = new AlbumsService(token);
        browse = new BrowseService(token);
        playlist = new PlaylistService(token);
    }

    private void distributeToken(String token) {
        search.setToken(token);
        player.setToken(token);
        artsits.setToken(token);
        album.setToken(token);
        browse.setToken(token);
        playlist.setToken(token);
    }

    public Map<String, String> search(String[] searchWords, String market,
            int limit, int offset, String type) {
        return search.search(searchWords, market, limit, offset, type);

    }

    /**
     * search tracks.
     * 
     * @param searchWords
     * @param market
     * @param limit
     * @param offset
     * @return
     */
    public SearchResultTracks searchTracks(String[] searchWords, String market,
            int limit, int offset) {
        return search.searchTracks(searchWords, market, limit, offset);
    }

    /**
     * search albums.
     * 
     * @param searchWords
     * @param market
     * @param limit
     * @param offset
     * @return
     */
    public SearchResultAlbums searchAlbums(String[] searchWords, String market,
            int limit, int offset) {
        return search.searchAlbums(searchWords, market, limit, offset);
    }

    /**
     * search artists.
     * 
     * @param searchWords
     * @param market
     * @param limit
     * @param offset
     * @return
     */
    public SearchResultArtists searchArtists(String[] searchWords,
            String market, int limit, int offset) {
        return search.searchArtists(searchWords, market, limit, offset);
    }

    /**
     * search playlists.
     * 
     * @param searchWords
     * @param market
     * @param limit
     * @param offset
     * @return
     */
    public SearchResultPlaylists searchPlaylists(String[] searchWords,
            String market, int limit, int offset) {
        return search.searchPlaylists(searchWords, market, limit, offset);
    }

    /**
     * Resume playback.
     * 
     * @param deviceId
     */
    public void startMusic(String deviceId) {
        player.startMusic(deviceId);
    }

    /**
     * Set device's volume.
     *
     * @param volumePercent 0-100
     * @param device
     * @return
     */
    public void setVolume(int volumePercent, Device device) {
        player.setVolume(volumePercent, device);
    }

    /**
     * Start playback with specified tracks.
     * 
     * @param device
     * @param tracks
     */
    public void playTracks(Device device, List<Track> tracks) {
        String url = JastifyUtils.get("me.player.startPlayback");

        List<String> trackIdList = new ArrayList<>();

        tracks.forEach(p -> trackIdList.add("spotify:track:" + p.getId()));

        Map<String, String[]> map = new HashMap<String, String[]>();

        map.put("uris", trackIdList.toArray(new String[] {}));

        String test = null;

        try {
            test = new ObjectMapper().writeValueAsString(map);
        } catch (IOException e) {
            //            e.printStackTrace();
            throw new RuntimeException(e);
        }

        JastifyUtils
                .sendRequest(new Request.Builder()
                        .url(HttpUrl.parse(url)
                                .newBuilder()
                                .addEncodedQueryParameter("device_id",
                                        device.getId())
                                .build())
                        .put(RequestBody.create(Const.JSON, test))
                        .addHeader(Const.TOKEN_KEY, Const.TOKEN_PREFIX + token)
                        .build());
    }

    /**
     * Get infomation nowplaying track.
     * 
     * @return
     */
    public PlayingItem getNowPlaying() {
        final String url = JastifyUtils.get("me.player.currentlyPlaying");

        Request request =
            new Request.Builder()
                    .url(HttpUrl.parse(url)
                            .newBuilder()
                            .addEncodedQueryParameter("market", "JP")
                            .build())
                    .addHeader(Const.TOKEN_KEY, Const.TOKEN_PREFIX + token)
                    .build();

        return JastifyUtils.setResult(JastifyUtils.sendRequest(request),
                PlayingItem.class);
    }

    public UsersPlaylists usersPlaylist(int limit, int offset, String market) {
        final String url = JastifyUtils.get("user.playlists");

        return JastifyUtils
                .setResult(
                        JastifyUtils.sendRequest(new Request.Builder()
                                .url(HttpUrl.parse(url)
                                        .newBuilder()
                                        .addEncodedQueryParameter("market",
                                                market)
                                        .addEncodedQueryParameter("offset",
                                                String.valueOf(limit))
                                        .addEncodedQueryParameter("offset",
                                                String.valueOf(offset))
                                        .build())
                                .addHeader("Authorization",
                                        Const.TOKEN_PREFIX + token)

                                .build()),
                        UsersPlaylists.class);
    }

    /**
     * Refresh the access token.
     */
    public String refreshToken() {
        final String url = JastifyUtils.get("api.refreshToken");

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
                    JastifyUtils.sendRequest(new Request.Builder().url(url)
                            .post(new FormBody.Builder()
                                    .add("grant_type", "refresh_token")
                                    .add("refresh_token", refreshToken)
                                    .build())
                            .addHeader(Const.TOKEN_KEY, result)
                            .build()).get("body"));

        //        System.out.println(a_token);

        token = jsonMap.get("access_token").toString();

        distributeToken(token);

        return token;
    }

    /**
     * return s device available on Spotify specified by name.
     *
     * @param deviceName
     * @return
     */
    public Device device(String deviceName) {
        for (Device device : devices().getDevices()) {
            if (device.getName().equals(deviceName)) {
                return device;
            }
        }
        return new Device();
    }

    /**
     * return all devices available on Spotify.
     *
     * @return
     */
    public Devices devices() {
        return player.devices();
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
        String url = JastifyUtils.get("user", userID);

        return JastifyUtils.setResult(
                JastifyUtils.sendRequest(new Request.Builder().url(url)
                        .addHeader("Accept", "application/json")
                        .addHeader("user_id", userID)
                        .addHeader(Const.TOKEN_KEY, Const.TOKEN_PREFIX + token)
                        .build()),
                UsersProfile.class);
    }

    /**
     * add tracks to playlist.
     * 
     * @param playlistId
     * @param tracks
     * @param position
     * @return
     */
    public Snapshot addTracksToPlaylist(String playlistId, String[] tracks,
            int position) {
        return playlist.addTracksToPlaylist(playlistId, tracks, position);
    }

    /**
     * create a playlist.
     * 
     * @param userId
     * @param name
     * @param isPublic
     * @param collaborative
     * @param description
     * @return
     */
    public Playlist createPlayList(String userId, String name, boolean isPublic,
            boolean collaborative, String description) {
        return playlist.createPlayList(userId,
                name,
                isPublic,
                collaborative,
                description);
    }

    /**
     * get albums infomation specified ids.
     * 
     * @param ids
     * @param market
     * @return
     */
    public AlbumList getAlbums(String[] ids, String market) {
        return album.getAlbums(ids, market);
    }

    /**
     * get albums infomation specified albums.
     * 
     * @param albums
     * @param market
     * @return
     */
    public AlbumList getAlbums(Album[] albums, String market) {
        String[] ids =
            Arrays.stream(albums).map(Album::getId).toArray(String[]::new);
        return album.getAlbums(ids, market);
    }

    /**
     * get albums infomation specified albums.
     * 
     * @param albums
     * @param market
     * @return
     */
    public AlbumList getAlbums(AlbumSimplified[] albums, String market) {
        String[] ids =
            Arrays.stream(albums)
                    .map(AlbumSimplified::getId)
                    .toArray(String[]::new);
        return album.getAlbums(ids, market);
    }

    /**
     * get album list specified artist,
     * 
     * @param artist
     * @param includeGroups
     * @param country
     * @param limit
     * @param offset
     * @return
     */
    public SpotifySearchAlbums getArtistsAlbums(ArtistSimplified artist,
            IncludeGroups[] includeGroups, String country, int limit,
            int offset) {
        String[] array =
            Arrays.stream(includeGroups)
                    .map(IncludeGroups::getName)
                    .toArray(String[]::new);
        return artsits.getArtistsAlbums(artist
                .getId(), array, country, limit, offset);
    }

    /**
     * get album list specified artist,
     * 
     * @param artist
     * @param includeGroups
     * @param country
     * @param limit
     * @param offset
     * @return
     */
    public SpotifySearchAlbums getArtistsAlbums(Artist artist,
            IncludeGroups[] includeGroups, String country, int limit,
            int offset) {
        String[] array =
            Arrays.stream(includeGroups)
                    .map(IncludeGroups::getName)
                    .toArray(String[]::new);

        return artsits.getArtistsAlbums(artist
                .getId(), array, country, limit, offset);
    }

    /**
     * get artists related for artist.
     * 
     * @param artist
     * @return
     */
    public RelatedArtsits relatedArtists(ArtistSimplified artist) {
        return relatedArtists(artist.getId());
    }

    /**
     * get artists related for artist.
     * 
     * @param artist
     * @return
     */
    public RelatedArtsits relatedArtists(String artistID) {
        return artsits.relatedArtists(artistID);
    }

    /**
     * get recomendations.
     * 
     * @param market
     * @param seedTracks
     * @param seedArtists
     * @param seedGenres
     * @param target
     * @param max
     * @param min
     * @param limit
     * @return
     */
    public RecommendationsResponse getRecommendations(String market,
            String[] seedTracks, String[] seedArtists, String[] seedGenres,
            TuneableTrack target, TuneableTrack max, TuneableTrack min,
            int limit) {
        return browse.getRecommendeations(market,
                seedTracks,
                seedArtists,
                seedGenres,
                target,
                max,
                min,
                limit);
    }

    public Category category() {
        String url = JastifyUtils.get("me.player.devices");

        Request request =
            new Request.Builder().url(url)
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader(Const.TOKEN_KEY, Const.TOKEN_PREFIX + token)
                    .build();

        Map<String, String> map = JastifyUtils.sendRequest(request);

        ObjectMapper mapper = new ObjectMapper();
        Category t = new Category();
        //        t.setCode(Integer.valueOf(map.get("code")));

        try {
            t = mapper.readValue(map.get("body"), Category.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return t;
    }

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
                throw new RuntimeException(e);
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
