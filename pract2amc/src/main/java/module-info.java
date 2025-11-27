module com.pract2amc {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    opens com.pract2amc to javafx.fxml;
    exports com.pract2amc;
}