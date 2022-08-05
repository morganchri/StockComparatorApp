package edu.neu.team28finalproject;

public class StockListObj {

    private String ticker;
    private String sector;
    private String name;

    public StockListObj(String ticker, String name, String sector) {
        this.ticker = ticker;
        this.name = name;
        this.sector = sector;
    }

    public String getTicker() {
        return this.ticker;
    }

    public String getName() {
        return this.name;
    }

    public String getSector() {
        return this.sector;
    }
}
