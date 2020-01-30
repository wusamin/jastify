package jastify.dto;

import java.util.Map;

import lombok.Data;

@Data
public class Action {
    private Map<String, Boolean> disallows;
}
