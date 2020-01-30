package jastify;

import java.util.Arrays;
import java.util.stream.Collectors;

import jastify.common.Const;
import jastify.dto.RelatedArtsits;
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

    /**
     * GET https://api.spotify.com/v1/artists/{id}/albums
     * 
     * @param artistId
     * @param include_groups
     * @param country
     * @param limit
     * @param offset
     * @return
     */
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

    /**
     * GET https://api.spotify.com/v1/artists/{id}/related-artists
     * 
     * @param artistID
     * @return
     */
    protected RelatedArtsits relatedArtists(String artistID) {
        final String url = JastifyUtils.get("artists.relatedArtists", artistID);

        return JastifyUtils.setResult(
                JastifyUtils.sendRequest(new Request.Builder().url(url)
                        .addHeader("Accept", "application/json")
                        .addHeader(Const.TOKEN_KEY, Const.TOKEN_PREFIX + token)
                        .build()),
                RelatedArtsits.class);
    }
}
