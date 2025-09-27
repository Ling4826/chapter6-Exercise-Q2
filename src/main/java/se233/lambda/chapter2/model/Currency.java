package se233.lambda.chapter2.model;

import java.util.List;

public class Currency {
    private String shortCode; // [cite: 3388]
    private CurrencyEntity current; // [cite: 3388]
    private List<CurrencyEntity> historical; // [cite: 3388]
    private Boolean isWatch; // [cite: 3388]
    private Double watchRate; // [cite: 3388]

    public Currency(String shortCode) { // [cite: 3389]
        this.shortCode = shortCode; // [cite: 3389]
        this.isWatch = false; // [cite: 3389]
        this.watchRate = 0.0; // [cite: 3390]
    }

    // Getters and Setters must be added here as mentioned in the textbook
    public String getShortCode() { return shortCode; }
    public void setCurrent(CurrencyEntity current) { this.current = current; }
    public CurrencyEntity getCurrent() { return current; }
    public void setHistorical(List<CurrencyEntity> historical) { this.historical = historical; }
    public List<CurrencyEntity> getHistorical() { return historical; }
    public void setWatch(Boolean watch) { isWatch = watch; }
    public Boolean getWatch() { return isWatch; }
    public void setWatchRate(Double watchRate) { this.watchRate = watchRate; }
    public Double getWatchRate() { return watchRate; }
}