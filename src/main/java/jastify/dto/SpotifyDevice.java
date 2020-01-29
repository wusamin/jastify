package jastify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jastify.base.SpotifyResponseBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SpotifyDevice extends SpotifyResponseBase {
    private String id;

    @JsonProperty("is_active")
    private boolean isActive;

    @JsonProperty("is_private_session")
    private boolean isPrivateSession;

    @JsonProperty("is_restricted")
    private boolean isRestricted;

    private String name;

    private String type;

    @JsonProperty("volume_percent")
    private double volumePercent;
}
