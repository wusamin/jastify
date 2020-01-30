package jastify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class LinkedForm {

    @JsonProperty("external_urls")
    private ExternalUrl externalUrls;

    private String href;

    private String id;

    private String type;

    private String uri;
}
