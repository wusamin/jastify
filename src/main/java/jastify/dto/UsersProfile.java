package jastify.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jastify.dto.base.SpotifyResponseBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UsersProfile extends SpotifyResponseBase {
    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("external_urls")
    private ExternalUrl externalUrls;

    private Followers followers;

    private String href;

    private String id;

    private List<Image> images;

    private String type;

    private String uri;
}
