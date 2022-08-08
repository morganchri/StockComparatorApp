package edu.neu.team28finalproject.controller;

import java.util.List;

import edu.neu.team28finalproject.datatransferobjects.CompanyProfile;
import edu.neu.team28finalproject.datatransferobjects.Quote;
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

    /**
     * Get general information of company with ticker provided.
     * When there is no match, the empty json will still be
     * deserialized into a java object with all reference values = null
     *
     * @param ticker symbol/ticker of the company e.g AAPL
     * @return company information
     */
    Call<CompanyProfile> getCompany(String ticker);

    /**
     * Get real time quote data for stocks.
     * When there is no match, the empty json will still be
     * deserialized into a java object with all reference values = null
     *
     * @param ticker symbol
     * @return quote data
     */
    Call<Quote> getQuote(String ticker);
}
