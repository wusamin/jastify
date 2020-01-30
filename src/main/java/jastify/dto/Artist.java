package jastify.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Artist Objeect - Full
 *
 */
@Data
public class Artist {

    @JsonProperty("external_urls")
    private ExternalUrl externalUrls;

    private Followers followers;

    private List<String> genres;

    private String href;

    private String id;

    private List<Image> images;

    private String name;

    private double popularity;

    private String type;

    private String uri;
}
