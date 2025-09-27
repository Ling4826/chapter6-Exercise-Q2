package se233.lambda.chapter2.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import se233.lambda.chapter2.Launcher;
import se233.lambda.chapter2.model.Currency;
import se233.lambda.chapter2.model.CurrencyEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class AllEventHandlers {
    private static final Logger logger = LogManager.getLogger(AllEventHandlers.class);
    public static void onRefresh() { //
        try {
            Launcher.refreshPane(); //
        } catch (Exception e) { // [cite: 3474]
            e.printStackTrace(); // [cite: 3474]
        }
    }

    public static void onAdd() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Add Currency");
        dialog.setContentText("Currency code:");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);

        boolean valid = false;

        while (!valid) {
            Optional<String> code = dialog.showAndWait();

            // ถ้าผู้ใช้กด "Cancel"
            if (code.isEmpty()) {
                break;
            }

            try {
                String inputCode = code.get().trim().toUpperCase();

                if (inputCode.isEmpty()) {
                    throw new IllegalArgumentException("Currency code cannot be empty.");
                }

                String base = Launcher.getBaseCurrency();
                List<CurrencyEntity> cList = FetchData.fetchRange(base, inputCode, 30);

                if (cList == null || cList.isEmpty()) {
                    throw new IllegalArgumentException("No data returned. Possibly invalid code.");
                }

                Currency c = new Currency(inputCode);
                c.setHistorical(cList);
                c.setCurrent(cList.get(cList.size() - 1));

                List<Currency> currencyList = Launcher.getCurrencyList();
                currencyList.add(c);
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
                logger.info("Added currency: {}", code.get());

                valid = true; // จบ loop
            } catch (IllegalArgumentException e) {
                showError("Invalid currency code", "The code is invalid or not supported. Please try again.");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                showError("Error", "Failed to fetch data from API.");
                break;
            } finally {
                dialog.getEditor().clear(); // ล้างช่องใส่รหัสหลังทุกครั้ง
            }
        }
    }

    private static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static void onDelete(String code) {
        try {
            List<Currency> currencyList = Launcher.getCurrencyList();
            int index = -1;
            for (int i = 0; i < currencyList.size(); i++) {
                if (currencyList.get(i).getShortCode().equals(code)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                currencyList.remove(index);
                Launcher.setCurrencyList(currencyList);
                logger.info("Deleted currency: {}", code);
                Launcher.refreshPane();
            }
        } catch (InterruptedException | ExecutionException e) { // [cite: 3533, 3534]
            e.printStackTrace(); // [cite: 3534]
        }
    }

    public static void onWatch(String code) { // [cite: 3544]
        try {
            List<Currency> currencyList = Launcher.getCurrencyList(); // [cite: 3544]
            int index = -1; // [cite: 3545]
            for (int i = 0; i < currencyList.size(); i++) { // [cite: 3545]
                if (currencyList.get(i).getShortCode().equals(code)) { // [cite: 3545]
                    index = i; // [cite: 3546]
                    break; // [cite: 3546]
                }
            }
            if (index != -1) { // [cite: 3546]
                TextInputDialog dialog = new TextInputDialog(); // [cite: 3547]
                dialog.setTitle("Add Watch"); // [cite: 3547]
                dialog.setContentText("Rate:"); // [cite: 3547]
                dialog.setHeaderText(null); // [cite: 3547]
                dialog.setGraphic(null); // [cite: 3547]
                Optional<String> retrievedRate = dialog.showAndWait(); // [cite: 3548]
                if (retrievedRate.isPresent()) { // [cite: 3548]
                    double rate = Double.parseDouble(retrievedRate.get()); // [cite: 3548]
                    currencyList.get(index).setWatch(true); // [cite: 3548]
                    currencyList.get(index).setWatchRate(rate); // [cite: 3548]
                    Launcher.setCurrencyList(currencyList); // [cite: 3549]
                    Launcher.refreshPane(); // [cite: 3549]
                }
            }
        } catch (InterruptedException | ExecutionException e) { // [cite: 3550, 3551]
            e.printStackTrace(); // [cite: 3551]
        }
    }
    public static void onUnwatch(String code) {
        try {
            List<Currency> currencyList = Launcher.getCurrencyList();
            int index = -1;
            for (int i = 0; i < currencyList.size(); i++) {
                if (currencyList.get(i).getShortCode().equals(code)) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                currencyList.get(index).setWatch(false);
                currencyList.get(index).setWatchRate(0.0); // Reset watch rate
                Launcher.setCurrencyList(currencyList);
                Launcher.refreshPane();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void onSetBaseCurrency(String base) {
        try {
            // 1. ตั้งค่า base currency ใหม่
            Launcher.setBaseCurrency(base.toUpperCase());
            List<Currency> oldList = Launcher.getCurrencyList();
            List<Currency> newList = new ArrayList<>();

            // 2. ดึงข้อมูลใหม่สำหรับทุก currency ที่มีอยู่
            for (Currency c : oldList) {
                List<CurrencyEntity> cList = FetchData.fetchRange(Launcher.getBaseCurrency(), c.getShortCode(), 30);
                c.setHistorical(cList);
                c.setCurrent(cList.get(cList.size() - 1));
                newList.add(c);
            }
            Launcher.setCurrencyList(newList);

            // 3. รีเฟรชหน้าจอ
            Launcher.refreshPane();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
