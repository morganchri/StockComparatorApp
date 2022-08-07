package edu.neu.team28finalproject.webservice;

import java.util.List;

import edu.neu.team28finalproject.datatransferobjects.Symbol;
import edu.neu.team28finalproject.datatransferobjects.SymbolLookupWrapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import  retrofit2.converter.gson.GsonConverterFactory;

/**
 * Implementation of the controller interface.
 */
public class ControllerImpl implements Controller {
    private Retrofit retrofit;
    private WebService webService;

    public  ControllerImpl() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(chain -> {
            Request request = chain.request().newBuilder()
                    .addHeader("X-Finnhub-Token", "cbnbs4aad3ifu7vkqsrg").build();
            return chain.proceed(request);
        });
        retrofit = new Retrofit.Builder()
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
}
