package jastify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Owner {

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("external_urls")
    private ExternalUrl externalUrls;

    private String href;

    private String id;

    private String type;

    private String uri;
}
