package edu.neu.team28finalproject.controller;

import java.util.List;

import edu.neu.team28finalproject.datatransferobjects.CompanyProfile;
import edu.neu.team28finalproject.datatransferobjects.Indicator;
import edu.neu.team28finalproject.datatransferobjects.IndicatorResolution;
import edu.neu.team28finalproject.datatransferobjects.Quote;
import edu.neu.team28finalproject.datatransferobjects.Symbol;
import edu.neu.team28finalproject.datatransferobjects.SymbolLookupWrapper;
import edu.neu.team28finalproject.webservice.WebService;
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
    private WebService webService;

    /**
     * Creates an instance of this class.
     */
    public ControllerImpl() {
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
        webService = retrofit.create(WebService.class);
    }

    @Override
    public Call<List<Symbol>> getSymbols() {
        return webService.getSymbols("US");
    }

    @Override
    public Call<SymbolLookupWrapper> searchSymbol(String query) {
        return webService.searchSymbol(query);
    }

    @Override
    public Call<CompanyProfile> getCompany(String ticker) {
        return webService.getCompany(ticker);
    }

    @Override
    public Call<Quote> getQuote(String ticker) {
        return webService.getQuote(ticker);
    }

    @Override
    public Call<Indicator> getIndicators(String ticker, IndicatorResolution resolution,
                                         long from, long to) {
        String indicator = "sma";
        return webService.getIndicators(ticker, resolution.getResolution(), from, to, indicator);
    }
}
