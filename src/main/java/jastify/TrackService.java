package jastify;

import jastify.common.Const;
import jastify.dto.AudioFeature;
import jastify.dto.AudioFeatures;
import lombok.Setter;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class TrackService {
    @Setter
    private String token;

    public TrackService(String token) {
        this.token = token;
    }

    /**
     * GET https://api.spotify.com/v1/tracks
     */
    public void getTracks() {

    }

    /**
     * GET https://api.spotify.com/v1/audio-features/{id}
     * 
     * @param trackId
     * @return
     */
    public AudioFeature getAudioFeature(String trackId) {
        final String url = JastifyUtils.get("tracks.getAudioFeatures", trackId);

        return JastifyUtils.setResult(
                JastifyUtils.sendRequest(new Request.Builder().url(url)
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")
                        .addHeader(Const.TOKEN_KEY, Const.TOKEN_PREFIX + token)
                        .build()),
                AudioFeature.class);
    }

    /**
     * GET https://api.spotify.com/v1/audio-features
     * 
     * @param trackIds
     */
    public AudioFeatures getAudioFeatures(String[] trackIds) {
        final String url =
            JastifyUtils.get("tracks.getAudioFeaturesForSeveralTracks");

        var httpUrl =
            HttpUrl.parse(url)
                    .newBuilder()
                    .addEncodedQueryParameter("ids", String.join(",", trackIds))
                    .build();

        var request =
            new Request.Builder().url(httpUrl)
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .addHeader(Const.TOKEN_KEY, Const.TOKEN_PREFIX + token)
                    .build();

        return JastifyUtils.setResult(JastifyUtils.sendRequest(request),
                AudioFeatures.class);

    }
}
