package jastify.dto;

import jastify.base.SpotifyResponseBase;
import lombok.Data;

@Data
public class SearchResultArtists extends SpotifyResponseBase {
    private SpotifySearchArtist artists;
}
