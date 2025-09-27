package se233.lambda.chapter2.controller;
import javafx.scene.control.Alert;
import se233.lambda.chapter2.Launcher;
import se233.lambda.chapter2.model.Currency;

import java.util.List;
import java.util.concurrent.Callable;

class WatchTask implements Callable<Void> { // [cite: 3580]
    @Override
    public Void call() { // [cite: 3580]
        List<Currency> allCurrency = Launcher.getCurrencyList(); // [cite: 3580]
        String found = ""; // [cite: 3581]
        for (int i = 0; i < allCurrency.size(); i++) { // [cite: 3581]
            if (allCurrency.get(i).getWatchRate() != 0 && allCurrency.get(i).getWatchRate() > allCurrency.get(i).getCurrent().getRate()) { // [cite: 3581]
                if (found.equals("")) { // [cite: 3581]
                    found = allCurrency.get(i).getShortCode(); // [cite: 3582]
                } else { // [cite: 3582]
                    found = found + " and " + allCurrency.get(i).getShortCode(); // [cite: 3582]
                }
            }
        }
        if (!found.equals("")) { // [cite: 3583]
            Alert alert = new Alert(Alert.AlertType.WARNING); // [cite: 3584]
            alert.setTitle(null); // [cite: 3584]
            alert.setHeaderText(null); // [cite: 3584]
            if (found.length() > 3) { // [cite: 3584]
                alert.setContentText(String.format("%s have become lower than the watch rate!", found)); // [cite: 3585]
            } else { // [cite: 3585]
                alert.setContentText(String.format("%s has become lower than the watch rates!", found)); // [cite: 3586]
            }
            alert.showAndWait(); // [cite: 3586]
        }
        return null; // [cite: 3586]
    }
}
