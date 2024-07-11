module com.example.youtube {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.json;
    requires javafx.media;


    opens com.example.youtube to javafx.fxml;
    exports com.example.youtube;
    exports com.example.youtube.server;
    opens com.example.youtube.server to javafx.fxml;
    exports com.example.youtube.client;
    opens com.example.youtube.client to javafx.fxml;
    exports com.example.youtube.client.controllers.channels.usersChannelsControllers;
    opens com.example.youtube.client.controllers.channels.usersChannelsControllers to javafx.fxml;
    exports com.example.youtube.client.controllers.channels.ChannelsConltrollers;
    opens com.example.youtube.client.controllers.channels.ChannelsConltrollers to javafx.fxml;
    exports com.example.youtube.client.controllers.searchbarController;
    opens com.example.youtube.client.controllers.searchbarController to javafx.fxml;
    exports com.example.youtube.client.controllers.signUpLoginHomeControllers;
    opens com.example.youtube.client.controllers.signUpLoginHomeControllers to javafx.fxml;
    exports com.example.youtube.client.controllers.videoViewControllers;
    opens com.example.youtube.client.controllers.videoViewControllers to javafx.fxml;
    exports com.example.youtube.client.controllers;
    opens com.example.youtube.client.controllers to javafx.fxml;

}