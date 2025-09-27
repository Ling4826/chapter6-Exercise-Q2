package se233.lambda.chapter2.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import se233.lambda.chapter2.controller.AllEventHandlers;
import se233.lambda.chapter2.controller.draw.DrawGraphTask;
import se233.lambda.chapter2.controller.draw.DrawInfoPaneTask;
import se233.lambda.chapter2.controller.draw.DrawTopAreaTask;
import se233.lambda.chapter2.model.Currency;

import java.util.concurrent.*;

public class CurrencyPane extends BorderPane {
    private Currency currency; //
    private Button watch; // [cite: 3404]
    private Button delete; // [cite: 3535]
    private Button unwatch;

    public CurrencyPane(Currency currency) {
        this.setPadding(new Insets(0));
        this.setPrefSize(640, 300);
        this.setStyle("-fx-border-color: black");
        try {
            this.refreshPane(currency);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshPane(Currency currency) throws ExecutionException, InterruptedException { // [cite: 3464]
        this.currency = currency;

        ExecutorService executor = Executors.newFixedThreadPool(3);


        Future<Pane> futureInfoPane = executor.submit(new DrawInfoPaneTask(currency));
        Future<Pane> futureTopArea = executor.submit(new DrawTopAreaTask(currency));
        Future<VBox> futureGraph = executor.submit(new DrawGraphTask(currency));


        this.setLeft(futureInfoPane.get());
        this.setTop(futureTopArea.get());
        this.setCenter(futureGraph.get());


        executor.shutdown();
    }

    private Pane genInfoPane() { // [cite: 3408]
        VBox currencyInfoPane = new VBox(10); // [cite: 3409]
        currencyInfoPane.setPadding(new Insets(5, 25, 5, 25)); // [cite: 3409]
        currencyInfoPane.setAlignment(Pos.CENTER); // [cite: 3409]
        Label exchangeString = new Label(""); // [cite: 3410]
        Label watchString = new Label(""); // [cite: 3410]
        exchangeString.setStyle("-fx-font-size: 20;"); // [cite: 3410]
        watchString.setStyle("-fx-font-size: 14;"); // [cite: 3411]
        if (this.currency != null) { // [cite: 3411]
            exchangeString.setText(String.format("%s: %.4f", this.currency.getShortCode(), this.currency.getCurrent().getRate())); // [cite: 3411]
            if (this.currency.getWatch() == true) { // [cite: 3412]
                watchString.setText(String.format("(Watch @%.4f)", this.currency.getWatchRate())); // [cite: 3412]
            }
        }
        currencyInfoPane.getChildren().addAll(exchangeString, watchString); // [cite: 3412]
        return currencyInfoPane; // [cite: 3413]
    }

    private HBox genTopArea() { // [cite: 3413]
        HBox topArea = new HBox(10); // [cite: 3414]
        topArea.setPadding(new Insets(5)); // [cite: 3414]
        topArea.getChildren().addAll(watch, delete); // [cite: 3539]
        ((HBox) topArea).setAlignment(Pos.CENTER_RIGHT); // [cite: 3414]
        return topArea; // [cite: 3414]
    }
}
