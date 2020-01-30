package jastify.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PlaylistSimplified {
    private boolean collaborative;

    private String description;

    @JsonProperty("external_urls")
    private ExternalUrl externalUrls;

    private String href;

    private String id;

    private List<Image> images;

    private String name;

    private Owner owner;

    @JsonProperty("primary_color")
    private String primaryColor;

    @JsonProperty("public")
    private String isPublic;

    @JsonProperty("snapshot_id")
    private String snapshotId;

    private PlaylistTracks tracks;

    private String type;

    private String uri;
}
