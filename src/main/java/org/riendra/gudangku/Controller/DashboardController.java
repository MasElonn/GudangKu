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
    private Db db = new Db();
    private Item selectedItem;

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
    private Button deleteBtn;
    @FXML
    private Button updateBtn;
    @FXML
    private Button addBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;

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
                selectedItem = newValue;
                System.out.println("Selected item: " + selectedItem.getName());
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
    private void addBtn() {
        if (!txtName.getText().isBlank() && !cBoxCategory.getSelectionModel().isEmpty() && !txtQuantity.getText().isBlank() && !txtPrice.getText().isBlank()) {
            db.insertItem(txtName.getText(),
                    cBoxCategory.getValue(),
                    Integer.parseInt(txtQuantity.getText()),
                    Double.parseDouble(txtPrice.getText())
            );
            clearFields();
            loadData();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Input Field Cannot Be Empty");
            alert.showAndWait();
        }
    }


    @FXML
    private void deleteBtn() {
        if (selectedItem == null) {
            new Alert(Alert.AlertType.WARNING, "Please select an item to delete").showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this item?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            db.deleteItem(selectedItem.getId());
            loadData();
            clearFields();
        }
    }

    @FXML
    private void updateBtn() {
        if (selectedItem == null) {
            new Alert(Alert.AlertType.WARNING, "Please select an item to update").showAndWait();
            return;
        }
        setEditMode(true);
        txtName.setText(selectedItem.getName());
        cBoxCategory.setValue(selectedItem.getCategory());
        txtQuantity.setText(String.valueOf(selectedItem.getQuantity()));
        txtPrice.setText(String.valueOf(selectedItem.getPrice()));
    }

    @FXML
    private void saveBtn() {
        if (selectedItem != null) {
            db.updateItem(
                    selectedItem.getId(),
                    txtName.getText(),
                    cBoxCategory.getValue(),
                    Integer.parseInt(txtQuantity.getText()),
                    Double.parseDouble(txtPrice.getText())
            );
            loadData();
            setEditMode(false);
            clearFields();
        }
    }

    @FXML
    private void cancelBtn() {
        setEditMode(false);
        clearFields();
    }

    private void setEditMode(boolean editing) {
        addBtn.setDisable(editing);
        deleteBtn.setDisable(editing);
        updateBtn.setDisable(editing);
        saveBtn.setDisable(!editing);
        cancelBtn.setDisable(!editing);
        itemTable.setDisable(editing);
    }

    private void clearFields() {
        txtName.clear();
        cBoxCategory.getSelectionModel().clearSelection();
        txtQuantity.clear();
        txtPrice.clear();
        selectedItem = null;
        itemTable.getSelectionModel().clearSelection();
    }



}
