package jastify.dto;

import java.sql.Timestamp;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PlaylistTrack {

    @JsonProperty("added_at")
    private Timestamp addedAt;

    @JsonProperty("added_by")
    private UsersProfile addedBy;

    @JsonProperty("is_local")
    private boolean isLocal;

    @JsonProperty("primary_color")
    private String primaryColor;

    private Track track;

    @JsonProperty("video_thumbnail")
    private Map<String, String> videoThumbnail;
}
