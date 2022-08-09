package edu.neu.team28finalproject.datatransferobjects;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Class representing technical indicator data.
 */
public class Indicator {
    @SerializedName(value = "c")
    private List<Double> closePrices;
    @SerializedName(value = "h")
    private List<Double> highPrices;
    @SerializedName(value = "l")
    private List<Double> lowPrices;
    @SerializedName(value = "o")
    private List<Double> openPrices;
    @SerializedName(value = "s")
    private String status;
    @SerializedName(value = "t")
    private List<Long> timestamp;
    private List<Double> sma;
    @SerializedName(value = "v")
    private List<Long> volume;
    private String error;

    /**
     * Creates an instance of this class.
     */
    public Indicator() {
        super();
    }

    /**
     * Creates an instance of this class with the required parameters.
     *
     * @param closePrices list of close prices for returned candles
     * @param highPrices list of high prices for returned candles
     * @param lowPrices list of low prices for returned candles
     * @param openPrices list of open prices for returned candles
     * @param status status of the response. One of two possible values (ok and no_data)
     * @param timestamp list of timestamp for returned candles
     * @param sma list of simple moving average values
     * @param volume list of volume data for returned candles
     * @param error error message if requested could not be handled
     */
    public Indicator(List<Double> closePrices, List<Double> highPrices, List<Double> lowPrices,
                     List<Double> openPrices, String status, List<Long> timestamp,
                     List<Double> sma, List<Long> volume, String error) {
        this.closePrices = closePrices;
        this.highPrices = highPrices;
        this.lowPrices = lowPrices;
        this.openPrices = openPrices;
        this.status = status;
        this.timestamp = timestamp;
        this.sma = sma;
        this.volume = volume;
        this.error = error;
    }

    public List<Double> getClosePrices() {
        return closePrices;
    }

    public void setClosePrices(List<Double> closePrices) {
        this.closePrices = closePrices;
    }

    public List<Double> getHighPrices() {
        return highPrices;
    }

    public void setHighPrices(List<Double> highPrices) {
        this.highPrices = highPrices;
    }

    public List<Double> getLowPrices() {
        return lowPrices;
    }

    public void setLowPrices(List<Double> lowPrices) {
        this.lowPrices = lowPrices;
    }

    public List<Double> getOpenPrices() {
        return openPrices;
    }

    public void setOpenPrices(List<Double> openPrices) {
        this.openPrices = openPrices;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Long> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(List<Long> timestamp) {
        this.timestamp = timestamp;
    }

    public List<Double> getSma() {
        return sma;
    }

    public void setSma(List<Double> sma) {
        this.sma = sma;
    }

    public List<Long> getVolume() {
        return volume;
    }

    public void setVolume(List<Long> volume) {
        this.volume = volume;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "Indicator{" +
                "closePrices=" + closePrices +
                ", highPrices=" + highPrices +
                ", lowPrices=" + lowPrices +
                ", openPrices=" + openPrices +
                ", status='" + status + '\'' +
                ", timestamp=" + timestamp +
                ", sma=" + sma +
                ", volume=" + volume +
                ", error='" + error + '\'' +
                '}';
    }
}
