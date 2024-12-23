module map.socialnetwork {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens map.socialnetwork to javafx.fxml;
    exports map.socialnetwork;
}