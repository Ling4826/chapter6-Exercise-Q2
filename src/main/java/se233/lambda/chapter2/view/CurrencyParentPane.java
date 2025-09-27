package se233.lambda.chapter2.view;

import javafx.geometry.Insets;
import javafx.scene.layout.FlowPane;
import se233.lambda.chapter2.model.Currency;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CurrencyParentPane extends FlowPane { // [cite: 3495]
    public CurrencyParentPane(List<Currency> currencyList) throws ExecutionException, InterruptedException { // [cite: 3495]
        this.setPadding(new Insets(0)); // [cite: 3496]
        refreshPane(currencyList); // [cite: 3496]
    }

    public void refreshPane(List<Currency> currencyList) throws ExecutionException, InterruptedException { // [cite: 3496]
        this.getChildren().clear(); // [cite: 3497]
        for (int i = 0; i < currencyList.size(); i++) { // [cite: 3497]
            CurrencyPane cp = new CurrencyPane(currencyList.get(i)); // [cite: 3497]
            this.getChildren().add(cp); // [cite: 3497]
        }
    }
}
