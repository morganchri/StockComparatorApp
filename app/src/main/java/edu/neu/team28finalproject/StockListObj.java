package edu.neu.team28finalproject;

import java.io.Serializable;

public class StockListObj implements Serializable {

    private final String ticker;
    private final String name;

    public StockListObj(String ticker, String name) {
        this.ticker = ticker;
        this.name = name;

    }

    public String getTicker() {
        return this.ticker;
    }

    public String getName() {
        return this.name;
    }

}
