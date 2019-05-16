package jastify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SpotifyLinkedForm {

    @JsonProperty("external_urls")
    private SpotifyExternalUrl externalUrls;

    private String href;

    private String id;

    private String type;

    private String uri;
}
