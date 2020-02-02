package jastify;

import java.util.Arrays;
import java.util.stream.Collectors;

import jastify.common.Const;
import jastify.dto.RecommendationsResponse;
import jastify.parameter.TuneableTrack;
import lombok.Setter;
import okhttp3.HttpUrl;
import okhttp3.HttpUrl.Builder;
import okhttp3.Request;

public class BrowseService {
    @Setter
    private String token;

    public BrowseService(String token) {
        this.token = token;
    }

    protected void getCategory(String categoryId, String locale) {

    }

    /**
     * GET https://api.spotify.com/v1/recommendations
     */
    public RecommendationsResponse getRecommendeations(String market,
            String[] seedTracks, String[] seedArtists, String[] seedGenres,
            TuneableTrack target, TuneableTrack max, TuneableTrack min,
            int limit) {
        final String url = JastifyUtils.get("browse.getReccomendations");

        Builder b = HttpUrl.parse(url).newBuilder();

        target.toMap().forEach((k, v) -> {
            b.addEncodedQueryParameter(k, v);
        });

        max.toMap().forEach((k, v) -> {
            b.addEncodedQueryParameter(k, v);
        });

        min.toMap().forEach((k, v) -> {
            b.addEncodedQueryParameter(k, v);
        });

        if (0 < limit) {
            b.addEncodedQueryParameter("limit", String.valueOf(limit));
        }

        if (market != null && !market.isEmpty()) {
            b.addEncodedQueryParameter("market", market);
        }

        if (seedArtists != null && seedArtists.length != 0) {
            b.addEncodedQueryParameter("seed_artists",
                    Arrays.stream(seedArtists)
                            .collect(Collectors.joining(",")));
        }

        if (seedGenres != null && seedGenres.length != 0) {
            b.addEncodedQueryParameter("seed_genres",
                    Arrays.stream(seedGenres).collect(Collectors.joining(",")));
        }

        if (seedTracks != null && seedTracks.length != 0) {
            b.addEncodedQueryParameter("seed_tracks",
                    Arrays.stream(seedTracks).collect(Collectors.joining(",")));
        }

        return JastifyUtils
                .setResult(
                        JastifyUtils.sendRequest(
                                new Request.Builder().url(b.build())
                                        .addHeader(Const.TOKEN_KEY,
                                                Const.TOKEN_PREFIX + token)
                                        .build()),
                        RecommendationsResponse.class);
    }
}
