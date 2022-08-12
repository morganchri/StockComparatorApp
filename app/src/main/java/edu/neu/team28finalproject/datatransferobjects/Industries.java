package edu.neu.team28finalproject.datatransferobjects;

public enum Industries {
    AUTOS("Autos"), BANKS("Banks"), BANKSDIVERSIFIED("Banks Diversified"),
    SOFTWARE("Software"), BANKSREGIONAL("Banks Regional"),
    BEVERAGESALCOHOLIC("Beverages Alcoholic"), BEVERAGESBREWERS("Beverages Brewers"),
    BEVERAGESNONALCOHOLIC("Beverages Non-Alcoholic");

    private String value;

    Industries(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
