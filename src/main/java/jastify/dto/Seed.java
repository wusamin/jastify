package jastify.dto;

import lombok.Data;

@Data
public class Seed {
    private int initialPoolSize;

    private int afterFilteringSize;

    private int afterRelinkingSize;

    private String href;

    private String id;

    private String type;
}
