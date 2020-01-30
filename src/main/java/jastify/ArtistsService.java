package jastify;

import java.util.Arrays;
import java.util.stream.Collectors;

import jastify.common.Const;
import jastify.dto.SpotifySearchAlbums;
import lombok.Setter;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class ArtistsService {
    @Setter
    private String token;

    public ArtistsService(String token) {
        this.token = token;
    }

    protected SpotifySearchAlbums getArtistsAlbums(String artistId,
            String[] include_groups, String country, int limit, int offset) {
        final String url =
            JastifyUtils.get("artists.getArtsitsAlbums", artistId);

        return JastifyUtils
                .setResult(JastifyUtils.sendRequest(new Request.Builder()
                        .url(HttpUrl.parse(url)
                                .newBuilder()
                                .addEncodedQueryParameter("include_groups",
                                        Arrays.stream(include_groups)
                                                .collect(Collectors
                                                        .joining(",")))
                                .addEncodedQueryParameter("country", country)
                                .addEncodedQueryParameter("limit",
                                        String.valueOf(limit))
                                .addEncodedQueryParameter("offset",
                                        String.valueOf(offset))
                                .build())
                        .addHeader(Const.TOKEN_KEY, Const.TOKEN_PREFIX + token)
                        .build()), SpotifySearchAlbums.class);
    }
}
