module se233.lambda.chapter2 {
    requires javafx.controls;
    requires javafx.fxml;

    // Add these two lines
    requires org.apache.commons.io;
    requires org.json;

    opens se233.lambda.chapter2 to javafx.fxml;
    exports se233.lambda.chapter2;
    // Potentially other exports/opens for your specific project structure
}