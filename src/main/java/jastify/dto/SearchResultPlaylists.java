package jastify.dto;

import jastify.base.SpotifyResponseBase;
import lombok.Data;

@Data
public class SearchResultPlaylists extends SpotifyResponseBase {
    private SpotifySearchPlaylists playlists;
}
