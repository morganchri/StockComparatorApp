package edu.neu.team28finalproject.preferences;

import java.util.List;

/**
 * Interface that represents a set of personalized actions that
 * a user can perform. Such as make a stock favorite or maintain
 * a history of viewed stocks.
 */
public interface UserPreferences {
    /**
     * Add stock to favorites.
     *
     * @param ticker ticker/ symbol of stock
     */
    void likeStock(String ticker);

    /**
     * Add stock to history.
     *
     * @param ticker ticker/ symbol of stock
     */
    void viewStock(String ticker);

    /**
     * Get favorite stocks.
     *
     * @return list of favorite stocks
     */
    List<String> getLikedStocks();

    /**
     * Get viewed stocks.
     *
     * @return list of viewed stocks
     */
    List<String> getViewedStocks();

    /**
     * Clear liked stocks.
     */
    void clearLikedStocks();

    /**
     * Clear viewed stocks.
     */
    void clearViewedStocks();

    /**
     * Unlike stock.
     *
     * @param ticker name of stock
     */
    void unlikeStock(String ticker);
}
