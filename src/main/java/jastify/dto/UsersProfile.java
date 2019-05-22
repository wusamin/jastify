package jastify.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jastify.base.SpotifyResponseBase;
import lombok.Data;

@Data
public class UsersProfile extends SpotifyResponseBase {
    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("external_urls")
    private SpotifyExternalUrl externalUrls;

    private SpotifyFollowers followers;

    private String href;

    private String id;

    private List<SpotifyImage> images;

    private String type;

    private String uri;
}
