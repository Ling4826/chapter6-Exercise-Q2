package se233.lambda.chapter2.controller.draw;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import se233.lambda.chapter2.controller.AllEventHandlers;
import se233.lambda.chapter2.model.Currency;
import java.util.concurrent.Callable;

public class DrawTopAreaTask implements Callable<Pane> {
    private Button watch;
    private Button unwatch;
    private Button delete;
    private Currency currency;

    public DrawTopAreaTask(Currency currency) {
        this.currency = currency;
        this.watch = new Button("Watch");
        this.unwatch = new Button("Unwatch");
        this.delete = new Button("Delete");
        this.watch.setOnAction(event -> AllEventHandlers.onWatch(currency.getShortCode()));
        this.unwatch.setOnAction(event -> AllEventHandlers.onUnwatch(currency.getShortCode()));
        this.delete.setOnAction(event -> AllEventHandlers.onDelete(currency.getShortCode()));
    }

    @Override
    public Pane call() throws Exception {
        HBox topArea = new HBox(10);
        topArea.setPadding(new Insets(5));
        topArea.getChildren().addAll(watch, unwatch, delete);
        topArea.setAlignment(Pos.CENTER_RIGHT);
        return topArea;
    }
}
