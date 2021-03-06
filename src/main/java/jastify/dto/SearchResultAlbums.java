package jastify.dto;

import jastify.dto.base.SpotifyResponseBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchResultAlbums extends SpotifyResponseBase {
    private SpotifySearchAlbums albums;
}
