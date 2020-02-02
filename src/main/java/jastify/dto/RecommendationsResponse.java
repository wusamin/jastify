package jastify.dto;

import java.util.List;

import jastify.dto.base.SpotifyResponseBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RecommendationsResponse extends SpotifyResponseBase {
    private List<Track> tracks;

    private List<Seed> seeds;
}
