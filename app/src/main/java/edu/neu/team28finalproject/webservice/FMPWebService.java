package edu.neu.team28finalproject.webservice;

import java.util.List;

import edu.neu.team28finalproject.datatransferobjects.BiggestMovers;
import edu.neu.team28finalproject.datatransferobjects.StockScreener;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FMPWebService {
    /**
     * Gets the biggest movers in the positive direction.
     *
     * @return list of biggest movers
     */
    @GET("/api/v3/stock_market/gainers")
    Call<List<BiggestMovers>> getMostGainers(@Query("apikey") String apiKey);

    /**
     * Gets the biggest movers in the negative direction.
     *
     * @return list of biggest movers
     */
    @GET("/api/v3/stock_market/losers")
    Call<List<BiggestMovers>> getMostLosers(@Query("apikey") String apiKey);

    /**
     * Get stocks by industry.
     *
     * @param industry industry to query upon
     * @return list of stocks for that industry
     */
    @GET("/api/v3/stock-screener")
    Call<List<StockScreener>> getStocksByIndustry(@Query("apikey") String apiKey,
                                                  @Query("industry") String industry);

    /**
     * Get all info for all stocks
     *
     * @return list of stocks
     */
    @GET("/api/v3/stock-screener")
    Call<List<StockScreener>> getAllStockInfo(@Query("apikey") String apiKey);

}
