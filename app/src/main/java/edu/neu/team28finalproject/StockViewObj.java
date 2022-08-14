package edu.neu.team28finalproject;

public class StockViewObj {
    private final String ticker;
    private final double current;
    private double open;
    private final double change;
    private final double pctChange;

    public StockViewObj(String ticker, double current, double open) {
        this.ticker = ticker;
        this.current = current;
        this.open = open;
        this.change = current - open;
        this.pctChange = ((current - open) / open)*100;
    }

    public String getTicker() {
        return this.ticker;
    }

    public double getCurrent() {
        return this.current;
    }

    public double getOpen() {
        return this.open;
    }

    public double getChange() {
        return Math.round((current - open) * 100.0) / 100.0;
    }

    public double getPctChange() {
        return Math.round(((current - open) / open)*100 * 100.00) / 100.00;
    }

    public void setOpen(double newPrice) {
        this.open = newPrice;
    }
}
