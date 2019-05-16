package jastify.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SpotifyResultArtist {

    @JsonProperty("external_urls")
    private SpotifyExternalUrl externalUrls;

    private SpotifyFollowers followers;

    private List<String> genres;

    private String href;

    private String id;

    private List<SpotifyImage> images;

    private String name;

    private double popularity;

    private String type;

    private String uri;
}
