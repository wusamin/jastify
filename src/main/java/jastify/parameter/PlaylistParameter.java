package jastify.parameter;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class PlaylistParameter {
    private String name = null;

    private Boolean isPublic = null;

    private Boolean collaborative = null;

    private String description = null;

    public Map<String, String> toMap() {
        Map<String, String> r = new HashMap<>();

        if (name != null) {
            r.put("name", name);
        }

        if (isPublic != null) {
            r.put("public", String.valueOf(isPublic));
        }

        if (collaborative != null) {
            r.put("collaborative", String.valueOf(collaborative));
        }

        if (description != null) {
            r.put("description", description);
        }

        return r;
    }
}
