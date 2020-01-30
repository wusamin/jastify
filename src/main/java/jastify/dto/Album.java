package jastify.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Album {
    @JsonProperty("album_type")
    private String albumType;

    private List<ArtistSimplified> artists;

    @JsonProperty("available_markets")
    private List<String> availableMarkets;

    private List<Copyright> copyrights;

    @JsonProperty("external_ids")
    private Map<String, String> externalIds;

    @JsonProperty("external_urls")
    private ExternalUrl externalUrls;

    private List<String> genres;

    private String href;

    private String id;

    private List<Image> images;

    private String label;

    private String name;

    private int popularity;

    @JsonProperty("release_date")
    private Date releaseDate;

    @JsonProperty("release_date_precision")
    private String releaseDatePrecision;

    private SpotifySearchTracks tracks;

    @JsonProperty("total_tracks")
    private int totalTracks;

    private String type;

    private String uri;
}
