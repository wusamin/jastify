package jastify.common;

import lombok.Getter;

public class JastifyEnums {
    public enum Country {
        JP("JP");

        @Getter
        private String name;

        Country(String name) {
            this.name = name;
        }
    }

    public enum IncludeGroups {
        album("album"), single("single"), appearsOn("appears_on"), compilation(
                "compilation");

        @Getter
        private String name;

        IncludeGroups(String name) {
            this.name = name;
        }
    }
}
