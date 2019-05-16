package jastify.dto;

import jastify.base.SpotifyResponseBase;
import lombok.Data;

@Data
public class SearchResultTracks extends SpotifyResponseBase {
    private SpotifySearchTracks tracks;
}
