package edu.neu.team28finalproject.webservice;

import java.util.List;

import edu.neu.team28finalproject.datatransferobjects.CompanyProfile;
import edu.neu.team28finalproject.datatransferobjects.Indicator;
import edu.neu.team28finalproject.datatransferobjects.Quote;
import edu.neu.team28finalproject.datatransferobjects.Symbol;
import edu.neu.team28finalproject.datatransferobjects.SymbolLookupWrapper;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
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

    /**
     * Get general information of company with ticker provided.
     *
     * @param ticker symbol/ticker of the company e.g AAPL
     * @return company information
     */
    @GET("/api/v1/stock/profile2")
    Call<CompanyProfile> getCompany(@Query("symbol") String ticker);

    /**
     * Get real time quote data for stocks.
     *
     * @param ticker symbol
     * @return quote data
     */
    @GET("/api/v1/quote")
    Call<Quote> getQuote(@Query("symbol") String ticker);

    /**
     * Get technical indicator with price data.
     *
     * @param ticker stock symbol
     * @param resolution data resolution
     * @param from unix timestamp. Interval initial value
     * @param to unix timestamp. Interval end value
     * @param indicator indicator name e.g sma (simple moving average)
     * @return indicator data
     */
    @POST("/api/v1/indicator")
    Call<Indicator> getIndicators(@Query("symbol") String ticker,
                                  @Query("resolution") String resolution, @Query("from") long from,
                                  @Query("to") long to, @Query("indicator") String indicator);
}
