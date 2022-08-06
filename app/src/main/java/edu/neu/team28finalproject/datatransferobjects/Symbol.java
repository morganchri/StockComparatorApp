package edu.neu.team28finalproject.datatransferobjects;

/**
 * Class representing a stock symbol and summary data about it.
 */
public class Symbol {
    private String currency;
    private String description;
    private String displaySymbol;
    private String figi;
    private String mic;
    private String symbol;
    private String type;

    /**
     * Creates an instance of this class.
     */
    public Symbol() {
        super();
    }

    /**
     * Creates an instance of this class with the required parameters.
     *
     * @param currency currency of stock price
     * @param description  description of stock
     * @param displaySymbol display symbol of stock
     * @param figi FIGI identifier of stock
     * @param mic primary exchange's MIC
     * @param symbol unique symbol used to identify this stock
     * @param type security type
     */
    public Symbol(String currency, String description, String displaySymbol,
                  String figi, String mic, String symbol, String type) {
        this.currency = currency;
        this.description = description;
        this.displaySymbol = displaySymbol;
        this.figi = figi;
        this.mic = mic;
        this.symbol = symbol;
        this.type = type;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplaySymbol() {
        return displaySymbol;
    }

    public void setDisplaySymbol(String displaySymbol) {
        this.displaySymbol = displaySymbol;
    }

    public String getFigi() {
        return figi;
    }

    public void setFigi(String figi) {
        this.figi = figi;
    }

    public String getMic() {
        return mic;
    }

    public void setMic(String mic) {
        this.mic = mic;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
