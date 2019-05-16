package jastify.dto;

import java.util.List;

import lombok.Data;

@Data
public class Playlist {
    private String href;

    private List<SpotifyPlaylistItem> items;

    private int limit;

    private String next;

    private int offset;

    private String previous;

    private int total;

}
