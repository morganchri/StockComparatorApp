package edu.neu.team28finalproject.datatransferobjects;

public enum Industries {
    AUTOS("Auto"),
    BANKS("Banks"),
    SOFTWARE("Software"),
    BEVERAGES("Beverages"),
    CONSUMERELECTRONICS("Consumer Electronics"),
    INTERNET("Internet"),
    INSURANCE("Insurance"),
    HEALTHCARE("Healthcare"),
    OILANDGAS("Oil & Gas"),
    SEMICONDUCTORS("Semiconductors"),
    CREDITSERVICES("Credit Services"),
    DISCOUNTSTORES("Discount Store"),
    HOUSEHOLD("Household"),
    LUXURYGOODS("Luxury Goods"),
    MATERIALS("Materials"),
    RESORTSANDCASINOS("Resorts & Casinos"),
    APPAREL("Apparel"),
    GOLD("Gold");

    private String value;

    Industries(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
