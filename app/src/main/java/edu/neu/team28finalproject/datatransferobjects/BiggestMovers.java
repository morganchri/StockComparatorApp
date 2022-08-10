package edu.neu.team28finalproject.datatransferobjects;

import com.google.gson.annotations.SerializedName;

/**
 * Class that represents the most gainers or losers for the day.
 */
public class BiggestMovers {
    private String symbol;
    private String name;
    private double change;
    private double price;
    @SerializedName(value = "changesPercentage")
    private double percentageChange;

    /**
     * Creates an instance of this class.
     */
    public BiggestMovers() {
        super();
    }

    /**
     * Creates an instance of this class with the specified parameters.
     *
     * @param symbol ticker or symbol
     * @param name company name
     * @param change dollar change
     * @param price share price
     * @param percentageChange percentage change
     */
    public BiggestMovers(String symbol, String name, double change, double price,
                         double percentageChange) {
        this.symbol = symbol;
        this.name = name;
        this.change = change;
        this.price = price;
        this.percentageChange = percentageChange;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(double percentageChange) {
        this.percentageChange = percentageChange;
    }

    @Override
    public String toString() {
        return "BiggestMovers{" +
                "symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", change=" + change +
                ", price=" + price +
                ", percentageChange=" + percentageChange +
                '}';
    }
}
