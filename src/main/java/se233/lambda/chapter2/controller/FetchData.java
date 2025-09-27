package se233.lambda.chapter2.controller;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import se233.lambda.chapter2.model.CurrencyEntity;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class FetchData {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // [cite: 3334]

    public static List<CurrencyEntity> fetchRange(String base, String symbol, int N) {
        String dateEnd = LocalDate.now().format(formatter);
        String dateStart = LocalDate.now().minusDays(N).format(formatter);
        String urlStr = String.format("https://cmu.to/SE233currencyapi?base=%s&symbol=%s&start_date=%s&end_date=%s", base, symbol, dateStart, dateEnd);
        List<CurrencyEntity> histList = new ArrayList<>();

        try {
            String retrievedJson = IOUtils.toString(new URL(urlStr), Charset.defaultCharset());
            JSONObject root = new JSONObject(retrievedJson);


            if (!root.has("rates")) {
                System.err.println("⚠ API response has no 'rates': " + retrievedJson);
                throw new IllegalArgumentException("Invalid currency code or no data returned.");
            }

            JSONObject jsonOBJ = root.getJSONObject("rates");
            Iterator<String> keysToCopyIterator = jsonOBJ.keys();

            while (keysToCopyIterator.hasNext()) {
                String key = keysToCopyIterator.next();
                Double rate = Double.parseDouble(jsonOBJ.get(key).toString());
                histList.add(new CurrencyEntity(rate, key));
            }

            histList.sort(Comparator.comparing(CurrencyEntity::getTimestamp));

        } catch (MalformedURLException e) {
            System.err.println("Encountered a Malformed URL exception");
        } catch (IOException e) {
            System.err.println("Encountered an IO exception");
        } catch (IllegalArgumentException e) {
            throw e; // ส่งต่อให้ onAdd() ไปแสดง Alert
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }

        return histList;
    }

}
