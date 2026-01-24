module org.riendra.gudangku {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires org.xerial.sqlitejdbc;

    opens org.riendra.gudangku to javafx.fxml;
    opens org.riendra.gudangku.models to javafx.base;
    exports org.riendra.gudangku;
    exports org.riendra.gudangku.Controller;
    opens org.riendra.gudangku.Controller to javafx.fxml;
}