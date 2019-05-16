package jastify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SpotifyArtist {

    @JsonProperty("external_urls")
    private SpotifyExternalUrl externalUrls;

    private String href;

    private String id;

    private String name;

    private String type;

    private String uri;
}
