package jastify.dto;

import java.util.Map;

import lombok.Data;

@Data
public class SpotifyAction {
    private Map<String, Boolean> disallows;
}
