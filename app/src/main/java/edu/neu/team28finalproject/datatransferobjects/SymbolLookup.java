package edu.neu.team28finalproject.datatransferobjects;

/**
 * Class that represents a search result for best-matching symbols
 * based on a query. Query can be the symbol, security's name, ISIN
 * and Cusip.
 */
public class SymbolLookup {
    private String description;
    private String displaySymbol;
    private String symbol;
    private String type;

    /**
     * Creates an instance of this class.
     */
    public SymbolLookup() {
        super();
    }

    /**
     * Creates an instance of this class with the required parameters.
     *
     * @param description symbol/stock description
     * @param displaySymbol display symbol name
     * @param symbol unique symbol used to identify the stock
     * @param type security type
     */
    public SymbolLookup(String description, String displaySymbol, String symbol, String type) {
        this.description = description;
        this.displaySymbol = displaySymbol;
        this.symbol = symbol;
        this.type = type;
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


