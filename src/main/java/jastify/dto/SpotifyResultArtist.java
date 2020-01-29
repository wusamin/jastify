package jastify.dto;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SpotifyResultArtist extends SpotifyArtist {

    private SpotifyFollowers followers;

    private List<String> genres;

    private List<SpotifyImage> images;

    private double popularity;
}
