package edu.neu.team28finalproject.datatransferobjects;

/**
 * Class that represents the result returned by search for stocks based on industry.
 */
public class StockScreener {
    private String symbol;
    private String companyName;
    private double marketCap;
    private String sector;
    private double beta;
    private double price;
    private double lastAnnualDividend;
    private long volume;
    private String exchange;
    private String exchangeShortName;
    private String industry;

    /**
     * Creates an instance of this class.
     */
    public StockScreener() {
        super();
    }

    /**
     * Creates an instance of this class with the required parameters.
     *
     * @param symbol stock ticker
     * @param companyName company name
     * @param marketCap company market capitalization
     * @param sector company sector
     * @param beta stock beta
     * @param price stock price
     * @param lastAnnualDividend last annual dividend
     * @param volume trade volume
     * @param exchange exchange that stock is being traded on
     * @param exchangeShortName exchange short name
     * @param industry company industry
     */
    public StockScreener(String symbol, String companyName, double marketCap,
                         String sector, double beta, double price, double lastAnnualDividend,
                         long volume, String exchange, String exchangeShortName,
                         String industry) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.marketCap = marketCap;
        this.sector = sector;
        this.beta = beta;
        this.price = price;
        this.lastAnnualDividend = lastAnnualDividend;
        this.volume = volume;
        this.exchange = exchange;
        this.exchangeShortName = exchangeShortName;
        this.industry = industry;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public double getBeta() {
        return beta;
    }

    public void setBeta(double beta) {
        this.beta = beta;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getLastAnnualDividend() {
        return lastAnnualDividend;
    }

    public void setLastAnnualDividend(double lastAnnualDividend) {
        this.lastAnnualDividend = lastAnnualDividend;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getExchangeShortName() {
        return exchangeShortName;
    }

    public void setExchangeShortName(String exchangeShortName) {
        this.exchangeShortName = exchangeShortName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    @Override
    public String toString() {
        return "StockScreener{" +
                "symbol='" + symbol + '\'' +
                ", companyName='" + companyName + '\'' +
                ", marketCap=" + marketCap +
                ", sector='" + sector + '\'' +
                ", beta=" + beta +
                ", price=" + price +
                ", lastAnnualDividend=" + lastAnnualDividend +
                ", volume=" + volume +
                ", exchange='" + exchange + '\'' +
                ", exchangeShortName='" + exchangeShortName + '\'' +
                ", industry='" + industry + '\'' +
                '}';
    }
}
