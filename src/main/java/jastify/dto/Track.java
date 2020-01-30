package jastify.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Track {
    private AlbumSimplified album;

    private List<ArtistSimplified> artists;

    @JsonProperty("available_markets")
    private List<String> availableMarkets;

    @JsonProperty("disc_number")
    private int discNumber;

    @JsonProperty("duration_ms")
    private int durationMs;

    private boolean explicit;

    @JsonProperty("external_ids")
    private Map<String, String> externalIds;

    @JsonProperty("external_urls")
    private ExternalUrl externalUrls;

    private String href;

    private String id;

    @JsonProperty("is_local")
    private boolean isLocal;

    @JsonProperty("is_playable")
    private boolean isPlayable;

    @JsonProperty("linked_from")
    private LinkedForm linkedForm;

    private String name;

    private double popularity;

    @JsonProperty("preview_url")
    private String previewUrl;

    @JsonProperty("track_number")
    private int trackNumber;

    private String type;

    private String uri;
}
