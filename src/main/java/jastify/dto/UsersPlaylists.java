package jastify.dto;

import java.util.List;

import jastify.base.SpotifyResponseBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UsersPlaylists extends SpotifyResponseBase {
    private String href;

    private List<SpotifyPlaylist> items;

    private int limit;

    private String next;

    private int offset;

    private String previous;

    private int total;
}
