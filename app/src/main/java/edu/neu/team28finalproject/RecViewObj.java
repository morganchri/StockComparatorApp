package edu.neu.team28finalproject;

public class RecViewObj {

    private final String ticker;
    private final double current;
    private final double open;
    private final double change;
    private final double pctChange;
    private final String industry;
    private final double marketCap;
    private final double volume;
    private final double dividends;

    public RecViewObj(String ticker, double current, double open, String industry, double marketCap,
                      double volume, double dividends) {
        this.ticker = ticker;
        this.current = current;
        this.open = open;
        this.change = current - open;
        this.pctChange = ((current - open) / open) * 100;
        this.industry = industry;
        this.marketCap = marketCap;
        this.volume = volume;
        this.dividends = dividends;
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
        return change;
    }

    public double getPctChange() {
        return pctChange;
    }

    public String getIndustry() {return industry;}

    public double getMarketCap() {return marketCap;}

    public double getVolume() {return volume;}

    public double getDividends() {return dividends;}

    public String toString() {
        return "Ticker: " + this.ticker + "\n"
                + "Current: " + this.current + "\n"
                + "Open: " + this.open + "\n"
                + "Change: " + this.change + "\n"
                + "Pct Change: " + this.pctChange + "\n" +
                "Industry: " + this.industry + "\n" +
                "MarketCap: " + this.marketCap + "\n" +
                "Volume: " + this.volume + "\n" +
                "Dividends: " + this.dividends + "\n";
    }
}
