package edu.neu.team28finalproject;

public class RecViewObj {

    private final String ticker;
    private final double current;
    private final double open;
    private final double change;
    private final double pctChange;
    private final String industry;
    private final double marketCap;
    private final double pE;
    private final double totalRev;

    public RecViewObj(String ticker, double current, double open,String industry, double marketCap,
                      double pE, double totalRev) {
        this.ticker = ticker;
        this.current = current;
        this.open = open;
        this.change = current - open;
        this.pctChange = (current - open) / open;
        this.industry = industry;
        this.marketCap = marketCap;
        this.pE = pE;
        this.totalRev = totalRev;
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

    public double getPE() {return pE;}

    public double getTotalRev() {return totalRev;}
}
