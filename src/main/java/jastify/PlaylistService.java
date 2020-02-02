package jastify;

import jastify.common.Const;
import jastify.dto.Playlist;
import lombok.Setter;
import okhttp3.FormBody;
import okhttp3.Request;

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

        return JastifyUtils.setResult(
                JastifyUtils.sendRequest(new Request.Builder().url(url)
                        .post(new FormBody.Builder().add("name", name)
                                .add("public", String.valueOf(isPublic))
                                .add("collaborative", String.valueOf(isPublic))
                                .add("description", description)
                                .build())
                        .addHeader(Const.TOKEN_KEY, Const.TOKEN_PREFIX + token)
                        .build()),
                Playlist.class);

    }
}
