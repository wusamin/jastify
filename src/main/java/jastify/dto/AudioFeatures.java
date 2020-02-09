package jastify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jastify.dto.base.SpotifyResponseBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AudioFeatures extends SpotifyResponseBase {

    @JsonProperty("duration_ms")
    private int durationMs;

    private int key;

    private int mode;

    @JsonProperty("time_signature")
    private int timeSignature;

    private float acousticness;

    private float danceability;

    private float energy;

    private float instrumentalness;

    private float liveness;

    private float loudness;

    private float speechiness;

    private float valence;

    private float tempo;

    private String id;

    private String uri;

    @JsonProperty("track_href")
    private String trackHref;

    @JsonProperty("analysis_url")
    private String analysisUrl;

    private String type;
}
