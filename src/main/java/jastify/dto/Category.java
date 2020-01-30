package jastify.dto;

import java.util.List;

import lombok.Data;

@Data
public class Category {
    private String href;

    private List<Icon> icons;

    private String id;

    private String name;
}
