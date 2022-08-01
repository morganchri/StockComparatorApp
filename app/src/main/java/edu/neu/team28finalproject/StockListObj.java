package edu.neu.team28finalproject;

public class StockListObj {

    private String ticker;
    private String sector;

    public StockListObj(String ticker, String sector) {
        this.ticker = ticker;
        this.sector = sector;
    }

    public String getTicker() {
        return this.ticker;
    }

    public String getSector() {
        return this.sector;
    }
}
