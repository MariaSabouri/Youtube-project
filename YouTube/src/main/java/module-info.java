module com.example.youtube {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;


    opens com.example.youtube to javafx.fxml;
    exports com.example.youtube;
}