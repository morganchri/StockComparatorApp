package edu.neu.team28finalproject.webservice;

import java.util.List;

import edu.neu.team28finalproject.datatransferobjects.Symbol;
import edu.neu.team28finalproject.datatransferobjects.SymbolLookup;
import edu.neu.team28finalproject.datatransferobjects.SymbolLookupWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Interface that represents a web service communication to a remote endpoint.
 */
public interface WebService {
    /**
     * Get a list of supported stocks.
     *
     * @param exchange exchange in which stock trades on
     * @return call that resolves to a list of Symbols
     */
    @GET("/api/v1/stock/symbol")
    Call<List<Symbol>> getSymbols(@Query("exchange") String exchange);

    /**
     * Search for best matching symbol based on query.
     *
     * @param query query text such as symbol, name, isin or cusip
     * @return call that resolves to an object with a count and list of SymbolLookups
     */
    @GET("/api/v1/search")
    Call<SymbolLookupWrapper> searchSymbol(@Query("q") String query);
}
