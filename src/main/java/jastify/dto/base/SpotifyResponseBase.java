package jastify.dto.base;

import jastify.dto.SpotifyError;
import lombok.Data;

@Data
public class SpotifyResponseBase {
    private int code;

    private SpotifyError error;
}
