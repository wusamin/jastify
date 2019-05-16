package jastify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SpotifyDisallow {
    private boolean resuming;

    @JsonProperty("skipping_prev")
    private boolean skippingPrev;
}
