package se233.lambda.chapter2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import se233.lambda.chapter2.controller.Initialize;
import se233.lambda.chapter2.controller.RefreshTask;
import se233.lambda.chapter2.model.Currency;
import se233.lambda.chapter2.view.CurrencyParentPane;
import se233.lambda.chapter2.view.TopPane;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class Launcher extends Application {
    private static Stage primaryStage; //
    private static FlowPane mainPane; // [cite: 3441]
    private static TopPane topPane; // [cite: 3441]
    private static CurrencyParentPane currencyParentPane; // [cite: 3499]
    private static List<Currency> currencyList; // [cite: 3500]
    private static String baseCurrency = "THB";
    public static void setBaseCurrency(String baseCurrency) { Launcher.baseCurrency = baseCurrency; } // Setter
    public static String getBaseCurrency() { return baseCurrency; } // Getter
    @Override
    public void start(Stage stage) throws ExecutionException, InterruptedException { // [cite: 3501]
        primaryStage = stage; // [cite: 3386]
        primaryStage.setTitle("Currency Watcher"); // [cite: 3386]
        primaryStage.setResizable(false); // [cite: 3387]
        currencyList = Initialize.initializeApp(); // [cite: 3502]
        initMainPane(); // [cite: 3443]
        Scene mainScene = new Scene(mainPane); // [cite: 3444]
        primaryStage.setScene(mainScene); // [cite: 3444]
        primaryStage.show(); // [cite: 3387]
        RefreshTask r = new RefreshTask(); // [cite: 3574]
        Thread th = new Thread(r); // [cite: 3574]
        th.setDaemon(true); // [cite: 3574]
        th.start(); // [cite: 3574]
    }

    public void initMainPane() throws ExecutionException, InterruptedException { // [cite: 3503]
        mainPane = new FlowPane(); // [cite: 3445]
        topPane = new TopPane(); // [cite: 3446]
        currencyParentPane = new CurrencyParentPane(currencyList); // [cite: 3504]
        mainPane.getChildren().add(topPane); // [cite: 3446]
        mainPane.getChildren().add(currencyParentPane); // [cite: 3505]
    }
    public static void main(String[] args) {
        launch(args);
    }
    public static void refreshPane() throws InterruptedException, ExecutionException { // [cite: 3472]
        topPane.refreshPane();
        currencyParentPane.refreshPane(currencyList);
        primaryStage.sizeToScene();
    }

    public static List<Currency> getCurrencyList() { return currencyList; }
    public static void setCurrencyList(List<Currency> currencyList) { Launcher.currencyList = currencyList; }
}