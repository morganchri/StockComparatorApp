package edu.neu.team28finalproject.datatransferobjects;

import com.google.gson.annotations.SerializedName;

/**
 * A class representing real-time quote data for stocks.
 */
public class Quote {
    @SerializedName(value = "c")
    private double currentPrice;
    @SerializedName(value = "d")
    private double change;
    @SerializedName(value = "dp")
    private double percentChange;
    @SerializedName(value = "h")
    private double highPrice;
    @SerializedName(value = "l")
    private double lowPrice;
    @SerializedName(value = "o")
    private double openPrice;
    @SerializedName(value = "pc")
    private double previousClosePrice;
    @SerializedName(value = "t")
    private long timestamp;

    /**
     * Creates an instance of this class.
     */
    public Quote() {
        super();
    }

    /**
     * Creates an instance of this class with the required
     * parameters.
     *
     * @param currentPrice current price
     * @param change change
     * @param percentChange percent change
     * @param highPrice high price of the day
     * @param lowPrice low price of the day
     * @param openPrice open price of the day
     * @param previousClosePrice previous close price
     * @param timestamp timestamp
     */
    public Quote(double currentPrice, double change, double percentChange,
                 double highPrice, double lowPrice, double openPrice,
                 double previousClosePrice, long timestamp) {
        this.currentPrice = currentPrice;
        this.change = change;
        this.percentChange = percentChange;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.openPrice = openPrice;
        this.previousClosePrice = previousClosePrice;
        this.timestamp = timestamp;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(double percentChange) {
        this.percentChange = percentChange;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public double getPreviousClosePrice() {
        return previousClosePrice;
    }

    public void setPreviousClosePrice(double previousClosePrice) {
        this.previousClosePrice = previousClosePrice;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "currentPrice=" + currentPrice +
                ", change=" + change +
                ", percentChange=" + percentChange +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                ", openPrice=" + openPrice +
                ", previousClosePrice=" + previousClosePrice +
                ", timestamp=" + timestamp +
                '}';
    }
}
