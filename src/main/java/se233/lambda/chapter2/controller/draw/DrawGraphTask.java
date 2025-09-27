package se233.lambda.chapter2.controller.draw;

import javafx.geometry.Insets;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import se233.lambda.chapter2.model.Currency;
import se233.lambda.chapter2.model.CurrencyEntity;

import java.util.concurrent.Callable;

public class DrawGraphTask implements Callable<VBox> { // [cite: 3447]
    Currency currency; // [cite: 3447]

    public DrawGraphTask(Currency currency) { // [cite: 3448]
        this.currency = currency;
    }

    @Override
    public VBox call() throws Exception { // [cite: 3449]
        VBox graphPane = new VBox(10); // [cite: 3449]
        graphPane.setPadding(new Insets(0, 25, 5, 25)); // [cite: 3450]
        CategoryAxis xAxis = new CategoryAxis(); // [cite: 3450]
        NumberAxis yAxis = new NumberAxis(); // [cite: 3451]
        yAxis.setAutoRanging(true); // [cite: 3451]
        LineChart lineChart = new LineChart(xAxis, yAxis); // [cite: 3451]
        lineChart.setLegendVisible(false); // [cite: 3451]
        if (this.currency != null) { // [cite: 3452]
            XYChart.Series series = new XYChart.Series(); // [cite: 3452]
            double minY = Double.MAX_VALUE; // [cite: 3452]
            double maxY = Double.MIN_VALUE; // [cite: 3453]
            for (CurrencyEntity c : currency.getHistorical()) { // [cite: 3453]
                series.getData().add(new XYChart.Data(c.getTimestamp(), c.getRate())); // [cite: 3453]
                if (c.getRate() > maxY) maxY = c.getRate(); // [cite: 3454]
                if (c.getRate() < minY) minY = c.getRate(); // [cite: 3454]
            }
            yAxis.setAutoRanging(false); // [cite: 3455]
            yAxis.setLowerBound(minY - (maxY - minY) / 2); // [cite: 3455]
            yAxis.setUpperBound(maxY + (maxY - minY) / 2); // [cite: 3455]
            yAxis.setTickUnit((maxY - minY) / 2); // [cite: 3455]
            lineChart.getData().add(series); // [cite: 3455]
        }
        graphPane.getChildren().add(lineChart); // [cite: 3456]
        return graphPane; // [cite: 3456]
    }
}
