package org.riendra.gudangku.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import org.riendra.gudangku.database.Db;
import org.riendra.gudangku.models.Item;

import static org.riendra.gudangku.util.TxtFormatter.doubleFormatter;
import static org.riendra.gudangku.util.TxtFormatter.intFormatter;


public class DashboardController {
    Db db = new Db();
    String columnNameValue;

    @FXML
    Label conLabel;

    @FXML
    private TableView<Item> itemTable;
    @FXML
    private TableColumn<Item, String> nameCol;
    @FXML
    private TableColumn<Item, String> categoryCol;
    @FXML
    private TableColumn<Item, Integer> quantityCol;
    @FXML
    private TableColumn<Item, Double> priceCol;
    @FXML
    private TableColumn<Item, String> updateCol;

    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<String> cBoxCategory = new ComboBox<>();
    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtPrice;


    @FXML
    public void initialize() {
        if (Db.isConnected) {
            conLabel.setAlignment(Pos.CENTER);
            conLabel.setFont(Font.font("Poppins Medium"));
            conLabel.setText("Connected");
            conLabel.setTextFill(Color.GREEN);
        }

        // Setup columns
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        updateCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        // Load data
        loadData();
        itemTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {

                columnNameValue = newValue.getName();
                System.out.println("Selected value: " + columnNameValue);
            }
        });
        cBoxCategory.getItems().addAll("Accessories","Electronics");
        txtQuantity.setTextFormatter(intFormatter);
        txtPrice.setTextFormatter(doubleFormatter);

    }

    private void loadData() {
        ObservableList<Item> itemList = FXCollections.observableArrayList(db.getAllItems());
        itemTable.setItems(itemList);
    }


    @FXML
    private void saveBtn(){
        if (!txtName.getText().isBlank() || !cBoxCategory.getSelectionModel().isEmpty() || !txtQuantity.getText().isBlank() || !txtPrice.getText().isBlank()){
            db.insertItem(txtName.getText(),
                    cBoxCategory.getValue(),
                    Integer.parseInt(txtQuantity.getText()),
                    Double.parseDouble(txtPrice.getText())
            );

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING,"Input Field Cannot Be Empty");
            alert.showAndWait();
        }


        loadData();
    }


    @FXML
    private void deleteBtn(){

       db.deleteItem(columnNameValue);
        loadData();
    }



}
