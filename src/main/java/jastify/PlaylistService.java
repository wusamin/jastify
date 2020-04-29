package jastify;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import jastify.common.Const;
import jastify.dto.Playlist;
import jastify.dto.PlaylistTracks;
import jastify.dto.Playlists;
import jastify.dto.Snapshot;
import jastify.parameter.PlaylistParameter;
import lombok.Setter;
import okhttp3.HttpUrl;
import okhttp3.HttpUrl.Builder;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PlaylistService {

    @Setter
    private String token;

    public PlaylistService(String token) {
        this.token = token;
    }

    /**
     * GET https://api.spotify.com/v1/me/playlists
     * 
     * @param limit Default:20, Minimum:1, Maximum:50.
     * @param offset
     * @return
     */
    public Playlists getCunrretUsersPlaylists(int limit, int offset) {
        final String url =
            JastifyUtils.get("playlists.getCurrentUsersPlaylists");

        Builder b = HttpUrl.parse(url).newBuilder();

        if (1 <= limit && limit <= 50) {
            b.addEncodedQueryParameter("limit", String.valueOf(limit));
        }

        if (1 <= offset) {
            b.addEncodedQueryParameter("offset", String.valueOf(offset));
        }

        return JastifyUtils
                .setResult(
                        JastifyUtils.sendRequest(
                                new Request.Builder().url(b.build())
                                        .addHeader(Const.TOKEN_KEY,
                                                Const.TOKEN_PREFIX + token)
                                        .build()),
                        Playlists.class);
    }

    /**
     * POST https://api.spotify.com/v1/playlists/{playlist_id}/tracks
     * 
     * @param playlistId
     * @param tracks
     * @param position
     * @return
     */
    public Snapshot addTracksToPlaylist(String playlistId, String[] tracks,
            int position) {

        if (100 < tracks.length) {
            throw new RuntimeException(
                    "Number of track is not allowed over 100.");
        }

        final String url = JastifyUtils.get("playlists.addTracks", playlistId);

        String[] spotifiedTracks =
            Arrays.stream(tracks)
                    .map(p -> "spotify:track:" + p)
                    .toArray(String[]::new);

        Map<String, Object> map = new HashMap<>();
        map.put("uris", spotifiedTracks);

        if (0 <= position) {
            map.put("position", position);
        }

        String rb = null;

        try {
            rb = new ObjectMapper().writeValueAsString(map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return JastifyUtils.setResult(
                JastifyUtils.sendRequest(new Request.Builder().url(url)
                        .post(RequestBody.create(Const.JSON, rb))
                        .addHeader(Const.TOKEN_KEY, Const.TOKEN_PREFIX + token)
                        .addHeader("Content-Type", "application/json")
                        .build()),
                Snapshot.class);
    }

    /**
     * PUT https://api.spotify.com/v1/playlists/{playlist_id}
     * 
     * @param playlistId
     * @param param
     * @return
     */
    public void changePlaylistDetail(String playlistId,
            PlaylistParameter param) {
        final String url =
            JastifyUtils.get("playlists.changePlaylistDetail", playlistId);

        String rb = null;

        try {
            rb = new ObjectMapper().writeValueAsString(param.toMap());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JastifyUtils.sendRequest(new Request.Builder().url(url)
                .put(RequestBody.create(Const.JSON, rb))
                .addHeader(Const.TOKEN_KEY, Const.TOKEN_PREFIX + token)
                .addHeader("Content-Type", "application/json")
                .build());
    }

    /**
     * POST https://api.spotify.com/v1/users/{user_id}/playlists
     */
    public Playlist createPlayList(String userId, String name, boolean isPublic,
            boolean collaborative, String description) {
        final String url = JastifyUtils.get("playlists.createPlaylist", userId);

        Map<String, String> map = new HashMap<>();

        map.put("name", name);
        map.put("public", String.valueOf(isPublic));
        map.put("collaborative", String.valueOf(collaborative));

        if (description != null && !description.isEmpty()) {
            map.put("description", description);
        }

        String rb = null;

        try {
            rb = new ObjectMapper().writeValueAsString(map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return JastifyUtils.setResult(
                JastifyUtils.sendRequest(new Request.Builder().url(url)
                        .post(RequestBody.create(Const.JSON, rb))
                        .addHeader(Const.TOKEN_KEY, Const.TOKEN_PREFIX + token)
                        .addHeader("Content-Type", "application/json")

                        .build()),
                Playlist.class);
    }

    /**
     * DELETE https://api.spotify.com/v1/playlists/{playlist_id}/tracks
     * 
     * @param playlistId
     * @param tracks
     * @return
     */
    public Snapshot deleteTracks(String playlistId, String[] tracks) {
        final String url =
            JastifyUtils.get("playlists.removeTracks", playlistId);

        List<Map<String, String>> spotifiedTracks =
            Arrays.stream(tracks).map(p -> {
                Map<String, String> m = new HashMap<>();
                m.put("uri", "spotify:track:" + p);
                return m;
            }).collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("tracks", spotifiedTracks);

        String rb = null;

        try {
            rb = new ObjectMapper().writeValueAsString(map);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return JastifyUtils.setResult(
                JastifyUtils.sendRequest(new Request.Builder().url(url)
                        .delete(RequestBody.create(Const.JSON, rb))
                        .addHeader(Const.TOKEN_KEY, Const.TOKEN_PREFIX + token)
                        .addHeader("Content-Type", "application/json")
                        .build()),
                Snapshot.class);
    }

    /**
     * GET https://api.spotify.com/v1/playlists/{playlist_id}/tracks
     * 
     * @param playlistId
     * @return
     */
    public PlaylistTracks getPlaylistTracks(String playlistId, String fileds,
            int limit, int offset, String market) {
        final String url = JastifyUtils.get("playlists.getTracks", playlistId);

        Builder b = HttpUrl.parse(url).newBuilder();

        if (fileds != null && !fileds.isEmpty()) {
            b.addEncodedQueryParameter("fileds", "");
        }

        if (market != null && !market.isEmpty()) {
            b.addEncodedQueryParameter("market", market);
        }

        if (0 < limit) {
            b.addEncodedQueryParameter("limit", String.valueOf(limit));
        }

        if (0 < offset) {
            b.addEncodedQueryParameter("offset", String.valueOf(offset));
        }

        return JastifyUtils
                .setResult(
                        JastifyUtils.sendRequest(
                                new Request.Builder().url(b.build())
                                        .addHeader(Const.TOKEN_KEY,
                                                Const.TOKEN_PREFIX + token)
                                        .build()),
                        PlaylistTracks.class);
    }
}
