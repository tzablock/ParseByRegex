package com.parsebyregex.constants;

public enum SeparationCharacter {
    COMMA(","),
    POINT("."),
    DASH("-");
    private String separator;

    SeparationCharacter(String separator) {
        this.separator = separator;
    }

    public String getSeparator() {
        return separator;
    }
}
