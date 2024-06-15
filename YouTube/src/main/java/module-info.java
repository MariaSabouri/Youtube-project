module com.example.youtube {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.youtube to javafx.fxml;
    exports com.example.youtube;
}