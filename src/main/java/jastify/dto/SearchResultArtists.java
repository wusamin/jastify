package jastify.dto;

import jastify.base.SpotifyResponseBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchResultArtists extends SpotifyResponseBase {
    private SpotifySearchArtist artists;
}
