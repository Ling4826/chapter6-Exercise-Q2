package se233.lambda.chapter2.controller;

import se233.lambda.chapter2.Launcher;
import se233.lambda.chapter2.model.Currency;
import se233.lambda.chapter2.model.CurrencyEntity;

import java.util.ArrayList;
import java.util.List;

public class Initialize {
    public static List<Currency> initializeApp() { // [cite: 3493]
        Currency c = new Currency("USD"); // [cite: 3438]
        List<CurrencyEntity> cList = FetchData.fetchRange(Launcher.getBaseCurrency(), c.getShortCode(), 30); // [cite: 3438]
        c.setHistorical(cList); // [cite: 3438]
        c.setCurrent(cList.get(cList.size() - 1)); // [cite: 3438]
        List<Currency> currencyList = new ArrayList<>(); // [cite: 3494]
        currencyList.add(c); // [cite: 3494]
        return currencyList; // [cite: 3494]
    }
}
