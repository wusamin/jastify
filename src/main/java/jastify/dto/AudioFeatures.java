package jastify.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jastify.dto.base.SpotifyResponseBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AudioFeatures extends SpotifyResponseBase {

    @JsonProperty("audio_features")
    private List<AudioFeature> audioFeatures;
}
