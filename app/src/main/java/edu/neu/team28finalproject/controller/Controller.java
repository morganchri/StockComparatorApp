package edu.neu.team28finalproject.controller;

import java.util.List;

import edu.neu.team28finalproject.datatransferobjects.BiggestMovers;
import edu.neu.team28finalproject.datatransferobjects.CompanyProfile;
import edu.neu.team28finalproject.datatransferobjects.Indicator;
import edu.neu.team28finalproject.datatransferobjects.IndicatorResolution;
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
     * Get general information (such as name, industry, market cap etc.)
     * of company with ticker provided. When there is no match, the empty
     * json will still be deserialized into a java object with all reference
     * values = null
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

    /**
     * Get technical indicator with price data. This can return null in certain
     * instances, e.g when the resolution is incompatible with the time interval
     * specified. In that case, we need to extract the error string from the
     * response (i.e response.errorBody().string()), and map it to a POJO with the Jackson Object
     * Mapper, so we can get the error message and display to the user. The status field of the
     * indicator object is also relevant for handling situation where no data is returned.
     *
     * @param ticker stock symbol
     * @param resolution data resolution
     * @param from unix timestamp. Interval initial value
     * @param to unix timestamp. Interval end value
     * @return indicator data
     */
    Call<Indicator> getIndicators(String ticker, IndicatorResolution resolution, long from,
                                  long to);

    /**
     * Gets the biggest movers in the positive direction.
     *
     * @return list of biggest movers
     */
    Call<List<BiggestMovers>> getMostGainers();

    /**
     * Gets the biggest movers in the negative direction.
     *
     * @return list of biggest movers
     */
    Call<List<BiggestMovers>> getMostLosers();
}
