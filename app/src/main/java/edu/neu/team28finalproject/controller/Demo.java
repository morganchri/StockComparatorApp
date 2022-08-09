package edu.neu.team28finalproject.controller;

import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

import edu.neu.team28finalproject.datatransferobjects.CompanyProfile;
import edu.neu.team28finalproject.datatransferobjects.Error;
import edu.neu.team28finalproject.datatransferobjects.Indicator;
import edu.neu.team28finalproject.datatransferobjects.IndicatorResolution;
import edu.neu.team28finalproject.datatransferobjects.Quote;
import edu.neu.team28finalproject.datatransferobjects.Symbol;
import edu.neu.team28finalproject.datatransferobjects.SymbolLookupWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This class demonstrates how to use the controller to make
 * requests to the remote API. It will be deleted later.
 */
public class Demo {
    private final Controller controller = new ControllerImpl();
    private static final String TAG = "Demo";

    /**
     * Demo get all symbols/stocks from api
     */
    private void getSymbols() {
        controller.getSymbols().enqueue(new Callback<List<Symbol>>() {
            @Override
            public void onResponse(Call<List<Symbol>> call, Response<List<Symbol>> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "getSymbolsOnResponse: " + response.body());
                } else {
                    try {
                        Log.i(TAG, "getSymbolsOnResponseNotSuccessful: " +
                                response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Symbol>> call, Throwable t) {
                Log.i(TAG, "getSymbolsOnFailure: " + t);
            }
        });
    }

    /**
     * Demo search symbol
     */
    private void searchSymbol() {
        controller.searchSymbol("apple").enqueue(new Callback<SymbolLookupWrapper>() {
            @Override
            public void onResponse(Call<SymbolLookupWrapper> call,
                                   Response<SymbolLookupWrapper> response) {
                if (response.isSuccessful()) {
                    if (response.body().getCount() > 0)
                        Log.i(TAG, "searchSymbolOnResponse: " + response.body());
                    else
                        ; // No match
                } else {
                    try {
                        Log.i(TAG, "searchSymbolOnResponseNotSuccessful: " +
                                response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SymbolLookupWrapper> call, Throwable t) {
                Log.i(TAG, "searchSymbolOnFailure: " + t);
            }
        });
    }

    /**
     * Demo get company. Response returns an empty object if there is no match,
     * however the gson library deserializes the object regardless and sets all
     * values to their default for primitives or null for objects. Thus, checking
     * if the ticker is not null is an indicator of if a match was found or not.
     */
    private void getCompany() {
        controller.getCompany("AAPL").enqueue(new Callback<CompanyProfile>() {
            @Override
            public void onResponse(Call<CompanyProfile> call, Response<CompanyProfile> response) {
                if (response.isSuccessful()) {
                    if (response.body().getTicker() != null)
                        Log.i(TAG, "getCompanyOnResponse: " + response.body());
                    else
                        ; // No match
                } else {
                    try {
                        Log.i(TAG, "getCompanyOnResponseNotSuccesful: " +
                                response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CompanyProfile> call, Throwable t) {
                Log.i(TAG, "getCompanyOnFailure: " + t);
            }
        });
    }

    /**
     * Demo get quote. Response always returns data regardless of whether there is
     * a match or not. If no match, it returns 0 or null for all fields based
     * on the type. Thus, a timestamp of 0 is a good indicator of if a match was found or
     * not.
     */
    private void getQuote() {
        controller.getQuote("AAPL").enqueue(new Callback<Quote>() {
            @Override
            public void onResponse(Call<Quote> call, Response<Quote> response) {
                if (response.isSuccessful()) {
                    if (response.body().getTimestamp() > 0)
                        Log.i(TAG, "getQuoteOnResponse: " + response.body());
                    else
                        ; // No match
                } else {
                    try {
                        Log.i(TAG, "getQuoteOnResponseNotSuccessful: " +
                                response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Quote> call, Throwable t) {
                Log.i(TAG, "getQuoteOnFailure: " + t);
            }
        });
    }

    /**
     * Demo get indicators. Errors in this endpoint are handled differently. If there is no
     * data, the status will be set to no_data else ok. Certain time intervals are not
     * compatible with some resolution. Such combinations will give a 422 error.
     * To get that we need to extract the error message from the error body. This message may
     * be relevant to the user, thus it is important to map the json to a java object so we
     * can gain access to the message and show it to the user as needed.
     */
    private void getIndicators() {
        controller.getIndicators("AAPL", IndicatorResolution.RES_D, 1583098857,
                1584308457).enqueue(new Callback<Indicator>() {
            @Override
            public void onResponse(Call<Indicator> call, Response<Indicator> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("ok")) {
                        Log.i(TAG, "getIndicatorsOnResponse: " + response.body());
                    } else {
                        // No data
                    }
                } else {
                    try {
                        Log.i(TAG, "getIndicatorsOnResponseNotSuccessful: " +
                                response.errorBody().string());
                        ObjectMapper om = new ObjectMapper();
                        Error e = om.readValue(response.errorBody().string(), Error.class);
                        Log.i(TAG, "error: " + e.getError());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Indicator> call, Throwable t) {
                Log.i(TAG, "getIndicatorsOnFailure: " + t);
            }
        });
    }
}
