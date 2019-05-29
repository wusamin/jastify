package jastify.dto;

import java.util.List;

import lombok.Data;

@Data
public class SpotifyResultArtist extends SpotifyArtist {

    private SpotifyFollowers followers;

    private List<String> genres;

    private List<SpotifyImage> images;

    private double popularity;
}
