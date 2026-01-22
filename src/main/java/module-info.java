module org.riendra.gudangku {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;

    opens org.riendra.gudangku to javafx.fxml;
    exports org.riendra.gudangku;
    exports org.riendra.gudangku.Controller;
    opens org.riendra.gudangku.Controller to javafx.fxml;
}