package jastify.dto;

import java.util.List;

import jastify.dto.base.SpotifyResponseBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SpotifySearchAlbums extends SpotifyResponseBase {
    private String href;

    private List<AlbumSimplified> items;

    private int limit;

    private String next;

    private int offset;

    private String previous;

    private int total;
}
