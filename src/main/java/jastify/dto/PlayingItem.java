package jastify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jastify.dto.base.SpotifyResponseBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PlayingItem extends SpotifyResponseBase {
    private String timestamp;

    private Context context;

    @JsonProperty("progress_ms")
    private int progressMs;

    private Track item;

    @JsonProperty("currently_playing_type")
    private String currentlyPlayingType;

    private Action actions;

    @JsonProperty("is_playing")
    private boolean isPlaying;
}
