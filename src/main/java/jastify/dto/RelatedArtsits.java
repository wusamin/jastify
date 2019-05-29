package jastify.dto;

import java.util.List;

import jastify.base.SpotifyResponseBase;
import lombok.Data;

@Data
public class RelatedArtsits extends SpotifyResponseBase {
    private List<SpotifyResultArtist> artists;
}
