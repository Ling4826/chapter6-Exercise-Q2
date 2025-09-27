package se233.lambda.chapter2.model;

public class CurrencyEntity {
    private Double rate; // [cite: 3338]
    private String date; // [cite: 3339]

    public CurrencyEntity(Double rate, String date) { // [cite: 3339]
        this.rate = rate; // [cite: 3340]
        this.date = date; // [cite: 3340]
    }

    public Double getRate() { // [cite: 3340]
        return rate;
    }

    public String getTimestamp() { // [cite: 3342]
        return date;
    }

    @Override
    public String toString() { // [cite: 3342]
        return String.format("%s %.4f", date, rate);
    }
}
