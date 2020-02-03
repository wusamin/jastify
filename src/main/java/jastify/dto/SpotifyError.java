package jastify.dto;

import lombok.Data;

@Data
public class SpotifyError {
    private int status;

    private String message;
}
