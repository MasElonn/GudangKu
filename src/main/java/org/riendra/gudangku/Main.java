package org.riendra.gudangku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.riendra.gudangku.database.Db;

public class Main extends Application {
    Db db = new Db();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("views/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 700, 500);
        stage.setTitle("GudangKu");
        stage.setScene(scene);
        stage.show();
        db.connect();
    }

    public static void main(String[] args) {

        launch();
        
    }
}