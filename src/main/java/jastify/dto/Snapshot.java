package jastify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import jastify.dto.base.SpotifyResponseBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Snapshot extends SpotifyResponseBase {
    @JsonProperty("snapshot_id")
    private String snapshotId;
}
