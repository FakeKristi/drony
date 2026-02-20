module com.example.drony {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.drony to javafx.fxml;
    exports com.example.drony;
}