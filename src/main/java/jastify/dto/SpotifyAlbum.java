package jastify.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SpotifyAlbum {
    @JsonProperty("album_type")
    private String albumType;

    private List<SpotifyArtist> artists;

    @JsonProperty("external_urls")
    private SpotifyExternalUrl externalUrls;

    private String href;

    private String id;

    private List<SpotifyImage> images;

    private String name;

    @JsonProperty("release_date")
    private Date releaseDate;

    @JsonProperty("release_date_precision")
    private String releaseDatePrecision;

    @JsonProperty("total_tracks")
    private int totalTracks;

    private String type;

    private String uri;
}
