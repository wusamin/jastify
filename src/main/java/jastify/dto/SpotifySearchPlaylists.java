package jastify.dto;

import java.util.List;

import lombok.Data;

@Data
public class SpotifySearchPlaylists {
    private String href;

    private List<SpotifyPlaylist> items;

    private int limit;

    private String next;

    private int offset;

    private String previous;

    private int total;
}
