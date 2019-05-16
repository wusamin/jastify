package jastify.dto;

import lombok.Data;

@Data
public class SpotifyTracks {
    /**
     * href has url of tracks a playlist have.
     */
    private String href;

    private String uri;
}
