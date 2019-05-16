package jastify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SpotifyOwner {

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("external_urls")
    private SpotifyExternalUrl externalUrls;

    private String href;

    private String id;

    private String type;

    private String uri;
}
