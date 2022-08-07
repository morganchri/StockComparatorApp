package edu.neu.team28finalproject.datatransferobjects;

import java.util.List;

/**
 * Class that represents a wrapper around object returned after symbol search.
 */
public class SymbolLookupWrapper {
    private int count;
    private List<SymbolLookup> result;

    /**
     * Creates an instance of this class.
     */
    public SymbolLookupWrapper() {
        super();
    }

    /**
     * Creates an instance of this class with the required parameters.
     *
     * @param count count of symbols matching query
     * @param result list of symbols matching query
     */
    public SymbolLookupWrapper(int count, List<SymbolLookup> result) {
        this.count = count;
        this.result = result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<SymbolLookup> getSymbols() {
        return result;
    }

    public void setSymbols(List<SymbolLookup> result) {
        this.result = result;
    }
}
