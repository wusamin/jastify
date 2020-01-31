package jastify;

import java.util.Arrays;
import java.util.stream.Collectors;

import jastify.common.Const;
import jastify.dto.AlbumList;
import lombok.Setter;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class BrowseService {
    @Setter
    private String token;

    public BrowseService(String token) {
        this.token = token;
    }

    /**
     * GET https://api.spotify.com/v1/recommendations
     */
    public void getRecommendeations(int limit, String market,
            String seedArtists, String seedGenres) {
        final String url = JastifyUtils.get("browse.getReccomendations");
        return JastifyUtils
                .setResult(JastifyUtils.sendRequest(new Request.Builder()
                        .url(HttpUrl.parse(url)
                                .newBuilder()
                                .addEncodedQueryParameter("ids",
                                        Arrays.stream(ids)
                                                .collect(Collectors
                                                        .joining(",")))
                                .addEncodedQueryParameter("market", market)
                                .build())
                        .addHeader(Const.TOKEN_KEY, Const.TOKEN_PREFIX + token)
                        .build()), AlbumList.class);
    }
}
