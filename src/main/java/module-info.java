module org.example.java_project_iii {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires com.dlsc.formsfx;
    requires java.desktop;

    opens org.example.java_project_iii to javafx.fxml;
    exports org.example.java_project_iii;
}