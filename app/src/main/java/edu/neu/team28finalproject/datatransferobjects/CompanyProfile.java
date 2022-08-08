package edu.neu.team28finalproject.datatransferobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Class that represents the general information of a company.
 */
public class CompanyProfile {
    private String country;
    private String currency;
    private String exchange;
    @SerializedName(value = "finnhubIndustry")
    private String industry;
    private Date ipo;
    private String logo;
    private double marketCapitalization;
    private String name;
    private String phone;
    private double shareOutstanding;
    private String ticker;
    private String weburl;

    /**
     * Creates an instance of this class.
     */
    public CompanyProfile() {
        super();
    }

    /**
     * Creates an instance of this class with the required
     * parameters.
     *
     * @param country country of company's headquarters
     * @param currency currency used in company's filings
     * @param exchange listed exchange
     * @param industry industry classification
     * @param ipo date of ipo
     * @param logo path to logo
     * @param marketCapitalization market capitalization
     * @param name company name
     * @param phone company phone number
     * @param shareOutstanding number of shares outstanding
     * @param ticker ticker used on listed exchange
     * @param weburl company website
     */
    public CompanyProfile(String country, String currency, String exchange,
                          String industry, Date ipo, String logo,
                          double marketCapitalization, String name, String phone,
                          double shareOutstanding, String ticker, String weburl) {
        this.country = country;
        this.currency = currency;
        this.exchange = exchange;
        this.industry = industry;
        this.ipo = ipo;
        this.logo = logo;
        this.marketCapitalization = marketCapitalization;
        this.name = name;
        this.phone = phone;
        this.shareOutstanding = shareOutstanding;
        this.ticker = ticker;
        this.weburl = weburl;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Date getIpo() {
        return ipo;
    }

    public void setIpo(Date ipo) {
        this.ipo = ipo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public double getMarketCapitalization() {
        return marketCapitalization;
    }

    public void setMarketCapitalization(double marketCapitalization) {
        this.marketCapitalization = marketCapitalization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getShareOutstanding() {
        return shareOutstanding;
    }

    public void setShareOutstanding(double shareOutstanding) {
        this.shareOutstanding = shareOutstanding;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getWeburl() {
        return weburl;
    }

    public void setWeburl(String weburl) {
        this.weburl = weburl;
    }

    @Override
    public String toString() {
        return "CompanyProfile{" +
                "country='" + country + '\'' +
                ", currency='" + currency + '\'' +
                ", exchange='" + exchange + '\'' +
                ", industry='" + industry + '\'' +
                ", ipo=" + ipo +
                ", logo='" + logo + '\'' +
                ", marketCapitalization=" + marketCapitalization +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                ", shareOutstanding=" + shareOutstanding +
                ", ticker='" + ticker + '\'' +
                ", weburl='" + weburl + '\'' +
                '}';
    }
}
