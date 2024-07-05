module com.example.youtube {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;
    requires javafx.media;


    opens com.example.youtube to javafx.fxml;
    exports com.example.youtube;
    exports com.example.youtube.Server;
    opens com.example.youtube.Server to javafx.fxml;
    exports com.example.youtube.Client;
    opens com.example.youtube.Client to javafx.fxml;
//    exports com.example.youtube.Client.Controllers;
//    opens com.example.youtube.Client.Controllers to javafx.fxml;
}