module clientandserver.clientandserver {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens clientandserver.clientandserver to javafx.fxml;
    exports clientandserver.clientandserver;
}