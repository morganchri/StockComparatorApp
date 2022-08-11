package edu.neu.team28finalproject.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.neu.team28finalproject.R;

/**
 * Class that represents an implementation of the UserPreference interface.
 */
public class UserPreferencesImpl implements UserPreferences {
    private Set<String> favorites;
    private Set<String> viewed;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private final String favoritesKey = "favorites";
    private final String viewedKey = "viewed";
    private final Type setType = new TypeToken<HashSet<String>>(){}.getType();
    private final Gson gson = new Gson();

    /**
     * Creates an instance of this class.
     *
     * @param context any context within the application
     */
    public  UserPreferencesImpl(Context context) {
        preferences = context.getSharedPreferences(context
                .getResources().getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        editor = preferences.edit();
        favorites = loadFavorites();
        viewed = loadViewed();
    }

    @Override
    public void likeStock(String ticker) {
        if (!favorites.contains(ticker)) {
            favorites.add(ticker);
            editor.putString(favoritesKey, gson.toJson(favorites));
            editor.commit();
        }
    }

    @Override
    public void viewStock(String ticker) {
        if (!viewed.contains(ticker)) {
            viewed.add(ticker);
            editor.putString(viewedKey, gson.toJson(viewed));
            editor.commit();
        }
    }

    @Override
    public List<String> getLikedStocks() {
        return new ArrayList<>(favorites);
    }

    @Override
    public List<String> getViewedStocks() {
        return new ArrayList<>(viewed);
    }

    @Override
    public void clearLikedStocks() {
        editor.remove(favoritesKey);
        editor.commit();
        favorites.clear();
    }

    @Override
    public void clearViewedStocks() {
        editor.remove(viewedKey);
        editor.commit();
        viewed.clear();
    }

    /**
     * Load favorites from shared preferences. Returns an empty set if nothing
     * is found.
     *
     * @return set of favorites / liked stocks
     */
    private Set<String> loadFavorites() {
        return gson.fromJson(preferences.getString(favoritesKey, "[]"), setType);
    }

    /**
     * Load viewed stocks from shared preferences. Returns an empty set if nothing
     * is found.
     *
     * @return set of viewed stocks
     */
    private Set<String> loadViewed() {
        return gson.fromJson(preferences.getString(viewedKey, "[]"), setType);
    }
}
