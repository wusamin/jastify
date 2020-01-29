package jastify;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import jastify.dto.SearchResultAlbums;
import jastify.dto.SearchResultArtists;
import jastify.dto.SearchResultPlaylists;
import jastify.dto.SearchResultTracks;
import lombok.Setter;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class Search {
    @Setter
    private String token;

    public Search(String token) {
        this.token = token;
    }

    protected Map<String, String> search(String[] searchWords, String market,
            int limit, int offset, String type) {
        final String url = JastifyUtils.get("api.search");

        return JastifyUtils
                .sendRequest(new Request.Builder()
                        .url(HttpUrl.parse(url)
                                .newBuilder()
                                .addEncodedQueryParameter("q",
                                        Arrays.stream(searchWords)
                                                .collect(Collectors
                                                        .joining(Const.JOIN)))
                                .addEncodedQueryParameter("type", type)
                                .addEncodedQueryParameter("market", market)
                                .addEncodedQueryParameter("limit",
                                        String.valueOf(limit))
                                .addEncodedQueryParameter("offset",
                                        String.valueOf(offset))
                                .build())
                        .addHeader(Const.TOKEN_KEY, Const.TOKEN_PREFIX + token)
                        .build());
    }

    protected SearchResultTracks searchTracks(String[] searchWords,
            String market, int limit, int offset) {
        return JastifyUtils.setResult(
                search(searchWords, market, limit, offset, "track"),
                SearchResultTracks.class);
    }

    protected SearchResultAlbums searchAlbums(String[] searchWords,
            String market, int limit, int offset) {
        return JastifyUtils.setResult(
                search(searchWords, market, limit, offset, "album"),
                SearchResultAlbums.class);
    }

    protected SearchResultArtists searchArtists(String[] searchWords,
            String market, int limit, int offset) {
        return JastifyUtils.setResult(
                search(searchWords, market, limit, offset, "artist"),
                SearchResultArtists.class);
    }

    protected SearchResultPlaylists searchPlaylists(String[] searchWords,
            String market, int limit, int offset) {
        return JastifyUtils.setResult(
                search(searchWords, market, limit, offset, "playlist"),
                SearchResultPlaylists.class);
    }
}
