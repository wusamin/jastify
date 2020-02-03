package jastify;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import jastify.common.Const;
import jastify.dto.Playlist;
import lombok.Setter;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PlaylistService {

    @Setter
    private String token;

    public PlaylistService(String token) {
        this.token = token;
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
}
