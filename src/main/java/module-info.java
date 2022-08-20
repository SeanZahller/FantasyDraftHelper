module com.example.drafthelper {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.drafthelper to javafx.fxml;
    exports com.example.drafthelper;
}