package jastify;

import lombok.Setter;

public class BrowseService {
    @Setter
    private String token;

    public BrowseService(String token) {
        this.token = token;
    }

    public void getRecommendeations() {

    }
}
