package jastify.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Disallow {
    private boolean resuming;

    @JsonProperty("skipping_prev")
    private boolean skippingPrev;
}
