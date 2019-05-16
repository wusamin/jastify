package jastify.dto;

import jastify.base.SpotifyResponseBase;
import lombok.Data;

@Data
public class SearchResultAlbums extends SpotifyResponseBase {
    private SpotifySearchAlbums albums;
}
