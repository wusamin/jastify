package jastify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jastify.base.SpotifyResponseBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayingItem extends SpotifyResponseBase {
    private String timestamp;

    private SpotifyContext context;

    @JsonProperty("progress_ms")
    private int progressMs;

    private SpotifyTrack item;

    @JsonProperty("currently_playing_type")
    private String currentlyPlayingType;

    private SpotifyAction actions;

    @JsonProperty("is_playing")
    private boolean isPlaying;
}
