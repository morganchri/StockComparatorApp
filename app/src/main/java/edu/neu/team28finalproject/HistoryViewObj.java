package edu.neu.team28finalproject;

public class HistoryViewObj {

    public String tickerName;
    public String timestamp;

    public HistoryViewObj(String tickerName, String timestamp) {
        this.tickerName = tickerName;
        this.timestamp = timestamp;
    }

    public String getTickerName() {
        return this.tickerName;
    }

    public String getTimestamp() {
        return this.timestamp;
    }


}
