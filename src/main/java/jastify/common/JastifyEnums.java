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

    public enum Market {
        none(""), fromToken("from_token");

        @Getter
        private String name;

        Market(String name) {
            this.name = name;
        }
    }

    /**
     * standard Pitch Class notation
     */
    public enum Pitch {
        C(0), C_sharp(1), D_flat(1), D(2), D_sharp(3), E_flat(3), E(4), F(
                5), F_sharp(6), G_flat(6), G(7), G_sharp(
                        8), A_flat(8), A(9), A_sharp(10), B_flat(10), B(11);

        @Getter
        private int code;

        Pitch(int code) {
            this.code = code;
        }
    }
}
