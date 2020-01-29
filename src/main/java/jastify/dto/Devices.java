package jastify.dto;

import java.util.List;

import jastify.base.SpotifyResponseBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Devices extends SpotifyResponseBase {
    private List<SpotifyDevice> devices;
}
