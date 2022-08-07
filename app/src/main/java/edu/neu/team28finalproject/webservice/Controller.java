package edu.neu.team28finalproject.webservice;

import java.util.List;

import edu.neu.team28finalproject.datatransferobjects.Symbol;
import edu.neu.team28finalproject.datatransferobjects.SymbolLookupWrapper;
import retrofit2.Call;

/**
 * Interface that represents a controller that sits in between the
 * application and the web service.
 */
public interface Controller {
    /**
     * Get a list of supported stocks.
     *
     * @return completable future that resolves to a list of Symbols
     */
    Call<List<Symbol>> getSymbols();

    /**
     * Search for best matching symbol based on query.
     *
     * @param query query text such as symbol, name, isin or cusip
     * @return call that resolves to a count and list of SymbolLookups
     */
    Call<SymbolLookupWrapper> searchSymbol(String query);
}
