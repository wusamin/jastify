package jastify.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AlbumSimplified {
    @JsonProperty("album_group")
    private String albumGroup;

    @JsonProperty("album_type")
    private String albumType;

    private List<ArtistSimplified> artists;

    @JsonProperty("available_markets")
    private List<String> availableMarkets;

    @JsonProperty("external_urls")
    private ExternalUrl externalUrls;

    private String href;

    private String id;

    private List<Image> images;

    private String name;

    @JsonProperty("release_date")
    private Date releaseDate;

    @JsonProperty("release_date_precision")
    private String releaseDatePrecision;

    private Map<String, String> restrictions;

    @JsonProperty("total_tracks")
    private int totalTracks;

    private String type;

    private String uri;
}
