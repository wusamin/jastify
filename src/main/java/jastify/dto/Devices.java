package jastify.dto;

import java.util.List;

import jastify.base.SpotifyResponseBase;
import lombok.Data;

@Data
public class Devices extends SpotifyResponseBase {
    private List<SpotifyDevice> devices;
}
