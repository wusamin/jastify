package jastify.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SpotifyPlaylistItem {
    private boolean collaborative;

    @JsonProperty("external_urls")
    private SpotifyExternalUrl externalUrls;

    private String href;

    private String id;

    private List<SpotifyImage> images;

    private String name;

    private SpotifyOwner owners;

    @JsonProperty("primary_color")
    private String primaryColor;

    @JsonProperty("public")
    private boolean isPublic;

    @JsonProperty("snapshot_id")
    private String snapshotId;

    private SpotifyTracks tracks;

    private String type;

    private String uri;

}
