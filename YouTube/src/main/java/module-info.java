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
    exports com.example.youtube.Client.Controllers.Channels.UsersChannelsControllers;
    opens com.example.youtube.Client.Controllers.Channels.UsersChannelsControllers to javafx.fxml;
    exports com.example.youtube.Client.Controllers.Channels.ChannelsConltrollers;
    opens com.example.youtube.Client.Controllers.Channels.ChannelsConltrollers to javafx.fxml;
    exports com.example.youtube.Client.Controllers.SearchbarController;
    opens com.example.youtube.Client.Controllers.SearchbarController to javafx.fxml;
    exports com.example.youtube.Client.Controllers.SignUpLoginHomeControllers;
    opens com.example.youtube.Client.Controllers.SignUpLoginHomeControllers to javafx.fxml;
    exports com.example.youtube.Client.Controllers.VideoViewControllers;
    opens com.example.youtube.Client.Controllers.VideoViewControllers to javafx.fxml;
    exports com.example.youtube.Client.Controllers;
    opens com.example.youtube.Client.Controllers to javafx.fxml;

}