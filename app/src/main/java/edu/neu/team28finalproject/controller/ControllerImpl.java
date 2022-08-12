package edu.neu.team28finalproject.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import edu.neu.team28finalproject.datatransferobjects.BiggestMovers;
import edu.neu.team28finalproject.datatransferobjects.CompanyProfile;
import edu.neu.team28finalproject.datatransferobjects.Indicator;
import edu.neu.team28finalproject.datatransferobjects.IndicatorResolution;
import edu.neu.team28finalproject.datatransferobjects.Industries;
import edu.neu.team28finalproject.datatransferobjects.Quote;
import edu.neu.team28finalproject.datatransferobjects.StockScreener;
import edu.neu.team28finalproject.datatransferobjects.Symbol;
import edu.neu.team28finalproject.datatransferobjects.SymbolLookupWrapper;
import edu.neu.team28finalproject.webservice.FMPWebService;
import edu.neu.team28finalproject.webservice.FinnhubWebService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Implementation of the controller interface.
 */
public class ControllerImpl implements Controller {
    private FinnhubWebService finnhubWebService;
    private FMPWebService fmpWebService;
    private static final String fmpKey = "244771ae43691404cf9b74d75c3d858c";

    /**
     * Creates an instance of this class.
     */
    public ControllerImpl() {
        finnhubWebService = createFinnhubWebService();
        fmpWebService = createFMPWebService();
    }

    @Override
    public Call<List<Symbol>> getSymbols() {
        return finnhubWebService.getSymbols("US");
    }

    @Override
    public Call<SymbolLookupWrapper> searchSymbol(String query) {
        return finnhubWebService.searchSymbol(query);
    }

    @Override
    public Call<CompanyProfile> getCompany(String ticker) {
        return finnhubWebService.getCompany(ticker);
    }

    @Override
    public Call<Quote> getQuote(String ticker) {
        return finnhubWebService.getQuote(ticker);
    }

    @Override
    public Call<Indicator> getIndicators(String ticker, IndicatorResolution resolution,
                                         long from, long to) {
        String indicator = "sma";
        return finnhubWebService.getIndicators(ticker, resolution.getResolution(),
                from, to, indicator);
    }

    @Override
    public Call<List<BiggestMovers>> getMostGainers() {
        return fmpWebService.getMostGainers(fmpKey);
    }

    @Override
    public Call<List<BiggestMovers>> getMostLosers() {
        return fmpWebService.getMostLosers(fmpKey);
    }

    @Override
    public List<String> getIndustries() {
        return Arrays.asList(Industries.values()).stream()
                .map(industry -> industry.getValue()).collect(Collectors.toList());
    }

    @Override
    public Call<List<StockScreener>> getStocksByIndustry(String industry) {
        return fmpWebService.getStocksByIndustry(fmpKey, industry);
    }

    /**
     * Creates a FinnhubWebService object. An interceptor is added to
     * include api key in all requests.
     *
     * @return webservice
     */
    private FinnhubWebService createFinnhubWebService() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(chain -> {
            Request request = chain.request().newBuilder()
                    .addHeader("X-Finnhub-Token", "cbnbs4aad3ifu7vkqsrg").build();
            return chain.proceed(request);
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://finnhub.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        return retrofit.create(FinnhubWebService.class);
    }

    /**
     * Creates a FMPWebService object.
     *
     * @return webservice
     */
    private FMPWebService createFMPWebService() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://financialmodelingprep.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        return retrofit.create(FMPWebService.class);
    }
}
