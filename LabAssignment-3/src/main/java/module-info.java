module com.example.labassignment3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.labassignment3 to javafx.fxml;
    exports com.example.labassignment3;
}