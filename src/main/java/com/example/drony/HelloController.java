package com.example.drony;

import com.example.drony.vyroba.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class HelloController {
    @FXML private TableView<Objednavka> table;
    @FXML private TableColumn<Objednavka, String> nameCol;
    @FXML private TableColumn<Objednavka, Integer> amountCol;

    private ObservableList<Objednavka> data = FXCollections.observableArrayList();
    private Sklad sklad;
    private Vyroba vyroba;

    @FXML
    public void initialize() {
        sklad = new Sklad();
        vyroba = new Vyroba(sklad);

        // Nastavení sloupců (musí odpovídat názvům property v Objednavka)
        nameCol.setCellValueFactory(cellData -> cellData.getValue().itemProperty());
        amountCol.setCellValueFactory(cellData -> cellData.getValue().amountProperty().asObject());

        // Propojení dat
        sklad.setData(data);
        table.setItems(data);

        Platform.runLater(() -> {
            Stage stage = (Stage) table.getScene().getWindow();
            stage.setOnCloseRequest(event -> {
                // Zastavení výroby před zavřením
                onStopClick();
            });
        });

        Logger.println("SYSTEM", "START");
        vyroba.start();
    }

    private void onStopClick() {
        Logger.println("SYSTEM", "STOP");
        vyroba.stop();
    }

    @FXML
    protected void onStartClick(ActionEvent actionEvent) {
        vyroba.start();
        Logger.println("USER", "START");
    }

    @FXML
    protected void onStopClick(ActionEvent actionEvent) {
        vyroba.stop();
        Logger.println("USER", "STOP");
    }
}